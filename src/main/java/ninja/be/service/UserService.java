package ninja.be.service;

import lombok.RequiredArgsConstructor;
import ninja.be.dto.user.request.UserUpdateRequest;
import ninja.be.dto.user.response.UserResponse;
import ninja.be.entity.User;
import ninja.be.entity.enums.Authority;
import ninja.be.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    public UserResponse findByUserId(Long id){
        User user = userRepository.findById(id).get();
        return UserResponse.from(user);
    }

    public void updateUser(Long id, UserUpdateRequest userUpdateRequest){
        User user = userRepository.findById(id).get();
        user.updateUser(passwordEncoder.encode(userUpdateRequest.getPassword()), userUpdateRequest.getUsername(), userUpdateRequest.getLocation());
    }

}
