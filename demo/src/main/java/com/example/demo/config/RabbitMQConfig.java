package com.example.demo.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    @Value("${amqp.queue.transaction-completed}")
    private String queueName;

    @Value("${amqp.exchange.notification}")
    private String exchangeName;

    @Value("${amqp.routing-key.transaction-completed}")
    private String routingKey;

    @Bean
    public Queue transactionCompletedQueue() { return new Queue(queueName, true); }

    @Bean
    public TopicExchange notificationExchange() { return new TopicExchange(exchangeName); }

    @Bean
    public Binding binding(Queue queue, TopicExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with(routingKey);
    }

    @Bean
    public Jackson2JsonMessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }
}