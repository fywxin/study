package com.wjs.tools.kafka;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.wjs.tools.kafka.sink.FileSink;
import com.wjs.tools.kafka.sink.KafkaSink;
import com.wjs.tools.kafka.sink.Sink;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.TopicPartition;
import org.apache.kafka.common.serialization.StringDeserializer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

/**
 * @author: 王金绍
 * @create: 2023-06-06 10:52
 **/
public class KafkaCustomer {

    final Sink sink;

    public KafkaCustomer(Sink sink) {
        this.sink = sink;
    }

    public void consumer(String url, String topics, String fromOffsets, Integer maxSize){
        KafkaConsumer<String, String> consumer = null;
        List<ConsumerRecord<String, String>> list = new ArrayList<>();
        try {
            sink.open();
            consumer = KafkaUtil.getConsumer(url);
            if (StrUtil.isNotBlank(topics)){
                consumer.subscribe(Arrays.asList(topics.split(",")));
            }else if (StrUtil.isNotBlank(fromOffsets)){
                JSONObject obj = JSON.parseObject(fromOffsets);
                String topic = obj.keySet().iterator().next();
                JSONObject partitions = obj.getJSONObject(topic);
                for (String partitionKey : partitions.keySet()){
                    consumer.seek(new TopicPartition(topic, Integer.parseInt(partitionKey)), partitions.getLong(partitionKey));
                }
            }
            int i = 0;
            while (true){
                ConsumerRecords<String, String> records = consumer.poll(3000);
                for (ConsumerRecord<String, String> record : records) {
                    sink.save(record);
                    i++;
                    if (i> maxSize){
                        break;
                    }
                }
            }
        }finally {
            if (consumer != null){
                consumer.close();
            }
            sink.close();
        }
    }

    public static void main(String[] args) {
        //Sink sink = new FileSink("/home/wjs/1.txt");
        Sink sink = new KafkaSink("10.0.0.186:9092", "sdk_recharge_app_log_stream");
        KafkaCustomer customer = new KafkaCustomer(sink);
        customer.consumer("ger-frankfurt-kafka-core-001:9092,ger-frankfurt-kafka-core-003:9092,ger-frankfurt-kafka-core-005:9092,ger-frankfurt-kafka-core-006:9092", "sdk_recharge_app_log_stream", null, 1000);
    }
}
