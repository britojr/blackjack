import java.util.ArrayList;
import java.util.Scanner;

public class Poker
{
	private ArrayList< Player > players;//lista de jogadores
	private ArrayList< Card > table;	//cartas que ficam na mesa
	private Player player;
	private Deck deck;
	private int nPlayers;
	private int dealer;
	private int smallBlind;
	private int bigBlind;
	private int pot;
	private int punters;				//numero de jogadores que pagaram a aposta
	
	public Poker(int nPlayers, int smallBlind, int bigBlind)
	{
		players = new ArrayList< Player >();
		table = new ArrayList< Card >();
		deck = new Deck();
		this.nPlayers = nPlayers;
		dealer = 0;
		this.smallBlind = smallBlind;	//(?) dealer +1 % n
		this.bigBlind = bigBlind;		//(?) dealer +3 % n
	}
	
	public int numberOfPlayers()
	{
		return (players.size());
	}
	
	public void connect(Player p)
	{
		
		if (players.size() < nPlayers)
		{
			if (p.getCash() > bigBlind)
			{
				players.add(p);
			}
			else
			{
				System.out.println("Voce nao possui dinheiro suficiente para a aposta minima!");
			}
		}
		
		if (players.size() == nPlayers)
		{
			start();
			//System.out.println("A mesa esta cheia!");
		}
	}
	
	public void start()
	{
		ArrayList< Player > currentPlayers = new ArrayList< Player >();	//lista de jogadores que estão na rodada
		ArrayList< Integer > currentBet = new ArrayList< Integer >();	//valor apostado por cada jogador que está na rodade
		int maxBet = bigBlind;											//aposta da mesa
		
		while (players.size() == nPlayers)
		{
			System.out.printf("\n\n\n\n\n\n\n\n\n\n");
			System.out.println("          COMECANDO A RODADA");
			pot = 0;
			
			//distribuindo as cartas
			dealingCards(currentPlayers);
			//rodada de apostas antes de virar o flop
			maxBet = preFlop(currentPlayers, currentBet, maxBet);
			
			if (currentPlayers.size() != 1)
			{
				punters = 0;
				
				//virando as 3 primeiras cartas
				flop();
				//rodada de apostas
				maxBet = betting(currentPlayers, currentBet, maxBet);
				
				if (currentPlayers.size() != 1)
				{
					punters = 0;
					
					//virando a 4ª carta
					turn();
					//rodada de apostas
					maxBet = betting(currentPlayers, currentBet, maxBet);
				
					if (currentPlayers.size() != 1)
					{
						punters = 0;
						
						//virando a 5ª carta
						river();
						//rodada de apostas
						maxBet = betting(currentPlayers, currentBet, maxBet);
						
						if (currentPlayers.size() != 1)
						{
							//determinar o vencedor!
						}
					}
				}
			}
			
			for (Player p: players)
			{
				p.quit();
			}
		}
	}
	
	private void dealingCards(ArrayList< Player > currentPlayers)
	{
		//variável que receberá as duas cartas a serem passadas aos jogadores
		ArrayList< Card > hand = new ArrayList< Card >();
		//posição do apostador
		int pos;
		
		System.out.println("________________________________________________________________________________");
		System.out.println("    DISTRIBUINDO AS CARTAS");
		
		//embaralhando as cartas
		deck.shuffle();
		
		//determinando o primeiro apostardor a receber as cartas
		pos = dealer;
		
		//distribuindo as cartas
		for (int i = 0; i < players.size(); i++)
		{
			//apaga as cartas da variável hand
			hand.clear();
			
			//coloca duas cartas na variável hand
			hand.add(deck.getCard());
			hand.add(deck.getCard());
			
			//passa as cartas ao jogador
			player = players.get(pos);
			player.addCards(hand);
			
			//adiciona o jogador na lista de jogadores atualmente na rodada
			currentPlayers.add(player);
			
			//calcula a posição do próximo jogador
			pos = (pos + 1) % players.size();
		}
	}
	
	private int preFlop(ArrayList< Player > currentPlayers, ArrayList< Integer > currentBet, int maxBet)
	{
		int i;
		punters = 0;
		
		System.out.println("________________________________________________________________________________");
		System.out.println("    RODADA DE APOSTAS");
		
		//primeira rodada de apostas
		for (i = 0; i < players.size(); i++) 
		{
			player = currentPlayers.get(i);	//seleciona o primeiro jogador a apostar
			
			if (i > 1) 
			{
				currentBet.add( player.bet("PRE-FLOP",maxBet) ); //aposta do jogador
				
				//se a aposta tiver um valor negativo, o jogador é excluido da rodada
				if (currentBet.get(i) < 0) 
				{
					currentPlayers.remove(i);
					currentBet.remove(i);
				}
				else 
				{
					if (currentBet.get(i) > maxBet) //aumentando a aposta
					{ 
						maxBet = currentBet.get(i);
						punters = 0;
					}
					pot += currentBet.get(i); //atualiza o valor do pote
					punters++;
				}
			}
			else 
			{
				if (i == 1) 
				{
					currentBet.add( player.blind("PRE-FLOP",bigBlind) );
					punters++;
				}
				else 
				{
					currentBet.add( player.blind("PRE-FLOP",smallBlind) );
				}
				pot += currentBet.get(i); 
			}
		}
		
		i = 0;
		while ( punters < currentPlayers.size() ) 
		{
			int newBet = maxBet-currentBet.get(i); //diferença do que falta o jogador pagar
			
			player = currentPlayers.get(i); //jogador a apostar
			newBet = player.bet("PRE-FLOP", newBet); //nova aposta
			
			if (newBet < 0) //jogador nao pagou a aposta, é removido
			{ 
				currentPlayers.remove(i);
				currentBet.remove(i);
				if (i == 0)
				{
					i = currentPlayers.size() - 1;
				}
				else
				{
					i--;
				}
			}
			else //jogador pagou a aposta
			{ 
				int total = newBet + currentBet.get(i);
				currentBet.set(i,total);
				
				if (total > maxBet) //jogador aumentou a aposta
				{ 
					maxBet = total;
					punters = 0;
				}
				pot += newBet; //atualiza o pote
				punters++;
			}
			
			System.out.println("MaxBet: " + maxBet + " | currentBet: " + currentBet.get(i));
			
			//posição do próximo apostador
			i = ( (i + 1) % currentPlayers.size() );
		}
		
		return (maxBet);
	}
	
	private int betting(ArrayList< Player > currentPlayers, ArrayList< Integer > currentBet, int maxBet)
	{
		System.out.println("________________________________________________________________________________");
		System.out.println("    RODADA DE APOSTAS");
		
		//ciclo de apostas até que os jogadores pagem uma aposta ou que sobre apenas um jogador
		int i = dealer + 2;
		while ( punters < currentPlayers.size() ) 
		{
			int newBet = maxBet-currentBet.get(i); //diferença do que falta o jogador pagar
			
			player = currentPlayers.get(i); //jogador a apostar
			newBet = player.bet("FLOP", newBet); //nova aposta
			
			if (newBet < 0) //jogador nao pagou a aposta, é removido
			{ 
				currentPlayers.remove(i);
				currentBet.remove(i);
				if (i == 0)
				{
					i = currentPlayers.size() - 1;
				}
				else
				{
					i--;
				}
			}
			else //jogador pagou a aposta
			{ 
				int total = newBet + currentBet.get(i);
				currentBet.set(i,total);
				
				if (total > maxBet) //jogador aumentou a aposta
				{ 
					maxBet = total;
					punters = 0;
				}
				pot += newBet; //atualiza o pote
				punters++;
			}
			
			//posição do próximo apostador
			i = ( (i + 1) % currentPlayers.size() );
		}
		
		return (maxBet);
	}
	
	private void flop()
	{
		System.out.println("________________________________________________________________________________");
		System.out.println("    FLOP");
		
		//descartando 2 cartas
		deck.addCard( deck.getCard() );
		deck.addCard( deck.getCard() );
		
		//colocando 3 cartas na mesa
		for (int i = 0; i < 3; i++)
			table.add(deck.getCard());
		
		//mostrando as cartas
		showCards();
	}
	
	private void turn()
	{
		System.out.println("________________________________________________________________________________");
		System.out.println("    TURN");
		
		//descartando 1 carta
		deck.addCard( deck.getCard() );
		
		//colocando 1 carta na mesa
		table.add(deck.getCard());
		
		//mostrando as cartas
		showCards();
	}
	
	private void river()
	{
		System.out.println("________________________________________________________________________________");
		System.out.println("    RIVER");
		
		turn();
	}
	
	private void showCards()
	{
		Card card;
		
		for (int i = 0; i < table.size(); i++)
		{
			card = table.get(i);
			System.out.print("   " + card );
		}
		System.out.println();
	}
	
	public static void main (String args[])
	{
		int cash;
		String name;
		Player player;
		Poker poker = new Poker(3, 10, 20);
		Scanner input = new Scanner( System.in );
		
		for (int i=1; i<4; i++)
		{
			name = "PLAYER "+i;
			cash = 200;
			player = new Player(name, cash);
			
			//Verificar a conecao dos jogadores
			System.out.println("Jogador " + name + " conectando ao jogo...");
			if (poker.numberOfPlayers() == 3-1) System.out.println();
			
			poker.connect(player);
		}
		
		
	}
}