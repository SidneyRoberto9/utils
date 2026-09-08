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
import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.AutoConfigurations;
import org.springframework.boot.test.context.runner.ApplicationContextRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static org.assertj.core.api.Assertions.assertThat;

class RotomAutoConfigurationTest {

    private final ApplicationContextRunner contextRunner =
            new ApplicationContextRunner().withConfiguration(AutoConfigurations.of(RotomAutoConfiguration.class));

    @Test
    void registersAllUtilityBeans() {
        contextRunner.run(context -> {
            assertThat(context).hasSingleBean(RotomCepUtils.class);
            assertThat(context).hasSingleBean(RotomCepService.class);
            assertThat(context).hasSingleBean(RotomCPFService.class);
            assertThat(context).hasSingleBean(RotomCNPJService.class);
            assertThat(context).hasSingleBean(RotomPhoneNumberService.class);
            assertThat(context).hasSingleBean(RotomDateUtils.class);
            assertThat(context).hasSingleBean(RotomDateService.class);
            assertThat(context).hasSingleBean(RotomStringUtils.class);
            assertThat(context).hasSingleBean(RotomRandomUtil.class);
            assertThat(context).hasSingleBean(RotomEncodingUtils.class);
            assertThat(context).hasSingleBean(RotomHashUtils.class);
            assertThat(context).hasSingleBean(RotomGsm7Converter.class);
            assertThat(context).hasSingleBean(RotomEmailValidator.class);
            assertThat(context).hasSingleBean(RotomPasswordValidator.class);
            assertThat(context).hasSingleBean(RotomNumberUtils.class);
            assertThat(context).hasSingleBean(RotomMoneyUtils.class);
            assertThat(context).hasSingleBean(RotomFileUtils.class);
            assertThat(context).hasSingleBean(RotomMaskUtils.class);
            assertThat(context).hasSingleBean(RotomDurationUtils.class);
            assertThat(context).hasSingleBean(RotomBrasilApiHolidayService.class);
            assertThat(context).hasSingleBean(RotomCollectionUtils.class);
            assertThat(context).hasSingleBean(RotomCryptoUtils.class);
            assertThat(context).hasSingleBean(RotomHttpUtils.class);
        });
    }

    @Test
    void backsOffWhenUserDefinesOwnBean() {
        contextRunner.withUserConfiguration(CustomCpfServiceConfiguration.class).run(context -> {
            assertThat(context).hasSingleBean(RotomCPFService.class);
            assertThat(context.getBean(RotomCPFService.class)).isSameAs(CustomCpfServiceConfiguration.CUSTOM_INSTANCE);
        });
    }

    @Configuration
    static class CustomCpfServiceConfiguration {

        static final RotomCPFService CUSTOM_INSTANCE = new RotomCPFService();

        @Bean
        RotomCPFService cpfService() {
            return CUSTOM_INSTANCE;
        }
    }
}
