package msg.service.integration;

import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;

public interface MessageSource {

    String CHANNEL_NAME = "msgChannel";

    @Output
    MessageChannel msgChannel();

}
