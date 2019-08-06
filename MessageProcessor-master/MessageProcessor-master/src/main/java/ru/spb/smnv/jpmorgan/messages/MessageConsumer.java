package ru.spb.smnv.jpmorgan.messages;

public interface MessageConsumer {
    void process(String message);
}
