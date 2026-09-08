package io.github.sidneyroberto9.rotom.cep.domain;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

class RotomCepServiceTest {

    @Test
    void lookupReturnsFirstSuccessfulProviderResult() {
        Address expected = new Address("PB", "58038000", null, "João Pessoa", null, null, null, null, null, null, null, null, null);
        RotomCepService service = new RotomCepService(List.of(
                failingProvider("Failing"),
                emptyProvider("Empty"),
                successProvider("Success", expected)
        ));

        assertEquals(expected, service.lookup("58038-000"));
    }

    @Test
    void lookupSkipsProvidersThatThrow() {
        Address expected = new Address("PB", "58038000", null, "João Pessoa", null, null, null, null, null, null, null, null, null);
        RotomCepService service = new RotomCepService(List.of(
                failingProvider("Failing"),
                successProvider("Success", expected)
        ));

        assertEquals(expected, service.lookup("58038000"));
    }

    @Test
    void lookupReturnsCepOnlyAddressWhenAllProvidersFailOrReturnEmpty() {
        RotomCepService service = new RotomCepService(List.of(
                failingProvider("Failing"),
                emptyProvider("Empty")
        ));

        Address result = service.lookup("58038000");

        assertEquals("58038000", result.getCep());
        assertNull(result.getUf());
        assertNull(result.getLogradouro());
    }

    @Test
    void lookupThrowsForInvalidCep() {
        RotomCepService service = new RotomCepService(List.of(emptyProvider("Empty")));

        assertThrows(IllegalArgumentException.class, () -> service.lookup("123"));
    }

    private static CepProvider successProvider(String name, Address address) {
        return new CepProvider() {
            @Override
            public String name() {
                return name;
            }

            @Override
            public Optional<Address> fetch(String cep) {
                return Optional.of(address);
            }
        };
    }

    private static CepProvider emptyProvider(String name) {
        return new CepProvider() {
            @Override
            public String name() {
                return name;
            }

            @Override
            public Optional<Address> fetch(String cep) {
                return Optional.empty();
            }
        };
    }

    private static CepProvider failingProvider(String name) {
        return new CepProvider() {
            @Override
            public String name() {
                return name;
            }

            @Override
            public Optional<Address> fetch(String cep) throws IOException {
                throw new IOException("boom");
            }
        };
    }
}
