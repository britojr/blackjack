
<%@ page session="true" errorPage="error.jsp" import="java.util.Set,java.util.Iterator,java.util.Map,tpw.cassino.*,tpw.cassino.servlet.*"%>
<HTML>
<HEAD>
    <TITLE>Cassino - Lista de Salas</TITLE>
    <link rel="stylesheet" type="text/css" href="cassino.css">

    <SCRIPT language="JavaScript">
        if(window.top != window.self)
        {
            window.top.location = window.location;
        }
    </SCRIPT>
</HEAD>

<BODY bgcolor="#FFFFFF">
<%
        String nickname = (String) session.getAttribute("nickname");
        if (nickname == null || nickname == "") {
            response.sendRedirect("login.jsp");
        } else {
            String roomname = request.getParameter("rn");
%>
<%@ include file="header.jsp" %>
<TABLE width="80%" align="center">

    <TR>
        <TD width="100%">Selecione a sala que deseja entrar
        </TD>
    </TR>
</TABLE>
<BR>
<%


            try {
                //BlackJackRoomList roomlist = (BlackJackRoomList) application.getAttribute("blackjackroomlist");
				BlackJackRoomList roomlist = (BlackJackRoomList)getServletContext().getAttribute("blackjackroomlist");
                BlackJackRoom[] blackjackrooms = roomlist.getRoomListArray();
				String currentroom = roomlist.getRoomOfWatcher(nickname).getName();
				if (roomname == null) {
                    roomname = roomlist.getRoomOfWatcher(nickname).getName();
                }
                roomname = roomname.trim();
				if(!currentroom.equalsIgnoreCase("StartUp")){
					BlackJackRoom blackJackRoom = roomlist.getRoom("StartUp");
					BlackJackRoom blackJackRoomOld = roomlist.getRoomOfWatcher(nickname);
					blackJackRoom.addWatcher(blackJackRoomOld.getWatcher(nickname));
					blackJackRoomOld.removeWatcher(nickname);				
					
				}
%>
<DIV align="center">
<CENTER>
<FORM name="blackjackrooms" action="<%=request.getContextPath()%>/start.jsp" method="post">
<TABLE width="80%" border="1" cellspacing="1" cellpadding="1" align="center">
<TR>
    <TD colspan="2" class="pagetitle">Lista de Salas</TD>
</TR>
<TR>
    <TD>Salas</TD>
    <TD>Quantidade de jogadores</TD>
</TR>
<%
            for (int i = 0; i < blackjackrooms.length; i++) {
                if (blackjackrooms[i].getName().equalsIgnoreCase("StartUp")) {
                    continue;
                }
%>
<TR>
<TD width="50%">
    <INPUT type=radio name="rn" value="<%=blackjackrooms[i].getName()%>"
           <%if (blackjackrooms[i].getName().equals(roomname)) {
        out.write("checked");
    }%>><%=blackjackrooms[i].getName()%></A>
</TD>
		<TD width="50%"><%=blackjackrooms[i].getNoOfWatchers()%></TD>
</TR>
<%
                }
            } catch (Exception e) {
                System.out.println("Problema no ServletContext: " + e.getMessage());
                e.printStackTrace();
            }
%>
<TR>	
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