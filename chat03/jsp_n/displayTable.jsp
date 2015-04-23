
<%@ page isErrorPage="false" errorPage="error.jsp" import="java.util.Set,java.util.Iterator,java.util.Map,java.util.Date,java.text.DateFormat,tpw.cassino.*"%>
<%
	String nickname = (String)session.getAttribute("nickname");
	
	if (nickname != null) {
		BlackJackRoom blackJackRoom = (BlackJackRoom)getServletContext().getAttribute("blackjackroom");
		player = blackJackRoom.getChatter(nickname);
		String roomname = blackJackRoom.getName();
	
%>
<HTML>
<HEAD>
	<!-- (?) porque ele nao da o refresh assim?>-->
	<meta http-equiv="refresh" content="3">

	<link rel="stylesheet" type="text/css" href="cassino.css">

</HEAD>
<BODY onLoad="window.location.hash='#current'" bgcolor="#FFFFFF">

	<table width="100%" border="0">
	<tr>
		<td width="70%" valign="top">
			<%@ include file="header.jsp" %>
		<table>
			<tr>
				<td>
	<h3><i><%=(String)session.getAttribute("nickname")%></i> you are in room <b><%=roomname%></b></h3>
<%
	/*se o jogo ja esta rodando*/
	if(blackJackRoom.running) {
		/*Exibir os nomes dos jogadores aqui*/
		
			%>
			<p>&nbsp;</p>
			<table border="0" width="100%">
				<tr>
					<td width="100%">
						<p align="center">MESA:</td>
				</tr>
				<tr>
					<td width="100%">
						<div align="center">
						<center>
						<table border="0" width="400" height="100">
						<tr>
			<%
		
		ArrayList cards = blackJackRoom.getCards();
		switch(blackJackRoom.round) {
			case 1:{
				/*upcard: A mesa mostra uma carta, e entrega as duas do usuario.
				Ele tem as opcoes de hit e stand se for a vez dele.
				Quando todos os usuarios jogarem vai para a proxima rodada, o holecard*/
				/*Aqui tem que mostrar as cartas da mesa. (as opcoes do usuario ficam no outro frameS)*/
				%>
						<td width="50%" align="center"> <img src="images/<%out.print(cards.get(0).getValue()+"_"+cards.get(0).getSuit() )%>.gif">  </td>
						<td width="50%" align="center"> <img src="images/CARTA.gif"> </td>
						</tr>
						</table>
						</center>
						</div>
					</td>
				</tr>
				<%
				break;
			}
			case 2:{
				/*holecard: A mesa mostra a segunda carta, e segue as regras para comprar mais ou parar,
				logo apos mostrar a carta, o jogador pode desistir e perder so metade do dinheiro*/
				%>
						<td width="50%" align="center"> <img src="images/<%out.print(cards.get(0).getValue()+"_"+cards.get(0).getSuit() )%>.gif">  </td>
						<td width="50%" align="center"> <img src="images/<%out.print(cards.get(1).getValue()+"_"+cards.get(1).getSuit() )%>.gif"> </td>
						</tr>
						</table>
						</center>
						</div>
					</td>
				</tr>
				<%
				break;
			}
			case 3:{
				/*faz as comparacoes e exibe os resultados: cada vitoria ou derrota e individual.
				se o usuario ganhar, leva 2x, se foi por black jack natural leva 3x (2,5x na verdade)*/
				%>
				
				<%
				break;
			}
			%>
			</table><%
		}
		
		
	}
	/*Se o jogo nao estiver rodando ainda*/
	else {
		/*Exibe a mensagem e o botao para iniciar o jogo.
		Colocar posteriormente a verificacao de numero minimo de jogadores para habilitar o botao*/
		%>
		<FORM name="StartGame" method="post" action="<%=request.getContextPath()%>/servlet/blackJack">
			<TD width="15%"> 
				<INPUT type="hidden" name="rn" value="<%=roomname%>">
				<INPUT type="hidden" name="n" value="<%=nickname%>">
				<INPUT type="hidden" name="b" value="1">
				<INPUT name="StartGame" type="submit" id="StartGame" value="Start Game">
			</TD>
		</FORM>
		<%
	}
		%>
				</td>
			</tr>
		</table>
		</td>
	</tr>
	</table>

<%
	
}
else {
	response.sendRedirect("login.jsp");
}
%>

</BODY>
</HTML

