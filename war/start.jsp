
<%@ page errorPage="error.jsp" import="java.util.Set,java.util.Iterator,java.util.Map,tpw.cassino.*"%>
<%
        String roomname = request.getParameter("rn");
        String nickname = (String) session.getAttribute("nickname");
       // BlackJackRoomList roomlist = (BlackJackRoomList) application.getAttribute("blackjackroomlist");
        BlackJackRoomList roomlist = (BlackJackRoomList)getServletContext().getAttribute("blackjackroomlist");
        if (nickname == null) {
            response.sendRedirect("login.jsp");
        } else if (roomname == null) {
            response.sendRedirect("listrooms.jsp");
        } else {
            BlackJackRoom blackJackRoom = roomlist.getRoom(roomname);
            if (blackJackRoom == null) {
                out.write("<font color=\"red\" size=\"+1\">Sala " + roomname + " nao foi encontrada</font>");
                out.close();
                return;
            }
			//...
			if(roomlist == null){
				out.write("ERRO!! Lista de salas nao encontrada.");
			}
            BlackJackRoom blackJackRoomOld = roomlist.getRoomOfWatcher(nickname);

            if (blackJackRoomOld != null && blackJackRoom != null) {
                if (!blackJackRoomOld.getName().equals(blackJackRoom.getName())) {
                    blackJackRoom.addWatcher(blackJackRoomOld.getWatcher(nickname));

                    blackJackRoomOld.removeWatcher(nickname);
                }

                if (session.getAttribute("nickname") == null) {
                    session.setAttribute("nickname", nickname);
                }
                response.sendRedirect("blackJack.jsp");
            } else {
                out.write("<span class=\"error\">Ocorreu um erro");
            }
        }
%>