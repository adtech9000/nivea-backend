package com.nivea_be.nivea_ad.service;

import com.nivea_be.nivea_ad.dto.request.UserDTO;
import com.nivea_be.nivea_ad.dto.response.ApiResponse;
import com.nivea_be.nivea_ad.entity.User;
import com.nivea_be.nivea_ad.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.nivea_be.nivea_ad.constants.TrackConstants.*;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public ApiResponse<UserDTO> login(String username, String password) {
        Optional<User> userOptional = userRepository.findByUsername(username);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            if (user.getPassword().equals(password)) {
                UserDTO userDTO = new UserDTO(user.getUsername(), null);
                return ApiResponse.success(userDTO, LOGIN_SUCCESS);
            }
        }
        return ApiResponse.failure(INVALID_CREDENTIALS_ERROR);
    }

    public ApiResponse<List<UserDTO>> findAllUserDTOs() {
        List<UserDTO> userDTOs = userRepository.findAll().stream()
                .map(user -> new UserDTO(user.getUsername(), user.getPassword()))
                .collect(Collectors.toList());
        return ApiResponse.success(userDTOs, FETCH_USERS_SUCCESS);
    }

    public ApiResponse<UserDTO> saveUserDTO(UserDTO userDTO) {
        User user = new User(null, userDTO.getUsername(), userDTO.getPassword());
        User savedUser = userRepository.save(user);
        UserDTO savedUserDTO = new UserDTO(savedUser.getUsername(), savedUser.getPassword());
        return ApiResponse.success(savedUserDTO, USER_SAVE_SUCCESS);
    }
}
