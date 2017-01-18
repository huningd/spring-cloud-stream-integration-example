package msg.service.integration;

import msg.processor.domain.Message;
import msg.service.client.MessageHandlerClient;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.dsl.IntegrationFlow;

/**
 * This configuration defines integration flows and all other components regarding to the integration logic.
 */
@Configuration
@EnableBinding(MessageSource.class)
public class IntegrationConfiguration {

    /**
     * This flow uses the feign client {@link MessageHandlerClient} to send message to the msg-handler service. You can active this flow through defining the property msg.direct and assign the value true.
     */
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

    /**
     * This flow routes an message to the message channel msgChannel. It will be active if the property msg.direct is false.
     */
    @Bean("handler")
    @ConditionalOnProperty(value = "msg.direct", havingValue = "false")
    IntegrationFlow handlerAsync(MessageHandlerClient client) {
        return f -> f
                .channel(MessageSource.CHANNEL_NAME);
    }


    /**
     * This integration flow is an example of how to route an message depending on a header. If the value of header direct ist true the message will be send via FeignClient otherwise via a message queue.
     */
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
