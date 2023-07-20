package ninja.be.service;

import lombok.RequiredArgsConstructor;
import ninja.be.entity.User;
import ninja.be.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;


    public User findByUserId(Long id){
       return userRepository.findById(id).get();
    }
}
