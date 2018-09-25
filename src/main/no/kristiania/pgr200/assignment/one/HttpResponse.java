package no.kristiania.pgr200.assignment.one;

import no.kristiania.pgr200.assignment.one.Enums.HttpStatus;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class HttpResponse extends HttpCommon {
    private HttpStatus status;


    public HttpResponse(InputStream inputStream) throws IOException {
        String[] parts = readLine(inputStream).split(" ");
        this.status = HttpStatus.valueOf(Integer.parseInt(parts[1]));
        this.headers = readHeaders(inputStream);
        this.body = readBody(Integer.parseInt(headers.get("content-length")), inputStream);
    }

    public HttpResponse() {
    }

    public HttpStatus getStatus() {
        return status;
    }

    public void setStatus(HttpStatus status) {
        this.status = status;
    }

    public int getStatusCode() {
        return status.getStatusCode();
    }

    @Override
    protected void writeFirstLine(OutputStream stream) throws IOException {
        writeLine(String.format("%s %d %s", "HTTP/1.1", status.getStatusCode(), status.getName()), stream);
    }
}
