# RotomStringUtils.removeAccents(String input)

**Pacote:** `io.github.sidneyroberto9.rotom.strings`
**Arquivo:** `RotomStringUtils.java`

## Descrição

Remove acentos e diacríticos via normalização Unicode NFD (decompõe o caractere acentuado em
letra-base + marca combinante, depois remove a marca).

## Parâmetros

| Nome | Tipo | Significado |
|---|---|---|
| `input` | `String` | string com acentos |

## Retorno

`String` — string sem acentos, ou `null` se o input for `null`.

## Comportamento de borda

- `input == null` → `null` (diferente de `alphanumericOnly`/`digitsOnly`, que retornam `""`).
- Usado internamente por [`slugify`](slugify.md).
- Só remove marcas diacríticas combinantes (`\p{InCombiningDiacriticalMarks}`) — não afeta
  caracteres que não têm forma decomposta NFD (ex. `ß`, `æ` permanecem intactos).

## Exemplo

```java
RotomStringUtils str = new RotomStringUtils();

str.removeAccents("café com açúcar");  // "cafe com acucar"
str.removeAccents(null);                 // null
```
