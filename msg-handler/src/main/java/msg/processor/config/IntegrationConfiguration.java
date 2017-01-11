package msg.processor.config;

import msg.processor.messaging.MsgSink;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableBinding(MsgSink.class)
public class IntegrationConfiguration {

}
