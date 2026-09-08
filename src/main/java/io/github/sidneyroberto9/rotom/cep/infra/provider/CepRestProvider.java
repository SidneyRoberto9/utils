package io.github.sidneyroberto9.rotom.cep.infra.provider;

import io.github.sidneyroberto9.rotom.cep.domain.Address;
import io.github.sidneyroberto9.rotom.cep.infra.mapper.CepRestMapper;
import com.fasterxml.jackson.databind.JsonNode;

import java.io.IOException;
import java.util.Optional;

public class CepRestProvider extends BaseProvider {

    private final String baseUrl;

    public CepRestProvider() {
        this("https://cep.rest/cep/");
    }

    CepRestProvider(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    @Override
    public String name() {
        return "CEP.Rest";
    }

    @Override
    public Optional<Address> fetch(String cep) throws IOException {
        JsonNode data = this.get(this.baseUrl + cep);

        return CepRestMapper.map(data);
    }
}
