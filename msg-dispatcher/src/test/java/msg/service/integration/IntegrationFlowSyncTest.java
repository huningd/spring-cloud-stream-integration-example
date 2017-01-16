package msg.service.integration;

import msg.processor.domain.Message;
import msg.service.client.MessageHandlerClient;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@DirtiesContext
@TestPropertySource(properties = { "msg.direct = true" })
public class IntegrationFlowSyncTest {

    @Autowired
    private MessageGateway gateway;

    @MockBean
    private MessageHandlerClient client;

    @Test
    public void testSendSync(){
        gateway.generate(new Message("id", "text"));
        verify(client, times(1)).handleMessage(any());
    }
}
