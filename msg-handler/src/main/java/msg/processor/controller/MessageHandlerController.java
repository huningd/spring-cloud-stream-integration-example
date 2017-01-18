package msg.processor.controller;

import msg.processor.domain.Message;
import msg.processor.domain.MessageHandler;
import msg.processor.service.MessageService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class MessageHandlerController implements MessageHandler {

    private final MessageService service;

    public MessageHandlerController(MessageService service){
        this.service = service;
    }

    @Override
    public void handleMessage(@RequestBody Message message) {
        service.handle(message);
    }
}
