package org.stelios.courses.adapter.springweb.configuration;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.stelios.courses.adapter.springweb.filters.RequestIdFilter;

@Configuration
public class FiltersConfiguration {

    @Bean
    public FilterRegistrationBean<RequestIdFilter> mdcResetFilterRegistrationBean() {
        FilterRegistrationBean<RequestIdFilter> registrationBean = new FilterRegistrationBean<>(new RequestIdFilter());
        registrationBean.setOrder(0);
        return registrationBean;
    }
}
