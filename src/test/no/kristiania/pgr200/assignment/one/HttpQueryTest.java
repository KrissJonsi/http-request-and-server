package no.kristiania.pgr200.assignment.one;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class HttpQueryTest {
    @Test
    public void shouldReturnUrlEncodedQueryString() {
        HttpQuery query = new HttpQuery();
        query.putParameter("status", "418");

        assertThat(query.toString()).isEqualTo("status=418");
    }

    @Test
    public void shouldReturnUrlEncodedQueryStringMultipleParameters() {
        HttpQuery query = new HttpQuery();
        query.putParameter("status", "418");
        query.putParameter("location", "https://google.com");

        assertThat(query.toString()).containsOnlyOnce("status=418");
        assertThat(query.toString()).containsOnlyOnce("location=https%3A%2F%2Fgoogle.com");
        assertThat(query.toString()).containsOnlyOnce("&");
        assertThat(query.toString()).doesNotStartWith("&");
        assertThat(query.toString()).doesNotEndWith("&");
    }
}
