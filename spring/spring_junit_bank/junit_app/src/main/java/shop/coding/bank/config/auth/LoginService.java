package shop.coding.bank.config.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import shop.coding.bank.domain.user.User;
import shop.coding.bank.domain.user.UserRepository;

@Service
public class LoginService implements UserDetailsService {

    private UserRepository userRepository;

    @Autowired
    public LoginService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // 시큐리티 로그인시, 시큐리티가 loadUserByUsername를 실행해 username을 체크.
    // 없으면 오류,
    // 있으면 정상 시큐리티 컨텍스트 내부 로그인된 세션이 만들어짐.
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User userPS = userRepository.findByUsername(username).orElseThrow(
                ()-> new InternalAuthenticationServiceException("인증 실패")
        );
        return new LoginUser(userPS);
    }
}
