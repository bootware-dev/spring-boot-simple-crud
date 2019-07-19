package sbscrud.adaptor;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
public class ConfigurerAdaptor extends WebMvcConfigurerAdapter {

    @Bean(name="validator")
    public LocalValidatorFactoryBean localValidatorFactoryBean() {
        var localValidatorFactoryBean = new LocalValidatorFactoryBean();
        localValidatorFactoryBean.setValidationMessageSource(messageSource());
        return localValidatorFactoryBean;
    }

    @Bean(name = "messageSource")
    public MessageSource messageSource() {
        var bean = new ReloadableResourceBundleMessageSource();
        bean.setBasename("classpath:validation-messages");
        bean.setDefaultEncoding("UTF-8");
        return bean;
    }

}
