package io.github.riwcwt.token.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.UnsupportedEncodingException;
import java.util.Date;

/**
 * Created by michael on 2017-06-12.
 */
public class SecurityUtil {
    private static final Logger logger = LoggerFactory.getLogger(SecurityUtil.class);

    public static final String secret = "rG*Usp%j[MWb6vg7p3yQVjOcW[k*^0m2";

    public static String encode(String id, Date expire) throws UnsupportedEncodingException {
        return Jwts.builder()
                .setId(id)
                .setIssuedAt(new Date())
                .setExpiration(expire)
                .claim("version", 1)
                .signWith(SignatureAlgorithm.HS512, SecurityUtil.secret.getBytes("UTF-8"))
                .compact();
    }

    public static Claims decode(String token) {
        try {
            if (token != null) {
                Jws<Claims> claims = Jwts.parser()
                        .setSigningKey(SecurityUtil.secret.getBytes("UTF-8"))
                        .parseClaimsJws(token);
                return claims.getBody();
            }
        } catch (Exception e) {
            logger.error("token解码错误！", e);
        }
        return null;
    }
}
