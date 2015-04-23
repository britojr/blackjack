<!-- Written by Sukhwinder Singh (ssruprai@hotmail.com -->
<%-- Written by Sukhwinder Singh (ssruprai@hotmail.com --%>
<!-- (?)para que serve session_true? -->
<!-- (!)errorPage diz a pagina de erro que deve redirecionar, redireciona sempre que der um erro qualquer? -->
<%@ page session="true" errorPage="error.jsp" import="java.util.Set,java.util.Iterator,java.util.Map,sukhwinder.chat.*"%>
<HTML>
<HEAD>
<TITLE>Chat01 - Room List</TITLE>
<LINK rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/chat.css">
<!--<meta name="Author" content="Sukhwinder Singh (ssruprai@hotmail.com">	
<SCRIPT language="JavaScript">
	 
	 if(window.top != window.self)
	 {
		 window.top.location = window.location;
	 }
	 
</SCRIPT>//-->
</HEAD>

<BODY bgcolor="#FFFFFF">
<%
	String nickname = (String)session.getAttribute("nickname");
	if (nickname == null || nickname == "")
	{
		response.sendRedirect("login.jsp");
		//System.out.println("Redirecting");
	}
	else
	{
		String roomname = request.getParameter("rn");	
		String descr = request.getParameter("sd");
		boolean see = false;
		/*(?)Precisa comparar se e diferente de null?*/
		if (descr != null && descr.equals("y"))
		{
			see = true;
		}
%>
<%@ include file="header.jsp" %>
<TABLE width="80%" align="center">	
	<!--<tr>
		<td class="normal">Welcome <span class="chattername"><%=nickname%></span></td>
	</tr>	
	-->
	<TR>
		<TD width="100%">Select the room you want to enter or click view description to view description
			 about the room.
		</TD>
	</TR>
</TABLE>
<BR>
	<%
		
		try
		{
			/*(?)Chamando chatroomlist do alem...*/
			ChatRoomList roomlist = (ChatRoomList)application.getAttribute("chatroomlist");
			ChatRoom[] chatrooms = roomlist.getRoomListArray();
			if(roomname == null)
			{
				roomname = roomlist.getRoomOfChatter(nickname).getName();
			}
			roomname = roomname.trim();

	%>
<DIV align="center">
<CENTER>
<FORM name="chatrooms" action="<%=request.getContextPath()%>/start.jsp" method="post">
	<TABLE width="80%" border="1" cellspacing="1" cellpadding="1" align="center">
	<TR>
	<TD colspan="2" class="pagetitle">Room List</TD>
	</TR>
	<%
			for (int i = 0; i < chatrooms.length; i++)
			{
				if (chatrooms[i].getName().equalsIgnoreCase("StartUp"))
					continue;
	%>
			<TR>
				<TD width="50%">
				<INPUT type=radio name="rn" value="<%=chatrooms[i].getName()%>"
		<%if (chatrooms[i].getName().equals(roomname))
					out.write("checked");%>><%=chatrooms[i].getName()%></A>
				</TD>
		<%
					/*Se descricao visivel, entao imprima a descricao
					senao imprime o link*/
					if (see == true && chatrooms[i].getName().equals(roomname))
					{
		%>	
				<TD width="50%"><%=chatrooms[i].getDescription()%></TD>
		<%
					}
					else
					{
		%>
				<TD width="50%"><A href="<%=request.getContextPath()%>/listrooms.jsp?rn=<%=chatrooms[i].getName()%>&sd=y">View Description</A></TD>
		<%
					}
		%>
			</TR>
	<%
			}
		}
		catch (Exception e)
		{
			System.out.println("Unable to get handle to Servlet Context: " + e.getMessage());
			e.printStackTrace();
		}
%>
		<TR>
			<TD>&nbsp;<A href="<%=request.getContextPath()%>/addRoom.jsp" title="Add new Room">Add new Room</A></TD>
			<TD><INPUT type="Submit" value="Start"></TD>
		</TR>
		</TABLE>
</FORM>
</CENTER>
</DIV>
<%
	}
%>
<%@ include file="/footer.jsp"%>
</BODY>
</HTML>