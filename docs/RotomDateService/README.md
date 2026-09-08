# RotomDateService

Pacote: `io.github.sidneyroberto9.rotom.date`
Arquivo: `src/main/java/io/github/sidneyroberto9/rotom/date/RotomDateService.java`

Serviço de cálculo de dias úteis usando o calendário de feriados nacionais brasileiros (biblioteca
**jollyday**, `HolidayCalendar.BRAZIL`). Todos os métodos com data são **sobrecarregados** para
`LocalDate`, `LocalDateTime` e `Date` (exceto `getFirstBusinessDayOfMonth`,
`getLastBusinessDayOfMonth` e `countBusinessDays`, que só existem para `LocalDate`).

> Cobre apenas **feriados nacionais**. Feriados estaduais/municipais não são considerados — se
> precisar deles, injete um `HolidayManager` customizado.

## Construtores

```java
RotomDateService dateService = new RotomDateService();
// calendário BRAZIL + timezone padrão do sistema

RotomDateService dateService = new RotomDateService(holidayManager);
// HolidayManager customizado + timezone padrão do sistema

RotomDateService dateService = new RotomDateService(holidayManager, zone);
// HolidayManager customizado + timezone customizada (usada só para converter Date <-> LocalDate)
```

## Bean Spring

Com `RotomAutoConfiguration` ativo, um bean `RotomDateService` (construído com `new RotomDateService()`,
calendário BRAZIL padrão) fica disponível para injeção.

## Métodos

| Método | Retorno | Descrição |
|---|---|---|
| [`isHoliday(date)`](isHoliday.md) | `boolean` | é feriado nacional? |
| [`isBusinessDay(date)`](isBusinessDay.md) | `boolean` | é dia útil (não fds, não feriado)? |
| [`addBusinessDays(date, int days)`](addBusinessDays.md) | `LocalDate`/`LocalDateTime`/`Date` | soma/subtrai dias úteis |
| [`subtractBusinessDays(date, int days)`](subtractBusinessDays.md) | `LocalDate`/`LocalDateTime`/`Date` | subtrai dias úteis (days ≥ 0) |
| [`adjustToNextBusinessDay(date)`](adjustToNextBusinessDay.md) | `LocalDate`/`LocalDateTime`/`Date` | avança até o próximo dia útil |
| [`adjustToPreviousBusinessDay(date)`](adjustToPreviousBusinessDay.md) | `LocalDate`/`LocalDateTime`/`Date` | recua até o dia útil anterior |
| [`getFirstBusinessDayOfMonth(year, month)`](getFirstBusinessDayOfMonth.md) | `LocalDate` | 1º dia útil do mês |
| [`getLastBusinessDayOfMonth(year, month)`](getLastBusinessDayOfMonth.md) | `LocalDate` | último dia útil do mês |
| [`countBusinessDays(from, to)`](countBusinessDays.md) | `long` | conta dias úteis no intervalo |
