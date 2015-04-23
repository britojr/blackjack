<%@ page isErrorPage="false" errorPage="error.jsp" import="java.util.Set,java.util.Iterator,java.util.ArrayList,java.util.Map,java.util.Date,java.text.DateFormat,tpw.cassino.*"%>
<%
        String nickname = (String) session.getAttribute("nickname");

        if (nickname != null) {
            BlackJackRoomList roomlist = (BlackJackRoomList) getServletContext().getAttribute("blackjackroomlist");
            BlackJackRoom blackJackRoom = (BlackJackRoom) roomlist.getRoomOfWatcher(nickname);
            Player player = blackJackRoom.getWatcher(nickname);
            String roomname = blackJackRoom.getName();
            System.out.println("display table");
%>

<HTML>
    <HEAD>

       <!--<meta http-equiv="refresh" content="3">-->

        <link rel="stylesheet" type="text/css" href="cassino.css">

    </HEAD>
    <BODY onLoad="window.location.hash='#current'" bgcolor="#FFFFFF">
        <table width="100%" border="0">
        <tr>
        <td width="70%" valign="top">
        <!--<%@ include file="header.jsp" %>-->
        <table>
        <tr>
        <td>
            <h3><i><%=(String) session.getAttribute("nickname")%></i> voce esta na sala: <b><%=roomname%></b>.</h3>
            <h3>Neste momento, <b><%=blackJackRoom.getNoOfWatchers()%></b> pessoa(s) nesta sala.</h3>
            <h3><b><%=blackJackRoom.getNoOfPlayers()%></b> jogadore(s).</h3>
            <h3>Round: <b><%=blackJackRoom.getRound()%></b></h3>

            <%
    /*se o jogo ja esta rodando*/
    if (blackJackRoom.gameIsRunnig()) {
        /*(!) Exibir os nomes dos jogadores aqui*/
            %>
            <h3>E a vez de <b><%=blackJackRoom.getCurrentPlayerName()%></b> jogar.</h3>

            <tr>
                <td width="100%">
                <p align="center">MESA:</td>
            </tr>
            <tr>
                <td width="100%">
                    <div align="center">
                        <center>
                            <table border="0" width="600" height="0">
                                <tr>
                                    <%
                ArrayList cards = blackJackRoom.getCards();
                int per = 0;
                if (cards.size() > 0) {
                    per = 100 / cards.size();
                }
                for (Object o : cards) {
                    Card c = (Card) o;
                                    %>
                                    <td width="<%= per%>%" align="center"> <img src="images/<%out.print(c.getValue() + "_" + c.getSuit());%>.gif"> </td>
                                    <%
                }
                if (blackJackRoom.getRound() > 1) {
                    if (blackJackRoom.getSum() == 21) {
                        if (cards.size() == 2) {
                            out.print("A mesa fez um blackjack!");
                        } else {
                            out.print("A mesa fez um 21.");
                        }
                    } else if (blackJackRoom.getSum() > 21) {
                        out.print("A mesa estourou pois somou " + blackJackRoom.getSum() + " pontos.");
                    } else {
                        out.print("A mesa fez " + blackJackRoom.getSum() + " pontos.");
                    }
                }
                if (blackJackRoom.getRound() == 3) {
                                    %>
                                    <h3><b><%out.print(player.getName() + ", ");%><%out.print(player.getStatus());%> e agora possui $<%out.print(player.getCash());%>.</b></h3>
                                    <%}%>
                                </tr>
                            </table>
                        </center>
                    </div>
                </td>
            </tr>
        </TD>
        <%
            }
        } else {
            response.sendRedirect("login.jsp");
        }
        %>

    </BODY>
</HTML>

