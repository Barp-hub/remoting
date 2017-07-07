package io.github.riwcwt.terminal.model;

/**
 * Created by michael on 2017-07-07.
 */
public class Command {
    private String command;

    public String getCommand() {
        return command;
    }

    public void setCommand(String command) {
        this.command = command;
    }

    @Override
    public String toString() {
        return "Command{" +
                "command='" + command + '\'' +
                '}';
    }
}
