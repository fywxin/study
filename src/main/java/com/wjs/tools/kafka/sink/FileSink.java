package com.wjs.tools.kafka.sink;

import cn.hutool.core.io.FileUtil;
import org.apache.kafka.clients.consumer.ConsumerRecord;

/**
 * @author: 王金绍
 * @create: 2023-06-06 14:30
 **/
public class FileSink implements Sink {

    final String fileName;

    public FileSink(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public void open() {

    }

    @Override
    public void save(ConsumerRecord<String, String> record) {
        FileUtil.appendString(record.value()+"\n", fileName, "UTF-8");
    }

    @Override
    public void close() {

    }
}
