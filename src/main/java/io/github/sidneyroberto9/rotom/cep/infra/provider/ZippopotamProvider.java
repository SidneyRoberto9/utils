package io.github.sidneyroberto9.rotom.cep.infra.provider;

import io.github.sidneyroberto9.rotom.cep.domain.Address;
import io.github.sidneyroberto9.rotom.cep.infra.mapper.ZippopotamMapper;
import com.fasterxml.jackson.databind.JsonNode;

import java.io.IOException;
import java.util.Optional;

public class ZippopotamProvider extends BaseProvider {

    private final String baseUrl;

    public ZippopotamProvider() {
        this("https://api.zippopotam.us/br/");
    }

    ZippopotamProvider(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    @Override
    public String name() {
        return "Zippopotam";
    }

    @Override
    public Optional<Address> fetch(String cep) throws IOException {
        String formatted = cep.substring(0, 5) + "-" + cep.substring(5);

        JsonNode data = this.get(this.baseUrl + formatted);

        return ZippopotamMapper.map(data);
    }
}
