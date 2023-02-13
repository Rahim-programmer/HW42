import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;



public class EchoServer {
    private int port;

    private EchoServer(int port) {
        this.port = port;
    }
    public static EchoServer bindToPort(int port) {
        return new EchoServer(port);
    }
    private final ExecutorService pool =
            Executors.newCachedThreadPool();

    public static LinkedList <ServerSomething> serverList = new LinkedList<>();

    public final List<String> names = List.of("Bahtiyar", "Maksim", "Aleksei", "Jamilya", "Kairat", "Andrei", "Azidin", "Asema", "Tariel'");

    public static String name;
    private static final Random r = new Random();



    public void run() {
        try (var server = new ServerSocket(port)) {
            while (!server.isClosed()) {
                Socket clientSocket = server.accept();
                this.name = names.get(r.nextInt(names.size()));
                pool.submit( () -> Aid.handle(clientSocket, name));

                serverList.add(new ServerSomething(clientSocket));

            }
        } catch (IOException e) {
            System.out.printf("Most probably port %s is busy.%n", port);
            e.printStackTrace();

        }
    }


}

