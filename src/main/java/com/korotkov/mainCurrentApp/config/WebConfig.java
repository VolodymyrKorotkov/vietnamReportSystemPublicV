package com.korotkov.mainCurrentApp.config;

import com.korotkov.mainCurrentApp.interceptor.PortalInterceptor;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustAllStrategy;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.exception.VelocityException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.task.TaskExecutor;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.concurrent.ConcurrentTaskScheduler;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.ui.velocity.VelocityEngineFactory;
import org.springframework.ui.velocity.VelocityEngineFactoryBean;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.*;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.XmlViewResolver;

import javax.net.ssl.SSLContext;
import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;
import java.util.Locale;
import java.util.Properties;

import static com.korotkov.mainCurrentApp.service.email.EmailConfig.*;

@Configuration
@EnableWebMvc
@EnableScheduling
@EnableAsync
@ComponentScan(basePackages = "com.korotkov")
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/res/**").addResourceLocations("/res/");
    }

    @Bean
    ViewResolver viewResolver(){
        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
        viewResolver.setPrefix("/WEB-INF/pages/");
        viewResolver.setSuffix(".jsp");
        return viewResolver;
    }

    @Autowired
    PortalInterceptor portalInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry){
        registry.addInterceptor(getLocalChangeInterceptor());
    }

    @Bean(name = "localeChangeInterceptor")
    public LocaleChangeInterceptor getLocalChangeInterceptor() {
        LocaleChangeInterceptor localeChangeInterceptor = new LocaleChangeInterceptor();
        localeChangeInterceptor.setParamName("languageVar");
        return localeChangeInterceptor;
    }

    @Bean(name = "localeResolver")
    public CookieLocaleResolver getLocaleResolver() {
        CookieLocaleResolver cookieLocaleResolver = new CookieLocaleResolver();
        cookieLocaleResolver.setDefaultLocale(new Locale("en"));
        cookieLocaleResolver.setCookieMaxAge(100000);
        return cookieLocaleResolver;
    }

    @Bean(name = "messageSource")
    public ReloadableResourceBundleMessageSource getMessageSource() {
        ReloadableResourceBundleMessageSource resource = new ReloadableResourceBundleMessageSource();
        resource.setBasename("classpath:/locales/messages");
        resource.setCacheSeconds(1);
        resource.setDefaultEncoding("UTF-8");
        return resource;
    }

    @Override
    public void addViewControllers(ViewControllerRegistry registry){
        registry.addViewController("/security/login").setViewName("/security/login");
    }

    /*
     *  Java Mail Configuration
     */

    //String mailUsername = "volodymyr.korotkov@tienoi.com.vn"; //"info@scoring-machine.com";
    //String mailPassword = "q1o72upvsQ";
    String mailHost = "smtp.office365.com"; //"smtp.gmail.com";

    @Bean(name = "mailSender")
    public JavaMailSenderImpl getJavaMailSender(){
        JavaMailSenderImpl javaMailSender = new JavaMailSenderImpl();
        javaMailSender.setUsername(EMAIL_USERNAME);
        javaMailSender.setPassword(EMAIL_PASSWORD);
        javaMailSender.setPort(587);

        Properties javaMailProperties = new Properties();
        javaMailProperties.put("mail.smtp.auth", true);
        javaMailProperties.put("mail.smtp.starttls.enable", true);
        javaMailProperties.put("mail.smtp.starttls.required",true);
        javaMailProperties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        javaMailProperties.put("mail.smtp.host",mailHost);
        javaMailProperties.put("mail.smtp.ssl.protocols", "TLSv1.2");
//        javaMailProperties.put("mail.smtp.port", "587");

        javaMailSender.setJavaMailProperties(javaMailProperties);
        return javaMailSender;
    }

    @Bean(name = "mailSender01")
    public JavaMailSenderImpl getJavaMailSender01(){
        JavaMailSenderImpl javaMailSender = new JavaMailSenderImpl();
        javaMailSender.setUsername(EMAIL_USERNAME_01);
        javaMailSender.setPassword(EMAIL_PASSWORD);
        javaMailSender.setPort(587);

        Properties javaMailProperties = new Properties();
        javaMailProperties.put("mail.smtp.auth", true);
        javaMailProperties.put("mail.smtp.starttls.enable", true);
        javaMailProperties.put("mail.smtp.starttls.required",true);
        javaMailProperties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        javaMailProperties.put("mail.smtp.host",mailHost);
        javaMailProperties.put("mail.smtp.ssl.protocols", "TLSv1.2");
//        javaMailProperties.put("mail.smtp.port", "587");

        javaMailSender.setJavaMailProperties(javaMailProperties);
        return javaMailSender;
    }

    @Bean(name = "mailSender02")
    public JavaMailSenderImpl getJavaMailSender02(){
        JavaMailSenderImpl javaMailSender = new JavaMailSenderImpl();
        javaMailSender.setUsername(EMAIL_USERNAME_02);
        javaMailSender.setPassword(EMAIL_PASSWORD);
        javaMailSender.setPort(587);

        Properties javaMailProperties = new Properties();
        javaMailProperties.put("mail.smtp.auth", true);
        javaMailProperties.put("mail.smtp.starttls.enable", true);
        javaMailProperties.put("mail.smtp.starttls.required",true);
        javaMailProperties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        javaMailProperties.put("mail.smtp.host",mailHost);
        javaMailProperties.put("mail.smtp.ssl.protocols", "TLSv1.2");
//        javaMailProperties.put("mail.smtp.port", "587");

        javaMailSender.setJavaMailProperties(javaMailProperties);
        return javaMailSender;
    }

    @Bean(name = "mailSender03")
    public JavaMailSenderImpl getJavaMailSender03(){
        JavaMailSenderImpl javaMailSender = new JavaMailSenderImpl();
        javaMailSender.setUsername(EMAIL_USERNAME_03);
        javaMailSender.setPassword(EMAIL_PASSWORD);
        javaMailSender.setPort(587);

        Properties javaMailProperties = new Properties();
        javaMailProperties.put("mail.smtp.auth", true);
        javaMailProperties.put("mail.smtp.starttls.enable", true);
        javaMailProperties.put("mail.smtp.starttls.required",true);
        javaMailProperties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        javaMailProperties.put("mail.smtp.host",mailHost);
        javaMailProperties.put("mail.smtp.ssl.protocols", "TLSv1.2");
//        javaMailProperties.put("mail.smtp.port", "587");

        javaMailSender.setJavaMailProperties(javaMailProperties);
        return javaMailSender;
    }

    @Bean(name = "mailSender04")
    public JavaMailSenderImpl getJavaMailSender04(){
        JavaMailSenderImpl javaMailSender = new JavaMailSenderImpl();
        javaMailSender.setUsername(EMAIL_USERNAME_04);
        javaMailSender.setPassword(EMAIL_PASSWORD);
        javaMailSender.setPort(587);

        Properties javaMailProperties = new Properties();
        javaMailProperties.put("mail.smtp.auth", true);
        javaMailProperties.put("mail.smtp.starttls.enable", true);
        javaMailProperties.put("mail.smtp.starttls.required",true);
        javaMailProperties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        javaMailProperties.put("mail.smtp.host",mailHost);
        javaMailProperties.put("mail.smtp.ssl.protocols", "TLSv1.2");
//        javaMailProperties.put("mail.smtp.port", "587");

        javaMailSender.setJavaMailProperties(javaMailProperties);
        return javaMailSender;
    }

    @Bean(name = "mailSender05")
    public JavaMailSenderImpl getJavaMailSender05(){
        JavaMailSenderImpl javaMailSender = new JavaMailSenderImpl();
        javaMailSender.setUsername(EMAIL_USERNAME_05);
        javaMailSender.setPassword(EMAIL_PASSWORD);
        javaMailSender.setPort(587);

        Properties javaMailProperties = new Properties();
        javaMailProperties.put("mail.smtp.auth", true);
        javaMailProperties.put("mail.smtp.starttls.enable", true);
        javaMailProperties.put("mail.smtp.starttls.required",true);
        javaMailProperties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        javaMailProperties.put("mail.smtp.host",mailHost);
        javaMailProperties.put("mail.smtp.ssl.protocols", "TLSv1.2");
//        javaMailProperties.put("mail.smtp.port", "587");

        javaMailSender.setJavaMailProperties(javaMailProperties);
        return javaMailSender;
    }

    @Bean(name = "mailSender06")
    public JavaMailSenderImpl getJavaMailSender06(){
        JavaMailSenderImpl javaMailSender = new JavaMailSenderImpl();
        javaMailSender.setUsername(EMAIL_USERNAME_06);
        javaMailSender.setPassword(EMAIL_PASSWORD);
        javaMailSender.setPort(587);

        Properties javaMailProperties = new Properties();
        javaMailProperties.put("mail.smtp.auth", true);
        javaMailProperties.put("mail.smtp.starttls.enable", true);
        javaMailProperties.put("mail.smtp.starttls.required",true);
        javaMailProperties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        javaMailProperties.put("mail.smtp.host",mailHost);
        javaMailProperties.put("mail.smtp.ssl.protocols", "TLSv1.2");
//        javaMailProperties.put("mail.smtp.port", "587");

        javaMailSender.setJavaMailProperties(javaMailProperties);
        return javaMailSender;
    }

    @Bean(name = "mailSender07")
    public JavaMailSenderImpl getJavaMailSender07(){
        JavaMailSenderImpl javaMailSender = new JavaMailSenderImpl();
        javaMailSender.setUsername(EMAIL_USERNAME_07);
        javaMailSender.setPassword(EMAIL_PASSWORD);
        javaMailSender.setPort(587);

        Properties javaMailProperties = new Properties();
        javaMailProperties.put("mail.smtp.auth", true);
        javaMailProperties.put("mail.smtp.starttls.enable", true);
        javaMailProperties.put("mail.smtp.starttls.required",true);
        javaMailProperties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        javaMailProperties.put("mail.smtp.host",mailHost);
        javaMailProperties.put("mail.smtp.ssl.protocols", "TLSv1.2");
//        javaMailProperties.put("mail.smtp.port", "587");

        javaMailSender.setJavaMailProperties(javaMailProperties);
        return javaMailSender;
    }

    @Bean(name = "mailSender08")
    public JavaMailSenderImpl getJavaMailSender08(){
        JavaMailSenderImpl javaMailSender = new JavaMailSenderImpl();
        javaMailSender.setUsername(EMAIL_USERNAME_08);
        javaMailSender.setPassword(EMAIL_PASSWORD);
        javaMailSender.setPort(587);

        Properties javaMailProperties = new Properties();
        javaMailProperties.put("mail.smtp.auth", true);
        javaMailProperties.put("mail.smtp.starttls.enable", true);
        javaMailProperties.put("mail.smtp.starttls.required",true);
        javaMailProperties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        javaMailProperties.put("mail.smtp.host",mailHost);
        javaMailProperties.put("mail.smtp.ssl.protocols", "TLSv1.2");
//        javaMailProperties.put("mail.smtp.port", "587");

        javaMailSender.setJavaMailProperties(javaMailProperties);
        return javaMailSender;
    }

    @Bean(name = "mailSender09")
    public JavaMailSenderImpl getJavaMailSender09(){
        JavaMailSenderImpl javaMailSender = new JavaMailSenderImpl();
        javaMailSender.setUsername(EMAIL_USERNAME_09);
        javaMailSender.setPassword(EMAIL_PASSWORD);
        javaMailSender.setPort(587);

        Properties javaMailProperties = new Properties();
        javaMailProperties.put("mail.smtp.auth", true);
        javaMailProperties.put("mail.smtp.starttls.enable", true);
        javaMailProperties.put("mail.smtp.starttls.required",true);
        javaMailProperties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        javaMailProperties.put("mail.smtp.host",mailHost);
        javaMailProperties.put("mail.smtp.ssl.protocols", "TLSv1.2");
//        javaMailProperties.put("mail.smtp.port", "587");

        javaMailSender.setJavaMailProperties(javaMailProperties);
        return javaMailSender;
    }

    @Bean(name = "mailSender10")
    public JavaMailSenderImpl getJavaMailSender10(){
        JavaMailSenderImpl javaMailSender = new JavaMailSenderImpl();
        javaMailSender.setUsername(EMAIL_USERNAME_10);
        javaMailSender.setPassword(EMAIL_PASSWORD);
        javaMailSender.setPort(587);

        Properties javaMailProperties = new Properties();
        javaMailProperties.put("mail.smtp.auth", true);
        javaMailProperties.put("mail.smtp.starttls.enable", true);
        javaMailProperties.put("mail.smtp.starttls.required",true);
        javaMailProperties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        javaMailProperties.put("mail.smtp.host",mailHost);
        javaMailProperties.put("mail.smtp.ssl.protocols", "TLSv1.2");
//        javaMailProperties.put("mail.smtp.port", "587");

        javaMailSender.setJavaMailProperties(javaMailProperties);
        return javaMailSender;
    }

    @Bean(name = "mailSender11")
    public JavaMailSenderImpl getJavaMailSender11(){
        JavaMailSenderImpl javaMailSender = new JavaMailSenderImpl();
        javaMailSender.setUsername(EMAIL_USERNAME_11);
        javaMailSender.setPassword(EMAIL_PASSWORD);
        javaMailSender.setPort(587);

        Properties javaMailProperties = new Properties();
        javaMailProperties.put("mail.smtp.auth", true);
        javaMailProperties.put("mail.smtp.starttls.enable", true);
        javaMailProperties.put("mail.smtp.starttls.required",true);
        javaMailProperties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        javaMailProperties.put("mail.smtp.host",mailHost);
        javaMailProperties.put("mail.smtp.ssl.protocols", "TLSv1.2");
//        javaMailProperties.put("mail.smtp.port", "587");

        javaMailSender.setJavaMailProperties(javaMailProperties);
        return javaMailSender;
    }

    @Bean(name = "mailSender12")
    public JavaMailSenderImpl getJavaMailSender12(){
        JavaMailSenderImpl javaMailSender = new JavaMailSenderImpl();
        javaMailSender.setUsername(EMAIL_USERNAME_12);
        javaMailSender.setPassword(EMAIL_PASSWORD);
        javaMailSender.setPort(587);

        Properties javaMailProperties = new Properties();
        javaMailProperties.put("mail.smtp.auth", true);
        javaMailProperties.put("mail.smtp.starttls.enable", true);
        javaMailProperties.put("mail.smtp.starttls.required",true);
        javaMailProperties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        javaMailProperties.put("mail.smtp.host",mailHost);
        javaMailProperties.put("mail.smtp.ssl.protocols", "TLSv1.2");
//        javaMailProperties.put("mail.smtp.port", "587");

        javaMailSender.setJavaMailProperties(javaMailProperties);
        return javaMailSender;
    }

    @Bean(name = "mailSender13")
    public JavaMailSenderImpl getJavaMailSender13(){
        JavaMailSenderImpl javaMailSender = new JavaMailSenderImpl();
        javaMailSender.setUsername(EMAIL_USERNAME_13);
        javaMailSender.setPassword(EMAIL_PASSWORD);
        javaMailSender.setPort(587);

        Properties javaMailProperties = new Properties();
        javaMailProperties.put("mail.smtp.auth", true);
        javaMailProperties.put("mail.smtp.starttls.enable", true);
        javaMailProperties.put("mail.smtp.starttls.required",true);
        javaMailProperties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        javaMailProperties.put("mail.smtp.host",mailHost);
        javaMailProperties.put("mail.smtp.ssl.protocols", "TLSv1.2");
//        javaMailProperties.put("mail.smtp.port", "587");

        javaMailSender.setJavaMailProperties(javaMailProperties);
        return javaMailSender;
    }

    @Bean(name = "mailSender14")
    public JavaMailSenderImpl getJavaMailSender14(){
        JavaMailSenderImpl javaMailSender = new JavaMailSenderImpl();
        javaMailSender.setUsername(EMAIL_USERNAME_14);
        javaMailSender.setPassword(EMAIL_PASSWORD);
        javaMailSender.setPort(587);

        Properties javaMailProperties = new Properties();
        javaMailProperties.put("mail.smtp.auth", true);
        javaMailProperties.put("mail.smtp.starttls.enable", true);
        javaMailProperties.put("mail.smtp.starttls.required",true);
        javaMailProperties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        javaMailProperties.put("mail.smtp.host",mailHost);
        javaMailProperties.put("mail.smtp.ssl.protocols", "TLSv1.2");
//        javaMailProperties.put("mail.smtp.port", "587");

        javaMailSender.setJavaMailProperties(javaMailProperties);
        return javaMailSender;
    }

    @Bean(name = "mailSender15")
    public JavaMailSenderImpl getJavaMailSender15(){
        JavaMailSenderImpl javaMailSender = new JavaMailSenderImpl();
        javaMailSender.setUsername(EMAIL_USERNAME_15);
        javaMailSender.setPassword(EMAIL_PASSWORD);
        javaMailSender.setPort(587);

        Properties javaMailProperties = new Properties();
        javaMailProperties.put("mail.smtp.auth", true);
        javaMailProperties.put("mail.smtp.starttls.enable", true);
        javaMailProperties.put("mail.smtp.starttls.required",true);
        javaMailProperties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        javaMailProperties.put("mail.smtp.host",mailHost);
        javaMailProperties.put("mail.smtp.ssl.protocols", "TLSv1.2");
//        javaMailProperties.put("mail.smtp.port", "587");

        javaMailSender.setJavaMailProperties(javaMailProperties);
        return javaMailSender;
    }

    @Bean(name = "mailSender16")
    public JavaMailSenderImpl getJavaMailSender16(){
        JavaMailSenderImpl javaMailSender = new JavaMailSenderImpl();
        javaMailSender.setUsername(EMAIL_USERNAME_16);
        javaMailSender.setPassword(EMAIL_PASSWORD);
        javaMailSender.setPort(587);

        Properties javaMailProperties = new Properties();
        javaMailProperties.put("mail.smtp.auth", true);
        javaMailProperties.put("mail.smtp.starttls.enable", true);
        javaMailProperties.put("mail.smtp.starttls.required",true);
        javaMailProperties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        javaMailProperties.put("mail.smtp.host",mailHost);
        javaMailProperties.put("mail.smtp.ssl.protocols", "TLSv1.2");
//        javaMailProperties.put("mail.smtp.port", "587");

        javaMailSender.setJavaMailProperties(javaMailProperties);
        return javaMailSender;
    }

    @Bean(name = "mailSender17")
    public JavaMailSenderImpl getJavaMailSender17(){
        JavaMailSenderImpl javaMailSender = new JavaMailSenderImpl();
        javaMailSender.setUsername(EMAIL_USERNAME_17);
        javaMailSender.setPassword(EMAIL_PASSWORD);
        javaMailSender.setPort(587);

        Properties javaMailProperties = new Properties();
        javaMailProperties.put("mail.smtp.auth", true);
        javaMailProperties.put("mail.smtp.starttls.enable", true);
        javaMailProperties.put("mail.smtp.starttls.required",true);
        javaMailProperties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        javaMailProperties.put("mail.smtp.host",mailHost);
        javaMailProperties.put("mail.smtp.ssl.protocols", "TLSv1.2");
//        javaMailProperties.put("mail.smtp.port", "587");

        javaMailSender.setJavaMailProperties(javaMailProperties);
        return javaMailSender;
    }

    @Bean(name = "mailSender18")
    public JavaMailSenderImpl getJavaMailSender18(){
        JavaMailSenderImpl javaMailSender = new JavaMailSenderImpl();
        javaMailSender.setUsername(EMAIL_USERNAME_18);
        javaMailSender.setPassword(EMAIL_PASSWORD);
        javaMailSender.setPort(587);

        Properties javaMailProperties = new Properties();
        javaMailProperties.put("mail.smtp.auth", true);
        javaMailProperties.put("mail.smtp.starttls.enable", true);
        javaMailProperties.put("mail.smtp.starttls.required",true);
        javaMailProperties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        javaMailProperties.put("mail.smtp.host",mailHost);
        javaMailProperties.put("mail.smtp.ssl.protocols", "TLSv1.2");
//        javaMailProperties.put("mail.smtp.port", "587");

        javaMailSender.setJavaMailProperties(javaMailProperties);
        return javaMailSender;
    }

    @Bean(name = "mailSender19")
    public JavaMailSenderImpl getJavaMailSender19(){
        JavaMailSenderImpl javaMailSender = new JavaMailSenderImpl();
        javaMailSender.setUsername(EMAIL_USERNAME_19);
        javaMailSender.setPassword(EMAIL_PASSWORD);
        javaMailSender.setPort(587);

        Properties javaMailProperties = new Properties();
        javaMailProperties.put("mail.smtp.auth", true);
        javaMailProperties.put("mail.smtp.starttls.enable", true);
        javaMailProperties.put("mail.smtp.starttls.required",true);
        javaMailProperties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        javaMailProperties.put("mail.smtp.host",mailHost);
        javaMailProperties.put("mail.smtp.ssl.protocols", "TLSv1.2");
//        javaMailProperties.put("mail.smtp.port", "587");

        javaMailSender.setJavaMailProperties(javaMailProperties);
        return javaMailSender;
    }

    @Bean(name = "velocityEngine")
    public VelocityEngineFactoryBean getVelocityEngineFactoryBean(){
        VelocityEngineFactoryBean bean = new VelocityEngineFactoryBean();
        bean.setResourceLoaderPath("/WEB-INF/email-templates/");
        return bean;
    }

//    @Bean
//    public VelocityEngine getVelocityEngine() throws VelocityException, IOException {
//        VelocityEngineFactory factory = new VelocityEngineFactory();
//        VelocityEngine velocityEngine = new VelocityEngine();
//        Properties props = new Properties();
//        props.setProperty("resource.loader", "class");
//        props.setProperty("class.resource.loader.class", "org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader");
//        velocityEngine.init(props);
//        return velocityEngine;
//    }

    @Bean(name = "multipartResolver")
    public CommonsMultipartResolver getMultipartResolver(){
        CommonsMultipartResolver cmr = new CommonsMultipartResolver();
        cmr.setMaxUploadSize(100000000);
        return cmr;
    }

    @SuppressWarnings("deprecation")
    @Bean(name = "xmlViewResolver")
    public XmlViewResolver getXmlViewResolver() {
        XmlViewResolver xmlViewResolver = new XmlViewResolver();
        Resource resource = new ClassPathResource("excel-pdf-config.xml");
        xmlViewResolver.setOrder(0);
        xmlViewResolver.setLocation(resource);
        return xmlViewResolver;
    }

    @Primary
    @Bean("mainRestTemplate")
    public RestTemplate restTemplate() throws NoSuchAlgorithmException, KeyStoreException, KeyManagementException {
        TrustStrategy acceptingTrustStrategy = (X509Certificate[] chain, String authType) -> true;
        SSLContext sslContext = org.apache.http.ssl.SSLContexts.custom()
                .loadTrustMaterial(null, acceptingTrustStrategy)
                .build();

        SSLConnectionSocketFactory csf = new SSLConnectionSocketFactory(sslContext);

        CloseableHttpClient httpClient = HttpClients.custom()
                .setSSLSocketFactory(csf)
                .build();

        HttpComponentsClientHttpRequestFactory requestFactory =
                new HttpComponentsClientHttpRequestFactory();

        requestFactory.setHttpClient(httpClient);
        requestFactory.setHttpClient(httpClient);
        requestFactory.setConnectionRequestTimeout(60000);
        requestFactory.setConnectTimeout(60000);
        requestFactory.setReadTimeout(60000);
        return new RestTemplate(requestFactory);
    }

    @Bean("restTemplateWithoutSsl")
    public RestTemplate restTemplateWithoutSsl() throws NoSuchAlgorithmException, KeyStoreException, KeyManagementException {

        TrustStrategy acceptingTrustStrategy = (x509Certificates, s) -> true;
        SSLContext sslContext = org.apache.http.ssl.SSLContexts.custom().loadTrustMaterial(null, acceptingTrustStrategy).build();
        SSLConnectionSocketFactory csf = new SSLConnectionSocketFactory(sslContext, new NoopHostnameVerifier());
        CloseableHttpClient httpClient = HttpClients.custom().setSSLSocketFactory(csf).build();
        HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory();
        requestFactory.setHttpClient(httpClient);
        requestFactory.setHttpClient(httpClient);
        requestFactory.setConnectionRequestTimeout(60000);
        requestFactory.setConnectTimeout(60000);
        requestFactory.setReadTimeout(60000);
        RestTemplate restTemplate = new RestTemplate(requestFactory);
        return restTemplate;
    }

    @Bean("reportExecutor")
    public TaskExecutor reportExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(10);
        executor.setMaxPoolSize(20);
        executor.setQueueCapacity(100);
        executor.setKeepAliveSeconds(1000);
        executor.setThreadNamePrefix("report-executor-");
        executor.initialize();
        return executor;
    }

    @Bean("taskScheduler")
    public TaskExecutor taskSchedulerExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(5);
        executor.setMaxPoolSize(10);
        executor.setQueueCapacity(100);
        executor.setKeepAliveSeconds(1000);
        executor.setThreadNamePrefix("task-scheduler-executor-");
        executor.initialize();
        return executor;
    }

    @Bean("emailScheduler")
    public TaskExecutor emailSchedulerExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(20);
        executor.setMaxPoolSize(25);
        executor.setQueueCapacity(100);
        executor.setKeepAliveSeconds(1000);
        executor.setThreadNamePrefix("email-scheduler-executor-");
        executor.initialize();
        return executor;
    }


    @Bean("emailManual")
    public TaskExecutor emailManualExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(2);
        executor.setMaxPoolSize(5);
        executor.setQueueCapacity(100);
        executor.setKeepAliveSeconds(1000);
        executor.setThreadNamePrefix("email-scheduler-executor-");
        executor.initialize();
        return executor;
    }

    @Bean("addingClientPhones")
    public TaskExecutor addingClientPhonesExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(1);
        executor.setMaxPoolSize(1);
        executor.setQueueCapacity(10);
        executor.setKeepAliveSeconds(1000);
        executor.setThreadNamePrefix("addingClientPhones-executor-");
        executor.initialize();
        return executor;
    }

    @Bean("batchExecutorForVicidial")
    public TaskExecutor batchExecutorForVicidial() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(50);
        executor.setMaxPoolSize(100);
        executor.setQueueCapacity(500);
        executor.setKeepAliveSeconds(1000);
        executor.setThreadNamePrefix("addingClientPhones-executor-");
        executor.initialize();
        return executor;
    }


    @Bean("scoringExecutor")
    public TaskExecutor scoringExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(5);
        executor.setMaxPoolSize(10);
        executor.setQueueCapacity(100);
        executor.setKeepAliveSeconds(1000);
        executor.setThreadNamePrefix("scoring-executor-");
        executor.initialize();
        return executor;
    }

}
