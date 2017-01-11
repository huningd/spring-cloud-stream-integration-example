package msg.service.integration;

import msg.processor.domain.Message;
import org.springframework.integration.annotation.Gateway;
import org.springframework.integration.annotation.MessagingGateway;

@MessagingGateway
public interface MessageGateway {
	@Gateway(requestChannel = MessageSource.CHANNEL_NAME)
	void generate(Message message);

	@Gateway(requestChannel = "handler.input")
	void generateDirect(Message message);
}
