package security.corespringsecurity.security.config;

import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.method.MapBasedMethodSecurityMetadataSource;
import org.springframework.security.access.method.MethodSecurityMetadataSource;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.GlobalMethodSecurityConfiguration;
import security.corespringsecurity.security.factory.MethodResourceMapFactoryBean;
import security.corespringsecurity.service.SecurityResourceService;

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
        return methodResourceMapFactoryBean;
    }
}