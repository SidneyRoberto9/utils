# RotomEncodingUtils

Pacote: `io.github.sidneyroberto9.rotom.encoding`
Arquivo: `src/main/java/io/github/sidneyroberto9/rotom/encoding/RotomEncodingUtils.java`

Utilitário para codificação/decodificação Base64 de strings e inteiros. Introduzido na v1.1.0.

## Construtor

```java
RotomEncodingUtils encoding = new RotomEncodingUtils();
```

## Bean Spring

Com `RotomAutoConfiguration` ativo, um bean `RotomEncodingUtils` fica disponível para injeção.

## Métodos

| Método | Retorno | Descrição |
|---|---|---|
| [`toBase64(String input)`](toBase64.md) | `String` | codifica texto para Base64 (UTF-8) |
| [`fromBase64(String base64)`](fromBase64.md) | `String` | decodifica Base64 para texto (UTF-8) |
| [`encodeInt(int value)`](encodeInt.md) | `String` | codifica inteiro (4 bytes big-endian) em Base64 |
| [`decodeInt(String encoded)`](decodeInt.md) | `int` | decodifica Base64 de volta a inteiro |
