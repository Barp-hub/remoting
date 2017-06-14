package io.github.riwcwt.token.util;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.io.UnsupportedEncodingException;
import java.util.Date;

/**
 * Created by michael on 2017-06-12.
 */
public class SecurityUtil {
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

}
