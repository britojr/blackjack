<!-- (?) O que quer dizer session true?-->
<%@ page session="true" import="sukhwinder.chat.ChatRoomList, sukhwinder.chat.ChatRoom" errorPage="error.jsp"%>
	<%
	String nickname = (String)session.getAttribute("nickname");
	if (nickname != null && nickname.length() > 0) {
		BlackJackRoom blackJackRoom = (BlackJackRoom)getServletContext().getAttribute("blackjackroom");
		String roomname = blackJackRoom.getName();
	%>
	
	<HTML>
		<HEAD>
			<TITLE>Cassino - <%=nickname%> (<%=roomname%>) </TITLE>
		</HEAD>
		<!--dois frames-->
		<FRAMESET rows="80%,20%">
			<!--janela de mensagem-->
			<!-- (?)pra que esse #current ? -->
			<FRAME SRC="displayTable.jsp#current" name="TableWin">
			<!--janela para digitar-->
			<FRAME SRC="playerOption.jsp" name="OptionWin">
		</FRAMESET>
		<!--Caso o browser nao aguente os frames...
		<NOFRAMES>
			<H2>This chat rquires a browser with frames support</h2>
		</NOFRAMES>
		-->
	</HTML>
	<%
	}
	else {
		response.sendRedirect("login.jsp");
	}
	%>