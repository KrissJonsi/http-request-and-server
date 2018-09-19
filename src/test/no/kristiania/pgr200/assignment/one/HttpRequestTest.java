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
}
