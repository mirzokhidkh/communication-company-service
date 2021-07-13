package uz.mk.communicationcompanyservice.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import uz.mk.communicationcompanyservice.service.AuthService;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Base64;

@Component
public class JwtFilter extends OncePerRequestFilter {
    @Autowired
    JwtProvider jwtProvider;

    @Autowired
    AuthService authService;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
        String authorization = httpServletRequest.getHeader("Authorization");
        if (authorization != null && authorization.startsWith(("Bearer"))) {
            String client = httpServletRequest.getHeader("Client");
            boolean isClient = client != null && client.equals("Client");

            String token = authorization.substring(7);
            String username = jwtProvider.getUsernameFromToken(token);
            if (username != null) {
                UserDetails userDetails = isClient
                        ? authService.loadClientByClientNameFromSimCard(username)
                        : authService.loadUserByUsername(username);
                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                        new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            }
        } else if (authorization != null && authorization.startsWith(("Basic"))) {
            String basic = authorization.substring(6);
            byte[] bytes = Base64
                    .getDecoder()
                    .decode(basic);
            String s = new String(bytes);
            String[] split = s.split(":");
            String username = split[0];
            String pinCode = split[1];
            UserDetails userDetails = authService.loadSimCardByBackNumber(username);
            if (passwordEncoder.matches(pinCode, userDetails.getPassword())) {
                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                        new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            }

        }

        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }
}
