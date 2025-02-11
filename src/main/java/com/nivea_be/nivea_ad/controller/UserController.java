package com.nivea_be.nivea_ad.controller;

import com.nivea_be.nivea_ad.dto.request.UserDTO;
import com.nivea_be.nivea_ad.dto.response.ApiResponse;
import com.nivea_be.nivea_ad.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<UserDTO>> login(@RequestParam String username, @RequestParam String password) {
        ApiResponse<UserDTO> response = userService.login(username, password);
        return response.isSuccess() ? ResponseEntity.ok(response) : ResponseEntity.badRequest().body(response);
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<UserDTO>>> getAllUsers() {
        ApiResponse<List<UserDTO>> response = userService.findAllUserDTOs();
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<ApiResponse<UserDTO>> createUser(@RequestBody UserDTO userDTO) {
        ApiResponse<UserDTO> response = userService.saveUserDTO(userDTO);
        return ResponseEntity.ok(response);
    }
}

