package ridhopriambodo.buana.service;


import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import ridhopriambodo.buana.entity.User;
import ridhopriambodo.buana.model.GoogleUserRequest;
import ridhopriambodo.buana.model.RegisterUserRequest;
import ridhopriambodo.buana.model.Response;
import ridhopriambodo.buana.model.UserResponse;
import ridhopriambodo.buana.repository.UserRepository;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ValidationService validationService;

    @Autowired
    private AuthService authService;

    @Transactional
    public void register(RegisterUserRequest request){
        validationService.validator(request);

        if(userRepository.existsById(request.getEmail())){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Email Already Registered");
        }

        User user = new User();
        user.setEmail(request.getEmail());
        user.setName(request.getName());
        user.setPassword(BCrypt.hashpw(request.getPassword(),BCrypt.gensalt()));

        userRepository.save(user);
    }



    public UserResponse getUser(){
        User user = authService.getUserDetail();
        return UserResponse.builder()
                .email(user.getEmail())
                .name(user.getName())
                .build();
    }
}
