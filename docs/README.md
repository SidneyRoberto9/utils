# Documentação — rotom (io.github.sidneyroberto9)

Documentação detalhada, por método, de todos os serviços públicos da biblioteca. Cada classe tem
sua própria pasta com um `README.md` de visão geral e um arquivo `.md` por método.

Para a visão geral navegável (página única, estilo site) veja [`index.html`](index.html).

## Documentos (CPF / CNPJ)

- [`RotomCPFService`](RotomCPFService/README.md) — `io.github.sidneyroberto9.rotom.cpf`
- [`RotomCNPJService`](RotomCNPJService/README.md) — `io.github.sidneyroberto9.rotom.cnpj`

## Comunicação

- [`RotomPhoneNumberService`](RotomPhoneNumberService/README.md) — `io.github.sidneyroberto9.rotom.phoneNumber`
- [`RotomGsm7Converter`](RotomGsm7Converter/README.md) — `io.github.sidneyroberto9.rotom.sms` *(v1.1.0)*

## Texto

- [`RotomStringUtils`](RotomStringUtils/README.md) — `io.github.sidneyroberto9.rotom.strings`

## Data

- [`RotomDateService`](RotomDateService/README.md) — `io.github.sidneyroberto9.rotom.date` (dias úteis / feriados)
- [`RotomDateUtils`](RotomDateUtils/README.md) — `io.github.sidneyroberto9.rotom.date` (conversões / formatação)
- [`RotomDurationUtils`](RotomDurationUtils/README.md) — `io.github.sidneyroberto9.rotom.date` (duração legível em PT) *(v1.1.0)*
- [`RotomBrasilApiHolidayService`](RotomBrasilApiHolidayService/README.md) — `io.github.sidneyroberto9.rotom.date.holiday` (feriados via BrasilAPI, online) *(v1.1.0)*

## Localização (CEP)

- [`RotomCepService`](RotomCepService/README.md) — `io.github.sidneyroberto9.rotom.cep.domain` (busca com fallback multi-provider)
- [`RotomCepUtils`](RotomCepUtils/README.md) — `io.github.sidneyroberto9.rotom.cep.domain` (normalização/validação/formatação pura)

## Aleatoriedade & Codificação *(v1.1.0)*

- [`RotomRandomUtil`](RotomRandomUtil/README.md) — `io.github.sidneyroberto9.rotom.random` (números, strings, tokens, API keys aleatórias)
- [`RotomEncodingUtils`](RotomEncodingUtils/README.md) — `io.github.sidneyroberto9.rotom.encoding` (Base64 de texto e inteiros)
- [`RotomHashUtils`](RotomHashUtils/README.md) — `io.github.sidneyroberto9.rotom.hash` (MD5/SHA-256/SHA-512 de bytes, strings, streams, arquivos)

## Validação *(v1.1.0)*

- [`RotomEmailValidator`](RotomEmailValidator/README.md) — `io.github.sidneyroberto9.rotom.validation`
- [`RotomPasswordValidator`](RotomPasswordValidator/README.md) — `io.github.sidneyroberto9.rotom.validation`
- [`RotomNumberUtils`](RotomNumberUtils/README.md) — `io.github.sidneyroberto9.rotom.validation`

## Formatação, Máscara & Arquivos *(v1.1.0)*

- [`RotomMoneyUtils`](RotomMoneyUtils/README.md) — `io.github.sidneyroberto9.rotom.money` (moeda BRL)
- [`RotomMaskUtils`](RotomMaskUtils/README.md) — `io.github.sidneyroberto9.rotom.mask` (máscara LGPD de CPF/CNPJ)
- [`RotomFileUtils`](RotomFileUtils/README.md) — `io.github.sidneyroberto9.rotom.io` (Base64, tamanho legível, mover arquivo)

## Coleções *(v1.1.0)*

- [`RotomCollectionUtils`](RotomCollectionUtils/README.md) — `io.github.sidneyroberto9.rotom.collections`

## Auto-configuração Spring

Todas as 21 classes acima têm bean registrado por `RotomAutoConfiguration`
(`io.github.sidneyroberto9.rotom.autoconfigure`), condicionado a `@ConditionalOnMissingBean` — basta
ter a lib no classpath de um projeto Spring Boot para injetar qualquer um desses serviços sem
configuração adicional.

## Nota sobre fonte da verdade

Esta documentação foi gerada lendo diretamente o código-fonte em `src/main/java/...`, não o
`README.md` da raiz nem o `index.html` anterior — ambos continham imprecisões (ex. campos do
`Address` em inglês que não existem, afirmação incorreta de que todo método de `RotomDateUtils` tem
overload para `Date`). Onde uma divergência foi encontrada, o comportamento real do código está
documentado explicitamente na seção "Comportamento de borda" de cada método.
