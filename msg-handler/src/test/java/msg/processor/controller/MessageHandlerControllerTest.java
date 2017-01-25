package msg.processor.controller;

import msg.processor.domain.Message;
import msg.processor.domain.MessageHandler;
import msg.processor.service.MessageService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Matchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Created by dhuning on 25/01/2017.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@EnableFeignClients
@TestPropertySource(properties = {"messageHandler.ribbon.listOfServers = localhost:${local.server.port}", "ribbon.eureka.enabled = false"})
public class MessageHandlerControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private MessageHandlerClient client;

    @MockBean
    private MessageService service;

    @Test
    public void testWithFeignClient(){
        client.handleMessage(new Message("id","test"));
        Mockito.verify(service, Mockito.times(1)).handle(Matchers.any());
    }

    @FeignClient("messageHandler")
    public interface MessageHandlerClient extends MessageHandler{

    }

}