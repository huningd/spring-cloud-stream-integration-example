package msg.processor.messaging;

import com.rabbitmq.client.Channel;
import msg.processor.domain.Message;
import msg.processor.service.MessageService;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class MessageChannelListener {
    private final MessageService service;

    public MessageChannelListener(MessageService service){
        this.service = service;
    }

    @StreamListener(MsgSink.CHANNEL_NAME)
    public void process(@Payload final Message message, @Header(value = AmqpHeaders.CHANNEL, required = false) final Channel channel,
                        @Header(value = AmqpHeaders.DELIVERY_TAG, required = false) final Long deliveryTag) throws IOException {
        // Channel and deliveryTag is null if channel acknowledgeMode isn't MANUAL
        if("error".equals(message.getText())){
            throw new RuntimeException("Error!!!");
        }
        service.handle(message);
        channel.basicAck(deliveryTag, false);
    }
}
