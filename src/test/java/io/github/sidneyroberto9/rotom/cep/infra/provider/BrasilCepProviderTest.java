package io.github.sidneyroberto9.rotom.cep.infra.provider;

import io.github.sidneyroberto9.rotom.cep.domain.Address;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class BrasilCepProviderTest {

    private MockWebServer server;

    @BeforeEach
    void startServer() throws IOException {
        server = new MockWebServer();
        server.start();
    }

    @AfterEach
    void stopServer() throws IOException {
        server.shutdown();
    }

    @Test
    void fetchParsesSuccessfulResponse() throws IOException {
        server.enqueue(new MockResponse().setResponseCode(200).setBody(
                "{\"cep\":\"58038000\",\"state\":\"PB\",\"city\":\"João Pessoa\","
                        + "\"district\":\"Bairro dos Estados\",\"street\":\"Avenida Epitácio Pessoa\"}"
        ));
        BrasilCepProvider provider = new BrasilCepProvider(server.url("/api/cep/").toString());

        Optional<Address> result = provider.fetch("58038000");

        assertTrue(result.isPresent());
        assertEquals("PB", result.get().getUf());
    }

    @Test
    void fetchReturnsEmptyWhenCepIsMissing() throws IOException {
        server.enqueue(new MockResponse().setResponseCode(200).setBody("{}"));
        BrasilCepProvider provider = new BrasilCepProvider(server.url("/api/cep/").toString());

        assertTrue(provider.fetch("00000000").isEmpty());
    }

    @Test
    void fetchThrowsOnHttpError() {
        server.enqueue(new MockResponse().setResponseCode(500));
        BrasilCepProvider provider = new BrasilCepProvider(server.url("/api/cep/").toString());

        assertThrows(IOException.class, () -> provider.fetch("58038000"));
    }

    @Test
    void nameReturnsProviderIdentifier() {
        assertEquals("BrasilCEP", new BrasilCepProvider().name());
    }
}
