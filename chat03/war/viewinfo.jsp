<!-- Written by Sukhwinder Singh (ssruprai@hotmail.com -->
<%-- Written by Sukhwinder Singh (ssruprai@hotmail.com --%>

<%@ page errorPage="error.jsp" import="java.util.Set,java.util.Iterator,java.util.Map,sukhwinder.chat.*"%>
<%
String nickname = request.getParameter("chatterName");
%>

<HTML>
<HEAD>
<META http-equiv="pragma" content="no-cache">
<meta name="Author" content="Sukhwinder Singh (ssruprai@hotmail.com">	
<TITLE><%=nickname%>'s information</TITLE>
<LINK rel="stylesheet" href="<%=request.getContextPath()%>/chat.css" type="text/css">
</HEAD>

<BODY bgcolor="#FFFFFF">
<%
if (nickname != null)
{
	ChatRoomList roomList = (ChatRoomList)application.getAttribute("chatroomlist");
ChatRoom chatRoom = roomList.getRoomOfChatter(nickname);
if (chatRoom != null)
{
Chatter chatter = chatRoom.getChatter(nickname);
%>
<div align="center">
<center>
<table border="0" width="100%" cellpadding="0" cellspacing="0">
<tr>
	<TD valign="top"><h4>Nickname: </h4></td><TD valign="top"><%=nickname%></td>
</tr>
<tr>
<TD valign="top"><h4>Sex: </h4></td><TD valign="top"><%=chatter.getSex()%></td>
</tr>
<tr>
<TD valign="top"><h4>Age: </h4></td><TD valign="top"><% int age = chatter.getAge();
		if(age == -1)
		out.write("Not specified");
		else
		out.write(String.valueOf(age));
		%></td>
</tr>
<tr>
	<TD valign="top"><h4>Email: </h4></td><TD valign="top"><% String email = chatter.getEmail();
			 if (email != null && email.length() >0)
			  	 out.write(email);
			 else
				 out.write("Not specified");
		%></td>
</tr>
<tr>
<TD valign="top"><H4>Comment: </h4></td><TD valign="top"><%
				String comment = chatter.getComment();
				if (comment != null && comment.length() >0)
					out.write(comment);
				else
					out.write("Not specified");
			%></h4>
</tr>
<tr><TD valign="top">&nbsp;</td>
<TD valign="top">
<form name="closing">
<input type="button" onClick="window.close()" value="Close">
</form>
</td>
<tr>
</table>
</center>
</div>

<%
}
else
{

%>
<div align="center">
<center>
<span class="error">User <%=nickname %> doesn't exist.<br>He / She may have logged out.</span>
<form name="closing">
<input type="button" onClick="window.close()" value="Close">
</form>
</center>
</div>
<%
}
}
else
{
%>
<div align="center">
<center>
<span class="error">No username provided.</span>
</center>
</div>
<%
}
%>
</BODY>
</HTML>