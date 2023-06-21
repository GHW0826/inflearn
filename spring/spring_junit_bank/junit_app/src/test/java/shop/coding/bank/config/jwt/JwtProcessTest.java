package shop.coding.bank.config.jwt;

import org.junit.jupiter.api.Test;
import shop.coding.bank.config.auth.LoginUser;
import shop.coding.bank.domain.user.User;
import shop.coding.bank.domain.user.UserEnum;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class JwtProcessTest {

    private String createToken() {
        User user = User.builder().id(1L).role(UserEnum.ADMIN).build();
        LoginUser loginUser = new LoginUser(user);

        String jwtToken = JwtProcess.create(loginUser);
        return jwtToken;
    }

    @Test
    public void create_test() throws  Exception {
        // given

        // when
        String jwtToken = createToken();
        System.out.println("jwtToken = " + jwtToken);

        // then
        assertTrue(jwtToken.startsWith(JwtVO.TOKEN_PREFIX));
    }

    @Test
    public void verify_test() throws  Exception {
        // given
        // 만료시간이 고정이라 언젠가 정지됨
        // String jwtToken = "eyJhbGciOiJIUzUxMiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiJiYW5rIiwiZXhwIjoxNjg3Njc4ODMwLCJpZCI6MSwicm9sZSI6IkFETUlOIn0.TZtzQRUb-gaOwkkk1PewSnSL3vuCIZFMmR3rjR7S63lbbhgpJFnF2ZLbw22VM2FSpzcy1msrXY4vlzOwHvy8XA";
        String token = createToken();
        String jwtToken = token.replace(JwtVO.TOKEN_PREFIX, "");

        // when
        LoginUser verify = JwtProcess.verify(jwtToken);
        System.out.println("verify id = " + verify.getUser().getId());
        System.out.println("verify role = " + verify.getUser().getRole());

        // then
        assertThat(verify.getUser().getId()).isEqualTo(1L);
    }
}