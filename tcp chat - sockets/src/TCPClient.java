import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class TCPClient {
    private Socket socket;
    private DataInputStream input;
    private DataOutputStream output;
    private String clientName; // Nome do cliente

    public TCPClient(String name) {
        synchronized (TCPClient.class) {
            clientName = name;
        }
    }

    public void start(String serverIp, int serverPort) throws IOException {
        // Conecta ao servidor
        socket = new Socket(serverIp, serverPort);
        input = new DataInputStream(socket.getInputStream());
        output = new DataOutputStream(socket.getOutputStream());

        // Thread para ler e exibir mensagens do servidor
        Thread readThread = new Thread(() -> {
            try {
                while (true) {
                    String response = input.readUTF();
                    System.out.println(response);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        readThread.start();

        // Loop para enviar mensagens para o servidor
        Scanner scanner = new Scanner(System.in);
        try {
            while (true) {
                System.out.print("[" + clientName + "]: ");
                String msg = scanner.nextLine();
                output.writeUTF("[" + clientName + "]: " + msg);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            scanner.close();
        }
    }

    public void stop() {
        try {
            input.close();
            output.close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        String serverIp = "127.0.0.1"; // Coloque o endereço IP do servidor aqui
        int serverPort = 6666;

        Scanner scanner = new Scanner(System.in);
        try {
            System.out.print("Digite o nome do cliente: ");
            String clientName = scanner.nextLine();

            TCPClient client = new TCPClient(clientName);
            client.start(serverIp, serverPort);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            scanner.close();
        }
    }
}
