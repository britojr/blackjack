<%-- Written by Sukhwinder Singh (ssruprai@hotmail.com --%>

<%@ page errorPage="error.jsp" import="java.util.Set,java.util.Iterator,java.util.Map,sukhwinder.chat.*"%>
<%
	String roomname = request.getParameter("rn");
	/*no JSP a "session" (assim como "response" e "request") ja existe e e um objeto inicializado e pronto para usar
	nos servlets, ela precisa ser recuperada explicitamente*/
	String nickname = (String)session.getAttribute("nickname");
	/*recupera a lista de salas*/
	ChatRoomList roomlist = (ChatRoomList) application.getAttribute("chatroomlist");
	/*trata se nao tem login ou sala*/
	if (nickname == null)
	{
		response.sendRedirect("login.jsp");
	}
	else if (roomname == null)
	{
		response.sendRedirect("listrooms.jsp");
	}
	else
	{
		/*pega a sala em que o usuario vai (ou escolheu atualmente)*/
		ChatRoom chatRoom = roomlist.getRoom(roomname);
		if (chatRoom == null)
		{
			out.write("<font color=\"red\" size=\"+1\">Room " + roomname + " not found</font>");
			out.close();
			return;
		}
		/*pega a sala em que o usuario quer sair (se ele nao estava em alguma antes? sempre esta. Pelo menos na StartUp ele estara)*/
		ChatRoom chatRoomOld = roomlist.getRoomOfChatter(nickname);
		/*(!)obvio que chatRoom nao sera null*/
		if (chatRoomOld != null && chatRoom != null)
		{
			/*recupera o nick da sala que estava*/
			Chatter chatter = chatRoomOld.getChatter(nickname);
			/*Se as duas salas nao forem a mesma*/
			if (!chatRoomOld.getName().equals(chatRoom.getName()))
			{
				chatRoomOld.removeChatter(nickname);
				chatRoom.addChatter(chatter);
				/*Se a sala que ela saiu nao for a StartUp
				(?)pra que esse IgnoreCase? Ignora o case sensitive?*/
				if (!chatRoomOld.getName().equalsIgnoreCase("StartUp"))
				{
					chatRoomOld.addMessage(new Message("system", nickname + " has left and joined " + 	chatRoom.getName() + ".", new java.util.Date().getTime()));
				}
				chatRoom.addMessage(new Message("system", nickname + " has joined.", new java.util.Date().getTime()));
				chatter.setEnteredInRoomAt(new java.util.Date().getTime());

			}
			/*(?)Porque reatribuir o nick? para o caso de a sessao ter expirado?*/
			if (session.getAttribute("nickname") == null)
			{
				session.setAttribute("nickname", nickname);
			}
			response.sendRedirect("chat.jsp");
		}
		else
		{
			out.write("<span class=\"error\">Some error occured");
		}
	}	
%>