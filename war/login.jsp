<%@ page errorPage="error.jsp" %>
<%
        String nickname = (String) session.getAttribute("nickname");
//<!-- Se já estiver logado, vai para a pagina de salas -->
        if (nickname != null) {
            response.sendRedirect("listrooms.jsp");
        }
%>
<HTML>
    <HEAD>
        <TITLE>
            Cassino - Login
        </TITLE>
        <!--ContextPath é a pasta da aplicacao-->
        <LINK rel="stylesheet" href="<%=request.getContextPath()%>/cassino.css">
    </HEAD>
    <BODY bgcolor="#FFFFFF" onLoad="document.login.nickname.focus();"> 

        <%@ include file="/header.jsp" %>
        <TABLE width="40%" border="0" cellspacing="1" cellpadding="1" align="center">
            <%
        String d = request.getParameter("d"); //duplicado
        String n = request.getParameter("n"); //nickname
        String ic = request.getParameter("ic"); //imcompleto
        if (d != null && d.equals("t")) {
            %>

            <TR>
                <TD>
                    <TABLE width="100%" border="0" cellspacing="1" cellpadding="1" align="center">
                        <TR>
                            <TD colspan="2" align="center">
                                <SPAN class="error">Nickname já existe</SPAN><BR>
                            </TD>
                        </TR>
                        <TR>
                            <TD colspan="2">
                                Este nickname <B><%=n%></B> já foi escolhido.
                            </TD>
                        </TR>
                    </TABLE>
                </TD>
            </TR>
            <%
            } else if (ic != null && ic.equals("t")) {
            %>
            <TR>
                <TD colspan="2">
                    <TABLE width="100%" border="0" cellspacing="1" cellpadding="1" align="center">
                        <TR>
                            <TD colspan="2" align="center">
                                <SPAN class="error">Informacao imcompleta</SPAN>
                            </TD>
                        </TR>
                        <TR>
                            <TD colspan="2">
                                <b>Nickname</b> deve conter no minimo <b>4</b> caracteres sem espaco.
                            </TD>
                        </TR>
                    </TABLE>
                </TD>
            </TR>
            <%            }
            %>
            <TR>
                <TD colspan="2" class="panel">
                    <FORM name="login" method="post" action="<%=request.getContextPath()%>/servlet/login">
                        <TABLE width="100%" border="0">
                            <TR>
                                <TD width="30%" class="white">
                                    Nickname
                                </TD>
                                <TD width="70%">
                                    <INPUT type="text" name="nickname" size="15">
                                </TD>
                            </TR>
                            <TR>
                                <TD>&nbsp;</TD>
                                <TD>
                                    <INPUT type="submit" name="Enter" value="Enter">
                                </TD>
                            </TR>
                        </TABLE>
                    </FORM>
                </TD>
            </TR>
        </TABLE>
    </BODY>
</HTML>


