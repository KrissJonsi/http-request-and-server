package no.kristiania.pgr200.assignment.one;

import no.kristiania.pgr200.assignment.one.Enums.HttpStatus;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.LinkedHashMap;
import java.util.Map;

public class HttpQuery{
    Map<String, String> parameters = new LinkedHashMap<>();

    public HttpQuery(String query) {
        String[] parts = query.split("&");
        for (String s : parts) {
            int indexOfE = s.indexOf("=");
            if (indexOfE == -1) {
                continue;
            }
            try {
                String headerName = URLDecoder.decode(s.substring(0, indexOfE), "UTF-8");
                String headerValue = URLDecoder.decode(s.substring(indexOfE + 1), "UTF-8");
                parameters.put(headerName, headerValue);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
    }

    public HttpQuery() {
    }

    public String getParameter(String name) {
        return parameters.get(name);
    }

    public void putParameter(String name, String value) {
        this.parameters.put(name, value);
    }

    public String urlEncode(String s){
        try {
            return URLEncoder.encode(s, StandardCharsets.UTF_8.name());
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("nah mate", e);
        }
    }

    public String urlDecode(String s){
        try {
            return URLDecoder.decode(s, StandardCharsets.UTF_8.name());
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("nah mate", e);
        }
    }

    @Override
    public String toString() {
        StringBuilder query = new StringBuilder();
        for(String parameterName : parameters.keySet()){
            if (query.length() > 0){
                query.append("&");
            }
                query.append(urlEncode(parameterName));
                query.append("=");
                query.append(urlEncode(getParameter(parameterName)));
        }
        return query.toString();
    }

    public void addParameter(String name, String value) {
        parameters.put(name, value);
    }
}
