package no.kristiania.pgr200.assignment.one;

import com.sun.security.ntlm.Server;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class HttpServer {

    private void listen(ServerSocket serverSocket) {
        while (true) {
            try (Socket socket = serverSocket.accept()) {
                InputStream inputStream = socket.getInputStream();
                OutputStream outputStream = socket.getOutputStream();

                HttpRequest request = new HttpRequest(inputStream);



            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void runServer() {
        try (ServerSocket socket = new ServerSocket(0)) {
            new Thread(() -> listen(socket)).start();

        } catch (IOException e) {
            e.printStackTrace();
        }


    }

}
