# RotomStringUtils.digitsOnly(String input)

**Pacote:** `io.github.sidneyroberto9.rotom.strings`
**Arquivo:** `RotomStringUtils.java`

## Descrição

Remove todos os caracteres não numéricos, retornando apenas dígitos `0`–`9`.

## Parâmetros

| Nome | Tipo | Significado |
|---|---|---|
| `input` | `String` | string a processar |

## Retorno

`String` — apenas dígitos, ou `""` se `null` ou vazio.

## Comportamento de borda

- `input == null` ou `input.isEmpty()` → `""` (**não** `null` — diferente de
  `RotomCPFService.strip`/`RotomCNPJService.strip`/`RotomPhoneNumberService.strip`, que retornam `null` para
  input `null`). Atenção ao trocar entre esses helpers.
- Genérico: não é específico de CPF/CNPJ/telefone, serve para qualquer extração de dígitos.

## Exemplo

```java
RotomStringUtils str = new RotomStringUtils();

str.digitsOnly("(83) 98663-5812");  // "83986635812"
str.digitsOnly(null);                // ""
str.digitsOnly("");                  // ""
```
