package no.kristiania.pgr200.assignment.one;

import no.kristiania.pgr200.assignment.one.Enums.HttpStatus;

import java.io.ByteArrayOutputStream;
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

    private void listen(ServerSocket serverSocket) throws IOException {
        while (true) {
            Socket socket = serverSocket.accept();
            new Thread(() -> {
                try {
                    HttpRequest request = new HttpRequest(socket.getInputStream());
                    System.out.println(String.format("%s %s", request.getMethod().name(), request.getRequestPath()));
                    HttpResponse response = new HttpResponse();

                    response.setStatus(HttpStatus.ImaTeapot);
                    response.setBody("hello fam");

                    OutputStream outputStream = socket.getOutputStream();
                    response.writeToStream(outputStream);
                    outputStream.flush();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }).start();
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
