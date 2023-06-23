package shop.coding.bank.temp;

import org.junit.jupiter.api.Test;

public class LongTest {
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
