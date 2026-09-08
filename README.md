# Rotom

[![Maven Central](https://img.shields.io/maven-central/v/io.github.sidneyroberto9/rotom)](https://central.sonatype.com/artifact/io.github.sidneyroberto9/rotom)
[![License: MIT](https://img.shields.io/badge/License-MIT-blue.svg)](https://opensource.org/licenses/MIT)
[![Java](https://img.shields.io/badge/Java-11%2B-orange)](https://www.oracle.com/java/technologies/javase/jdk11-archive-downloads.html)

Brazilian-focused Java utility library with validators, formatters, CEP lookup, business day calculations, and string helpers. Includes optional Spring Boot auto-configuration.

## Requirements

- Java 11+
- (Optional) Spring Boot 2.7+

## Installation

**Maven**

```xml
<dependency>
    <groupId>io.github.sidneyroberto9</groupId>
    <artifactId>rotom</artifactId>
    <version>2.0.0</version>
</dependency>
```

**Gradle**

```groovy
implementation 'io.github.sidneyroberto9:rotom:2.0.0'
```

## Usage

All services can be instantiated directly or injected via Spring Boot auto-configuration.

---

### RotomCPFService

Validates and formats Brazilian CPF numbers. Accepts masked or unmasked input.

```java
RotomCPFService cpf = new RotomCPFService();

cpf.isValid("529.982.247-25");   // true
cpf.isValid("111.111.111-11");   // false — repeated digits

cpf.format("52998224725");       // "529.982.247-25"
cpf.strip("529.982.247-25");     // "52998224725"
cpf.isFormatted("529.982.247-25"); // true
```

---

### RotomCNPJService

Validates and formats Brazilian CNPJ numbers. Accepts masked or unmasked input.

```java
RotomCNPJService cnpj = new RotomCNPJService();

cnpj.isValid("11.222.333/0001-81");  // true
cnpj.isValid("11.111.111/1111-11");  // false — repeated digits

cnpj.format("11222333000181");       // "11.222.333/0001-81"
cnpj.strip("11.222.333/0001-81");    // "11222333000181"
cnpj.isFormatted("11.222.333/0001-81"); // true
```

---

### RotomCepService

Looks up Brazilian addresses by CEP with automatic multi-provider fallback.

Default provider priority: **ViaCEP → OpenCEP → BrasilCEP → CEP.Rest → Zippopotam**

```java
RotomCepService cepService = new RotomCepService();

Address address = cepService.lookup("58038-000");
// address.getLogradouro() → "Avenida Epitácio Pessoa"
// address.getLocalidade() → "João Pessoa"
// address.getUf()         → "PB"
```

The `Address` object contains: `uf`, `cep`, `bairro`, `localidade`, `logradouro`, `complemento`, `ibge`, `gia`, `ddd`, `siafi`, `unidade`, `estado`, `regiao`.

If no provider returns data, an `Address` with all fields `null` (except `cep`) is returned. Throws `IllegalArgumentException` for CEPs with invalid length or non-numeric characters.

**Custom providers**

```java
RotomCepService cepService = new RotomCepService(List.of(new ViaCepProvider(), new OpenCepProvider()));
```

---

### RotomDateService

Business day calculations using the Brazilian national holiday calendar (jollyday).

```java
RotomDateService dateService = new RotomDateService();

LocalDate date = LocalDate.of(2025, 6, 9); // Monday

dateService.isHoliday(date);       // false
dateService.isBusinessDay(date);   // true

dateService.addBusinessDays(date, 3);      // skips weekends and holidays
dateService.subtractBusinessDays(date, 2);

dateService.adjustToNextBusinessDay(date);     // returns date if already a business day
dateService.adjustToPreviousBusinessDay(date);

dateService.getFirstBusinessDayOfMonth(2025, 1); // 2025-01-02 (Jan 1 is a holiday)
dateService.getLastBusinessDayOfMonth(2025, 1);  // 2025-01-31

dateService.countBusinessDays(LocalDate.of(2025, 1, 1), LocalDate.of(2025, 1, 31)); // 21
```

All methods are overloaded for `LocalDate`, `LocalDateTime`, and `java.util.Date`.

---

### RotomPhoneNumberService

Formats and validates Brazilian phone numbers (mobile and landline).

```java
RotomPhoneNumberService phone = new RotomPhoneNumberService();

phone.isValid("83986635812");           // true
phone.isMobile("83986635812");          // true
phone.isLandline("8332221234");         // true

phone.format("83986635812");            // "(83) 98663-5812"
phone.format("8332221234");             // "(83) 3222-1234"
phone.formatWithCountryCode("83986635812"); // "+55 (83) 98663-5812"

phone.strip("(83) 98663-5812");         // "83986635812"
phone.getDDD("83986635812");            // "83"
phone.isFormatted("(83) 98663-5812");   // true
```

Numbers with country code prefix `+55` or `55` are stripped automatically. Numbers without area code (8 or 9 digits) are accepted.

---

### RotomStringUtils

String manipulation with Brazilian Portuguese support.

```java
RotomStringUtils str = new RotomStringUtils();

// Null-safety
str.isBlank(null);          // true
str.isNotBlank("hello");    // true
str.trimOrNull("  ");       // null
str.requireNonBlank("", "field required"); // throws IllegalArgumentException

// Capitalization
str.capitalize("hello");                          // "Hello"
str.capitalizeWords("joão da silva");             // "João da Silva"
str.firstTwoNames("João da Silva Souza");         // "João Da"

// Extraction
str.digitsOnly("CPF: 123.456.789-09");            // "12345678909"
str.alphanumericOnly("hello, world!");             // "helloworld"

// Normalization
str.removeAccents("café");                         // "cafe"
str.slugify("Olá Mundo!");                         // "ola-mundo"
str.truncate("hello world", 5);                    // "hello"
str.containsIgnoreCase("Hello World", "world");    // true

// Email
str.normalizeEmail("  USER@GMAIL.COM  ");          // "user@gmail.com"
str.emailDomain("user@gmail.com");                 // "GMAIL"
str.maskEmail("user@gmail.com");                   // "u***@gmail.com"

// URL
str.encodeUrl("https://example.com/search?q=", "hello world");
// "https://example.com/search?q=hello+world"
```

---

## Spring Boot Auto-Configuration

When Spring Boot is on the classpath, all services are registered as beans automatically — no configuration required.

```java
@Service
public class MyService {

    private final RotomCPFService cpfService;
    private final RotomCNPJService cnpjService;
    private final RotomCepService cepService;
    private final RotomDateService dateService;
    private final RotomPhoneNumberService phoneNumberService;
    private final RotomStringUtils stringUtils;

    public MyService(
            RotomCPFService cpfService,
            RotomCNPJService cnpjService,
            RotomCepService cepService,
            RotomDateService dateService,
            RotomPhoneNumberService phoneNumberService,
            RotomStringUtils stringUtils
    ) {
        this.cpfService = cpfService;
        this.cnpjService = cnpjService;
        this.cepService = cepService;
        this.dateService = dateService;
        this.phoneNumberService = phoneNumberService;
        this.stringUtils = stringUtils;
    }
}
```

## License

MIT — see [LICENSE](https://opensource.org/licenses/MIT).