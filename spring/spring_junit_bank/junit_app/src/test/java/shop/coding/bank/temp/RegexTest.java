package shop.coding.bank.temp;

import org.junit.jupiter.api.Test;

import java.util.regex.Pattern;

// java.util.regex.Pattern
public class RegexTest {

    @Test
    public void 한글_test() throws Exception {
        String value = "가나";
        boolean result = Pattern.matches("^[가-힣]+$", value);
        System.out.println("result = " + result);

        String value2 = "";
        boolean result2 = Pattern.matches("^[가-힣]+$", value2);
        System.out.println("result = " + result2);

        String value3 = "ㄹㄴㅇㄹㄴㅇㄹsdfsdf";
        boolean result3 = Pattern.matches("^[ㄱ-힣]+$", value3);
        System.out.println("result = " + result3);
    }

    @Test
    public void NO_한글_test() throws Exception {
        String value = "가나";
        boolean result = Pattern.matches("^[^ㄱ-힣]+$", value);
        System.out.println("result = " + result);

        String value2 = "adf@#";
        boolean result2 = Pattern.matches("^[^ㄱ-힣]+$", value2);
        System.out.println("result = " + result2);

        String value3 = "ㅎㅎㅎaerter ";
        boolean result3 = Pattern.matches("^[^ㄱ-힣]+$", value3);
        System.out.println("result = " + result3);
    }

    @Test
    public void 영어_test() throws Exception {
        String value = "ss";
        boolean result = Pattern.matches("^[a-zA-Z]+$", value);
        System.out.println("result = " + result);

        String value2 = "";
        boolean result2 = Pattern.matches("^[a-zA-Z]+$", value2);
        System.out.println("result = " + result2);

        String value3 = "ㄹㄴㅇㄹㄴㅇㄹsdfsdf";
        boolean result3 = Pattern.matches("^[a-zA-Z]+$", value3);
        System.out.println("result = " + result3);
    }

    @Test
    public void NO_영어_test() throws Exception {
        String value = "ss";
        boolean result = Pattern.matches("^[^a-zA-Z]+$", value);
        System.out.println("result = " + result);

        String value2 = "";
        boolean result2 = Pattern.matches("^[^a-zA-Z]+$", value2);
        System.out.println("result = " + result2);

        String value3 = "ㄹㄴㅇㄹㄴㅇㄹsdfsdf";
        boolean result3 = Pattern.matches("^[^a-zA-Z]+$", value3);
        System.out.println("result = " + result3);
    }

    @Test
    public void 영어_숫자_test() throws Exception {
        String value = "ss";
        boolean result = Pattern.matches("^[a-zA-Z0-9]+$", value);
        System.out.println("result = " + result);

        String value2 = "";
        boolean result2 = Pattern.matches("^[a-zA-Z0-9]+$", value2);
        System.out.println("result = " + result2);

        String value3 = "ㄹㄴㅇㄹㄴㅇㄹsdfsdf";
        boolean result3 = Pattern.matches("^[a-zA-Z0-9]+$", value3);
        System.out.println("result = " + result3);
    }

    @Test
    public void 영어_길이_2_4_test() throws Exception {
        String value = "sdf";
        boolean result = Pattern.matches("^[a-zA-Z]{2,4}$", value);
        System.out.println("result = " + result);
    }

    @Test
    public void user_username_test() throws Exception {
        String username = "ssar";
        boolean result = Pattern.matches("^[a-zA-Z0-9]{2,20}$", username);
        System.out.println("result = " + result);
    }

    @Test
    public void user_fullname_test() throws Exception {
        String username = "ssar가서소히히";
        boolean result = Pattern.matches("^[a-zA-Z가-힣]{1,20}$", username);
        System.out.println("result = " + result);
    }

    @Test
    public void user_email_test() throws Exception {
        String username = "test@gmail.com";
        boolean result = Pattern.matches("^[a-zA-Z0-9]{2,6}@[a-zA-Z0-9]{2,6}\\.[a-zA-Z0-9]{2,4}$", username);
        System.out.println("result = " + result);
    }
}
