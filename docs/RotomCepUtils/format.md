# RotomCepUtils.format(String cep)

**Pacote:** `io.github.sidneyroberto9.rotom.cep.domain`
**Arquivo:** `RotomCepUtils.java`

## Descrição

Formata o CEP para o padrão `XXXXX-XXX`. Diferente de `isValid`, **normaliza o input
internamente** antes de formatar — aceita CEP com ou sem máscara.

## Parâmetros

| Nome | Tipo | Significado |
|---|---|---|
| `cep` | `String` | CEP com ou sem máscara |

## Retorno

`String` — CEP formatado como `XXXXX-XXX`.

## Comportamento de borda

- **Lança `IllegalArgumentException("CEP inválido: " + cep)`** se, após normalizado, o CEP não
  tiver exatamente 8 dígitos (via [`isValid`](isValid.md) interno).
- `cep == null` → `normalize(null)` retorna `null`, e `isValid(null)` retorna `false` → **lança
  `IllegalArgumentException("CEP inválido: null")`** (não `NullPointerException`, diferente de
  `RotomCPFService.format`/`RotomCNPJService.format`).

## Exemplo

```java
RotomCepUtils cepUtils = new RotomCepUtils();

cepUtils.format("58038000");     // "58038-000"
cepUtils.format("58038-000");    // "58038-000" (idempotente)
cepUtils.format(null);            // lança IllegalArgumentException
cepUtils.format("123");           // lança IllegalArgumentException
```
