package no.kristiania.pgr200.assignment.one;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class HttpServer {

    public static void main(String[] args) {
        HttpServer server = new HttpServer();
        server.runServer(8080);
    }

    private void listen(ServerSocket serverSocket) {
        while (true) {
            new Thread(() -> {
                try (Socket socket = serverSocket.accept()) {
                    InputStream inputStream = socket.getInputStream(); // Request
                    OutputStream outputStream = socket.getOutputStream(); // Response

                    HttpRequest request = new HttpRequest(inputStream);


                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        }
    }

    public void runServer() {
        runServer(0);
    }

    public void runServer(int port) {
        try (ServerSocket socket = new ServerSocket(port)) {
            System.out.println("Server now running on port: " + socket.getLocalPort());
            listen(socket);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
