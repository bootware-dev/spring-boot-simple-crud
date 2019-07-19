package sbscrud.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sbscrud.entity.User;
import sbscrud.repository.UserRepository;

import java.util.Optional;

@Service
public class UserDetailsService implements org.springframework.security.core.userdetails.UserDetailsService {

    @Autowired
    private UserRepository repository;

    @Override
    public User loadUserByUsername(String username) throws UsernameNotFoundException {
        var userOfNullable = Optional.ofNullable(repository.findByUsername(username));
        userOfNullable.orElseThrow(() -> new UsernameNotFoundException("User" + username + "was not found in the database"));
        return userOfNullable.get();
    }

    public Iterable<User> findAllUser() {
        return repository.findAll();
    }

    @Transactional
    public User registerUser(User user) {
        return repository.save(user);
    }

    @Transactional
    public void deleteUser(String username) {
        repository.delete(loadUserByUsername(username));
    }

}
