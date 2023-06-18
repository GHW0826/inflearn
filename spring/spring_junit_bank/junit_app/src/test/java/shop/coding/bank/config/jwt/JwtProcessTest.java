package shop.coding.bank.config.jwt;

import org.junit.jupiter.api.Test;
import shop.coding.bank.config.auth.LoginUser;
import shop.coding.bank.domain.user.User;
import shop.coding.bank.domain.user.UserEnum;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class JwtProcessTest {
    @Test
    public void create_test() throws  Exception {
        // given
        User user = User.builder().id(1L).role(UserEnum.ADMIN).build();
        LoginUser loginUser = new LoginUser(user);

        // when
        String jwtToken = JwtProcess.create(loginUser);
        System.out.println("jwtToken = " + jwtToken);

        // then
        assertTrue(jwtToken.startsWith(JwtVO.TOKEN_PREFIX));
    }

    @Test
    public void verify_test() throws  Exception {
        // given
        String jwtToken = "eyJhbGciOiJIUzUxMiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiJiYW5rIiwiZXhwIjoxNjg3Njc4ODMwLCJpZCI6MSwicm9sZSI6IkFETUlOIn0.TZtzQRUb-gaOwkkk1PewSnSL3vuCIZFMmR3rjR7S63lbbhgpJFnF2ZLbw22VM2FSpzcy1msrXY4vlzOwHvy8XA";

        // when
        LoginUser verify = JwtProcess.verify(jwtToken);
        System.out.println("verify id = " + verify.getUser().getId());
        System.out.println("verify role = " + verify.getUser().getRole());

        // then
        assertThat(verify.getUser().getId()).isEqualTo(1L);
    }
}