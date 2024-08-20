package ridhopriambodo.buana.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.*;
import ridhopriambodo.buana.model.LoginUserRequest;
import ridhopriambodo.buana.model.RegisterUserRequest;
import ridhopriambodo.buana.model.Response;
import ridhopriambodo.buana.model.TokenResponse;
import ridhopriambodo.buana.service.AuthService;
import ridhopriambodo.buana.service.UserService;

import java.io.IOException;

@RestController
public class AuthController {
    @Autowired
    private UserService userService;

    @Autowired
    private AuthService authService;


    @GetMapping("/loginGoogle")
    public void dashboard( HttpServletResponse response, Authentication authentication) throws IOException {
        OAuth2AuthenticationToken oauth2AuthToken = (OAuth2AuthenticationToken) authentication;
        OAuth2User oauth2User = oauth2AuthToken.getPrincipal();
        String name = oauth2User.getAttribute("name");
        String email = oauth2User.getAttribute("email");

        TokenResponse tokenResponse = authService.google(name, email);
        String token = tokenResponse.getToken();
        String expirationDate = String.valueOf(tokenResponse.getExpiredAt());

        System.err.println(tokenResponse);
        String redirectUrl = "http://localhost:3000/callback?token=" + token + "&expiresAt=" + expirationDate;
        System.err.println(redirectUrl);

        response.sendRedirect(redirectUrl);

    }

    @GetMapping("/error")
    public String error() {

        return "error";

    }

    @PostMapping(
            path = "/api/register",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public Response<String> register(@RequestBody RegisterUserRequest request) {
        userService.register(request);
        return Response.<String>builder().data("OK").build();
    }

    @PostMapping(
            path = "/api/login",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public Response<TokenResponse> login(@RequestBody LoginUserRequest request) {
        TokenResponse tokenResponse = authService.login(request);
        return Response.<TokenResponse>builder().data(tokenResponse).build();
    }

    @DeleteMapping(
            path = "/api/logout",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public Response<String> logout() {
        authService.logout();
        return Response.<String>builder().data("OK").build();
    }

    @GetMapping(path = "/api/xx")
    public String welcome() {
        return "welcome";
    }
}
