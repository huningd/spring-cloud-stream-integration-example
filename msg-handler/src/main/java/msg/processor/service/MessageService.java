package msg.processor.service;

import msg.processor.domain.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * Created by dhuning on 11/01/2017.
 */
@Component
public class MessageService {
    private static final Logger LOGGER = LoggerFactory.getLogger(MessageService.class);

    public void handle(Message message){
        LOGGER.info("Handling message - id: {}, text: {}", message.getId(), message.getText());
    }
}
