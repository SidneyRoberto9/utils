# RotomRandomUtil.randomNumeric(int size)

**Pacote:** `io.github.sidneyroberto9.rotom.random`
**Arquivo:** `RotomRandomUtil.java`

## Descrição

Gera uma string aleatória contendo apenas dígitos (`0-9`), do tamanho informado.

## Parâmetros

| Nome | Tipo | Significado |
|---|---|---|
| `size` | `int` | tamanho desejado (deve ser ≥ 0) |

## Retorno

`String` — string numérica aleatória de comprimento `size`.

## Comportamento de borda

- **Lança `IllegalArgumentException`** se `size < 0`.
- `size == 0` → retorna `""`.
- Pode gerar zeros à esquerda (não é um número, é uma string de dígitos).

## Exemplo

```java
RotomRandomUtil random = new RotomRandomUtil();

random.randomNumeric(6);  // ex. "042817" (código de verificação)
```
