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

class ZippopotamProviderTest {

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
    void fetchParsesFirstPlaceFromSuccessfulResponse() throws IOException {
        server.enqueue(new MockResponse().setResponseCode(200).setBody(
                "{\"post code\":\"58038-000\","
                        + "\"places\":[{\"place name\":\"João Pessoa\",\"state\":\"Paraíba\",\"state abbreviation\":\"PB\"}]}"
        ));
        ZippopotamProvider provider = new ZippopotamProvider(server.url("/br/").toString());

        Optional<Address> result = provider.fetch("58038000");

        assertTrue(result.isPresent());
        assertEquals("PB", result.get().getUf());
    }

    @Test
    void fetchReturnsEmptyWhenPlacesArrayIsMissing() throws IOException {
        server.enqueue(new MockResponse().setResponseCode(200).setBody("{}"));
        ZippopotamProvider provider = new ZippopotamProvider(server.url("/br/").toString());

        assertTrue(provider.fetch("00000000").isEmpty());
    }

    @Test
    void fetchThrowsOnHttpError() {
        server.enqueue(new MockResponse().setResponseCode(500));
        ZippopotamProvider provider = new ZippopotamProvider(server.url("/br/").toString());

        assertThrows(IOException.class, () -> provider.fetch("58038000"));
    }

    @Test
    void nameReturnsProviderIdentifier() {
        assertEquals("Zippopotam", new ZippopotamProvider().name());
    }
}
