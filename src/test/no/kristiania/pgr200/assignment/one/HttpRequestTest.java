package no.kristiania.pgr200.assignment.one;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class HttpRequestTest {
    @Test
    public void shouldReadStatusCode() {
        HttpRequest request = new HttpRequest("urlecho.appspot.com", 80, "/echo?status=200");
        HttpResponse response = request.execute();


        assertThat(response.getStatusCode()).isEqualTo(200);
    }

    @Test
    public void shouldReadOtherStatusCodes() {
        HttpRequest request = new HttpRequest("urlecho.appspot.com", 80, "/echo?status=404");
        HttpResponse response = request.execute();

        assertThat(response.getStatusCode()).isEqualTo(404);
    }

    @Test
    public void shouldReadResponseHeaders() {
        HttpRequest request = new HttpRequest("urlecho.appspot.com",
                                              80, "/echo?status=307&Location=http%3A%2F%2Fwww.google.com");
        HttpResponse response = request.execute();

        assertThat(response.getStatusCode()).isEqualTo(307);
        assertThat(response.getHeader("Location")).isEqualTo("http://www.google.com");
    }

    @Test
    public void shouldReadResponseBody() {
        HttpRequest request = new HttpRequest("urlecho.appspot.com",
                                              80, "/echo?body=Hello+world!");
        HttpResponse response = request.execute();

        assertThat(response.getStatusCode()).isEqualTo(200);
        assertThat(response.getBody()).isEqualTo("Hello world!");
    }

}
