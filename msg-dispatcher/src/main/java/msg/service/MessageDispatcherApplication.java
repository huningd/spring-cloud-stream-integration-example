package msg.service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.integration.annotation.IntegrationComponentScan;

@SpringBootApplication
@EnableFeignClients
@IntegrationComponentScan
public class MessageDispatcherApplication {

    public static void main(String[] args) {
        SpringApplication.run(MessageDispatcherApplication.class, args);
    }

}
