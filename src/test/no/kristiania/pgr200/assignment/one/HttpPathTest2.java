package no.kristiania.pgr200.assignment.one;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

import java.io.UnsupportedEncodingException;

public class HttpPathTest2 {

    @Test
    public void shouldSeparatePathAndQuery() {
        HttpPath path = new HttpPath("/urlecho?status=200");
        assertThat(path.getPath()).isEqualTo("/urlecho");
        assertThat(path.getQuery().toString()).isEqualTo("status=200");
        assertThat(path.toString()).isEqualTo("/urlecho?status=200");
        assertThat(path.getQuery().getParameter("status")).isEqualTo("200");
    }

    @Test
    public void shouldHandleUriWithoutQuery() {
        HttpPath path = new HttpPath("/myapp/echo");
        assertThat(path.getPath()).isEqualTo("/myapp/echo");
        assertThat(path.getQuery()).isNull();
        assertThat(path.toString()).isEqualTo("/myapp/echo");
    }

    @Test
    public void shouldReadParameters() {
        HttpPath path = new HttpPath("?status=403&body=mer+bl%E5b%E6r+%26+jordb%E6r");
        assertThat(path.getQuery().getParameter("status")).isEqualTo("403");
        assertThat(path.getQuery().getParameter("body")).isEqualTo("mer bl�b�r & jordb�r");
    }

    @Test
    public void shouldCreateQuery() {
        HttpQuery query = new HttpQuery("status=200");
        query.addParameter("body", "mer bl�b�r");
        assertThat(query.toString()).isEqualTo("status=200&body=mer+bl%EF%BF%BDb%EF%BF%BDr");
    }

}