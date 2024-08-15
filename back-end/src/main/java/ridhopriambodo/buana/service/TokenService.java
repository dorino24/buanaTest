package ridhopriambodo.buana.service;

import org.antlr.v4.runtime.Token;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import ridhopriambodo.buana.entity.User;
import ridhopriambodo.buana.repository.UserRepository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class TokenService {

    @Autowired
    private UserRepository UserRepository;

    public boolean validateToken(String token) {
        Optional<User> tokenOpt = UserRepository.findByToken(token);

        if (tokenOpt.isEmpty()) {
            return false;
        }
        User dbToken = tokenOpt.get();
        return dbToken.isExpired();
    }

    public Authentication getAuthentication(String token) {
        Optional<User> tokenOpt = UserRepository.findByToken(token);

        if (tokenOpt.isPresent()) {
            User dbToken = tokenOpt.get();
            return new UsernamePasswordAuthenticationToken(dbToken.getEmail(), null, null);
        }

        return null;
}
}
