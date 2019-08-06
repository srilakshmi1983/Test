package com.jpmorgan.messages;

public interface MessageConsumer {
    void process(String message);
}
