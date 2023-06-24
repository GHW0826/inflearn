package shop.coding.bank.temp;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class LongTest {

    @Test
    public void long_test22() throws Exception {
        // given
        Long v1 = 100L;
        Long v2 = 200L;

        // when
        if (v1 < v2) {
            System.out.println(" v1이 작다");
        }
        else if (v1 > v2) {
            System.out.println(" v2가 작다 ");
        }

        // Long 타입의 특징 (2^8 까지 256 범위) (127까지)
        // 값이 작으면 ==으로 비교가 된다.
        // 값이 커지면 비교가 안된다
        if (v1 == v2) {
            System.out.println(" 값이 같다. ");
        }


        Long v3 = 1000L;
        Long v4 = 1000L;
        if (v3 == v4) {
            System.out.println(" 값이 같다. ");
        }

        assertThat(v3).isEqualTo(v4);
    }

    @Test
    public void long_test() throws  Exception {
        // given
        Long number1 = 1111L;
        Long number2 = 1111L;

        // when
        if (number1 == number2) {
            System.out.println(" same" );
        }
        else {
            System.out.println(" diff ");
        }

        if (number1.longValue() == number2.longValue()) {
            System.out.println(" same" );
        }
        else {
            System.out.println(" diff ");
        }

        Long amount1 = 100L;
        Long amount2 = 1000L;

        // when (대소는 상관 없음)
        if (amount1 < amount2) {
            System.out.println(" amount2" );
        }
        else {
            System.out.println(" amount1 ");
        }

        if (amount1.longValue() < amount2.longValue()) {
            System.out.println(" amount2 " );
        }
        else {
            System.out.println(" amount1 ");
        }

        // then
    }
}
