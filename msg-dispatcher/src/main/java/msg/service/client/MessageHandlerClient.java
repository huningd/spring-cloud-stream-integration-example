package msg.service.client;

import org.springframework.cloud.netflix.feign.FeignClient;
import msg.processor.domain.MessageHandler;


@FeignClient( serviceId = "handler")
public interface MessageHandlerClient extends MessageHandler {
}
