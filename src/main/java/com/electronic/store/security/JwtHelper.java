package com.electronic.store.security;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public class JwtHelper {

    private static final Logger logger= LoggerFactory.getLogger(JwtHelper.class);

    public static final long JWT_TOKEN_VALIDITY = 5 * 60 * 60;

    private String jwtSecret = "jwtTokenKeyqwertyuioppoiuytrewqasdfghjkllkjhgfdsaasdfghjklmnbvcxzasdfghjklmnbvcxzASDFGHJKERTBSDFGHJKERTYHBNMK";
//RETRIVE   userdetails from  jwt token

    public String getUserNameFromJwtToken(String token) {
        logger.info("Initialize request for get getUserNameFromJwtToken : {} ");
        return getClaimsFromToken(token, Claims::getSubject);

    }

    //retrieve expiration data from jwt token
    public Date getExpiratiobDateFromToken(String token) {
        logger.info("Initialize request for get getExpiratiobDateFromToken : {} ");

        return getClaimsFromToken(token,Claims::getExpiration);

    }

    public <T>T getClaimsFromToken(String token, Function<Claims,T> claimsResolver){
        logger.info("Initialize request for  getClaimsFromToken : {} ");
        final Claims claims=getAllClaimsFromToken(token);
        logger.info("Completed request for  getClaimsFromToken : {} ");
        return claimsResolver.apply(claims) ;
    }

    //for retrieving any information from token required secrete key
    private Claims getAllClaimsFromToken(String token) {
        logger.info("Initialize request for  getAllClaimsFromToken : {} ");

        // TODO Auto-generated method stub
        return Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody();
    }

    //check if token have expired
    private Boolean isTokenExpired(String token) {

        logger.info("Initialize request for  isTokenExpired : {} ");
        final Date expiratiobDateFromToken = getExpiratiobDateFromToken(token);

        return expiratiobDateFromToken.before(new Date());

    }

    public String generateToken(UserDetails userDetails) {
        logger.info("Initialize request for  generateToken : {} "+userDetails);
        Map<String,Object> claims= new HashMap<>();
        logger.info("Completed request for  generateToken : {} "+userDetails);
        return doGenerateToken(claims,userDetails.getUsername());


    }

    private String doGenerateToken(Map<String, Object> claims, String username) {
        // TODO Auto-generated method stub
        logger.info("Initialize request dogeneratetoken  {} "+username);
        final Date createdDate = new Date();
        final Date expirationDate = new Date(createdDate.getTime() + JWT_TOKEN_VALIDITY * 1000);
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(username)
                .setIssuedAt(createdDate)
                .setExpiration(expirationDate)
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();
    }

    //validate toen
    public boolean validateJwtToken(String token,UserDetails userDetails) {


        final String username = getUserNameFromJwtToken(token);
        logger.info("Completed request for  generateToken : {} "+userDetails);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

}
