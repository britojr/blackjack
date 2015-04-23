package tpw.cassino;

import java.util.Map;
import java.util.Iterator;
import java.util.HashMap;
import java.util.Set;
import java.util.ArrayList;

public class BlackJackRoom {

    public static final int minPlayers = 1;  //numero minimo de jogadores para poder comecar o jogo
    private String name;
    //broke: 0   normal: 1   blackjack: 2
    private static final int broke = 0,  normal = 1,  blackjack = 2;
    private Map players = new HashMap();
    private Map watchers = new HashMap();
    private Deck deck;
    private ArrayList<Card> table;	//cartas que ficam na mesa
    private boolean running;            //status do jogo
    private boolean betting;
    private int playerturn;         //id do jogador que deve jogar agora
    private int watcherturn;         //
    private int watcherCount;         //
    private int round;               //rodada atual
    private int tableSum;
    private int soft;

    public BlackJackRoom(String name) {
        this.name = name;
        watchers = new HashMap();
        table = new ArrayList();
        players = new HashMap();
        deck = new Deck();
        running = false;
        betting = false;
    }

    public synchronized void startBetting() {
        players.clear();
        round = -1;
        if (watchers.size() > 0) {
            watcherCount = watchers.size();
            Object[] watchersArray = watchers.values().toArray();
            watcherturn = 0;
            ((Player) watchersArray[watcherturn]).setTurn(true);

            betting = true;
        }
    }

    public synchronized void placeBet(Player watcher, int bet) {
        if (watcher.getTurn()) {
            watcher.setTurn(false);
            if (watcher.placeBet(bet) > 0) {
                watcher.setId(players.size());
                watcher.cleanHand();
                players.put(watcher.getId(), watcher);
            }
            watcherturn++;
            //if(watcherturn>=watchers.size()){
            if (watcherturn >= watcherCount) {
                betting = false;
                running = true;
                round = 0;
                watcherturn = -1;
            } else {
                Object[] watchersArray = watchers.values().toArray();
                ((Player) watchersArray[watcherturn]).setTurn(true);
            }
        }
    }

    public synchronized void startGame() {
        table.clear();
        deck = new Deck();
        deck.shuffle();
        //upcard
        table.add(deck.getCard());
        for (int i = 0; i < players.size(); i++) {
            ((Player) players.get(i)).addOneCard(deck.getCard());
            playerHit(((Player) players.get(i)));
        }
        if (players.size() < minPlayers) {
            table.clear();
            deck = new Deck();
            running = false;
            return;
        //("Nao ha jogadores suficientes");
        }
        round = 1;
        playerturn = 0;
        //running = true;
        ((Player) players.get(playerturn)).setTurn(true);
    }

    private int countCardValues(ArrayList<Card> cards) {
        String card = null;
        int j = 0;
        int value = 0;
        int aces = 0;
        for (int i = 0; i < cards.size(); i++) {
            card = cards.get(i).getValue();
            if (card.equals("K") || card.equals("J") || card.equals("Q")) {
                value += 10;
            } else if (card.equals("A")) {
                value += 11;
                aces++;
            } else {
                value += Integer.parseInt(card);
            }
        }
        if (value > 21 && aces > 0) {
            for (j = 0; j < aces; j++) {
                value -= 10;
                if (value <= 21) {
                    break;
                }
            }
        }
        soft = aces - j;
        return value;
    }

    public void playerHit(Player player) {
        player.addOneCard(deck.getCard());
        player.setSum(countCardValues(player.getCards()));
    }

    public void playerStand(Player player) {
        nextTurn(player);
    }

    private void nextTurn(Player player) {
        player.setTurn(false);
        playerturn++;
        if (playerturn >= players.size()) {
            round = 2;
            tableHit();
        } else {
            ((Player) players.get(playerturn)).setTurn(true);
        }
    }

    //Hole Card
    public void tableHit() {
        table.add(deck.getCard());
        tableSum = countCardValues(table);
    }

    public void tableTurn() {
        while (tableSum < 17 || (soft > 0 && tableSum < 21)) {
            tableHit();
        }
        round = 3;
    }

    private int handStatus(int size, int sum) {
        if (sum > 21) {
            return broke;
        }
        if (sum < 21) {
            return normal;
        }
        if (size == 2) {
            return blackjack;
        }
        return normal;
    }

    public void evaluateGame() {
        Player player;
        int tableHand = handStatus(table.size(), tableSum);
        for (int i = 0; i < players.size(); i++) {
            player = (Player) players.get(i);
            int playerHand = handStatus(player.getCards().size(), player.getSum());

            if (tableHand == normal && playerHand == normal) {
                int value = player.getSum() - tableSum;
                if (value > 0) {
                    //player wins 2x
                    player.addCash(player.getBet());
                    player.setStatus("Voce ganhou $" + player.getBet() + ".");
                } else if (value < 0) {
                    //player loses
                    player.subCash(player.getBet());
                    player.setStatus("Voce perdeu $" + player.getBet() + ".");
                } else {
                    //tie
                    //player.addCash(player.getBet());
                    player.setStatus("Voce empatou com a mesa.");
                }
            } else if (tableHand > playerHand) {
                //player loses
                player.subCash(player.getBet());
                player.setStatus("Voce perdeu $" + player.getBet() + ".");
            } else if (tableHand == playerHand) {
                //tie
                //player.addCash(player.getBet());
                player.setStatus("Voce empatou com a mesa.");
            } else if (tableHand < playerHand) {
                if (playerHand == blackjack) {
                    //player wins 3x
                    player.addCash(2 * player.getBet());
                    player.setStatus("Voce ganhou $" + (2 * player.getBet()) + ".");
                } else {
                    //player wins 2x
                    player.addCash(player.getBet());
                    player.setStatus("Voce ganhou $" + player.getBet() + ".");
                }
            }
            player.setBet(0);
        }
        round = 3;
    }

    public void finishGame() {

        running = false;
        betting = false;
        tableSum = 0;
        round = -1;
        //clean hand
        for (int i = 0; i < players.size(); i++) {
            ((Player) players.get(i)).cleanHand();
        }
        //players =  new HashMap();
        players.clear();
        deck = new Deck();
    }

    public boolean gameIsRunnig() {
        return running;
    }

    public ArrayList<Card> getCards() {
        return (table);
    }

    public String getName() {
        return name;
    }

    public int getRound() {
        return round;
    }

    public boolean getBetting() {
        return betting;
    }

    public int getSum() {
        return tableSum;
    }

    public synchronized void addWatcher(Player watcher) {
        watchers.put(watcher.getName(), watcher);
    }

    //(!!)
    public synchronized Object removeWatcher(String watcherName) {
        //...
        Player player = (Player) players.get(watcherName);
        if (player != null) {
            player.setSum(1000);
        }
        return watchers.remove(watcherName);
    }

    public Player getWatcher(String watcherName) {
        return (Player) watchers.get(watcherName);
    }

    public boolean watcherExists(String watcherName) {
        return watchers.containsKey(watcherName);
    }

    public boolean isPlaying(String playerName) {
        Player player = (Player) watchers.get(playerName);
        if (player != null) {
            return players.containsKey(player.getId());
        }
        return false;
    }

    public int getNoOfWatchers() {
        return watchers.size();
    }

    public int getNoOfPlayers() {
        return players.size();
    }

    public Player getCurrentPlayer() {
        if (playerturn < players.size()) {
            /*Object[] playersArray = players.values().toArray();
            return (Player)playersArray[playerturn];*/
            return (Player) players.get(playerturn);
        } else {
            return null;
        }
    }

    //(!!)
    public String getCurrentPlayerName() {
        if (betting) {
            //if (watcherturn < watchers.size()) {
            if (watcherturn < watcherCount) {
                Object[] watchersArray = watchers.values().toArray();
                if (watchersArray[watcherturn] == null) {
                    return "Erro tipo 2";
                }
                if (((Player) watchersArray[watcherturn]).getTurn()) {
                    return (((Player) watchersArray[watcherturn]).getName());
                }
                return "Erro tipo 1";
            } else {
                return "Mesa";
            }
        } else {
            if (playerturn < players.size()) {
                /*Object[] playersArray = players.values().toArray();
                if( ((Player)playersArray[playerturn]).getTurn()){*/
                if (((Player) players.get(playerturn)).getTurn()) {
                    return (((Player) players.get(playerturn)).getName());
                }
                return "Erro tipo 1";
            } else {
                return "Mesa";
            }
        }
    }

    public Set getWatchers() {
        return watchers.entrySet();
    }

    public Player[] getWatchersArray() {
        Player[] watchersArray = new Player[watchers.size()];
        Set watchersSet = this.getWatchers();
        Iterator watchersit = watchersSet.iterator();
        int i = 0;
        while (watchersit.hasNext()) {
            Map.Entry me = (Map.Entry) watchersit.next();
            watchersArray[i] = (Player) me.getValue();
            i++;
        }
        return watchersArray;
    }
}
