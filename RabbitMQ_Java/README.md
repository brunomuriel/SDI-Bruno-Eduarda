## Orientações

http://www.rabbitmq.com/getstarted.html
sudo rabbitmqctl list_queues

javac -cp $CP Recv.java
javac -cp $CP Send.java

java -cp $CP Send
java -cp $CP Recv

## Enunciado

"Acabamos de estudar a técnica de comunicação indireta Fila de Mensagens. 
No git da disciplina, vocês têm um exemplo básico de fila de mensagem, implementado em Java baseado no ambiente RabbitMq. 
Modifique este exemplo, implementando duas filas distintas com prioridade de atendimento. A política de prioridade é de sua escolha."