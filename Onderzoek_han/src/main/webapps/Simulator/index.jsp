<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<script src="http://code.jquery.com/jquery-1.10.1.min.js"></script>
<script src="http://code.jquery.com/ui/1.10.3/jquery-ui.js"></script>
<link rel="stylesheet" type="text/css"
	href="http://code.jquery.com/ui/1.10.3/themes/smoothness/jquery-ui.css">
	<link rel="stylesheet" href="http://yui.yahooapis.com/pure/0.3.0/pure-min.css">
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
	//var interfaceDir = "C:\Users\ndizigiye\Pictures\simulator\";

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
		$("#received").val(event.data);
		console.log(event.data);
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
<body>
<input id="gunvorLink" class="pure-input-1-2" type="text" placeholder="gunvor rules link">
<button class="pure-button">Start</button>
<input id="received" class="pure-input-1-2" size="60"type="text" placeholder="message received"></br>
<iframe id="iframe" src="/simulator/websocket.jsp"></iframe>
</body>
</html>