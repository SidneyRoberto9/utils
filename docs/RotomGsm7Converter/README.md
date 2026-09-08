# RotomGsm7Converter

Pacote: `io.github.sidneyroberto9.rotom.sms`
Arquivo: `src/main/java/io/github/sidneyroberto9/rotom/sms/RotomGsm7Converter.java`

Utilitário para converter texto UTF-8 para o alfabeto padrão GSM 7-bit usado em corpos de SMS.
Introduzido na v1.1.0.

## Construtor

```java
RotomGsm7Converter gsm7 = new RotomGsm7Converter();
```

## Bean Spring

Com `RotomAutoConfiguration` ativo, um bean `RotomGsm7Converter` fica disponível para injeção.

## Métodos

| Método | Retorno | Descrição |
|---|---|---|
| [`convertToGsm7(String input)`](convertToGsm7.md) | `String` | restringe o texto ao charset GSM-7 |
