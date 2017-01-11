package msg.processor.messaging;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.messaging.SubscribableChannel;

public interface MsgSink {
    String CHANNEL_NAME = "msgChannel";

    @Input
    SubscribableChannel msgChannel();
}
