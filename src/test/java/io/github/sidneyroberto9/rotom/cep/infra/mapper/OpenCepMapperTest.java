package io.github.sidneyroberto9.rotom.cep.infra.mapper;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.sidneyroberto9.rotom.cep.domain.Address;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class OpenCepMapperTest {

    private final ObjectMapper json = new ObjectMapper();

    @Test
    void mapParsesSuccessfulResponse() throws Exception {
        JsonNode data = json.readTree("{"
                + "\"cep\":\"58038-000\",\"logradouro\":\"Avenida Epitácio Pessoa\",\"bairro\":\"Bairro dos Estados\","
                + "\"localidade\":\"João Pessoa\",\"uf\":\"PB\",\"ibge\":\"2507507\"}");

        Optional<Address> result = OpenCepMapper.map(data);

        assertTrue(result.isPresent());
        Address address = result.get();
        assertEquals("PB", address.getUf());
        assertEquals("58038-000", address.getCep());
        assertEquals("João Pessoa", address.getLocalidade());
    }

    @Test
    void mapReturnsEmptyWhenCepIsMissing() throws Exception {
        JsonNode data = json.readTree("{}");

        assertTrue(OpenCepMapper.map(data).isEmpty());
    }
}
