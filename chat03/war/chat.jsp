<!-- Written by Sukhwinder Singh (ssruprai@hotmail.com -->
<%-- Written by Sukhwinder Singh (ssruprai@hotmail.com --%>

<%@ page session="true" import="sukhwinder.chat.ChatRoomList, sukhwinder.chat.ChatRoom" errorPage="error.jsp"%>
<%
	String nickname = (String)session.getAttribute("nickname");
	if (nickname != null && nickname.length() > 0)
	{
		ChatRoomList roomList = (ChatRoomList) application.getAttribute("chatroomlist");
		ChatRoom room = roomList.getRoomOfChatter(nickname);
		String roomname = room.getName();
%>
	
<HTML>
<HEAD>
<TITLE>S2R Chat - <%=nickname%> (<%=roomname%>) </TITLE>
<META name="Author" value="Sukhwinder Singh (ssruprai@hotmail.com)">
</HEAD>
<!--dois frames-->
<FRAMESET rows="80%,20%">
<!--janela de mensagem-->
<!-- (?)pra que esse #current ? -->
<FRAME SRC="displayMessages.jsp#current" name="MessageWin">
<!--janela para digitar-->
<FRAME SRC="sendMessage.jsp" name="TypeWin">
</FRAMESET>
<!--Caso o browser nao aguente os frames...-->
<NOFRAMES>
<H2>This chat rquires a browser with frames support</h2>
</NOFRAMES>
</HTML>
<%
}
else
{
	response.sendRedirect("login.jsp");
}
%>