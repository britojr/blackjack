<!-- (!)-->
<%@ page session="true" import="tpw.cassino.*, tpw.cassino.servlet.*" errorPage="error.jsp"%>
<%
        String nickname = (String) session.getAttribute("nickname");

        if (nickname != null && nickname.length() > 0) {

            BlackJackRoomList roomlist = (BlackJackRoomList) getServletContext().getAttribute("blackjackroomlist");
            BlackJackRoom blackJackRoom = (BlackJackRoom) roomlist.getRoomOfWatcher(nickname);
            String roomname = blackJackRoom.getName();
%>

<HTML>
    <HEAD>
        <TITLE>Cassino - <%=nickname%> (<%=roomname%>) </TITLE>
        <SCRIPT language="JavaScript">
            if(window.top != window.self) {
                window.top.location = window.location;
            }
        </SCRIPT>
          <LINK rel="stylesheet" href="<%=request.getContextPath()%>/cassino.css">
               <meta http-equiv="refresh" content="5">
    </HEAD>
	<!--<BODY bgcolor="#FFFFFF" onLoad="document.PlaceBet.bet.focus();">-->
	<BODY bgcolor="#FFFFFF">

   <%@ include file="header.jsp" %>
   <!--dois frames-->
   <TABLE width="40%" border="0" cellspacing="1" cellpadding="1" align="center" colspan="2" class="panel">
        <%
    if (!(blackJackRoom.gameIsRunnig() || blackJackRoom.getBetting())) {
        //jogo nao comecou, habilitar botao start
%>


    <TR>
        <TD width="30%">
            <CENTER>
                <FORM name="StartGame" method="post" action="<%=request.getContextPath()%>/servlet/blackJack">

                    <INPUT type="hidden" name="rn" value="<%=roomname%>">
                    <INPUT type="hidden" name="n" value="<%=nickname%>">
                    <INPUT type="hidden" name="bg" value="1">
                    <INPUT name="StartGame" type="submit" id="StartGame" value="Start Game">

                </FORM>
            </CENTER>
        </TD>
    </TR>
    <TR>
        <TD width="30%">
            <CENTER>
            <FORM name="ChangeRoom" method="post" action="<%=request.getContextPath()%>/listrooms.jsp">

                <INPUT type="hidden" name="rn" value="<%=roomname%>">
                <INPUT name="ChangeRoom" type="submit" id="ChangeRoom" value="Trocar Sala">

            </FORM>
            </CENTER>
        </TD>
    </TR>
</TABLE>

    <%
    } else if (blackJackRoom.getBetting()) {
        //rodada de apostas, habilitar caixa de texto aposta
%>
<TR>
<TD>
    <FORM name="PlaceBet" method="post" action="<%=request.getContextPath()%>/servlet/blackJack">

        <TABLE width="100%" border="0" align="center" colspan="2" class="panel">
            <% if (blackJackRoom.getCurrentPlayerName().equalsIgnoreCase(nickname)) {%>
            <%=nickname%>, quanto deseja apostar?
            <TR>
                <TD width="70%">
                    <INPUT type="text" name="bet" size="10">
                    <INPUT type="hidden" name="rn" value="<%=roomname%>">
                    <INPUT type="hidden" name="n" value="<%=nickname%>">
                    <INPUT type="hidden" name="eb" value="1">
                    <INPUT type="submit" name="Enter" value="Enter">
                </TD>
            </TR>
            <% } else {%>
            <%=nickname%>, aguarde enquanto <%=blackJackRoom.getCurrentPlayerName()%> faz sua aposta.
            <% }%>
        </TABLE>
    </FORM>
</TD>
</TR>
</TABLE>
    <%
    } else if (blackJackRoom.gameIsRunnig() && blackJackRoom.getRound() >= 0) {
        //o jogo esta rodando
        if (blackJackRoom.isPlaying(nickname)) {
            //jogador esta nesse jogo, exibir os frames
%>
</TABLE>
    <FRAMESET rows="60%,30%">
        <FRAME SRC="displayTable.jsp#current" name="TableWin">
        <FRAME SRC="playerOption.jsp" name="OptionWin">
    </FRAMESET>
	<!--Caso o browser nao aguente os frames...-->
	<NOFRAMES>
		<H2>E peciso suporte a frames!</h2>
	</NOFRAMES>
		
    <%        } else {
            //nao esta no jogo, exibir mensagem de espera
%>Aguarde o termino do jogo<%            }
    }
    %>

</BODY>
</HTML>
<%
        } else {
            response.sendRedirect("login.jsp");
        }
%>