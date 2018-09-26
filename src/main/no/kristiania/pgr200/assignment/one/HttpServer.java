package no.kristiania.pgr200.assignment.one;

import no.kristiania.pgr200.assignment.one.Enums.HttpStatus;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class HttpServer {
    private int port;
    private boolean listening = true;

    public static void main(String[] args) {
        HttpServer server = new HttpServer();
        try {
            server.runServer(8080);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void listen(ServerSocket serverSocket) throws IOException {
        new Thread(() -> {
            while (listening) {
                try (Socket socket = serverSocket.accept()) {
                    HttpRequest request = new HttpRequest(socket.getInputStream());
                    System.out.println(String.format("%s %s", request.getMethod().name(), request.getRequestPath()));
                    HttpResponse response = new HttpResponse();

                    HttpQuery query = request.getPath().getQuery();
                    String requestStatus = query.getParameter("status");
                    String requestBody = query.getParameter("body");
                    String requestLocation = query.getParameter("location");

                    HttpStatus status = HttpStatus.OK;
                    if (requestStatus != null) {
                        status = HttpStatus.valueOf(Integer.parseInt(requestStatus));
                    }

                    response.setStatus(status);
                    response.setBody(requestBody);
                    if(requestLocation != null) {
                        response.putHeader("location", requestLocation);
                    }

                    OutputStream outputStream = socket.getOutputStream();
                    response.writeToStream(outputStream);
                    outputStream.flush();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public void runServer() throws IOException {
        runServer(0);
    }

    public void runServer(int port) throws IOException {
        ServerSocket socket = new ServerSocket(port);
        this.port = socket.getLocalPort();
        System.out.println("Server now running on port: " + socket.getLocalPort());
        listen(socket);
    }

    public int getPort() {
        return port;
    }

    public void stop() {
        this.listening = false;
    }
}
