import java.io.*;
import java.net.Socket;
import java.util.*;

public class Aid {

    static void handle(Socket socket, String name){


        System.out.printf("Connected client:  %s%n", name);


        try (Scanner reader = getReader(socket);
             PrintWriter writer = getWriter(socket);
             socket) {
            sendResponse("Hello " + name, writer);
            while (true) {
                String message = reader.nextLine();
                System.out.println( name + ": "+ message);
                if (message.equals("stop")) {
                    break;
                }
                for (ServerSomething vr : EchoServer.serverList) {
                    vr.send(message, name);


                }
                if ("Bye".equalsIgnoreCase(message)) {
                    System.out.println("Bye bye!%n");
                    return;
                }
                if (isEmptyMsg(message) || isQuitMsg(message)) {
                    break;
                }
            }
        } catch (NoSuchElementException ex) {


            System.out.println("The client closed the connection!");
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.printf("Client disconnected:  %s%n", socket);
    }

    private static PrintWriter getWriter(Socket socket)
            throws IOException {
        OutputStream stream = socket.getOutputStream();
        return new PrintWriter(stream);
    }

    private static Scanner getReader(Socket socket) throws IOException {
        InputStream stream = socket.getInputStream();
        InputStreamReader input = new InputStreamReader(stream, "UTF-8");
        return new Scanner(input);
    }

    private static boolean isQuitMsg(String message) {
        return "bye".equals(message.toLowerCase());
    }

    private static boolean isEmptyMsg(String message) {
        return message == null || message.isBlank();
    }

    private static void sendResponse(String response, Writer writer) throws IOException {
        writer.write(response);
        writer.write(System.lineSeparator());
        writer.flush();

    }




}
