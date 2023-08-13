import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class SimpleTCPServer {
    private ServerSocket serverSocket;
    private List<Socket> connectedClients = new ArrayList<>();

    public void start(int port) throws IOException {
        // Cria server socket para aguardar conexões de clientes
        System.out.println("[S1] Criando server socket para aguardar conexões de clientes em loop");
        serverSocket = new ServerSocket(port);

        while (serverSocket.isBound()) {
            // Aguarda conexões de clientes
            System.out.println("[S2] Aguardando conexão em: " + serverSocket.getLocalSocketAddress());
            Socket socket = serverSocket.accept();

            // Conexão estabelecida com um cliente
            System.out.println("[S3] Conexão estabelecida com cliente:" + socket.getRemoteSocketAddress());

            // Thread para tratar mensagens do cliente
            Thread clientHandlerThread = new Thread(() -> {
                try {
                    DataInputStream input = new DataInputStream(socket.getInputStream());
                    while (true) {
                        String msg = input.readUTF();
                        System.out.println(msg);

                        // Enviar a mensagem para outros clientes
                        synchronized (serverSocket) {
                            for (Socket client : connectedClients) {
                                if (client != socket) {
                                    DataOutputStream clientOutput = new DataOutputStream(client.getOutputStream());
                                    clientOutput.writeUTF(msg);
                                }
                            }
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
            clientHandlerThread.start();

            // Adicionar cliente à lista de clientes conectados
            connectedClients.add(socket);
        }
    }

    public void stop() throws IOException {
        serverSocket.close();
    }

    public static void main(String[] args) {
        int serverPort = 6666;
        try {
            SimpleTCPServer server = new SimpleTCPServer();
            server.start(serverPort);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
