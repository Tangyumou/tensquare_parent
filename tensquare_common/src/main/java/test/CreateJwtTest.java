package test;

import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;

public class CreateJwtTest {
    public static void main(String[] args) {
        JwtBuilder builder = Jwts.builder()
                .setId("154")
                .setSubject("mike")
                .setIssuedAt(new Date())
                .signWith(SignatureAlgorithm.HS256,"tang");
        System.out.println(builder.compact());
    }
}
