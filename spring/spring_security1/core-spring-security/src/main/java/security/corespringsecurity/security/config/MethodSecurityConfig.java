package security.corespringsecurity.security.config;

import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.access.method.MapBasedMethodSecurityMetadataSource;
import org.springframework.security.access.method.MethodSecurityMetadataSource;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.GlobalMethodSecurityConfiguration;
import security.corespringsecurity.security.factory.MethodResourceMapFactoryBean;
import security.corespringsecurity.security.processor.ProtectPointcutPostProcessor;
import security.corespringsecurity.service.SecurityResourceService;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.Map;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
class MethodSecurityConfig extends GlobalMethodSecurityConfiguration {

    @Autowired
    private SecurityResourceService securityResourceService;

    @Override
    protected MethodSecurityMetadataSource customMethodSecurityMetadataSource() {
        return new MapBasedMethodSecurityMetadataSource();
    }

    @Bean
    public MapBasedMethodSecurityMetadataSource mapBasedMethodSecurityMetadataSource() throws Exception {
        return new MapBasedMethodSecurityMetadataSource(methodResourcesMapFactoryBean().getObject());
    }

    @Bean
    public MethodResourceMapFactoryBean methodResourcesMapFactoryBean() {

        MethodResourceMapFactoryBean methodResourceMapFactoryBean = new MethodResourceMapFactoryBean();
        methodResourceMapFactoryBean.setSecurityResourceService(securityResourceService);
        methodResourceMapFactoryBean.setResourceType("method");
        return methodResourceMapFactoryBean;
    }

    @Bean
    public MethodResourceMapFactoryBean pointcutResourcesMapFactoryBean() {
        MethodResourceMapFactoryBean methodResourceMapFactoryBean = new MethodResourceMapFactoryBean();
        methodResourceMapFactoryBean.setSecurityResourceService(securityResourceService);
        methodResourceMapFactoryBean.setResourceType("pointcut");
        return methodResourceMapFactoryBean;
    }

    @Bean
    public ProtectPointcutPostProcessor protectPointcutPostProcessor() throws Exception {
        ProtectPointcutPostProcessor protectPointcutPostProcessor = new ProtectPointcutPostProcessor(mapBasedMethodSecurityMetadataSource());
        protectPointcutPostProcessor.setPointcutMap(pointcutResourcesMapFactoryBean().getObject());
        return protectPointcutPostProcessor;
    }

//    @Bean
//    @Profile("pointcut")
//    BeanPostProcessor protectPointcutPostProcessor() throws Exception {
//
//        Class<?> clazz = Class.forName("org.springframework.security.config.method.ProtectPointcutPostProcessor");
//        Constructor<?> declaredConstructor = clazz.getDeclaredConstructor(MapBasedMethodSecurityMetadataSource.class);
//        declaredConstructor.setAccessible(true);
//        Object instance = declaredConstructor.newInstance(mapBasedMethodSecurityMetadataSource());
//        Method setPointcutMap = instance.getClass().getMethod("setPointcutMap", Map.class);
//        setPointcutMap.setAccessible(true);
//        setPointcutMap.invoke(instance, pointcutResourcesMapFactoryBean().getObject());
//
//        return (BeanPostProcessor)instance;
//    }

}