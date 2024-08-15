package ridhopriambodo.buana.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ridhopriambodo.buana.model.RegisterUserRequest;
import ridhopriambodo.buana.model.Response;
import ridhopriambodo.buana.model.UserResponse;
import ridhopriambodo.buana.service.UserService;

@RestController
public class UserController {

    @Autowired
    private UserService userService;


    @GetMapping(
            path = "/api/user",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public Response<UserResponse> getUser() {
        UserResponse userResponse = userService.getUser();
        return Response.<UserResponse>builder().data(userResponse).build();
    }
}
