package com.electronic.store.security;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private static Logger logger= LoggerFactory.getLogger(JwtAuthenticationFilter.class);

    @Autowired
    private JwtHelper jwtHelper;

    @Autowired
    private UserDetailsService userDetailService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String requestHeader = request.getHeader("Authorization");

        System.out.println(requestHeader);

        String username=null;
        String token=null;

        if(requestHeader!=null && requestHeader.startsWith("Bearer")) {

            token = requestHeader.substring(7);
            try {
                username = jwtHelper.getUserNameFromJwtToken(token);
                logger.info("get username/email from token : {} "+username);

            }
            catch(IllegalArgumentException ex) {
                logger.info("Illegal argument while fetching the username: {}", ex.getMessage());
                ex.printStackTrace();
            }
            catch(ExpiredJwtException ex ) {
                logger.error("JWT token is expired: {}", ex.getMessage());
                ex.printStackTrace();
            }
            catch(MalformedJwtException m) {
                logger.error("some changes have done in token : {}",m.getMessage());
                m.printStackTrace();

            }
            catch(Exception e) {
                logger.info("exception arises !!!!"+e.getMessage());
                e.printStackTrace();
            }

        }else {
            logger.info("invalid header");
        }


        if(username!=null  && SecurityContextHolder.getContext().getAuthentication()==null) {
            logger.info("Initialize request for to userlogin details in SecurityContextHolder ");
            //fetch userdetails from username

            UserDetails userDetails = this.userDetailService.loadUserByUsername(username);
            logger.info("Initialize request for SecurityContextHolder : {}"+userDetails);

            Boolean validateJwtToken = this.jwtHelper.validateJwtToken(token, userDetails);

            if(validateJwtToken) {
//				set authentication
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities());

                logger.info("Initialize request for UsernamePasswordAuthenticationToken authentication : {}"+userDetails);

                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));


                SecurityContextHolder.getContext().setAuthentication(authentication);
            } else {
                System.out.println("Invalid jwt token");
            }

        }
        /*else {
            System.out.println("username is null or context is not null");

        }*/


        filterChain.doFilter(request, response);


    }


}

