package msg.service.config;

import msg.processor.domain.Message;
import msg.service.client.MessageHandlerClient;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.IntegrationComponentScan;
import org.springframework.integration.annotation.MessagingGateway;
import org.springframework.integration.dsl.IntegrationFlow;

@Configuration
public class IntegrationConfiguration {


    @Bean
    IntegrationFlow handler(MessageHandlerClient client) {
        return f -> f
                .<Message>handle((payload, headers) ->
                        {
                            client.handleMessage(payload);
                            return null;
                        }
                );
    }
}
