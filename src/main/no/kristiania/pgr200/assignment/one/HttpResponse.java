package no.kristiania.pgr200.assignment.one;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class HttpResponse {
    private final String body;
    private Map<String, String> headers = new HashMap<>();
    private int statusCode;
    private InputStream inputStream;


    public HttpResponse(InputStream inputStream) throws IOException {
        this.inputStream = inputStream;
        this.statusCode = readStatusCode();

        String headerLine, headerName, headerValue;
        while(!(headerLine = readLine()).equals("")){
            int colonPos = headerLine.indexOf(":");

            headerName = headerLine.substring(0, colonPos).trim();
            headerValue = headerLine.substring(colonPos+1).trim();

            headers.put(headerName, headerValue);
        }
        this.body = readLine();

    }

    public String readLine() throws IOException {
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

    public int getStatusCode() {
        return statusCode;
    }

    public int readStatusCode() throws IOException {
        String[] parts = readLine().split(" ");
        return Integer.parseInt(parts[1]);
    }

    public String getHeader(String headerName){
        return headers.get(headerName);
    }

    public String getBody(){
        return body;
    }
}
