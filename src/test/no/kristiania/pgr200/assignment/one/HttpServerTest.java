package no.kristiania.pgr200.assignment.one;

import no.kristiania.pgr200.assignment.one.Enums.HttpMethod;
import no.kristiania.pgr200.assignment.one.Enums.HttpStatus;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

public class HttpServerTest {
    private HttpServer server;

    @Before
    public void before() throws IOException {
        server = new HttpServer();
        server.runServer();
    }

    @After
    public void after() {
        server.stop();
    }

    @Test
    public void shouldRespondWithCorrectStatusCode() {
        HttpRequest request = new HttpRequest();
        request.setHost("127.0.0.1");
        request.setPort(server.getPort());
        HttpPath path = new HttpPath("/echo");
        HttpQuery query = new HttpQuery();
        query.putParameter("status", String.valueOf(HttpStatus.ImaTeapot.getStatusCode()));
        path.setQuery(query);
        request.setRequestPath(path.toString());

        HttpResponse response = request.execute();

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.ImaTeapot.getStatusCode());
    }

    @Test
    public void shouldSetLocationHeaderIfSpecifiedInRequest() {
        HttpRequest request = new HttpRequest();
        request.setHost("127.0.0.1");
        request.setPort(server.getPort());
        HttpPath path = new HttpPath("/echo");
        HttpQuery query = new HttpQuery();
        query.putParameter("status", String.valueOf(HttpStatus.TemporaryRedirect.getStatusCode()));
        query.putParameter("location", "https://google.com");
        path.setQuery(query);
        request.setRequestPath(path.toString());

        HttpResponse response = request.execute();

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.TemporaryRedirect.getStatusCode());
        assertThat(response.getHeader("location")).isEqualTo("https://google.com");
    }

    @Test
    public void shouldRespondWithRequestedBody() {
        HttpRequest request = new HttpRequest();
        request.setHost("127.0.0.1");
        request.setPort(server.getPort());
        HttpPath path = new HttpPath("/echo");
        HttpQuery query = new HttpQuery();
        query.putParameter("status", String.valueOf(HttpStatus.OK.getStatusCode()));
        query.putParameter("body", "Hello World!");
        path.setQuery(query);
        request.setRequestPath(path.toString());

        HttpResponse response = request.execute();

        assertThat(response.getBody()).isEqualTo("Hello World!");
    }
}
