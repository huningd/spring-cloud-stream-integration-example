# Description
This example show how to use Spring Cloud Stream to send and receive message via RabbitMQ. The service msg-dispatcher publishes a endpoint to generate messages. The message could be send via direct channel or RabbitMQ to the service message-handler.

You can use curl to generate a message. The following command will send a message via RabbitMQ.
```
curl -d {} http://localhost:18080/generateMsg\?text\=test
```
To send a message via a direct channel use the next command.
```
curl -d {} http://localhost:18080/generateMsg\?text\=test&direct=true
```


# Execute
Execute run.sh to build this example and start the services. The run script will use the docker-compose to start some containers.