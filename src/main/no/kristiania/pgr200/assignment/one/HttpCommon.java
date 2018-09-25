package no.kristiania.pgr200.assignment.one;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

public abstract class HttpCommon {

    protected Map<String, String> headers = new HashMap<>();
    protected String body;

    public Map<String, String> getHeaders() {
        return headers;
    }

    public void setHeaders(Map<String, String> headers) {
        this.headers = headers;
    }

    protected String readLine(InputStream inputStream) throws IOException {
        StringBuilder line = new StringBuilder();
        int c;
        while((c = inputStream.read()) != -1){
            if (c == '\r'){
                c = inputStream.read();
                assert c == '\n';
                break;
            }
            line.append((char)c);
        }
        return line.toString();
    }

    protected String readBody(int length, InputStream stream) throws IOException {
        StringBuilder body = new StringBuilder();
        for(int i = 0; i < length; i++){
            body.append(stream.read());
        }
        return body.toString();
    }

    protected Map<String, String> readHeaders(InputStream inputStream) throws IOException {
        Map<String, String> headers = new HashMap<>();
        String line;
        while (!(line = readLine(inputStream)).equals("")) {
            String[] parts = line.split(": ", 2);
            headers.put(parts[0].trim(), parts[1].trim());
        }
        return headers;
    }

    public void writeToStream(OutputStream stream) throws IOException {
        writeFirstLine(stream);
        if (this.body != null && (this.body.getBytes("UTF-8").length > 0)) {
            headers.put("Content-Length", String.valueOf(this.body.getBytes("UTF8").length));
        }

        writeHeaders(stream);
        byte[] body = this.body.getBytes("UTF-8");

        if (body.length > 0) {
            stream.write(body);
        }
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getHeader(String headerName){
        return headers.get(headerName);
    }

    private void writeHeaders(OutputStream stream) throws IOException {
        for (Map.Entry<String, String> header : headers.entrySet()) {
            writeLine(String.format("%s: %s", header.getKey(), header.getValue()), stream);
        }
        writeLine("", stream);
    }

    public void putHeader(String headerName, String headerValue){
        headers.put(headerName, headerValue);
    }

    protected void writeLine(String line, OutputStream outputStream) throws IOException {
        outputStream.write((line + "\r\n").getBytes());
    }



    protected abstract void writeFirstLine(OutputStream stream) throws IOException;
}
