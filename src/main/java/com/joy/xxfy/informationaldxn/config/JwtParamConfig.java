package com.joy.xxfy.informationaldxn.config;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * JWT签证
 */
@Component
@ConfigurationProperties(value = "jwtparam")
public class JwtParamConfig {

    /**
     * 代表这个JWT的签发主体
     */
    private String issuer;

    /**
     * JWT的接收对象
     */
    private String audience;

    /**
     * JWT的过期时间
     */
    private Long TTLMillis;

    /**
     * 关键字
     */
    private String base64Security;

    public String getIssuer() {
        return issuer;
    }

    public void setIssuer(String issuer) {
        this.issuer = issuer;
    }

    public String getAudience() {
        return audience;
    }

    public void setAudience(String audience) {
        this.audience = audience;
    }

    public Long getTTLMillis() {
        return TTLMillis;
    }

    public void setTTLMillis(Long TTLMillis) {
        this.TTLMillis = TTLMillis;
    }

    public String getBase64Security() {
        return base64Security;
    }

    public void setBase64Security(String base64Security) {
        this.base64Security = base64Security;
    }
}
