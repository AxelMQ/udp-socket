import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.concurrent.ConcurrentHashMap;

public class UDPServer {
    private static ConcurrentHashMap<String, ClientHandler> clientHandlers = new ConcurrentHashMap<>();

    public static void main(String[] args) {
        Thread serverThread = new Thread(() -> {
            try {
                DatagramSocket socket = new DatagramSocket(12345);
                byte[] buffer = new byte[1024];
                System.out.println("Servidor UDP escuchando en el puerto 12345...");

                while (true) {
                    DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
                    socket.receive(packet);

                    String clientKey = packet.getAddress().getHostAddress() + ":" + packet.getPort();

                    // Crear un nuevo manejador para cada cliente y un nuevo hilo
                    ClientHandler handler = new ClientHandler(socket, packet);
                    clientHandlers.put(clientKey, handler);
                    new Thread(handler).start(); // Iniciar un nuevo hilo para cada cliente
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        serverThread.start(); // Iniciar el hilo del servidor
    }
}
