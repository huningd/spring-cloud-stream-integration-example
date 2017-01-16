package msg.service.integration;

import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;

public interface MessageSource {

    /*
        msgChannel is the name of the bindings properties specified in application.yml.
     */
    String CHANNEL_NAME = "msgChannel";

    @Output
    MessageChannel msgChannel();

}
