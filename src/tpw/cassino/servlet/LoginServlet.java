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

    /** Redireciona para a pagina de login.*/
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        contextPath = request.getContextPath();
        response.sendRedirect(contextPath + "/login.jsp");
    }

    /* Verifica o nickname, se estiver ok vai para a lista de salas*/
    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        contextPath = request.getContextPath();
        String nickname = request.getParameter("nickname");
        nickname = nickname.trim().toLowerCase();
        if ((nickname != null && nickname.length() > 3 && nickname.indexOf(" ") == -1)) {
            try {
                /*getServletContext retorna um ServletContext (um obj com o context em que o servlet est� rodando)
                getAtribute � um m�todo de ServletContext que retorna um Object dada uma string nome
                Neste caso, ele pega a lista de salas*/
                BlackJackRoomList roomlist = (BlackJackRoomList) getServletContext().getAttribute("blackjackroomlist");
                System.out.println(roomlist.getRoomOfWatcher(nickname)+ " sala");
                boolean watcherexists = roomlist.watcherExists(nickname);
                if (watcherexists) {
                    response.sendRedirect(contextPath + "/login.jsp?d=t&n=" + nickname);
                } else {

                    /*Retorna a sess�o atual, se n�o existir, cria uma*/
                    HttpSession session = request.getSession(true);
                    //(!)
                    int timeout = 1800; // 30 minutos
                    session.setMaxInactiveInterval(timeout);

                    session.setAttribute("nickname", nickname);

                    BlackJackRoom blackJackRoom = roomlist.getRoom("StartUp");

                    //Cria um jogador com 200 de cash
                    Player player = new Player(nickname, 200);

                    blackJackRoom.addWatcher(player);

                    /*redireciona para a lista de salas*/
                    response.sendRedirect(contextPath + "/listrooms.jsp");
                }
            } catch (Exception exception) {
                System.out.println("Exception thrown in LoginServlet: " + exception.getMessage());
                exception.printStackTrace();
            //	response.sendRedirect(contextPath + "/error.jsp");
            }
        } else {
            response.sendRedirect(contextPath + "/login.jsp?ic=t");
        }
    }
}


