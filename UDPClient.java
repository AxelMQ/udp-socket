import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Scanner;

public class UDPClient {
    public static void main(String[] args) throws Exception {
        DatagramSocket socket = new DatagramSocket();
        InetAddress address = InetAddress.getByName("localhost");
        Scanner scanner = new Scanner(System.in);

        int clientId = (int) (Math.random() * 1000); // Generar un ID aleatorio
        while (true) {
            // Agrega un número único para cada instancia
            String message = "Hola, servidor UDP desde cliente " + clientId;
            byte[] buffer = message.getBytes();

            DatagramPacket packet = new DatagramPacket(buffer, buffer.length, address, 12345);
            socket.send(packet);

            //Recibir respuesta
            byte[] responseBuffer = new byte[1024];
            DatagramPacket responsePacket = new DatagramPacket(responseBuffer, responseBuffer.length);
            socket.receive(responsePacket);

            String response = new String(responsePacket.getData(), 0, responsePacket.getLength());
            System.out.println("Respuesta del servidor: " + response);

            System.out.println("--> Desea enviar otro mensaje? (s/n)");
            if (scanner.nextLine().equalsIgnoreCase("n")) {
                break;
            }
    
        }
        socket.close();
        scanner.close();
    }
}
