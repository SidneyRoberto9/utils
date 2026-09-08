package io.github.sidneyroberto9.rotom.cep.infra.provider;

import io.github.sidneyroberto9.rotom.cep.domain.Address;
import io.github.sidneyroberto9.rotom.cep.infra.mapper.OpenCepMapper;
import com.fasterxml.jackson.databind.JsonNode;

import java.io.IOException;
import java.util.Optional;

public class OpenCepProvider extends BaseProvider {

    private final String baseUrl;

    public OpenCepProvider() {
        this("https://opencep.com/v1/");
    }

    OpenCepProvider(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    @Override
    public String name() {
        return "OpenCEP";
    }

    @Override
    public Optional<Address> fetch(String cep) throws IOException {
        JsonNode data = this.get(this.baseUrl + cep);

        return OpenCepMapper.map(data);
    }
}
