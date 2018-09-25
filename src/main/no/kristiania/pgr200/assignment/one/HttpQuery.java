package no.kristiania.pgr200.assignment.one;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;

public class HttpQuery{



    Map<String, String> parameters = new HashMap<>();

    public HttpQuery(String query) {

        String[] parts = query.split("&");
        for(String s : parts){
            int indexOfE = s.indexOf("=");
            try {
                String headerName = URLDecoder.decode(s.substring(0, indexOfE), "UTF-8");
                String headerValue = URLDecoder.decode(s.substring(indexOfE+1), "UTF-8");
                parameters.put(headerName, headerValue);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
    }

    public String getParameter(String name) {
        return parameters.get(name);
    }
}
