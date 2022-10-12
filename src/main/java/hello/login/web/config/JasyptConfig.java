package hello.login.web.config;

import hello.login.web.factory.YamlLoadFactory;
import org.jasypt.encryption.StringEncryptor;
import org.jasypt.encryption.pbe.PooledPBEStringEncryptor;
import org.jasypt.encryption.pbe.config.SimpleStringPBEConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource(value = {"application.yml"}, factory = YamlLoadFactory.class)   //@PropertySource -> 기본적으로 yml 파일을 로드하지 않아 따로 class 파일 생성
public class JasyptConfig {

    @Value("${cypher.key}")
    private String cypherKey;

    private final static String ALGORITHM = "PBEWithMD5AndDES";
    private final static String CNT = "1000";
    private final static String POOL_SIZE = "1";
    private final static String BASE64 = "base64";

    @Bean(name = "jasyptStringEncryptor")
    public StringEncryptor stringEncryptor() {
        SimpleStringPBEConfig config = new SimpleStringPBEConfig();
        config.setPassword(cypherKey);
        config.setAlgorithm(ALGORITHM);
        config.setKeyObtentionIterations(CNT);
        config.setPoolSize(POOL_SIZE);
        config.setStringOutputType(BASE64);

        PooledPBEStringEncryptor encryptor = new PooledPBEStringEncryptor();
        encryptor.setConfig(config);

        return encryptor;
    }
}
