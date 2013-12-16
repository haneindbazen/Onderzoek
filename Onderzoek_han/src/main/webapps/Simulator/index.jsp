<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<script src="http://code.jquery.com/jquery-1.10.1.min.js"></script>
<script src="http://code.jquery.com/ui/1.10.3/jquery-ui.js"></script>
<link rel="stylesheet" type="text/css"
	href="http://code.jquery.com/ui/1.10.3/themes/smoothness/jquery-ui.css">
<title>Apache Tomcat WebSocket Examples: Echo</title>
<style>
button {
	margin-left: 400px;
	margin-right: auto;
	margin-top: 30px;
}

#board {
	width: 400px;
	margin-left: auto;
	margin-right: auto;
	margin-top: 10%;
	padding: 20px;
	background: black;
	color: #fff;
	font-size: 21px;
	font-weight: bold;
	line-height: 1.3em;
	border: 2px dashed #fff;
	border-radius: 10px;
	box-shadow: 0 0 0 4px #ff0030, 2px 1px 6px 4px rgba(10, 10, 0, 0.5);
	text-shadow: -1px -1px #aa3030;
	font-weight: normal;
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
		$("#board").html(event.data);
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
		$("#start").click(function() {
			ws.send("start simulator");
		});
	});
</script>
</head>
<body>
	<div id="board">Hier komt de melding !!!!</div>
	<button type="button" id="start">Start Simulator</button>
</body>
</html>