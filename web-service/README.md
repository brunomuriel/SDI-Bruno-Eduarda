make clean; make ;

1) servidor
  java MYC.ServerPublisher < input.in

2) cliente
  java MYC.Client localhost 1


## Anotações

Para checar qual os caminhos:  

'$ update-alternatives --list java'
'$ update-alternatives --list javac'
'$ update-alternatives --list jar'

Comandos para compilar e executar: 

'$ cp input.in stdin0'
'$ sh E'