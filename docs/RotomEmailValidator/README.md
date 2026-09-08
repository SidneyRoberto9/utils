# RotomEmailValidator

Pacote: `io.github.sidneyroberto9.rotom.validation`
Arquivo: `src/main/java/io/github/sidneyroberto9/rotom/validation/RotomEmailValidator.java`

Utilitário para validar a estrutura de endereços de e-mail. Introduzido na v1.1.0. Usa **regex**,
evitando adicionar `javax.mail` como dependência da lib.

## Construtor

```java
RotomEmailValidator emailValidator = new RotomEmailValidator();
```

## Bean Spring

Com `RotomAutoConfiguration` ativo, um bean `RotomEmailValidator` fica disponível para injeção.

## Métodos

| Método | Retorno | Descrição |
|---|---|---|
| [`isValid(String email)`](isValid.md) | `boolean` | verifica se o e-mail é estruturalmente válido |
