package io.github.sidneyroberto9.rotom.cep.infra.provider;

import io.github.sidneyroberto9.rotom.cep.domain.Address;
import io.github.sidneyroberto9.rotom.cep.infra.mapper.BrasilCepMapper;
import com.fasterxml.jackson.databind.JsonNode;

import java.io.IOException;
import java.util.Optional;

public class BrasilCepProvider extends BaseProvider {

    private final String baseUrl;

    public BrasilCepProvider() {
        this("https://brasilcep.dev/api/cep/");
    }

    BrasilCepProvider(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    @Override
    public String name() {
        return "BrasilCEP";
    }

    @Override
    public Optional<Address> fetch(String cep) throws IOException {
        JsonNode data = this.get(this.baseUrl + cep);

        return BrasilCepMapper.map(data);
    }
}
