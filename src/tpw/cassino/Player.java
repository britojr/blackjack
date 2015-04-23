package tpw.cassino;

import java.util.ArrayList;

/**
 * Esta classe define o objeto Player, que representa um jogador.
 */
public class Player {

    private String name;
    private int cash;
    private ArrayList<Card> cards;
    private boolean itsTurn;
    private int sum;
    private int id;
    private int bet;
    private String status = "";

    /**
     * Construtor da classe
     *
     * @param name representa o nome do jogador
     * @param cash representa o valor em dinheiro do jogador
     */
    public Player(String name, int cash) {
        this.name = name;
        this.cash = cash;
        itsTurn = false;
        cards = new ArrayList<Card>();
        bet = 0;
    }

    public String getStatus() {
        return (status);
    }

    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * Retorna o nome do jogador
     *
     * @return nome do jogador
     */
    public String getName() {
        return (name);
    }

    public int getSum() {
        return (sum);
    }

    /**
     * Retorna o valor em dinheiro do jogador
     *
     * @return valor em dinheiro do jogador
     */
    public int getCash() {
        return (cash);
    }

    public void setTurn(boolean turn) {
        itsTurn = turn;
    }

    public void setSum(int soma) {
        sum = soma;
    }

    public boolean getTurn() {
        return (itsTurn);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getBet() {
        return bet;
    }

    public void setBet(int bet) {
        this.bet = bet;
    }

    /**
     * Retorna as cartas que estão na posse do jogador
     *
     * @return cartas que estão com o jogador
     */
    public ArrayList<Card> getCards() {
        return (cards);
    }

    /**
     * Adiciona uma valor ao dinheiro do jogador
     *
     * @param cash quantia a ser adicionada ao dinheiro do joagdor
     */
    public void addCash(int cash) {
        this.cash += cash;
    }

    public void subCash(int cash) {
        this.cash -= cash;
    }

    /**
     * Acrescenta uma carta á mão do jogador
     *
     * @param card uma carta
     */
    public void addOneCard(Card card) {
        cards.add(card);
    }

    /*
     * Decrementa o valor da aposta, retorna 0 caso o valor seja inválido
     *
     */
    public int placeBet(int bet) {
        if (bet > 0) {
            if (bet > cash) {
                bet = cash;
            }
        } else {
            bet = 0;
        }
        this.bet = bet;
        return bet;
    }

    //Limpa a mao do player
    public void cleanHand() {
        cards.clear();
        sum = 0;
    }

    /**
     * Método toString padrão
     */
    @Override
    public String toString() {
        Card c1, c2;

        c1 = cards.get(0);
        c2 = cards.get(1);

        return ("Name: " + name + " | Cash: " + cash + " | Mao: " + c1 + ", " + c2);
    }

    /* Método que testa a classe */
    public static void main(String args[]) {
        Player p = new Player("Artur", 200);
        Card c1 = new Card("A", "C");
        Card c2 = new Card("3", "P");
        ArrayList<Card> cards = new ArrayList<Card>();
        ArrayList<Card> cards2;

        cards.add(c1);
        cards.add(c2);

        p.addOneCard(c1);
        p.addOneCard(c2);
        cards.clear();
        System.out.println(p);

    /*		System.out.println();
    cards2 = p.getCards();
    c1 = cards2.get(1);
    c2 = cards2.get(0);
    System.out.println("Mao: " + c1 + ", " + c2);

    cards.add(c1);
    cards.add(c2);
    p.addCards(cards);
    System.out.println(p);
     */
    }
}

