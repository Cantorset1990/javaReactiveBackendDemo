package com.projectroboticscontent.contentDatabaseAPI.Utility;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.security.Key;
import java.util.Date;

public class JwtTokenUtil {

         public static long parseJWT(String JWT, String USERTYPE)
         {

                  //This line will throw an exception if it is not a signed JWS (as expected)
                  Claims claims = Jwts.parser()
                          .setSigningKey(DatatypeConverter.parseBase64Binary("Authenticate"))
                          .parseClaimsJws(JWT).getBody();
                  //System.out.println("ID: " + claims.getId());
                  //System.out.println("Subject: " + claims.getSubject());
                  //System.out.println("Issuer: " + claims.getIssuer());
                  //System.out.println("Expiration: " + claims.getExpiration());

                  if(claims.getExpiration().after(new Date()) && claims.getSubject().contains(USERTYPE))
                  {
                        return Long.parseLong(claims.getId());
                  }else {
                         return 0;
                  }




          }

    public static long parseJWTProfile(String JWT)
    {

        //This line will throw an exception if it is not a signed JWS (as expected)
        Claims claims = Jwts.parser()
                .setSigningKey(DatatypeConverter.parseBase64Binary("Authenticate"))
                .parseClaimsJws(JWT).getBody();
        //System.out.println("ID: " + claims.getId());
        //System.out.println("Subject: " + claims.getSubject());
        //System.out.println("Issuer: " + claims.getIssuer());
        //System.out.println("Expiration: " + claims.getExpiration());

        if(claims.getExpiration().after(new Date()))
        {
            return Long.parseLong(claims.getId());
        }else {
            return 0;
        }




    }

    public static String createJWT(long id, String subject) {

        long ttlMillis = 360000000;
        String issuer = "project-robotics-server";
        //String subject = "login-session";
        //The JWT signature algorithm we will be using to sign the token
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);

        //We will sign our JWT with our ApiKey secret
        byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary("Authenticate");
        Key signingKey = new SecretKeySpec(apiKeySecretBytes, signatureAlgorithm.getJcaName());

        //Let's set the JWT Claims
        JwtBuilder builder = Jwts.builder()
                .setId(String.valueOf(id))
                .setIssuedAt(now)
                .setSubject(subject)
                .setIssuer(issuer)

                .signWith(signatureAlgorithm, signingKey);

        //if it has been specified, let's add the expiration
        if (ttlMillis >= 0) {
            long expMillis = nowMillis + ttlMillis;
            Date exp = new Date(expMillis);
            builder.setExpiration(exp);
        }

        //Builds the JWT and serializes it to a compact, URL-safe string
        return builder.compact();
    }

}


