import java.net.DatagramPacket;
import java.net.DatagramSocket;

class ClientHandler implements Runnable {
    private DatagramSocket socket;
    private DatagramPacket packet;

    public ClientHandler(DatagramSocket socket, DatagramPacket packet) {
        this.socket = socket;
        this.packet = packet;
    }

    @Override
    public void run() {
        processPacket(packet);
    }

    public void processPacket(DatagramPacket packet) {
        try {
            String message = new String(packet.getData(), 0, packet.getLength());
            int clientId = Integer.parseInt(message.split(" ")[5]); // Asumiendo que el ID es la 5ta palabra
            System.out.println("Handler - Mensaje recibido del cliente " + clientId + ": " + message);

            String response = "Respuesta desde el servidor manejado por el hilo " + Thread.currentThread().getName();
            DatagramPacket responsePacket = new DatagramPacket(
                response.getBytes(), response.length(),
                packet.getAddress(), packet.getPort());
            socket.send(responsePacket);
        } catch (Exception e) {
            e.printStackTrace(); 
            System.err.println("Error procesando paquete del cliente: " + e.getMessage());
        }
    }
}
