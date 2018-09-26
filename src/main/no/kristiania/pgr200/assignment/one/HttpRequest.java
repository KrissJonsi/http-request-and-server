package no.kristiania.pgr200.assignment.one;

import no.kristiania.pgr200.assignment.one.Enums.HttpMethod;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

public class HttpRequest extends HttpCommon {
    private String host;
    private int port;
    private HttpPath path;

    public void setMethod(HttpMethod method) {
        this.method = method;
    }

    private HttpMethod method = HttpMethod.GET;

    public HttpRequest(String host, int port, String requestPath) {
        this.setHost(host);
        this.port = port;
        this.path = new HttpPath(requestPath);
    }

    public HttpRequest(InputStream inputStream) throws IOException {
        String line = readLine(inputStream);
        while (line.isEmpty()) {
            line = readLine(inputStream);
        }
        String[] parts = line.split(" ", 3);

        this.method = HttpMethod.valueOf(parts[0].toUpperCase());
        this.path = new HttpPath(parts[1]);
        this.headers = readHeaders(inputStream);
        this.host = headers.get("Host");
        int contentLength = 0;
        if (headers.containsKey("content-length")) {
            contentLength = Integer.parseInt(headers.get("content-length"));
        }

        this.body = readBody(contentLength, inputStream);
    }
    public HttpRequest() {

    }

    public HttpPath getPath() {
        return path;
    }

    public void setPath(HttpPath path) {
        this.path = path;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getRequestPath() {
        return this.path.toString();
    }

    public void setRequestPath(String requestPath) {
        this.path = new HttpPath(requestPath);
    }

    public HttpMethod getMethod() {
        return method;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
        this.headers.put("Host", host);
    }

    public HttpResponse execute() {
        HttpResponse response = null;

        try (Socket socket = new Socket(host, port)) {

            if (!headers.containsKey("Host")) {
                headers.put("Host", host);
            }
            if (!headers.containsKey("Connection")) {
                headers.put("Connection", "close");
            }
            if (!headers.containsKey("content-length") && body != null) {
                headers.put("content-length", String.valueOf(body.getBytes("UTF-8").length));
            }
            OutputStream outputStream = socket.getOutputStream();
            writeToStream(outputStream);
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
        writeLine(String.format("%s %s %s", method.name(), this.path.toString(), "HTTP/1.1"), stream);
    }
}
