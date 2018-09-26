package no.kristiania.pgr200.assignment.one;

import no.kristiania.pgr200.assignment.one.Enums.HttpStatus;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class HttpPathTest {

    @Test
    public void shouldParseUrl() {
        HttpPath path = new HttpPath("/myapp/echo?status=400&body=vi%20plukker%20bl%C3%A5b%C3%A6r");
        assertThat(path.getPath()).isEqualTo("/myapp/echo");
        assertThat(path.getPathParts()).containsExactly("myapp", "echo");
        assertThat(path.getQuery().getParameter("status")).isEqualTo("400");
        assertThat(path.getQuery().getParameter("body")).isEqualTo("vi plukker blåbær");
    }

    @Test
    public void shouldParseUrlWithoutQuery() {
        HttpPath path = new HttpPath("/myapp/echo");
        assertThat(path.getPath()).isEqualTo("/myapp/echo");
        assertThat(path.getPathParts()).containsExactly("myapp", "echo");
        assertThat(path.getQuery()).isNull();
    }

    @Test
    public void shouldReturnUrlEncodedDataOnToString() {
        HttpPath path = new HttpPath();
        path.setPath("/echo");
        HttpQuery query = new HttpQuery();
        query.putParameter("status", "404");
        query.putParameter("body", "Hello World!");
        path.setQuery(query);

        assertThat(path.toString()).startsWith("/echo?");
        assertThat(path.toString()).containsOnlyOnce("body=Hello+World%21");
        assertThat(path.toString()).containsOnlyOnce("status=404");
    }

    @Test
    public void shouldReturnUrlEncodedDataEvenIfMissingQuery() {
        HttpPath path = new HttpPath();
        path.setPath("/echo");

        assertThat(path.toString()).isEqualTo("/echo");
    }
}
