<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page import="com.han.simulator.utils.Workspace" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<script src="http://code.jquery.com/jquery-1.10.1.min.js"></script>
<script src="http://code.jquery.com/ui/1.10.3/jquery-ui.js"></script>
<link rel="stylesheet" type="text/css"
	href="http://code.jquery.com/ui/1.10.3/themes/smoothness/jquery-ui.css">
	<link rel="stylesheet" href="http://yui.yahooapis.com/pure/0.3.0/pure-min.css">
	<script language="javascript" type="text/javascript">
  function resizeIframe(obj) {
    obj.style.height = obj.contentWindow.document.body.scrollHeight + 'px';
  }
</script>
<title>Apache Tomcat WebSocket Examples: Echo</title>
<style>
#iframe{
border :0;
width:99%;
height:100%;
}

#gunvorLink{
margin: 10px 0px 10px 30px;
}
.pure-button-small {
            font-size: 85%;
        }
</style>
<script type="text/javascript">
	var ws = null;

	if ('WebSocket' in window) {
		ws = new WebSocket("ws://localhost:9090/pusher");
	} else if ('MozWebSocket' in window) {
		ws = new MozWebSocket("ws://localhost:9090/pusher");
	} else {
		alert('WebSocket is not supported by this browser.');
		return;
	}
	ws.onopen = function() {
	};
	ws.onmessage = function(event) {
		console.log(event.data);
		$("#received").val(event.data);
		var melding = event.data;
		var screenName = melding.split("#")[1];
		var meldingValue = melding.split("#")[0];
		var screenSrc = "/simulator/interfaces/"+"<%=Workspace.prototypeName%>"+"/review/screens/"+screenName+".html";
		$('#iframe').attr('src', screenSrc);
	};
	ws.onclose = function() {
	};

	function disconnect() {
		if (ws != null) {
			ws.close();
			ws = null;
		}
	}
	$(document).ready(function() {
		$(".pure-button").click(function() {
			ws.send("start#"+$("#gunvorLink").val());
		});
	});
</script>
</head>
<body style= "margin:0px;padding:0px;overflow:hidden">
<input id="gunvorLink" class="pure-input-1-2" size="60" type="text" placeholder="gunvor rules link">
<button class="pure-button">Start</button>
<input id="received" class="pure-input-1-2" size="60"type="text" placeholder="message received"></br>
</body>
</html>