package com.joy.xxfy.informationaldxn.publish.utils.jwt;

import com.joy.xxfy.informationaldxn.config.JwtParamConfig;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.security.Key;
import java.util.Date;
import java.util.Map;

/**
 * jwt处理工具类
 */
public class JwtUtil {

    /**
     * 解析jwt
     */
    public static Claims parseJWT(String jsonWebToken, String base64Security) {
        try {
            Claims claims = Jwts.parser().setSigningKey(DatatypeConverter.parseBase64Binary(base64Security))
                    .parseClaimsJws(jsonWebToken).getBody();
            return claims;
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 生成jwt
     */
    public static String createJWT(Map<String, Object> claims, JwtParamConfig jwtParamConfig) {
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);

        // 生成签名密钥
        byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(jwtParamConfig.getBase64Security());
        Key signingKey = new SecretKeySpec(apiKeySecretBytes, signatureAlgorithm.getJcaName());

        // 添加构成JWT的参数
        JwtBuilder builder = Jwts.builder().setHeaderParam("typ", "JWT").setClaims(claims)
                .setIssuer(jwtParamConfig.getIssuer()).setAudience(jwtParamConfig.getAudience())
                .signWith(signatureAlgorithm, signingKey);
        // 添加Token过期时间
        if (jwtParamConfig.getTTLMillis() >= 0) {
            long expMillis = nowMillis + jwtParamConfig.getTTLMillis();
            Date exp = new Date(expMillis);
            builder.setExpiration(exp).setNotBefore(now);
        }
        // 生成JWT
        return builder.compact();
    }
}
