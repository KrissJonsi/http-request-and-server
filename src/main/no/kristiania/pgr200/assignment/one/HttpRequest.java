package no.kristiania.pgr200.assignment.one;

import no.kristiania.pgr200.assignment.one.Enums.HttpMethod;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;

public class HttpRequest {
    private final String host;
    private final int port;
    private final String requestPath;
    private Map<String, String> headers = new HashMap<>();
    private HttpMethod method = HttpMethod.GET;

    public HttpRequest(String host, int port, String requestPath) {
        this.host = host;
        this.port = port;
        this.requestPath = requestPath;
    }

    public HttpResponse execute() {
        HttpResponse response = null;

        try(Socket socket = new Socket(host, port)){

            if(!headers.containsKey("Host")){
                headers.put("Host", host);
            }
            if(!headers.containsKey("Connection")){
                headers.put("Connection", "close");
            }

            OutputStream outputStream = socket.getOutputStream();
            writeRequestLine(outputStream);
            writeHeaders(headers, outputStream);
            //TODO: implement sending body
            outputStream.flush();

            response = new HttpResponse(socket.getInputStream());

        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return response;
    }

    private void writeHeaders(Map<String, String> headers, OutputStream outputStream) throws IOException {
        for (Map.Entry<String, String> header : headers.entrySet()) {
            writeLine(String.format("%s: %s", header.getKey(), header.getValue()), outputStream);
        }
        writeLine("", outputStream);
    }

    private void writeRequestLine(OutputStream outputStream) throws IOException {
        writeLine(String.format("%s %s %s", method.name(), requestPath, "HTTP/1.1"), outputStream);
    }

    private void writeLine(String line, OutputStream outputStream) throws IOException {
        outputStream.write((line + "\r\n").getBytes());
    }


}
