package no.kristiania.pgr200.assignment.one;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.applet.AppletStub;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class HttpPath {

    private String path;
    private String pathParts[];
    private HttpQuery query;

    public HttpPath(String rawUri) {
        int indexOfQ = rawUri.indexOf("?");
        if (indexOfQ == -1){
            indexOfQ = rawUri.length() - 1;
        }
        path = rawUri.substring(0, indexOfQ);

        List<String> pathPartsList = new ArrayList<>(Arrays.asList(path.split("/")));
        pathPartsList.removeIf(String::isEmpty);
        pathParts = pathPartsList.toArray(new String[]{});
        if (indexOfQ == rawUri.length() - 1) {
            query = new HttpQuery("");
        } else {
            query = new HttpQuery(rawUri.substring(indexOfQ+1));
        }

    }

    public HttpPath() {
    }

    public String getPath() {
        return path;
    }

    public String[] getPathParts() {
        return pathParts;
    }

    public HttpQuery getQuery() {
        return query;
    }

    public void setPath(String path) {
        throw new NotImplementedException();
    }

    public void setQuery(HttpQuery query) {
        throw new NotImplementedException();
    }
}
