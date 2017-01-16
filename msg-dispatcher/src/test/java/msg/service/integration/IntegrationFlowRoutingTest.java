package msg.service.integration;

import msg.processor.domain.Message;
import msg.service.client.MessageHandlerClient;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.cloud.stream.binder.BinderFactory;
import org.springframework.cloud.stream.test.binder.MessageCollector;
import org.springframework.messaging.MessageChannel;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.concurrent.BlockingQueue;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@TestPropertySource(properties = { "msg.direct = true" })
@DirtiesContext
public class IntegrationFlowRoutingTest {

    @Autowired
    private MessageGateway gateway;

    @Autowired
    private BinderFactory<MessageChannel> binderFactory;

    @Autowired
    private MessageCollector messageCollector;

    @Autowired
    private MessageSource source;

    @MockBean
    private MessageHandlerClient client;

    @Test
    public void testSendRouting() throws InterruptedException {
        final Message expectedDirect = new Message("id", "direct");
        final Message messageMessaging = new Message("id", "messaging");

        gateway.generateRoute(expectedDirect, true);
        gateway.generateRoute(messageMessaging, false);

        // Test direct handler
        ArgumentCaptor<Message> captor = ArgumentCaptor.forClass(Message.class);
        verify(client, times(1)).handleMessage(captor.capture());
        Assert.assertEquals("direct", captor.getValue().getText());

        // Test messaging handler
        final BlockingQueue<org.springframework.messaging.Message<?>> collector = messageCollector.forChannel(source.msgChannel());
        Assert.assertEquals(1, collector.size());
        final Object payload = collector.take().getPayload();
        Assert.assertTrue("Wrong message in MessageChannel", payload.toString().contains("messaging"));
    }

}