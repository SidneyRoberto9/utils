package io.github.sidneyroberto9.rotom.cep.infra.mapper;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.sidneyroberto9.rotom.cep.domain.Address;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ZippopotamMapperTest {

    private final ObjectMapper json = new ObjectMapper();

    @Test
    void mapParsesFirstPlaceFromSuccessfulResponse() throws Exception {
        JsonNode data = json.readTree("{"
                + "\"post code\":\"58038-000\","
                + "\"places\":[{\"place name\":\"João Pessoa\",\"state\":\"Paraíba\",\"state abbreviation\":\"PB\"}]"
                + "}");

        Optional<Address> result = ZippopotamMapper.map(data);

        assertTrue(result.isPresent());
        Address address = result.get();
        assertEquals("PB", address.getUf());
        assertEquals("58038-000", address.getCep());
        assertEquals("João Pessoa", address.getLocalidade());
        assertEquals("Paraíba", address.getEstado());
    }

    @Test
    void mapReturnsEmptyWhenPlacesArrayIsMissingOrEmpty() throws Exception {
        assertTrue(ZippopotamMapper.map(json.readTree("{}")).isEmpty());
        assertTrue(ZippopotamMapper.map(json.readTree("{\"places\":[]}")).isEmpty());
    }
}
