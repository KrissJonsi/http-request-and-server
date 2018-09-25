package no.kristiania.pgr200.assignment.one;

import no.kristiania.pgr200.assignment.one.Enums.HttpMethod;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;

public class HttpRequest extends HttpCommon {
    private String host;
    private int port;
    private String requestPath;
    private HttpMethod method = HttpMethod.GET;

    public HttpRequest(String host, int port, String requestPath) {
        this.setHost(host);
        this.port = port;
        this.requestPath = requestPath;
    }

    public HttpRequest(InputStream inputStream) throws IOException {
        String line = readLine(inputStream);
        String[] parts = line.split(" ", 3);

        this.method = HttpMethod.valueOf(parts[0].toUpperCase());
        this.requestPath = parts[1];
        this.headers = readHeaders(inputStream);
        this.host = headers.get("Host");
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
        this.headers.put("Host", host);
    }

    public HttpRequest() {

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
            writeToStream(outputStream);
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

    @Override
    protected void writeFirstLine(OutputStream stream) throws IOException {
        writeLine(String.format("%s %s %s", method.name(), requestPath, "HTTP/1.1"), stream);
    }
}
