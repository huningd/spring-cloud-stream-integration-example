# Description
This example show how to use Spring Cloud Stream to send and receive message via RabbitMQ or Apache Kafka. The service msg-dispatcher publishes a endpoint to generate messages. The message could be send via a direct channel or via a message channel to the service message-handler.

You can use curl to generate a message.
```
curl http://localhost:18080/generateMsg?text=test
```
Use the flag msg.direct to switch between message channel and direct channel. Is msg.direct true the integration flow will defined in IntegrationConfiguration will route the message to an feign client otherwise it will send it to the outgoing queue msg.exchange.

# PURPOSE
- Abstract the concrete broker from the business logic. For the business logic it's unnecessary to know if we use RabbitMQ, Apache Kafka, Amazon SQS or something else for messaging.
- Deal with error
    - We define three general types of exceptions: 
        - InfrastructureExceptions (Timeouts, Service unavailable, etc), 
        - BusinessLogicExceptions (Happens in the business logic) 
        - and TechnicalException (NullPointer, array out of bounds, etc). 
    - You need separate error handling for each type of errors. But for every type of error it's important to know which message leads to an error. Hence persist all messages an do not accidentally delete an message if an exception occurs. In the most messaging system you can switch from an automatically acknowledge mode to a manuel. In this mode you can guarantee that a message will not be delete before you send the acknowledge. In the mean time the message is in a pending state. If a logical exception or a technical exceptions occurs it's unnecessary to retry message delivery. This kind of errors will appear again. A better way to deal with this error is to move the message in a dead letter queue. Only when an infrastructure error occurs it's a good decision to use a retry mechanism with exponential backoff.   
 
 
# Execute
## Exceute via docker-compose
Execute run.sh to build this example and start the services. The run script will use the docker-compose to start some containers.
```
./run.sh docker/rabbit
```
## Execute manual
1. mvn package
1. docker run -d --hostname my-rabbit --name some-rabbit -p 15672:15672 -p 5672:5672 rabbitmq:3-management
1. cd msg-dispatcher
1. java -jar target/msg-dispatcher-0.0.1-SNAPSHOT.jar 
1. cd msg-handler
1. java -jar target/msg-handler-0.0.1-SNAPSHOT.jar

# Further information:
Spring Integration Java DSL Tuturial: https://spring.io/blog/2014/11/25/spring-integration-java-dsl-line-by-line-tutorial
Spring Integration Java DSL https://github.com/spring-projects/spring-integration-java-dsl/wiki/spring-integration-java-dsl-reference#spring-integration-java-dsl
Testing Spring Cloud Stream http://docs.spring.io/spring-cloud-stream/docs/Brooklyn.RELEASE/reference/html/_testing.html
# Open Issues
- It seems that there is no binder for aws sns or sqs in spring cloud stream. https://github.com/spring-cloud/spring-cloud-stream/issues/135
- Create an Dockerfile to test sqs local. This should be possible with elasticmq