package io.github.riwcwt.terminal.model;

/**
 * Created by michael on 2017-07-07.
 */
public class Message {

    public static final int MESSAGE_NORMAL = 1;
    public static final int MESSAGE_ERROR = 2;

    private Integer type;
    private String content;

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
