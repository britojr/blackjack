<%@ page errorPage="error.jsp" %>
<%
	String nickname = (String)session.getAttribute("nickname");
	<!-- Se já estiver logado, vai para a pagina de salas -->
	if (nickname != null) {
		response.sendRedirect("blacJack.jsp");
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
	<BODY bgcolor="#FFFFFF" > <!-- (?)  onLoad="document.login.nickname.focus();">  --> 
	<%@ include file="/header.jsp" %>
		<TABLE width="40%" border="0" cellspacing="1" cellpadding="1" align="center">
			<%
			/*obs: qdo trabalhar com as strings usar o trim antes de ver o tamanho*/
			String d=request.getParameter("d");
			String n=request.getParameter("n");
			String ic = request.getParameter("ic");

			if (d!=null && d.equals("t")) {
			%>
				
			<TR>
				<TD>
					<TABLE width="100%" border="0" cellspacing="1" cellpadding="1" align="center">
						<TR>
							<TD colspan="2" align="center">
								<SPAN class="error">Nickname exists</SPAN><BR>
							</TD>
						</TR>
						<TR>
							<TD colspan="2">
								Nickname <B><%=n%></B> has already been taken please select some other nick.
							</TD>
						</TR>
					</TABLE>
				</TD>
			</TR>
			<%
			}
			else if (ic!=null && ic.equals("t")) {
			%>
			<TR>
				<TD colspan="2">
					<TABLE width="100%" border="0" cellspacing="1" cellpadding="1" align="center">
						<TR>
							<TD colspan="2" align="center">
								<SPAN class="error">Incomplete information</SPAN>
							</TD>
						</TR>
						<TR>
							<TD colspan="2">
								<b>Bet</b> and <b>Nickname</b> must be entered and nickname must be atleast <b>4</b> characters long and must not contain any <b>space</b>.
							</TD>
						</TR>
					</TABLE>
				</TD>
			</TR>
			<%
			}
			%>
			<TR>
				<TD colspan="2" class="panel">
					<!--<FORM name="login" method="post" action="<%=request.getContextPath()%>/WEB-INF/classes/chat/servlet/LoginServlet.java">-->
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
								<TD width="30%" class="white">
									Bet
								</TD>
								<TD width="70%">
								  <INPUT type="text" name="bet" size="10">
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


