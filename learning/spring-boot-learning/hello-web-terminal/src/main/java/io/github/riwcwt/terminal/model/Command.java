package io.github.riwcwt.terminal.model;

/**
 * Created by michael on 2017-07-07.
 */
public class Command {

    public static final int COMMAND_LOGIN = 1;
    public static final int COMMAND_EXECUTE = 2;

    private Integer type;
    private String command;

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getCommand() {
        return command;
    }

    public void setCommand(String command) {
        this.command = command;
    }
}
