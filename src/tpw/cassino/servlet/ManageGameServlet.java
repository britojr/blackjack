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
import java.io.FileNotFoundException;
import tpw.cassino.*;

/**
 * Ao iniciar a aplicacao este servlet eh inicializado
 *
 */
public class ManageGameServlet extends HttpServlet {

    BlackJackRoom blackJackRoom;
    BlackJackRoomList rooms = new BlackJackRoomList();
    Properties props = null;
    private String contextPath = "";

    /** Le o arquivo cassino.properties cria um objeto e armazena em ServletContext
     */
    @Override
    public void init() throws ServletException {
        try {

            String path; //= "";
            path = "/WEB-INF/" + getServletContext().getInitParameter("cassinopropertyfile");
            String realPath;
            /*getRealPath: caminho absoluto do cassino.properties*/
            realPath = getServletContext().getRealPath(path);

            if (realPath != null) {
                /* Le o arquivo cassino.properties, pega as salas e descricoes dele e coloca la lista rooms	*/
                InputStream fis = new FileInputStream(realPath);
                props = new Properties();
                props.load(fis);
                Enumeration keys = props.keys();
                while (keys.hasMoreElements()) {
                    String roomname = (String) keys.nextElement();
                    blackJackRoom = new BlackJackRoom(roomname);
                    rooms.addRoom(blackJackRoom);
                }
                fis.close();
                /*Coloca la no servletContext com o nome "blackjackroomList"*/
                getServletContext().setAttribute("blackjackroomlist", rooms);
            } else {
                System.out.println("Nao foi possivel pegar o caminho para cassino.properties " + path);
            }
        } catch (FileNotFoundException fnfe) {
            System.err.println("Arquivo Properties nao encontrado:" + fnfe.getMessage());
        } catch (IOException ioe) {
            System.out.print("Nao foi possivel carregar o properties: " + ioe.getMessage());
        }
    }

	private boolean isParseable(String value){
		int aux;
		try{
			aux = Integer.parseInt(value);
			return true;
		}
		catch (Exception e){
			return false;
		}
	}
	
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        out.println("Room List Created");
        out.close();
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String roomname = request.getParameter("rn");
        String nickname = request.getParameter("n");
        String hitbutton = request.getParameter("h");
        String standbutton = request.getParameter("s");
        String beginbutton = request.getParameter("bg");
        String betbutton = request.getParameter("eb");
        String betValue = request.getParameter("bet");

        contextPath = request.getContextPath();

        BlackJackRoomList roomlist = (BlackJackRoomList) getServletContext().getAttribute("blackjackroomlist");
        BlackJackRoom blackJackRoom = (BlackJackRoom) roomlist.getRoomOfWatcher(nickname);

        Player player = blackJackRoom.getWatcher(nickname);

        /*comecar jogo*/
        if (beginbutton != null) {
            /*Comeca a rodada de apostas*/
            blackJackRoom.startBetting();
        } else if (betbutton != null) {
            // (!) ver se � parseavel
			int bet=0;
			if(betValue != null && isParseable(betValue)){
				bet = Integer.parseInt(betValue);
			}
            blackJackRoom.placeBet(player, bet);
            if (blackJackRoom.getBetting() == false) {
                blackJackRoom.startGame();               
            }
        } /* jogo ja comecou*/ else {
            switch (blackJackRoom.getRound()) {
                case 1: {
                    /*upcard: A mesa mostra uma carta, e entrega as duas do usuario.
                    Ele tem as opcoes de hit e stand se for a vez dele.
                    Quando todos os usuarios jogarem vai para a proxima rodada, o holecard*/
                    if (standbutton != null) {
                        blackJackRoom.playerStand(player);
                        System.out.println("Player: " + player.getName() + "Stand!");
                        if (blackJackRoom.getRound() == 2) {
                            /*holecard: A mesa mostra a segunda carta, e segue as regras para comprar mais ou parar,
                            logo apos mostrar a carta, o jogador pode desistir e perder so metade do dinheiro*/
                            /*faz as comparacoes e exibe os resultados: cada vitoria ou derrota e individual.
                            se o usuario ganhar, leva 2x, se foi por black jack natural leva 3x (2,5x na verdade)*/
                            //blackJackRoom.tableHit();
                            blackJackRoom.tableTurn();
                            blackJackRoom.evaluateGame();
                            System.out.println("Table: " + blackJackRoom.getName() + " Possui: " + blackJackRoom.getSum());
                        }
                    //nextturn()
                    } else if (hitbutton != null) {
                        blackJackRoom.playerHit(player);
                        System.out.println("Player: " + player.getName() + "Hit!");
                    }
                    break;
                }
                case 2: {
                    /*holecard: A mesa mostra a segunda carta, e segue as regras para comprar mais ou parar,
                    logo apos mostrar a carta, o jogador pode desistir e perder so metade do dinheiro*/
                    /*faz as comparacoes e exibe os resultados: cada vitoria ou derrota e individual.
                    se o usuario ganhar, leva 2x, se foi por black jack natural leva 3x (2,5x na verdade)*/
                    //blackJackRoom.tableHit();
					/*blackJackRoom.tableTurn();
                    blackJackRoom.evaluateGame();
                    System.out.println("Table: "+blackJackRoom.getName()+" Possui: "+blackJackRoom.getSum());*/
                    break;
                }
                case 3: {

                    blackJackRoom.finishGame();
                    break;
                }

            }
        }
        response.sendRedirect(contextPath + "/blackJack.jsp");
    }
}