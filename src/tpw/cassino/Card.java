package tpw.cassino;

/**
 * Esta classe define o objeto Card, que representa cartas do baralho de algum jogo.
 *
 * @author Artur Lovato da Cunha
 * @version 1.0
 */
public class Card{
	private String value;
	private String suit;

	/**
	 * Construtor da classe
	 *
	 * @param value representa o valor da carta (A, 2, 3, ... , J, K)
	 * @param suit representa o naipe da carta (paus, copas, espada ou ouro)
	 */
	public Card (String v, String s)
	{
		value = v;
		suit = s;
	}

	/**
	 * Retorna o valor da carta
	 *
	 * @return value
	 */
	public String getValue ()
	{
		return (value);
	}

	/**
	 * Retorna o naipe da carta
	 *
	 * @return naipe
	 */
	public String getSuit ()
	{
		return (suit);
	}

	/**
	 * Método toString padrão
	 */
    @Override
	public String toString ()
	{
		return (value + " de " + suit);
	}

	/* Método que testa a classe */
	public static void main (String args[])
	{
		Card c1 = new Card("A","COPAS");
		Card c2 = new Card("2","OUROS");
		Card c3 = new Card("3","PAUS");

		System.out.println("Carta 1: " + c1);
		System.out.println("Carta 2: " + c2);
		System.out.println("Carta 3: " + c3);
	}
}