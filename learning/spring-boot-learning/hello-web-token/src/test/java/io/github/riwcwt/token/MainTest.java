package io.github.riwcwt.token;

import io.github.riwcwt.token.util.SecurityUtil;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.CompressionCodecs;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.impl.crypto.MacProvider;
import org.junit.Test;

import java.io.UnsupportedEncodingException;
import java.security.Key;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.time.Instant;
import java.util.Date;

/**
 * Created by michael on 2017-06-09.
 */
public class MainTest {

    @Test
    public void key() throws NoSuchAlgorithmException {
        // RSA signatures require a public and private RSA key pair, the public key
        // must be made known to the JWS recipient in order to verify the signatures
        KeyPairGenerator keyGenerator = KeyPairGenerator.getInstance("RSA");
        keyGenerator.initialize(1024);

        KeyPair kp = keyGenerator.genKeyPair();
        RSAPublicKey publicKey = (RSAPublicKey) kp.getPublic();
        RSAPrivateKey privateKey = (RSAPrivateKey) kp.getPrivate();

        System.out.println(publicKey.toString());
        System.out.println(privateKey.toString());
    }

    @Test
    public void simple() {
        // We need a signing key, so we'll create one just for this example. Usually
        // the key would be read from your application configuration instead.
        Key key = MacProvider.generateKey();

        String compactJws = Jwts.builder()
                .setSubject("Joe")
                .compressWith(CompressionCodecs.DEFLATE)
                .signWith(SignatureAlgorithm.HS512, key)
                .compact();

        System.out.println(compactJws);
    }

    @Test
    public void encode() throws UnsupportedEncodingException {
        String token = Jwts.builder()
                .setSubject("1234567890")
                .setId("784fe208-5dfd-4c36-8f1f-5faeb25398b4")
                .setIssuedAt(Date.from(Instant.ofEpochSecond(1497234336)))
                .setExpiration(Date.from(Instant.ofEpochSecond(1497237936)))
                .claim("name", "John Doe")
                .claim("admin", true)
                .signWith(SignatureAlgorithm.HS512, SecurityUtil.secret.getBytes("UTF-8"))
                .compact();

        System.out.println(token);
    }

    @Test
    public void decode() throws UnsupportedEncodingException {
        Jws<Claims> claims = Jwts.parser()
                .setSigningKey("secret".getBytes("UTF-8"))
                .parseClaimsJws("eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwianRpIjoiNzg0ZmUyMDgtNWRmZC00YzM2LThmMWYtNWZhZWIyNTM5OGI0IiwiaWF0IjoxNDk3MjM0MzM2LCJleHAiOjE0OTcyMzc5MzYsIm5hbWUiOiJKb2huIERvZSIsImFkbWluIjp0cnVlfQ.yxGvWgMyTL2UvUjBRHIaqbsc6TxXCirlOlDXm4VxxOxSTjetsVJiwXdp5zD6DmjLTqICsK9jTlB3m60ka6MeeQ");

        System.out.println(claims.getSignature());
    }
}
