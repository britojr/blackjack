<!-- Written by Sukhwinder Singh (ssruprai@hotmail.com -->
<%-- Written by Sukhwinder Singh (ssruprai@hotmail.com --%>

<%@ page isErrorPage="false" errorPage="error.jsp" import="java.util.Set,java.util.Iterator,java.util.Map,sukhwinder.chat.*"
%>
<%
String nickname = (String)session.getAttribute("nickname");
if (nickname == null)
{
	out.write("<font color=\"red\" size=\"+1\">You have not logged in.</font>");
	out.close();
	return;
}
ChatRoomList roomList = (ChatRoomList)application.getAttribute("chatroomlist");
ChatRoom chatRoom = roomList.getRoomOfChatter(nickname);
if (chatRoom != null)
{
	Chatter chatter = chatRoom.getChatter(nickname);
%>
<HTML>
<HEAD>
<META http-equiv="pragma" content="no-cache">
<meta name="Author" content="Sukhwinder Singh (ssruprai@hotmail.com">	

<TITLE>Edit your (<%=chatter.getName()%>'s) Information</TITLE>
<LINK rel="stylesheet" href="<%=request.getContextPath()%>/chat.css" type="text/css">

</HEAD>

<BODY bgcolor="#FFFFFF">
<div align="center">
<center>
<FORM name="chatterinfo" method="post" action="<%=request.getContextPath()%>/servlet/saveInfo">
  <TABLE width="80%" border="0" cellspacing="0" cellpadding="2" align="center" bordercolor="#6633CC">
    <TR>
      <TD valign="top"><h4>Nickname:</h4></TD>
      <TD valign="top"><%=chatter.getName()%></TD>
		<input type="hidden" name="nickname" value="<%=chatter.getName()%>">
    </TR>
    <TR>
      <TD valign="top"><h4>Sex:</h4></TD>
      <TD valign="top"><%=chatter.getSex()%></TD>
    </TR>
    <TR>
      <TD valign="top"><h4>Age:</h4></TD>
      <TD valign="top">
		<% String temp;
		int age = chatter.getAge();
		if(age == -1)
			temp = "";
		else
			temp = String.valueOf(age);
		%>
        <INPUT type="text" name="age" maxlength="2" size="2" value="<%=temp%>">
      </TD>
    </TR>
    <TR>
      <TD valign="top"><h4>Email:</h4></TD>
      <TD valign="top">
		<%
			temp = chatter.getEmail();
			if(temp == null)
				temp = "Not Specified";			
		%>
        <INPUT type="text" name="email" value="<%=temp%>">
      </TD>
    </TR>
    <TR>
      <TD valign="top"><h4>Comment:</h4></TD>
      <TD valign="top">
		<% 
			temp = chatter.getComment();
			if(temp==null)
				temp = "Not Specified";		
		%>
        <TEXTAREA cols="30" rows="5" wrap="VIRTUAL" name="comment"><%=temp%></TEXTAREA>
      </TD>
    </TR>
    <TR>
      <TD valign="top">&nbsp;</TD>
      <TD valign="top">
        <INPUT type="submit" name="Submit" value="Save">
      </TD>
    </TR>
  </TABLE>
</FORM>
</center>
</div>
</BODY>
</HTML>
<%
}
else
{
	out.write("<span class=\"error\">Problem getting room information of the chatter</span>");
}
%>