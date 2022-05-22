package com.example.planningpoker.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;


@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfiguration implements WebSocketMessageBrokerConfigurer {


    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {

        //at this endpoint vue will get web socket response
        registry.enableSimpleBroker("/backend-response");

        //at this endpoint vue will send request
        registry.setApplicationDestinationPrefixes("/backend-request");
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.
                addEndpoint("/register-websocket")
                .setAllowedOriginPatterns("http://localhost:[*]")
                .withSockJS();
    }
}
