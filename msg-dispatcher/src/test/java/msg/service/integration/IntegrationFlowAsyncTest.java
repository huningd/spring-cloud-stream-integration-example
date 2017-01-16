package msg.service.integration;

import msg.processor.domain.Message;
import msg.service.client.MessageHandlerClient;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.cloud.stream.test.binder.MessageCollector;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.concurrent.BlockingQueue;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verify;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@DirtiesContext
@TestPropertySource(properties = { "msg.direct = false" })
public class IntegrationFlowAsyncTest {

    @Autowired
    private MessageGateway gateway;

    @MockBean
    private MessageHandlerClient client;

    @Autowired
    private MessageCollector messageCollector;

    @Autowired
    private MessageSource source;

    @Test
    public void testSendAsync() throws InterruptedException {
        gateway.generate(new Message("id", "text"));
        verify(client, times(0)).handleMessage(any());

        final BlockingQueue<org.springframework.messaging.Message<?>> collector = messageCollector.forChannel(source.msgChannel());
        Assert.assertEquals(1, collector.size());
        final Object payload = collector.take().getPayload();
        Assert.assertTrue("Wrong message in MessageChannel", payload.toString().contains("text"));
    }
}
