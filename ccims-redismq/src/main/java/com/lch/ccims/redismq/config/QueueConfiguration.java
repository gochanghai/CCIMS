package com.lch.ccims.redismq.config;

import com.lch.ccims.redismq.service.MsgConsumer;

public class QueueConfiguration {

    /**
     * 队列名称
     */
    private String queue;
    /**
     * 消费者
     */
    private MsgConsumer consumer;

    private QueueConfiguration() {
    }

    public static Builder builder() {
        return new Builder();
    }

    public String getQueue() {
        return queue;
    }

    public MsgConsumer getConsumer() {
        return consumer;
    }

    public static class Builder {

        private QueueConfiguration configuration = new QueueConfiguration();

        public QueueConfiguration defaultConfiguration(MsgConsumer consumer) {
            configuration.consumer = consumer;
            configuration.queue = consumer.getClass().getSimpleName();
            return configuration;
        }

        public Builder queue(String queue) {
            configuration.queue = queue;
            return this;
        }

        public Builder consumer(MsgConsumer consumer) {
            configuration.consumer = consumer;
            return this;
        }

        public QueueConfiguration build() {
            if (configuration.queue == null || configuration.queue.length() == 0) {
                if (configuration.consumer != null) {
                    configuration.queue = configuration.getClass().getSimpleName();
                }
            }
            return configuration;
        }

    }
}
