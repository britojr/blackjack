import java.util.ArrayList;
import java.util.Scanner;

/**
 * Esta classe define o objeto Player, que representa um jogador.
 *
 * @author Artur Lovato da Cunha
 * @version 1.0
 */
public class Player
{
	private int bet;
	private Scanner input;
	
	private String name;
	private int cash;
	private ArrayList< Card > cards;
	
	/**
	 * Construtor da classe
	 * 
	 * @param name representa o nome do jogador
	 * @param cash representa o valor em dinheiro do jogador
	 */
	public Player( String name, int cash )
	{
		input = new Scanner( System.in );
		
		this.name = name;
		this.cash = cash;
		cards = new ArrayList< Card >();
	}
	
	/**
	 * Retorna o nome do jogador
	 * 
	 * @return nome do jogador
	 */
	public String getName()
	{
		return (name);
	}
	
	/**
	 * Adiciona uma valor ao dinheiro do jogador
	 * 
	 * @param cash quantia a ser adicionada ao dinheiro do joagdor
	 */
	public void addCash(int cash)
	{
		this.cash += cash;
	}
	
	/**
	 * Retorna o valor em dinheiro do jogador
	 * 
	 * @return valor em dinheiro do jogador
	 */
	public int getCash()
	{
		return (cash);
	}
	
	/**
	 * Passa as cartas ao jogador
	 * 
	 * @param hand mão de cartas do jogador
	 */
	public void addCards(ArrayList< Card > hand)
	{
		cards.clear();
		cards.add( hand.remove(0) );
		cards.add( hand.remove(0) );
	}
	
	/**
	 * Retorna as cartas que estão na posse do jogador
	 * 
	 * @return cartas que estão com o jogador
	 */
	public ArrayList< Card > getCards()
	{
		return( cards );
	}
	
	/**
	 * Retorna as cartas que estão com o jogador para serem analisadas
	 * 
	 * @return cartas que estão com o jogador
	 */
	public ArrayList< Card > showdown()
	{
		return( cards );
	}
	
	/**
	 * Retira o valor do jodador e retorna-o. Método para o small e o big blind
	 * 
	 * @param baseBet valor a ser deduzido do montante do jogador
	 * @return valor deduzido do montante do jogador
	 */
	public int blind(String round, int baseBet)
	{
		topBet(round, baseBet);
		cash -= baseBet;
		bottomBet(round);
		return (baseBet);
	}
	
	/**
	 * Jogador faz a aposta maior ou igual à aposta base
	 * 
	 * @param round nome do round que está sendo disputado
	 * @param baseBet aposta base que está correndo na mesa
	 *
	 * @return valor da aposta do jogador
	 */
	public int bet(String round, int baseBet)
	{
		topBet(round, baseBet);
		System.out.print( "= Digite a sua aposta (ou -1 para desistir): " );
		bet = input.nextInt();
		bottomBet(round);
		
		if (bet < baseBet)
		{
			System.out.println("Valor inferior a aposta minima! Voce esta fora da rodada.");
			bet = -1;
		}
		else 
		{
			if (bet > cash) 
			{
				System.out.println("Valor superior ao seu limite! ALL-IN automatico.");
				bet = cash;
			}
			cash -= bet;
		}
		
		return (bet);
	}
	
	/**
	 * Jogador decide se continua no jogo
	 *
	 * @return true para sair do jogo, false para continuar
	 */
	public boolean quit()
	{
		topQuit();
		bet = input.nextInt();
		bottomQuit();
		
		if (bet == 0)
		{
			return (false);
		}
		
		return (true);
	}
	
	/**
	 * Método toString padrão
	 */
	public String toString()
	{
		Card c1,c2;
		
		c1 = cards.get(0);
		c2 = cards.get(1);
		
		return ( "Name: " + name + " | Cash: " + cash + " | Mao: " + c1 + ", " + c2 );
	}
	
	/* Método que testa a classe */
	public static void main( String args[] )
	{
		Player p = new Player("Artur",200);
		Card c1 = new Card("A","C");
		Card c2 = new Card("3","P");
		ArrayList< Card > cards = new ArrayList< Card >();
		ArrayList< Card > cards2;
		
		cards.add(c1);
		cards.add(c2);
		
		p.addCards(cards);
		cards.clear();
		p.bet("PRE-FLOP",10);
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
	
	private void topBet(String round, int baseBet)
	{
		Card card;
		
		//monitoramento via terminal
		System.out.println();
		System.out.println( "=============== " + name + " =============== " + round + " ===============" );
		card = cards.get(0);
		System.out.print( "= " + card );
		card = cards.get(1);
		System.out.println( " | " + card );
		System.out.println( "= Aposta minima: " + baseBet );
		System.out.println( "= Disponivel: " + cash );
	}
	
	private void bottomBet(String round)
	{
		//monitoramento via terminal
		System.out.print( "=================================================" );
		for (int i = 0; i < (round.length() + name.length()); i++)
			System.out.print( "=" );
		System.out.println();
	}
	
	private void topQuit()
	{
		//monitoramento via terminal
		System.out.println();
		System.out.println( "========= " + name + " =========" );
		System.out.print( "= Deseja sair do jogo (1 - sair | 0 - continuar)? " );
	}
	
	private void bottomQuit()
	{
		System.out.println( "====================" );
		for (int i = 0; i < name.length(); i++)
			System.out.print( "=" );
		System.out.println();
		//monitoramento via terminal
	}
}