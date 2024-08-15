package ridhopriambodo.buana.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import ridhopriambodo.buana.model.LoginUserRequest;
import ridhopriambodo.buana.model.RegisterUserRequest;
import ridhopriambodo.buana.model.Response;
import ridhopriambodo.buana.model.TokenResponse;
import ridhopriambodo.buana.service.AuthService;
import ridhopriambodo.buana.service.UserService;

@RestController
public class AuthController {
    @Autowired
    private UserService userService;

    @Autowired
    private AuthService authService;

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/home")
    public String home() {
        return "home";
    }

    @PostMapping(
            path = "/api/register" ,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public Response<String> register(@RequestBody RegisterUserRequest request){
        userService.register(request);
        return Response.<String>builder().data("OK").build();
    }

    @PostMapping(
            path = "/api/login" ,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public Response<TokenResponse> login(@RequestBody LoginUserRequest request){
        TokenResponse tokenResponse =  authService.login(request);
        return Response.<TokenResponse>builder().data(tokenResponse).build();
    }

    @DeleteMapping(
            path = "/api/logout" ,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public Response<String> logout(){
        authService.logout();
        return Response.<String>builder().data("OK").build();
    }

    @GetMapping( path = "/api/xx" )
    public String welcome(){
        return "welcome";
    }
}
