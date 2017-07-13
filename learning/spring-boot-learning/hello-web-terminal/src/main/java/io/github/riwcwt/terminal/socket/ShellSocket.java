package io.github.riwcwt.terminal.socket;

import com.alibaba.fastjson.JSON;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.jcabi.ssh.SSH;
import com.jcabi.ssh.Shell;
import io.github.riwcwt.terminal.model.Command;
import io.github.riwcwt.terminal.model.LoginCommand;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.File;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

/**
 * Created by michael on 2017-07-07.
 */
public class ShellSocket extends TextWebSocketHandler {

    public static final String[] COMMAND = new String[]{"ll", "ls", "ifconfig", "netstat", "cat", "pwd", "tail", "df", "du", "ps"};

    private static final Logger logger = LoggerFactory.getLogger(ShellSocket.class);

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        logger.info("received : " + message.getPayload());

        Command command = JSON.parseObject(message.getPayload(), Command.class);
        if (command.getType() == Command.COMMAND_LOGIN) {
            LoginCommand login = JSON.parseObject(command.getCommand(), LoginCommand.class);
            if (ShellSocket.hasToken(login.getToken())) {
                Shell shell = new SSH(login.getIp(), 22, "root", FileUtils.readFileToString(new File("/root/.ssh/id_rsa")));
                session.getAttributes().put("shell", shell);
                session.sendMessage(new TextMessage("login succeed!"));
            } else {
                session.close();
            }
        } else if (command.getType() == Command.COMMAND_EXECUTE) {
            Shell shell = Shell.class.cast(session.getAttributes().get("shell"));
            if (shell == null) {
                session.close();
            } else {
                String cmd = command.getCommand().trim();
                if (this.isPermittedCommand(cmd)) {
                    String content = new Shell.Plain(shell).exec(cmd);
                    session.sendMessage(new TextMessage(content));
                } else {
                    session.sendMessage(new TextMessage("命令不支持！"));
                }
            }
        } else {
            session.close();
        }
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        session.sendMessage(new TextMessage("支持使用的命令：" + StringUtils.join(COMMAND, ",")));
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
    }

    private boolean isPermittedCommand(String command) {
        for (String cmd : COMMAND) {
            if (command.toLowerCase().startsWith(cmd)) {
                return true;
            }
        }
        return false;
    }

    public static Cache<String, String> cache = CacheBuilder
            .newBuilder().maximumSize(100).expireAfterWrite(90, TimeUnit.SECONDS)
            .build();

    public static void addToken(String token) {
        cache.put(token, "1");
    }

    public static boolean hasToken(String token) {
        try {
            String data = cache.get(token, () -> "null");
            if (StringUtils.isEmpty(data)) {
                return false;
            }
            if ("null".equalsIgnoreCase(data)) {
                return false;
            }
        } catch (ExecutionException e) {
            return false;
        }

        cache.invalidate(token);
        return true;
    }
}
