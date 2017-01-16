package msg.service.integration;

import msg.processor.domain.Message;
import msg.service.client.MessageHandlerClient;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.dsl.IntegrationFlow;

/**
 * This configuration defines three integration flows. The route integration flow deciedes on the header direct if the message should be send via FeignClient or via a message queue.
 */
@Configuration
@EnableBinding(MessageSource.class)
public class IntegrationConfiguration {

    @Bean("handler")
    @ConditionalOnProperty(value = "msg.direct", havingValue = "true", matchIfMissing = true)
    IntegrationFlow handlerSync(MessageHandlerClient client) {
        return f -> f
                .<Message>handle((payload, headers)
                                ->
                        {
                            client.handleMessage(payload);
                            return null;
                        }
                );
    }

    @Bean("handler")
    @ConditionalOnProperty(value = "msg.direct", havingValue = "false")
    IntegrationFlow handlerAsync(MessageHandlerClient client) {
        return f -> f
                .channel(MessageSource.CHANNEL_NAME);
    }


    @Bean
    IntegrationFlow route(MessageHandlerClient client) {
        return f -> f
                .<Message>route("headers['direct']", m ->
                            m
                                    .channelMapping("true", "handler.input")
                                    .channelMapping("false", MessageSource.CHANNEL_NAME)

                );
    }
}
