package ridhopriambodo.buana.service;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import ridhopriambodo.buana.entity.User;
import ridhopriambodo.buana.model.LoginUserRequest;
import ridhopriambodo.buana.model.TokenResponse;
import ridhopriambodo.buana.repository.UserRepository;

import java.util.UUID;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;


    @Autowired
    private ValidationService validationService;

    public TokenResponse login(LoginUserRequest request){
        validationService.validator(request);

        User user = userRepository.findById(request.getEmail())
                .orElseThrow( () -> new ResponseStatusException(HttpStatus.UNAUTHORIZED," Email or password wrong"));
        if(BCrypt.checkpw(request.getPassword(),user.getPassword())){
            user.setToken(UUID.randomUUID().toString());
            user.setTokenExpiredAt(System.currentTimeMillis()+(1000*60*60*24*7));
            userRepository.save(user);

            return TokenResponse.builder()
                    .token(user.getToken())
                    .expiredAt(user.getTokenExpiredAt())
                    .build();

        }else{
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED,"email or password wrong");
        }
    }

    @Transactional
    public void logout(){
        User user = getUserDetail();
        user.setTokenExpiredAt(null);
        user.setToken(null);
        userRepository.save(user);
    }

    public User getUserDetail(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = (String) authentication.getPrincipal(); // Assuming username is used as ID
        User user = userRepository.findByEmail(email);
        return user;
    }
}
