package com.wjs.tools.kafka.sink;

import com.wjs.tools.kafka.KafkaUtil;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;

/**
 * @author: 王金绍
 * @create: 2023-06-06 14:36
 **/
public class KafkaSink implements Sink {

    final String topic;
    final String url;
    KafkaProducer<String, String> producer;

    public KafkaSink(String url, String topic) {
        this.url = url;
        this.topic = topic;
    }

    @Override
    public void open() {
        this.producer = KafkaUtil.getProducer(url);
    }

    @Override
    public void save(ConsumerRecord<String, String> item) {
        ProducerRecord<String, String> record = new ProducerRecord<>(topic, item.key(), item.value());
        producer.send(record);
    }

    @Override
    public void close() {
        this.producer.close();
    }
}
