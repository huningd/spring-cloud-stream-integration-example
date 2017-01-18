package msg.service.integration;

import msg.processor.domain.Message;
import org.springframework.integration.annotation.Gateway;
import org.springframework.integration.annotation.MessagingGateway;
import org.springframework.messaging.handler.annotation.Header;

/**
 * Gateway to easily interact with integration flows.
 */
@MessagingGateway
public interface MessageGateway {
	@Gateway(requestChannel = "handler.input")
	void generate(Message message);

	@Gateway(requestChannel = "route.input")
	void generateRoute(Message message, @Header("direct") boolean direct);
}
