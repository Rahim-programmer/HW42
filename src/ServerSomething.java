import java.io.*;
import java.net.Socket;


public class ServerSomething extends Thread {

    private Socket socket;

    private BufferedReader in;

    private BufferedWriter out;


    public ServerSomething(Socket socket) throws IOException {
        this.socket = socket;

        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        start();
    }

    void send(String msg, String name) {
        try {

            out.write(name + ": " + msg + "\n");
            out.flush();
        } catch (IOException ignored) {
        }

    }

}

