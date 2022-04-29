package com.example.producer.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMqConfig {

    public static final String QUEUE1 = "test_queue_1";
    public static final String QUEUE2 = "test_queue_2";
    public static final String QUEUE3 = "test_queue_3";
    public static final String EXCHANGE_1 = "direct_exchange";
    public static final String EXCHANGE_2 = "fanout_exchange";
    public static final String EXCHANGE_3 = "topic_exchange";
    public static final String ROUTING_KEY_1 = "queue.1";
    public static final String ROUTING_KEY_2 = "queue.2";
    public static final String ROUTING_KEY_3 = "queue.3";
    public static final String ROUTING_KEY_ALL = "queue.*";
    @Bean
    public Queue queue() {
        return new Queue(QUEUE1);
    }

    @Bean
    public Queue queue2() {
        return new Queue(QUEUE2);
    }

    @Bean
    public Queue queue3() {
        return new Queue(QUEUE3);
    }

    @Bean
    public DirectExchange exchangeDirect() {
        return new DirectExchange(EXCHANGE_1);
    }

    @Bean
    public FanoutExchange exchangeFanout() {
        return new FanoutExchange(EXCHANGE_2);
    }

    @Bean
    public TopicExchange exchangeTopic() {
        return new TopicExchange(EXCHANGE_3);
    }

    @Bean
    public Binding bindingDirect(Queue queue, DirectExchange exchangeDirect) {
        return BindingBuilder
                .bind(queue)
                .to(exchangeDirect)
                .with(ROUTING_KEY_1);
    }

    @Bean
    public Binding bindingFanout1(Queue queue, FanoutExchange exchangeFanout) {
        return BindingBuilder
                .bind(queue)
                .to(exchangeFanout);
    }

    @Bean
    public Binding bindingFanout2(Queue queue2, FanoutExchange exchangeFanout) {
        return BindingBuilder
                .bind(queue2)
                .to(exchangeFanout);
    }

    @Bean
    public Binding bindingFanout3(Queue queue3, FanoutExchange exchangeFanout) {
        return BindingBuilder
                .bind(queue3)
                .to(exchangeFanout);
    }

    @Bean
    public Binding bindingTopic1(Queue queue, TopicExchange exchangeTopic) {
        return BindingBuilder
                .bind(queue)
                .to(exchangeTopic)
                .with(ROUTING_KEY_1);
    }

    @Bean
    public Binding bindingTopic2(Queue queue2, TopicExchange exchangeTopic) {
        return BindingBuilder
                .bind(queue2)
                .to(exchangeTopic)
                .with(ROUTING_KEY_2);
    }

    @Bean
    public Binding bindingTopic3(Queue queue3, TopicExchange exchangeTopic) {
        return BindingBuilder
                .bind(queue3)
                .to(exchangeTopic)
                .with(ROUTING_KEY_3);
    }
    @Bean
    public MessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public AmqpTemplate template(ConnectionFactory connectionFactory) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(messageConverter());
        return rabbitTemplate;
    }
}
