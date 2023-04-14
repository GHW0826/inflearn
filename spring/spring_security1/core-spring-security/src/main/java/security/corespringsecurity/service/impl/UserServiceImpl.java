package security.corespringsecurity.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import security.corespringsecurity.domain.Account;
import security.corespringsecurity.repository.UserRepository;
import security.corespringsecurity.service.UserService;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;
    @Override
    @Transactional
    public void createUser(Account account) {
        userRepository.save(account);
    }
}
