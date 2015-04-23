package tpw.cassino.servlet;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.ServletException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Properties;
import java.util.Enumeration;
import java.io.InputStream;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.io.FileOutputStream;
import java.io.FileNotFoundException;
import tpw.cassino.*;

/** 
* At server startup this servlet is initialised.
* 
*/
public class ManageGameServlet extends HttpServlet {
	BlackJackRoom blackJackRoom = new BlackJackRoom();
	Properties props = null;
	/** Reads .properties file creates an object of and stores it in ServletContext
	*/
	/*Override: O metodo init e chamado uma vez na inicializacao*/
	public void init() throws ServletException {
		try {
			/*(?)Pra que inicia com vazia?*/
			String path = "";
			/*(?)O getInitParameter pega os valores do web.xml
			o path sera: /WEB-INF/chat.properties*/
			path = "/WEB-INF/"+getServletContext().getInitParameter("cassinopropertyfile");
			String realPath;
			/*getRealPath: caminho absoluto do chat.properties*/
			realPath = getServletContext().getRealPath(path);
			
			if (realPath != null) {
				/*(?)le o arquivo chat.properties, pega as salas e descricoes dele e coloca la lista rooms
				e por isso que desde o inicio ja existe as salas (e tbm a StartUp)
				...Pena que le de um jeito bem estranho... (pesquisar a classe Properties depois)*/
				InputStream fis = new FileInputStream(realPath);
				props = new Properties();
				props.load(fis);
				Enumeration keys = props.keys();
				if (keys.hasMoreElements()) {
					String roomname = (String)keys.nextElement();
					BlackJackRoom blackJackRoom = new BlackJackRoom(roomname);
				}
				fis.close();
				/*(!)Coloca la no servletContext com o nome "blackjackroom", entao ela nao vem do alem!*/
				getServletContext().setAttribute("blackjackroom", blackJackRoom);
				System.err.println("Room Created");
			}
			else {
				System.out.println("Unable to get realpath of chatproperty file " + path + ".\nCheck that application war file is expanded and file can be read.\nApplication won't work.");
			}
		}
		catch(FileNotFoundException fnfe) {
			System.err.println("Properites file not found:" + fnfe.getMessage());
		}
		catch(IOException ioe) {
			System.out.print("Unable to load Properties File: " + ioe.getMessage());
		}		
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		/*(?) O que que isso faz alem de imprimir coisas inuteis?*/
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		out.println("Room List Created");
		out.close();
	}
	
	/**	Allows users to add new rooms after performing minimum validation.
	* Also saves information to chat.properties files if required by initialization parameter <code>saveRooms</code>.
	*/
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String roomname = request.getParameter("rn");
		String nickname = request.getParameter("n");
		String hitbutton = request.getParameter("h");
		String standbutton = request.getParameter("s");
		String beginbutton = request.getParameter("b");
		contextPath = request.getContextPath();	
		
		BlackJackRoom blackJackRoom = (BlackJackRoom)getServletContext().getAttribute("blackjackroom");
		player = blackJackRoom.getChatter(nickname);
		
		/*comecar jogo*/
		if (beginbutton != null) {
			/*setar rodada para 1*/
			blackJackRoom.startGame();
		}
		/* jogo ja comecou*/
		else {
			ArrayList cards = blackJackRoom.getCards();
			switch(blackJackRoom.round) {
				case 1:{
					/*upcard: A mesa mostra uma carta, e entrega as duas do usuario.
					Ele tem as opcoes de hit e stand se for a vez dele.
					Quando todos os usuarios jogarem vai para a proxima rodada, o holecard*/
					if(standbutton != null) {
						blackJackRoom.playerStand();
						//nextturn()
					} else if(hitbutton != null) {
						blackJackRoom.playerHit();
					}
					break;
				}
				case 2:{
					/*holecard: A mesa mostra a segunda carta, e segue as regras para comprar mais ou parar,
					logo apos mostrar a carta, o jogador pode desistir e perder so metade do dinheiro*/
					blackJackRoom.holecard();
					blackJackRoom.tableTurn();
					break;
				}
				case 3:{
					/*faz as comparacoes e exibe os resultados: cada vitoria ou derrota e individual.
					se o usuario ganhar, leva 2x, se foi por black jack natural leva 3x (2,5x na verdade)*/
					blackJackRoom.finishGame();
					break;
				}
			}
		}
		
		response.sendRedirect(contextPath + "/blacJack.jsp");
	}
	
	/** Called when servlet is being destroyed */
	public void destroy() {
		System.err.println("Destroying all rooms");
	}
}