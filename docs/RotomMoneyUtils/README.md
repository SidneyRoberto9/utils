# RotomMoneyUtils

Pacote: `io.github.sidneyroberto9.rotom.money`
Arquivo: `src/main/java/io/github/sidneyroberto9/rotom/money/RotomMoneyUtils.java`

Utilitário para formatação de valores monetários como moeda brasileira (BRL). Introduzido na
v1.1.0.

## Construtor

```java
RotomMoneyUtils money = new RotomMoneyUtils();
```

## Bean Spring

Com `RotomAutoConfiguration` ativo, um bean `RotomMoneyUtils` fica disponível para injeção.

## Métodos

| Método | Retorno | Descrição |
|---|---|---|
| [`formatBRL(double value)`](formatBRL.md) | `String` | formata como moeda BRL |
| [`formatBRL(BigDecimal value)`](formatBRL.md) | `String` | formata como moeda BRL |
