package io.github.sidneyroberto9.rotom.strings;

import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class RotomStringUtilsTest {

    private final RotomStringUtils strings = new RotomStringUtils();

    @Test
    void isBlankAndIsNotBlankDetectEmptyContent() {
        assertTrue(strings.isBlank(null));
        assertTrue(strings.isBlank(""));
        assertTrue(strings.isBlank("   "));
        assertFalse(strings.isBlank("a"));
        assertFalse(strings.isNotBlank(""));
        assertTrue(strings.isNotBlank("a"));
    }

    @Test
    void trimOrNullTrimsOrReturnsNullWhenBlank() {
        assertEquals("hello", strings.trimOrNull("  hello  "));
        assertNull(strings.trimOrNull("   "));
        assertNull(strings.trimOrNull(null));
    }

    @Test
    void requireNonBlankReturnsTrimmedValueOrThrows() {
        assertEquals("hello", strings.requireNonBlank("  hello  ", "required"));
        assertThrows(IllegalArgumentException.class, () -> strings.requireNonBlank(" ", "required"));
    }

    @Test
    void capitalizeUppercasesFirstLetterAndLowercasesRest() {
        assertEquals("Hello", strings.capitalize("hELLO"));
        assertNull(strings.capitalize(null));
        assertEquals("", strings.capitalize(""));
    }

    @Test
    void capitalizeWordsKeepsConnectivesLowercaseExceptFirstWord() {
        assertEquals("Maria da Silva", strings.capitalizeWords("MARIA DA SILVA"));
        assertEquals("", strings.capitalizeWords(null));
    }

    @Test
    void firstTwoNamesExtractsAndCapitalizesLeadingWords() {
        assertEquals("João Da", strings.firstTwoNames("joão da silva souza"));
        assertEquals("João", strings.firstTwoNames("joão"));
        assertEquals("", strings.firstTwoNames(null));
    }

    @Test
    void digitsOnlyKeepsOnlyNumbers() {
        assertEquals("12345", strings.digitsOnly("a1b2c3d4e5"));
        assertEquals("", strings.digitsOnly(null));
        assertEquals("", strings.digitsOnly(""));
    }

    @Test
    void alphanumericOnlyKeepsLettersAndDigits() {
        assertEquals("abc123", strings.alphanumericOnly("abc-123!@#"));
        assertEquals("", strings.alphanumericOnly(null));
    }

    @Test
    void removeAccentsStripsDiacritics() {
        assertEquals("cafe", strings.removeAccents("café"));
        assertNull(strings.removeAccents(null));
    }

    @Test
    void slugifyProducesUrlFriendlyText() {
        assertEquals("ola-mundo", strings.slugify("Olá Mundo!"));
        assertEquals("", strings.slugify(null));
    }

    @Test
    void slugifyCollapsesRepeatedHyphensAndTrimsEnds() {
        assertEquals("a-b", strings.slugify("  a   b  "));
    }

    @Test
    void truncateShortensToMaxLength() {
        assertEquals("hell", strings.truncate("hello", 4));
        assertEquals("hi", strings.truncate("hi", 10));
        assertNull(strings.truncate(null, 4));
    }

    @Test
    void truncateThrowsOnNegativeMaxLength() {
        assertThrows(IllegalArgumentException.class, () -> strings.truncate("hello", -1));
    }

    @Test
    void containsIgnoreCaseIgnoresCasingAndNulls() {
        assertTrue(strings.containsIgnoreCase("Hello World", "world"));
        assertFalse(strings.containsIgnoreCase("Hello World", "xyz"));
        assertFalse(strings.containsIgnoreCase(null, "a"));
        assertFalse(strings.containsIgnoreCase("a", null));
    }

    @Test
    void normalizeEmailTrimsAndLowercases() {
        assertEquals("user@example.com", strings.normalizeEmail("  USER@Example.COM  "));
        assertNull(strings.normalizeEmail(null));
    }

    @Test
    void emailDomainExtractsUppercaseDomain() {
        assertEquals("GMAIL", strings.emailDomain("user@gmail.com"));
    }

    @Test
    void emailDomainThrowsForInvalidEmail() {
        assertThrows(IllegalArgumentException.class, () -> strings.emailDomain("not-an-email"));
        assertThrows(IllegalArgumentException.class, () -> strings.emailDomain(""));
    }

    @Test
    void maskEmailKeepsFirstCharacterOfLocalPart() {
        assertEquals("u***@gmail.com", strings.maskEmail("user@gmail.com"));
    }

    @Test
    void maskEmailThrowsForInvalidEmail() {
        assertThrows(IllegalArgumentException.class, () -> strings.maskEmail("not-an-email"));
    }

    @Test
    void encodeUrlAppendsUrlEncodedText() {
        assertEquals(
                "https://example.com/search?q=hello+world",
                strings.encodeUrl("https://example.com/search?q=", "hello world")
        );
    }

    @Test
    void toCamelCaseWithSpacesCapitalizesEachWord() {
        assertEquals("Hello World", strings.toCamelCaseWithSpaces("HELLO WORLD"));
        assertEquals("", strings.toCamelCaseWithSpaces(null));
        assertEquals("", strings.toCamelCaseWithSpaces(""));
    }

    @Test
    void nameInitialsReturnsFirstAndLastInitialsForMultiWordName() {
        assertEquals("JS", strings.nameInitials("joão silva"));
    }

    @Test
    void nameInitialsReturnsFirstTwoCharactersForSingleWordName() {
        assertEquals("JO", strings.nameInitials("joao"));
    }

    @Test
    void nameInitialsReturnsQuestionMarkForBlankName() {
        assertEquals("?", strings.nameInitials(null));
        assertEquals("?", strings.nameInitials("   "));
    }

    @Test
    void formatElevenDigitsAppliesStateRegistrationMask() {
        assertEquals("12.3.4567890-1", strings.formatElevenDigits("12345678901"));
    }

    @Test
    void formatElevenDigitsReturnsInputUnchangedWhenNotElevenDigits() {
        assertEquals("123", strings.formatElevenDigits("123"));
        assertNull(strings.formatElevenDigits(null));
    }

    @Test
    void defaultIfBlankReturnsTrimmedValueOrDefault() {
        assertEquals("hello", strings.defaultIfBlank("  hello  ", "default"));
        assertEquals("default", strings.defaultIfBlank("   ", "default"));
        assertEquals("default", strings.defaultIfBlank(null, "default"));
    }

    @Test
    void titleCasePtCapitalizesWordsAndLowersConnectivesExceptFirst() {
        assertEquals("Termo de Uso do Sistema", strings.titleCasePt("TERMO DE USO DO SISTEMA"));
    }

    @Test
    void titleCasePtWithAcronymsPreservesExactCasing() {
        assertEquals(
                "Termo de Responsabilidade para Uso de EPI",
                strings.titleCasePt("TERMO DE RESPONSABILIDADE PARA USO DE EPI", Map.of("epi", "EPI"))
        );
    }

    @Test
    void titleCasePtReturnsEmptyStringForNullOrBlank() {
        assertEquals("", strings.titleCasePt(null));
        assertEquals("", strings.titleCasePt("   "));
    }

    @Test
    void normalizeUfTrimsUppercasesAndTruncatesToTwoChars() {
        assertEquals("SP", strings.normalizeUf("  sp  "));
        assertEquals("RJ", strings.normalizeUf("rj-brasil"));
        assertNull(strings.normalizeUf(null));
    }

    @Test
    void isValidUfAcceptsOnlyKnownBrazilianStates() {
        assertTrue(strings.isValidUf("sp"));
        assertFalse(strings.isValidUf("XX"));
        assertFalse(strings.isValidUf(null));
    }
}
