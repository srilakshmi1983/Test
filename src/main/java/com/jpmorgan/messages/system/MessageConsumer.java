package com.jpmorgan.messages.system;


public interface MessageConsumer {
    void process(String message);
}
