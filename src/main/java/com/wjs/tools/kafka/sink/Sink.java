package com.wjs.tools.kafka.sink;

import org.apache.kafka.clients.consumer.ConsumerRecord;

/**
 * @author: 王金绍
 * @create: 2023-06-06 14:30
 **/
public interface Sink {

    void open();

    void save(ConsumerRecord<String, String> record);

    void close();
}
