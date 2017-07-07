package com.machine.design.pattern.behavioral.iterator;

/**
 * Created by michael on 2017-07-03.
 */
public interface ChannelCollection {
    public void addChannel(Channel c);

    public void removeChannel(Channel c);

    public ChannelIterator iterator(ChannelTypeEnum type);
}