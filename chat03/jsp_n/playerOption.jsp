
<%@ page isErrorPage="false" errorPage="error.jsp" import="java.util.Set,java.util.Iterator,java.util.Map,tpw.cassino.*"%>
<% 
	String nickname = (String)session.getAttribute("nickname");
	
	if (nickname != null) {
		BlackJackRoom blackJackRoom = (BlackJackRoom)getServletContext().getAttribute("blackjackroom");
		player = blackJackRoom.getChatter(nickname);
		String roomname = blackJackRoom.getName();
	
%>
<HTML>
<HEAD>
<LINK rel="stylesheet" type="text/css" href="cassino.css">
</HEAD>
<BODY onLoad="document.msg.messagebox.focus();" bgcolor="#FFFFFF">
<TABLE width="100%" cellpadding="3" cellspacing="0">
	<TR> 
		<%
		/*Se o jogo estiver rodando, imprime as cartas do jogador*/
		if(blackJackRoom.running){
			
		%>
		<p>&nbsp;</p>
		<TD width="50%" align="left" valign="top"> 
			<table border="0" width="100%">
				<tr>
					<td width="100%">
						<p align="center">SUAS CARTAS:</td>
				</tr>
				<tr>
					<td width="100%">
						<div align="center">
						<center>
						<table border="0" width="160" height="100">
						<tr>
						<%
						ArrayList cards = player.getCards();
						int per = 100/cards.size();
						for(Card c: cards){
							%>
							<td width="<%= per%>%" align="center"> <img src="images/<%out.print(c.getValue()+"_"+c.getSuit() )%>.gif"> </td>
							<%
						}
						%>	
						</tr>
						</table>
						</center>
						</div>
					</td>
				</tr>
			</table>
		</TD>
		
		
		<TD width="20%"> 
			<!-- Botoes do canto inferior direito -->
			<TABLE border="1" cellpadding="3" cellspacing="0" class="panel">
				<TR align="left" valign="top"> 
					<FORM name="hit" method="post" action="<%=request.getContextPath()%>/servlet/blackJack">
						<TD width="15%"> 
							<INPUT type="hidden" name="n" value="<%=nickname%>">
							<INPUT type="hidden" name="h" value="1">
							<% if(player.getTurn()){ %>
								<INPUT name="hit" type="submit" id="Hit" value="Hit">
							<% }else { %>
								<INPUT name="hit" type="button" id="Hit" value="Hit">
							<% } %>
						</TD>
					</FORM>
					<FORM name="stand" method="post" action="<%=request.getContextPath()%>/servlet/blackJack">
						<TD width="15%"> 
							<INPUT type="hidden" name="n" value="<%=nickname%>">
							<INPUT type="hidden" name="s" value="1">
							<% if(player.getTurn()){ %>
								<INPUT name="stand" type="submit" id="Stand" value="Stand">
							<% }else { %>
								<INPUT name="stand" type="button" id="Stand" value="Stand">
							<% } %>
						</TD>
					</FORM>
				</TR>
			</TABLE>
		</TD>
		
		<%
		}
		%>
	</TR>
</TABLE>
</BODY>
</HTML>
<%

	}
	else {
		response.sendRedirect("login.jsp");
	}
%>





