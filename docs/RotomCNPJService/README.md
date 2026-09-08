# RotomCNPJService

Pacote: `io.github.sidneyroberto9.rotom.cnpj`
Arquivo: `src/main/java/io/github/sidneyroberto9/rotom/cnpj/RotomCNPJService.java`

Serviço de validação e formatação de CNPJ (Cadastro Nacional da Pessoa Jurídica). Todos os métodos
aceitam CNPJ com ou sem máscara. Todos os métodos são de **instância**.

## Construtor

```java
RotomCNPJService cnpj = new RotomCNPJService();
```

## Bean Spring

Com `RotomAutoConfiguration` ativo, um bean `RotomCNPJService` fica disponível para injeção automática.

## Métodos

| Método | Retorno | Descrição |
|---|---|---|
| [`format(String cnpj)`](format.md) | `String` | Formata para `XX.XXX.XXX/XXXX-XX` |
| [`strip(String cnpj)`](strip.md) | `String` | Remove máscara, deixa só dígitos |
| [`isFormatted(String cnpj)`](isFormatted.md) | `boolean` | Verifica se já está no padrão mascarado |
| [`isValid(String cnpj)`](isValid.md) | `boolean` | Valida os dois dígitos verificadores |
