package io.github.sidneyroberto9.rotom.cep.infra.mapper;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.sidneyroberto9.rotom.cep.domain.Address;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class BrasilCepMapperTest {

    private final ObjectMapper json = new ObjectMapper();

    @Test
    void mapParsesSuccessfulResponse() throws Exception {
        JsonNode data = json.readTree("{"
                + "\"cep\":\"58038000\",\"state\":\"PB\",\"city\":\"João Pessoa\","
                + "\"district\":\"Bairro dos Estados\",\"street\":\"Avenida Epitácio Pessoa\"}");

        Optional<Address> result = BrasilCepMapper.map(data);

        assertTrue(result.isPresent());
        Address address = result.get();
        assertEquals("PB", address.getUf());
        assertEquals("58038000", address.getCep());
        assertEquals("João Pessoa", address.getLocalidade());
        assertEquals("Avenida Epitácio Pessoa", address.getLogradouro());
    }

    @Test
    void mapReturnsEmptyWhenCepIsMissing() throws Exception {
        JsonNode data = json.readTree("{}");

        assertTrue(BrasilCepMapper.map(data).isEmpty());
    }
}
