package ninja.be.service;

import lombok.RequiredArgsConstructor;
import ninja.be.dto.user.response.UserResponse;
import ninja.be.entity.User;
import ninja.be.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;


    public UserResponse findByUserId(Long id){
        User user = userRepository.findById(id).get();
        return UserResponse.from(user);
    }
}
