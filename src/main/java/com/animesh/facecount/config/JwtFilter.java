package com.animesh.facecount.config;

import com.animesh.facecount.services.JwtService;
import com.animesh.facecount.services.MyUserDetailsService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
 * @author Animesh Mahata
 * @version 1.0
 * @since 2025-04-04
 *
 * @Description:
 * Defines a Filter for authenticating users with jwt tokens.
 */

@Configuration
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final ApplicationContext context;


    /**
     * Method description
     * @param request request made by the client
     * @param response response for the client
     * @param filterChain a chain of filters within which this filter will be added
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {

        String authHeader = request.getHeader("Authorization");
        String token = null;
        String userid = null;

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            token = authHeader.substring(7);
            userid = jwtService.extractUserId(token);
        }

        if (userid != null && SecurityContextHolder.getContext().getAuthentication() == null) {

            UserDetails userDetails = context.getBean(MyUserDetailsService.class).loadUserByUsername(userid);

            if (jwtService.validateToken(token, userDetails)) {
                UsernamePasswordAuthenticationToken authToken =
                        new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }

        filterChain.doFilter(request, response);

    }
}
