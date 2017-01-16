package msg.processor.messaging;

import msg.processor.domain.Message;
import msg.processor.service.MessageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.stereotype.Service;

@Service
@ConditionalOnProperty(value = "spring.cloud.stream.bindings." + MsgSink.CHANNEL_NAME + ".binder", havingValue = "kafka")
public class MessageChannelListenerKafka {

    private static final Logger LOGGER = LoggerFactory.getLogger(MessageChannelListenerKafka.class);
    private final MessageService service;

    public MessageChannelListenerKafka(MessageService service){
        this.service = service;
    }

    @StreamListener(MsgSink.CHANNEL_NAME)
    public void process(org.springframework.messaging.Message<Message> message) {
        Acknowledgment acknowledgment = message.getHeaders().get(KafkaHeaders.ACKNOWLEDGMENT, Acknowledgment.class);
        if("error".equals(message.getPayload().getText())){
            throw new RuntimeException("Error!!!");
        }
        service.handle(message.getPayload());
        if (acknowledgment != null) {
            LOGGER.info("Acknowledgment provided");
            acknowledgment.acknowledge();
        }
    }
}
