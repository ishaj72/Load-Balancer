package com.example.backend.jwt;

import io.jsonwebtoken.Jwts;

import javax.crypto.SecretKey;
import java.util.Base64;

public class JwtSecretKeyGenerator {
    public static void main(String[] args){
        SecretKey secretKey = Jwts.SIG.HS256.key().build(); // this will create key with HS256 protocol
        String base64 = Base64.getEncoder().encodeToString(secretKey.getEncoded()); // this will encode key so that it can be stored in env variables
        System.out.println("Printing the jwt token : "+ secretKey);
    }
}
