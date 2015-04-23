
<%@ page import="tpw.cassino.*" errorPage="error.jsp" %>
<HTML>
<HEAD>
<TITLE>Voce saiu do jogo</TITLE>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/cassino.css">
</HEAD>

<BODY>
<%@ include file="header.jsp" %>
<div align="center">
<center>

<%
	String nickname = (String)session.getAttribute("nickname");
	if (nickname != null && nickname.length() > 0)
	{
		
       BlackJackRoomList roomlist = (BlackJackRoomList) getServletContext().getAttribute("blackjackroomlist");
       BlackJackRoom blackJackRoom = (BlackJackRoom) roomlist.getRoomOfWatcher(nickname);
		
		if ( blackJackRoom != null)
		{
			blackJackRoom.removeWatcher(nickname);

			session.invalidate();
			out.write("<font color=\"blue\">Deslogado com sucesso</font><br>");
			out.write("<a href=\"login.jsp\">Login</a>");
		}
		else
		{
			out.write("<h3><font color=\"red\">Nao foi possivel deslogar</font></h3>");
			response.sendRedirect("login.jsp");
		}
	}
	else
	{
		response.sendRedirect("login.jsp");
	}
	%>
</center>
</div>
</BODY>
</HTML>