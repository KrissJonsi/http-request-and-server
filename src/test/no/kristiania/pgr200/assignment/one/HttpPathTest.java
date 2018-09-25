package no.kristiania.pgr200.assignment.one;

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
}
