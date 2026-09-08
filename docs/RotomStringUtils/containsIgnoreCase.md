# RotomStringUtils.containsIgnoreCase(String text, String search)

**Pacote:** `io.github.sidneyroberto9.rotom.strings`
**Arquivo:** `RotomStringUtils.java`

## Descrição

Verifica se `text` contém `search`, ignorando diferenças de maiúsculas/minúsculas.

## Parâmetros

| Nome | Tipo | Significado |
|---|---|---|
| `text` | `String` | texto onde buscar |
| `search` | `String` | string a procurar |

## Retorno

`boolean` — `true` se encontrado, `false` se não encontrado **ou** se `text`/`search` for `null`.

## Comportamento de borda

- `text == null` ou `search == null` → `false` (não lança exceção).
- Implementado via `text.toLowerCase().contains(search.toLowerCase())` — não usa `Locale`
  explícito, então sensível ao locale padrão da JVM para conversão de maiúsculas/minúsculas em
  casos raros (ex. turco).

## Exemplo

```java
RotomStringUtils str = new RotomStringUtils();

str.containsIgnoreCase("João Silva", "silva");  // true
str.containsIgnoreCase("João Silva", "pedro");  // false
str.containsIgnoreCase(null, "silva");           // false
```
