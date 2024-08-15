package ridhopriambodo.buana.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.juli.logging.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;
import ridhopriambodo.buana.model.Response;
import ridhopriambodo.buana.service.TokenService;

import java.io.IOException;
import java.util.Collections;

public class JwtTokenFilter extends OncePerRequestFilter {

    @Autowired
    private TokenService tokenService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        if ("/api/login".equals(request.getRequestURI())) {
            filterChain.doFilter(request, response);
            return;
        }
        if ("/api/register".equals(request.getRequestURI())) {
            filterChain.doFilter(request, response);
            return;
        }
        String apiToken = request.getHeader("X-TOKEN-API");
        if (apiToken != null && tokenService.validateToken(apiToken)) {

            SecurityContextHolder.getContext().setAuthentication(tokenService.getAuthentication(apiToken));
        }else {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            Response<String> errorResponse = new Response<>();
            errorResponse.setErrors("Unauthorized"); // You can add more errors if needed

            String jsonResponse = new ObjectMapper().writeValueAsString(errorResponse);
            response.getWriter().write(jsonResponse);
            return;
        }

        filterChain.doFilter(request, response);
    }
}
