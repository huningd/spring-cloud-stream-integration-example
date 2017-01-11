package msg.service.controller;

import msg.processor.domain.Message;
import msg.service.integration.MessageGateway;
import org.apache.commons.lang.BooleanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.UUID;

@Controller
public class MessageController {

    @Autowired
    private MessageGateway messageGateway;

    @RequestMapping("/generateMsg")
    @ResponseBody
    public Message generateWork(@RequestParam("text") String text, @RequestParam(value = "direct", required = false) Boolean direct ) {
        Message sampleMessage = new Message(UUID.randomUUID().toString(), text);
        if(BooleanUtils.isTrue(direct)){
            messageGateway.generateDirect(sampleMessage);
        }else {
            messageGateway.generate(sampleMessage);
        }
        return sampleMessage;
    }
}
