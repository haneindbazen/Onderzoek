<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ page import="com.simulatieTool.*" %>
<%@ page import="java.util.ArrayList" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Event Pusher</title>
</head>
<body>
<%ArrayList<String> lines = EventPusher.Push("http:///localhost:9090/home/meldingen.txt");%>
<%
for(String line: lines){%>
Message: <%=line%>
<%
Thread.sleep(4000);
}%>
</body>
</html>