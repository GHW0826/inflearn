package security.corespringsecurity.security.factory;

import org.springframework.beans.factory.FactoryBean;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.web.util.matcher.RequestMatcher;
import security.corespringsecurity.service.SecurityResourceService;

import java.util.LinkedHashMap;
import java.util.List;

public class MethodResourceMapFactoryBean implements FactoryBean<LinkedHashMap<String, List<ConfigAttribute>>> {

    private SecurityResourceService securityResourceService;
    private LinkedHashMap<String, List<ConfigAttribute>> resourceMap;

    private String resourceType;

    public void setResourceType(String resourceType) {
        this.resourceType = resourceType;
    }

    private void init() {
        if ("method".equals(resourceType)) {
            resourceMap = securityResourceService.getMethodResourceList();
        }else if("pointcut".equals(resourceType)){
            resourceMap = securityResourceService.getPointcutResourceList();
        }
    }

    public void setSecurityResourceService(SecurityResourceService securityResourceService) {
        this.securityResourceService = securityResourceService;
    }

    @Override
    public LinkedHashMap<String, List<ConfigAttribute>> getObject() throws Exception {
        if (resourceMap == null) {
            init();
        }
        return resourceMap;
    }

    @Override
    public Class<?> getObjectType() {
        return LinkedHashMap.class;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }
}
