package shop.coding.bank.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import shop.coding.bank.config.dummy.DummyObject;
import shop.coding.bank.domain.user.User;
import shop.coding.bank.domain.user.UserEnum;
import shop.coding.bank.domain.user.UserRepository;
import shop.coding.bank.dto.user.UserReqDto;
import shop.coding.bank.dto.user.UserReqDto.JoinReqDto;
import shop.coding.bank.dto.user.UserRespDto;
import shop.coding.bank.dto.user.UserRespDto.JoinRespDto;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

// spring 관련 bean들이 하나도 없는 환경.
@ExtendWith(MockitoExtension.class)
class UserServiceTest extends DummyObject {

    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @Spy
    private BCryptPasswordEncoder passwordEncoder;

    @Test
    public void 회원가입_test() throws Exception {
        // given
        JoinReqDto joinReqDto = new JoinReqDto();
        joinReqDto.setUsername("ssar");
        joinReqDto.setPassword("1234");
        joinReqDto.setEmail("test@gmail.com");
        joinReqDto.setFullname("rice");

        // stub 1
        when(userRepository.findByUsername(any())).thenReturn(Optional.empty());

        // stub 2
        User ssar = newMockUser(1L, "ssar", "rice");
        when(userRepository.save(any())).thenReturn(ssar);

        // when
        JoinRespDto joinRespDto = userService.회원가입(joinReqDto);
        System.out.println("test joinRespDto = " + joinRespDto);

        // then
        assertThat(joinRespDto.getId()).isEqualTo(1L);
        assertThat(joinRespDto.getUsername()).isEqualTo("ssar");
    }
}