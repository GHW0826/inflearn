package security.corespringsecurity.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import security.corespringsecurity.domain.entity.RoleHierarchy;
import security.corespringsecurity.repository.RoleHierarchyRepository;
import security.corespringsecurity.service.RoleHierarchyService;

import java.util.Iterator;
import java.util.List;

@Service
public class RoleHierarchyServiceImpl implements RoleHierarchyService {
    @Autowired
    private RoleHierarchyRepository roleHierarchyRepository;

    @Override
    public String findAllHierarchy() {
        List<RoleHierarchy> roleHierarchyList = roleHierarchyRepository.findAll();

        Iterator<RoleHierarchy> itr = roleHierarchyList.iterator();
        StringBuilder concatedRoles = new StringBuilder();
        while (itr.hasNext()) {
            RoleHierarchy roleHierarchy = itr.next();
            if (roleHierarchy.getParentName() != null) {
                concatedRoles.append(roleHierarchy.getParentName().getChildName());
                concatedRoles.append(" > ");
                concatedRoles.append(roleHierarchy.getChildName());
                concatedRoles.append("\n");
            }
        }
        return concatedRoles.toString();
    }
}
