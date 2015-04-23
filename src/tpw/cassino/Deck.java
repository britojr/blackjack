package tpw.cassino;

import java.util.ArrayList;
import java.util.Random;
/**
 * Esta classe define o objeto Deck, que representa o baralho completo de algum jogo.
 *
 * @author Artur Lovato da Cunha
 * @version 1.0
 */
public class Deck {
	private static final String[] value = {"A", "K", "J", "Q", "10", "9", "8", "7", "6", "5", "4", "3", "2"};
	private static final String[] suit = {"COPAS", "ESPADAS", "OUROS", "PAUS"};

	private ArrayList< Card > deck = new ArrayList< Card >();
	private Card card;

	/**
	 * Construtor da classe, cria um baralho com todas as 52 cartas
	 */
	public Deck()
	{
		for (String v: value)
		{
			for (String s: suit)
			{
				card = new Card(v,s);
				deck.add(card);
			}
		}
	}

	/**
	 * Método para embaralhar as cartas aleatoriamente
	 */
	public void shuffle()
	{
		//determina o número de vezes que as cartas serão embaralhadas
		int times = 3 + ((int) (Math.random()*7));

		for (int i = 0; i < times; i++)
		{
			//escolhe uma carta aleatoriamente no baralho, a tira e coloca em último
			//refaz essa ação até atingir o número total de cartas do baralho
			for (int j = 0; j < deck.size(); j++)
			{
				deck.add( deck.remove( (int) (Math.random()*deck.size()) ) );
			}
		}
	}

	/**
	 * Retorna o número de cartas do baralho
	 *
	 * @return número de cartas do baralho
	 */
	public int getSize()
	{
		return (deck.size());
	}

	/**
	 * Retorna a primeira carta do baralho
	 *
	 * @return primeira carta do baralho
	 */
	public Card getCard()
	{
		return ((Card)deck.remove(0));
	}

	/**
	 * Adiciona uma carta ao baralho
	 *
	 * @param card carta a ser inserida no baralho
	 */
	public void addCard(Card card)
	{
		deck.add(card);
	}

	/* Método que testa a classe */
	public static void main(String args[])
	{
		Deck d = new Deck();
		Card c;

		System.out.printf("O numero de cartas do baralho eh: %d\n\n",d.getSize());

		System.out.println("    BARALHO AO SER CRIADO");
		int size = d.getSize();
		for (int i = 0; i < size; i++)
		{
			c = d.getCard();
			System.out.printf("%d: %s %s\t", i, c.getValue(), c.getSuit());
			d.addCard(c);

			if ((i%4) == 3) System.out.printf("\n");
		}
		System.out.println();

		System.out.println("    BARALHO APOS SER EMBARALHADO");
		d.shuffle();
		for (int i = 0; i < size; i++)
		{
			c = d.getCard();
			System.out.printf("%d: %s %s\t", i, c.getValue(), c.getSuit());
			d.addCard(c);

			if ((i%4) == 3) System.out.printf("\n");
		}
	}
}