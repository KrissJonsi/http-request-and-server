package no.kristiania.pgr200.assignment.one;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public abstract class HttpCommon {
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

    protected Map<String, String> readHeaders(InputStream inputStream) throws IOException {
        Map<String, String> headers = new HashMap<>();
        String line;
        while (!(line = readLine(inputStream)).equals("")) {
            String[] parts = line.split(": ", 2);
            headers.put(parts[0].trim(), parts[1].trim());
        }
        return headers;
    }
}
