
<%@ page isErrorPage="false" errorPage="error.jsp" import="java.util.Set,java.util.Iterator,java.util.ArrayList,java.util.Map,tpw.cassino.*"%>
<%
        String nickname = (String) session.getAttribute("nickname");
        if (nickname != null) {
            BlackJackRoomList roomlist = (BlackJackRoomList) getServletContext().getAttribute("blackjackroomlist");
            BlackJackRoom blackJackRoom = (BlackJackRoom) roomlist.getRoomOfWatcher(nickname);
            Player player = blackJackRoom.getWatcher(nickname);
            String roomname = blackJackRoom.getName();
            System.out.println("player option");
%>
<HTML>
    <HEAD>
        <LINK rel="stylesheet" type="text/css" href="cassino.css">
    </HEAD>
    <BODY  bgcolor="#FFFFFF">
        <TABLE width="100%" cellpadding="3" cellspacing="0">
            <TR>
                <%
    /*Se o jogo estiver rodando, imprime as cartas do jogador*/
    if (blackJackRoom.gameIsRunnig()) {

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
                    if (player.getSum() == 21) {
                        if (cards.size() == 2) {
                            out.print("Você fez um blackjack.");
                        } else {
                            out.print("Você fez 21.");
                        }
                    } else if (player.getSum() > 21) {
                        out.print("Você estourou pois somou " + player.getSum() + " pontos.");
                    } else {
                        out.print("Você tem " + player.getSum() + " pontos.");
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
                    <% if (player.getTurn()) {%>
                    Eh a sua vez <%=player.getName()%>.
                    <% } else {%>
                    Nao e a sua vez <%=player.getName()%>.
                    <% }%>
                    <TABLE border="1" cellpadding="3" cellspacing="0" class="panel">
                        <TR align="left" valign="top">
                            <% if (blackJackRoom.getRound() == 3) {%>
                            <FORM name="sair" method="post" action="<%=request.getContextPath()%>/servlet/blackJack">
                                <TD width="15%">
                                    <INPUT type="hidden" name="rn" value="<%=roomname%>">
                                    <INPUT type="hidden" name="n" value="<%=nickname%>">
                                    <INPUT name="sair" type="submit" id="Sair" value="Sair">
                                </TD>
                            </FORM>
                            <% } else {%>
                            <FORM name="hit" method="post" action="<%=request.getContextPath()%>/servlet/blackJack">
                                <TD width="15%">
                                    <INPUT type="hidden" name="rn" value="<%=roomname%>">
                                    <INPUT type="hidden" name="n" value="<%=nickname%>">
                                    <INPUT type="hidden" name="h" value="1">
                                    <% if (player.getTurn() && player.getSum() < 21) {%>
                                    <INPUT name="hit" type="submit" id="Hit" value="Hit">
                                    <% } else {%>
                                    <INPUT name="hit" type="button" id="Hit" value="Hit">
                                    <% }%>
                                </TD>
                            </FORM>
                            <FORM name="stand" method="post" action="<%=request.getContextPath()%>/servlet/blackJack">
                                <TD width="15%">
                                    <INPUT type="hidden" name="rn" value="<%=roomname%>">
                                    <INPUT type="hidden" name="n" value="<%=nickname%>">
                                    <INPUT type="hidden" name="s" value="1">
                                    <% if (player.getTurn()) {%>
                                    <INPUT name="stand" type="submit" id="Stand" value="Stand">
                                    <% } else {%>
                                    <INPUT name="stand" type="button" id="Stand" value="Stand">
                                    <% }%>
                                </TD>
                            </FORM>
                            <% }%>
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

        } else {
            response.sendRedirect("login.jsp");
        }
%>







