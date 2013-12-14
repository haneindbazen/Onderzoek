<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page import="com.simulatieTool.Webapp.*"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="org.drools.KnowledgeBase"%>
<%@ page import="org.drools.runtime.StatefulKnowledgeSession"%>
<%@ page import="org.drools.logger.KnowledgeRuntimeLogger"%>
<%@ page import="org.drools.logger.KnowledgeRuntimeLoggerFactory"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="java.util.ArrayList"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<script src="http://code.jquery.com/jquery-1.10.1.min.js"></script>
<title>Simulator Home</title>
</head>
<body>
	<script>
	function Test(){
		setInterval(function() {console.log("test")}, 2000);
	}
	
	</script>
<iframe id="interfaceFrame" src="http://localhost:8080/simulator/index.jsp"></iframe>
	Hello World;
	<%
	String transcriptPath = application.getRealPath("/meldingen.txt");
	KnowledgeBase kbase = Drools.readKnowledgeBase();
	StatefulKnowledgeSession ksession = kbase
			.newStatefulKnowledgeSession();
	KnowledgeRuntimeLogger logger = KnowledgeRuntimeLoggerFactory
			.newFileLogger(ksession, "test");
%>
	<%
		ArrayList<String> lines = TranscriptReader.GetLines(transcriptPath);
		for (String line : lines) {
	%>
	<%
		try {
				EventPusher event = new EventPusher();
				event.setMessage(line);
				ksession.insert(event);
				ksession.fireAllRules();
			} catch (Throwable t) {
				t.printStackTrace();
			}
	%>
		<script>Test();</script>
	<%
		}
	%>
	<%
		logger.close();
	%>
</body>
</html>