package ridhopriambodo.buana.service;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ridhopriambodo.buana.model.RegisterUserRequest;

import java.util.Set;

@Service
public class ValidationService {

    @Autowired
    public Validator validator;

    public void validator(Object request){
        Set<ConstraintViolation<Object>> constraintViolations = validator.validate(request);
        if(!constraintViolations.isEmpty()){
            throw new ConstraintViolationException(constraintViolations);
        }
    }
}
