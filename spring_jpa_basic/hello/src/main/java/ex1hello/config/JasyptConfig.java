package ex1hello.config;

import com.ulisesbocchio.jasyptspringboot.annotation.EnableEncryptableProperties;
import org.jasypt.encryption.StringEncryptor;
import org.jasypt.encryption.pbe.PooledPBEStringEncryptor;
import org.jasypt.encryption.pbe.config.SimpleStringPBEConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableEncryptableProperties
public class JasyptConfig {

    @Value("${jasypt.encryptor.password}")
    private String password;
    
    // 빈 이름 설정
    // 별도로 지정하지 않으면 기본적으로 jasyptStringEncryptor를 사용
    // appliaction.properties에 설정
    @Bean("jasyptStringEncryptor")
    public StringEncryptor stringEncryptor() {
        PooledPBEStringEncryptor encryptor = new PooledPBEStringEncryptor();
        SimpleStringPBEConfig config = new SimpleStringPBEConfig();
        // 암호화 사용 키
        config.setPassword(password);
        // 암호화 알고리즘
        config.setAlgorithm("PBEWithMD5AndDES");
        // 반복할 해싱 횟수
        config.setKeyObtentionIterations("1000");
        // 암호화 인스턴스 풀
        config.setPoolSize("1");
        // 인코딩 방식
        config.setStringOutputType("base64");

        config.setProviderName("SunJCE");
        config.setSaltGeneratorClassName("org.jasypt.salt.RandomSaltGenerator");
        config.setIvGeneratorClassName("org.jasypt.iv.NoIvGenerator");
        encryptor.setConfig(config);
        return encryptor;
    }
}