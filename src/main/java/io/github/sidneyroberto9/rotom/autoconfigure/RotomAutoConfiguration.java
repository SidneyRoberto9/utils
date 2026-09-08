package io.github.sidneyroberto9.rotom.autoconfigure;

import io.github.sidneyroberto9.rotom.cep.domain.RotomCepService;
import io.github.sidneyroberto9.rotom.cep.domain.RotomCepUtils;
import io.github.sidneyroberto9.rotom.cnpj.RotomCNPJService;
import io.github.sidneyroberto9.rotom.collections.RotomCollectionUtils;
import io.github.sidneyroberto9.rotom.cpf.RotomCPFService;
import io.github.sidneyroberto9.rotom.crypto.RotomCryptoUtils;
import io.github.sidneyroberto9.rotom.date.RotomDateService;
import io.github.sidneyroberto9.rotom.date.RotomDateUtils;
import io.github.sidneyroberto9.rotom.date.RotomDurationUtils;
import io.github.sidneyroberto9.rotom.date.holiday.RotomBrasilApiHolidayService;
import io.github.sidneyroberto9.rotom.encoding.RotomEncodingUtils;
import io.github.sidneyroberto9.rotom.hash.RotomHashUtils;
import io.github.sidneyroberto9.rotom.http.RotomHttpUtils;
import io.github.sidneyroberto9.rotom.io.RotomFileUtils;
import io.github.sidneyroberto9.rotom.mask.RotomMaskUtils;
import io.github.sidneyroberto9.rotom.money.RotomMoneyUtils;
import io.github.sidneyroberto9.rotom.phoneNumber.RotomPhoneNumberService;
import io.github.sidneyroberto9.rotom.random.RotomRandomUtil;
import io.github.sidneyroberto9.rotom.sms.RotomGsm7Converter;
import io.github.sidneyroberto9.rotom.strings.RotomStringUtils;
import io.github.sidneyroberto9.rotom.validation.RotomEmailValidator;
import io.github.sidneyroberto9.rotom.validation.RotomNumberUtils;
import io.github.sidneyroberto9.rotom.validation.RotomPasswordValidator;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;

@AutoConfiguration
public class RotomAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public RotomCepUtils cepUtils() {
        return new RotomCepUtils();
    }

    @Bean
    @ConditionalOnMissingBean
    public RotomCepService cepService() {
        return new RotomCepService();
    }

    @Bean
    @ConditionalOnMissingBean
    public RotomCPFService cpfService() {
        return new RotomCPFService();
    }

    @Bean
    @ConditionalOnMissingBean
    public RotomCNPJService cnpjService() {
        return new RotomCNPJService();
    }

    @Bean
    @ConditionalOnMissingBean
    public RotomPhoneNumberService phoneNumberService() {
        return new RotomPhoneNumberService();
    }

    @Bean
    @ConditionalOnMissingBean
    public RotomDateUtils dateUtils() {
        return new RotomDateUtils();
    }

    @Bean
    @ConditionalOnMissingBean
    public RotomDateService dateService() {
        return new RotomDateService();
    }

    @Bean
    @ConditionalOnMissingBean
    public RotomStringUtils stringUtils() {
        return new RotomStringUtils();
    }

    @Bean
    @ConditionalOnMissingBean
    public RotomRandomUtil randomUtil() {
        return new RotomRandomUtil();
    }

    @Bean
    @ConditionalOnMissingBean
    public RotomEncodingUtils encodingUtils() {
        return new RotomEncodingUtils();
    }

    @Bean
    @ConditionalOnMissingBean
    public RotomHashUtils hashUtils() {
        return new RotomHashUtils();
    }

    @Bean
    @ConditionalOnMissingBean
    public RotomGsm7Converter gsm7Converter() {
        return new RotomGsm7Converter();
    }

    @Bean
    @ConditionalOnMissingBean
    public RotomEmailValidator emailValidator() {
        return new RotomEmailValidator();
    }

    @Bean
    @ConditionalOnMissingBean
    public RotomPasswordValidator passwordValidator() {
        return new RotomPasswordValidator();
    }

    @Bean
    @ConditionalOnMissingBean
    public RotomNumberUtils numberUtils() {
        return new RotomNumberUtils();
    }

    @Bean
    @ConditionalOnMissingBean
    public RotomMoneyUtils moneyUtils() {
        return new RotomMoneyUtils();
    }

    @Bean
    @ConditionalOnMissingBean
    public RotomFileUtils fileUtils() {
        return new RotomFileUtils();
    }

    @Bean
    @ConditionalOnMissingBean
    public RotomMaskUtils maskUtils() {
        return new RotomMaskUtils();
    }

    @Bean
    @ConditionalOnMissingBean
    public RotomDurationUtils durationUtils() {
        return new RotomDurationUtils();
    }

    @Bean
    @ConditionalOnMissingBean
    public RotomBrasilApiHolidayService brasilApiHolidayService() {
        return new RotomBrasilApiHolidayService();
    }

    @Bean
    @ConditionalOnMissingBean
    public RotomCollectionUtils collectionUtils() {
        return new RotomCollectionUtils();
    }

    @Bean
    @ConditionalOnMissingBean
    public RotomCryptoUtils cryptoUtils() {
        return new RotomCryptoUtils();
    }

    @Bean
    @ConditionalOnMissingBean
    @ConditionalOnClass(name = "jakarta.servlet.http.HttpServletRequest")
    public RotomHttpUtils httpUtils() {
        return new RotomHttpUtils();
    }
}
