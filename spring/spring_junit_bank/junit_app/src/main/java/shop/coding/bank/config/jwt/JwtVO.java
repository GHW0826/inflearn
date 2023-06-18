package shop.coding.bank.config.jwt;


/*
    Secret은 환경변수, 파일등에 저장.
    refrash token: 일단 보류
 */
public interface JwtVO {
    public static final String SECRET = "key"; // HS256 (대칭키)
    public static final int EXPIRATION_TIME = 1000 * 60 * 60 * 24 * 7; // week
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String HEADER = "Authorization";
}
