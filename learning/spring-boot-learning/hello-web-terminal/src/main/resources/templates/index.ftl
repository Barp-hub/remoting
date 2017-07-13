<html>
<head>
    <meta charset="UTF-8"/>
    <title>web terminal</title>
    <script src="/lib/sockjs/sockjs.min.js"></script>
    <script src="/lib/jquery/jquery-3.2.1.min.js"></script>
    <script src="/lib/jquery.terminal/js/jquery.terminal-1.4.3.min.js"></script>
    <script src="/lib/jquery.terminal/js/jquery.mousewheel-min.js"></script>
    <link href="/lib/jquery.terminal/css/jquery.terminal-1.4.3.min.css" rel="stylesheet"/>
</head>
<body>
<noscript><h2 style="color: #e80b0a;">Sorry，浏览器不支持WebSocket</h2></noscript>
<div>
    <div>
        <p class="terminal"></p>
    </div>
</div>
<script>
    $(function () {

        var token = '${token}';
        var ip = '${ip}';
        var terminal = $('.terminal').terminal(function (command, term) {
            if (command !== '') {
                try {
                    ws.send(JSON.stringify({"type": 2, "command": command}));
                } catch (e) {
                    term.error(new String(e));
                }
            } else {
                term.echo('');
            }
        }, {
            greetings: 'web terminal',
            name: 'terminal',
            height: 600,
            prompt: '~ > ',
            onBlur: function () {
                return false;
            }
        });

        var ws = new SockJS("http://" + location.host + "/shell");
        ws.onopen = function () {
            var login = {"ip": ip, "token": token};
            ws.send(JSON.stringify({"type": 1, "command": JSON.stringify(login)}));
        };

        ws.onmessage = function (event) {
            terminal.echo(new String(event.data));
        }

        ws.onclose = function () {
            terminal.error("连接断开!");
            terminal.disable()
        };

    });
</script>
</body>
</html>