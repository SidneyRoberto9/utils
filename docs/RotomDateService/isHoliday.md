# RotomDateService.isHoliday(date)

**Pacote:** `io.github.sidneyroberto9.rotom.date`
**Arquivo:** `RotomDateService.java`

## Assinaturas

```java
boolean isHoliday(LocalDate date)
boolean isHoliday(LocalDateTime date)
boolean isHoliday(Date date)
```

## Descrição

Verifica se a data é feriado nacional brasileiro, consultando o `HolidayManager` (jollyday)
configurado no construtor.

## Parâmetros

| Nome | Tipo | Significado |
|---|---|---|
| `date` | `LocalDate` \| `LocalDateTime` \| `Date` | data a verificar |

## Retorno

`boolean` — `true` se for feriado nacional, `false` caso contrário.

## Comportamento de borda

- Overload `LocalDateTime` descarta a hora (`date.toLocalDate()`) antes de checar.
- Overload `Date` converte via `RotomDateUtils.toLocalDate(date, zone)`, usando a `zone` configurada no
  construtor do `RotomDateService` (não necessariamente a zone do sistema, se customizada).
- Só considera **feriados nacionais** do calendário `BRAZIL` (ou o `HolidayManager` customizado
  passado no construtor) — feriados estaduais/municipais não entram, a menos que o
  `HolidayManager` injetado os inclua.

## Exemplo

```java
RotomDateService dateService = new RotomDateService();

dateService.isHoliday(LocalDate.of(2026, 1, 1));   // true (Ano Novo)
dateService.isHoliday(LocalDate.of(2026, 1, 2));   // false
```
