import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class EchoServer {
    private final int port;


    public EchoServer(int port) {
        this.port = port;
    }

    static EchoServer bindToPort(int port) {
        return new EchoServer(port);
    }

    public void run() {
        try (ServerSocket server = new ServerSocket(port)) {
            while (!server.isClosed()){
                Socket clientSocket = server.accept();
                handle(clientSocket);
            }
            try (var clientSocket = server.accept()) {
                handle(clientSocket);
            }

        } catch (IOException e) {
            System.out.printf("Most likely the port %s is busy. %n");
            e.printStackTrace();
        }
    }

    private void handle(Socket socket) throws IOException {
        var input = socket.getInputStream();
        var isr = new InputStreamReader(input, StandardCharsets.UTF_8);
        var scanner = new Scanner(isr);

        OutputStream outputStream = socket.getOutputStream();
        PrintWriter writer = new PrintWriter(outputStream);

        try (scanner; writer) {
            while (true) {
                var message = scanner.nextLine().strip();
                System.out.printf("Got %s%n", message);

                StringBuilder reverseString = new StringBuilder(message).reverse();
                String result = reverseString.toString();
                System.out.printf("reversed - %s%n", result);

                writer.write(result);
                writer.write(System.lineSeparator());
                writer.flush();

                if ("bye".equalsIgnoreCase(message)) {
                    System.out.println("bye bye!%n");
                    return;
                }
            }
        } catch (NoSuchElementException e) {
            System.out.println("Client dropped the connection!%n");
        }
    }
}
