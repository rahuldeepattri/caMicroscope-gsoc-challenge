package com.rahuldeepattri.poc.configurations;

import com.twelvemonkeys.servlet.image.IIOProviderContextListener;
import org.springframework.boot.web.servlet.ServletListenerRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ImageIOListenerRegistration {
    @Bean
    public ServletListenerRegistrationBean<IIOProviderContextListener> registerr() {
        ServletListenerRegistrationBean<IIOProviderContextListener> listenerRegBean = new ServletListenerRegistrationBean<>();
        listenerRegBean.setListener(new IIOProviderContextListener());
        return listenerRegBean;
    }

}