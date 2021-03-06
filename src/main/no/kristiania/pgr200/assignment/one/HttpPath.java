package no.kristiania.pgr200.assignment.one;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

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
            indexOfQ = rawUri.length();
        }
        path = rawUri.substring(0, indexOfQ);

        if (indexOfQ != rawUri.length()) {
            query = new HttpQuery(rawUri.substring(indexOfQ+1));
        }
    }

    public HttpPath() {
    }

    public String getPath() {
        return path;
    }

    public String[] getPathParts() {
        List<String> pathPartsList = new ArrayList<>(Arrays.asList(path.split("/")));
        pathPartsList.removeIf(String::isEmpty);
        pathParts = pathPartsList.toArray(new String[]{});
        return pathParts;
    }

    public HttpQuery getQuery() {
        return query;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public void setQuery(HttpQuery query) {
        this.query = query;
    }

    @Override
    public String toString() {
        if(query != null) {
            return (path + "?" + query);
        } else {
            return path;
        }
    }
}
