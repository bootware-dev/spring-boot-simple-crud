package sbscrud.validation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import sbscrud.entity.User;
import sbscrud.service.UserDetailsService;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Optional;

public class UserExistsValidator implements ConstraintValidator<UserExists, String> {

    @Autowired
    UserDetailsService userDetailsService;

    @Override
    public void initialize(UserExists annotation) { }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        try {
            Optional<User> userOfNullable = Optional.ofNullable(userDetailsService.loadUserByUsername(value));
            return userOfNullable.isEmpty();
        } catch(UsernameNotFoundException ex) {
            return true;
        }
    }

}
