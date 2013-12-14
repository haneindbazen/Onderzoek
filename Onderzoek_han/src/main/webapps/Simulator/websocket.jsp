<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
    <head>
        <meta charset=UTF-8>
        <title>Tomcat WebSocket Chat</title>
        <script>
            var ws = new WebSocket("ws://localhost:9090/pusher");
            ws.onopen = function(){
            };
            ws.onmessage = function(message){
                document.getElementById("chatlog").textContent += message.data + "\n";
            };
            function postToServer(){
                ws.send(document.getElementById("msg").value);
                document.getElementById("msg").value = "";
            }
            function closeConnect(){
                ws.close();
            }
        </script>
    </head>
    <body>
        <textarea id="chatlog" readonly></textarea><br/>
        <input id="msg" type="text" />
        <button type="submit" id="sendButton" onClick="postToServer()">Send!</button>
        <button type="submit" id="sendButton" onClick="closeConnect()">End</button>
    </body>
</html>