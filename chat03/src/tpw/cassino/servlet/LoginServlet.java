package tpw.cassino.servlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import tpw.cassino.*;

public class LoginServlet extends HttpServlet {
	private String contextPath = "";
	/** This method just redirects user to a login page.*/
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		contextPath = request.getContextPath();
		response.sendRedirect(contextPath + "/login.jsp");		
	}
	/** Performs some validation and if everything is ok sends user to a page which displays a list of
	* rooms available.
	*/
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		contextPath = request.getContextPath();		
		String nickname = request.getParameter("nickname");
		nickname = nickname.trim().toLowerCase();
		/*Tratar se nao vier numero depois*/
		int bet = Integer.parseInt(request.getParameter("bet"));
		if ((nickname != null && nickname.length() > 3 && nickname.indexOf(" ") == -1) && (bet != null && bet>0 )) {
			try {
				/*getServletContext retorna um ServletContext (um obj com o context em que o servlet está rodando)
				getAtribute é um método de ServletContext que retorna um Object dada uma string nome
				Neste caso, ele pega a lista de salas*/
				/*(?) Será que pega o obj "ChatRoomList roomlist", se sim, onde foi criado?
				A lista de salas é criada pelo método sobrescrito init() do servlet manage*/
				// ChatRoomList roomlist = (ChatRoomList)getServletContext().getAttribute("chatroomlist");
				/*(?) Assim tbm funciona?
				ChatRoomList roomlist = (ChatRoomList) this.getServletContext().getAttribute("chatroomlist");
				*/
				/*
				boolean chatterexists = roomlist.chatterExists(nickname);
				if (chatterexists)
				{
					response.sendRedirect(contextPath + "/login.jsp?d=t&n="+nickname);
				}
				else
				{*/
					/*Retorna a sessão atual, se não existir, cria uma*/
					HttpSession session = request.getSession(true);
					int timeout = 1800; // 30 minutes
					/*(?)Pega informações do web.xml? como? porque?*/
					/*(!)Não ecencial, apenas para alterar o timeout...
							String t = getServletContext().getInitParameter("sessionTimeout"); // gets Minutes
							if (t != null)
							{
								try
								{
									timeout = Integer.parseInt(t);
									timeout = timeout * 60;
								}
								catch (NumberFormatException nfe)
								{							
								}
							}
					*/
					session.setMaxInactiveInterval(timeout);
					session.setAttribute("nickname", nickname);
					/* (!) Colocando direto na sala do jogo, mudar epois*/
					BlackJackRoom blackJackRoom = (BlackJackRoom)getServletContext().getAttribute("blackjackroom");
					
					player = new Player(nickname, 200);
					blackJackRoom.addPlayer(player);
					//response.sendRedirect(contextPath + "/listrooms.jsp");
					/*redireciona para a pagina do jogo*/
					response.sendRedirect(contextPath + "/blacJack.jsp");
				
			}
			catch(Exception exception) {
				System.out.println("Exception thrown in LoginServlet: " + exception.getMessage());
				exception.printStackTrace();
				response.sendRedirect(contextPath + "/error.jsp");
			}
		}
		else {
			response.sendRedirect(contextPath + "/login.jsp?ic=t");
		}
	}
}


