# Atividade_Pratica_1_-_Extra_-_Chat_com_Sockets_TCP


Apartir de algumas modificações no código do cliente e servidor TCP da Prática 1, foi desenvolvido um chat simples em linha de comando, onde os clientes podem se conectar a um servidor e trocar mensagens entre si, com cada mensagem exibindo a identificação do cliente que a enviou. O servidor retransmite essas mensagens para todos os clientes conectados, criando um ambiente de chat em grupo.

Explicação do que foi feito/modificado:

1. Clientes Identificados por Nome:
  - A classe "SimpleTCPClient" que agora se chama "TCPClient" foi modificada para que cada cliente seja identificado por um nome      fornecido pelo usuário.
  - O nome do cliente é passado como argumento para o construtor da classe "TCPClient.
  - Esse nome é utilizado como identificador nas mensagens enviadas pelo cliente.

2. Clientes e Servidor Enviando e Recebendo Mensagens:
  - Tanto os clientes quanto o servidor utilizam fluxos de entrada e saída de dados ("DataInputStream" e "DataOutputStream") para     trocar mensagens.
  - Os clientes enviam suas mensagens para o servidor através do canal de saída e o servidor recebe essas mensagens através do        canal de entrada.
  - O servidor, por sua vez, retransmite as mensagens recebidas para todos os clientes conectados.

3. Cliente Lidando com Mensagens:
  - Cada cliente possui uma thread para ler e exibir mensagens enviadas pelo servidor.
  - Um loop contínuo aguarda a entrada do usuário para enviar mensagens para o servidor. Cada mensagem é prefixada com o nome do      cliente.

4. Servidor Lidando com Clientes:
  - O servidor mantém uma lista de clientes conectados em uma lista chamada "connectedClients".
  - Quando um cliente se conecta, o servidor cria uma nova thread para lidar com as mensagens desse cliente.
  - Cada thread de cliente lê as mensagens enviadas pelo cliente e retransmite essas mensagens para todos os clientes conectados.

5. Sincronização Adequada:
  - O uso de synchronized no construtor da classe SimpleTCPClient garante que o contador de clientes seja atualizado de forma     
    segura, evitando condições de corrida.

6. Tratamento de Exceções:
  - Foram adicionados tratamentos de exceção adequados em locais relevantes, como na criação do "Scanner" e no fechamento de     
    fluxos e sockets.

7. Loop Infinito:
  - Tanto os clientes quanto o servidor estão em loops infinitos para manter a comunicação ativa e contínua.
