<!-- Written by Sukhwinder Singh (ssruprai@hotmail.com -->
<%-- Written by Sukhwinder Singh (ssruprai@hotmail.com --%>

<!-- I have left this file as jsp because in future some authentication may be added here -->

<html>
<head>
	<title>Add new Room</title>
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/chat.css">
	<meta name="Author" content="Sukhwinder Singh (ssruprai@hotmail.com">	
</head>

<body>
<%@ include file="header.jsp" %>

<div align="center">
<center>
<form action="<%=request.getContextPath()%>/servlet/manageChat" method="post">
<table width="80%" cellpadding="0" cellspacing="0" border="0">
	<%
	Object e = request.getAttribute("error");
	String error = null;
	if ( e != null)
	{
		error = (String)e;
	%>
	<tr>
		<td colspan="2"><h3 class="error"><%=error%></h3></td>
	</tr>
	<%
	}
	%>
	<tr>
		<td colspan="2"><h2>Add new Room</h2></td>
	</tr>
	<tr>
		<td><b>Room Name (no spaces)</b></td><td><input type="text" name="rn"></td>
	</tr>
	<tr>
		<td><b>Description</b></td><td><textarea rows="5" cols="30" name="rd"></textarea></td>
	</tr>
	<tr>
		<td>&nbsp;</td><td><input type="submit" value="Submit"></td>
	</tr>
</table>
</form>
</center>
</div>
<%@ include file="/footer.jsp"%>

</body>
</html>