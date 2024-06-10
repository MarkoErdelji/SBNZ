package com.ftn.sbnz.controller;

import com.ftn.sbnz.dto.*;
import com.ftn.sbnz.exception.NotFoundException;
import com.ftn.sbnz.model.TokenPrincipalModel;
import com.ftn.sbnz.model.User;
import com.ftn.sbnz.model.enums.SuspicionLevel;
import com.ftn.sbnz.service.UserServiceImpl;
import com.ftn.sbnz.service.intefaces.JwtService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.UUID;


@RestController
@RequestMapping("/users")
public class UserController {

    private final UserServiceImpl userServiceImpl;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtService jwtService;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public UserController(UserServiceImpl userServiceImpl) {
        this.userServiceImpl = userServiceImpl;
    }

    @GetMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userServiceImpl.getAllUsers();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        return userServiceImpl.getUserById(id)
                .map(user -> new ResponseEntity<>(user, HttpStatus.OK))
                .orElseThrow(() -> new NotFoundException("User not found with id: " + id));
    }

     @PostMapping()
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<?> createUser(@RequestBody CreateUserRequestDTO createUserRequestDTO) {
        ModelMapper modelMapper = new ModelMapper();
        User userModel = modelMapper.map(createUserRequestDTO, User.class);
        String hashedPassword = passwordEncoder.encode(createUserRequestDTO.getPassword());
        userModel.setPassword(hashedPassword);
        userModel.setSuspicionLevel(SuspicionLevel.NONE);
        User savedUser = userServiceImpl.saveUser(userModel);
        UserResponseDTO userResponse = modelMapper.map(savedUser, UserResponseDTO.class);
        return new ResponseEntity<>(userResponse, HttpStatus.OK);
    }

    @PostMapping(value = "/generateToken")
    public ResponseEntity<?> authenticateAndGetToken(@RequestBody LoginDTO loginDTO) throws NotFoundException, BadCredentialsException {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginDTO.getUsername(), loginDTO.getPassword()));
        User userModel = userServiceImpl.getActiveUserByUsername(loginDTO.getUsername());
        if (authentication.isAuthenticated()) {
            String token = jwtService.generateToken(userModel);
            JwtResponseDTO jwtResponseDTO = new JwtResponseDTO(token);
            return new ResponseEntity<>(jwtResponseDTO, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Bad credentials!", HttpStatus.BAD_REQUEST);
        }
    }



     @GetMapping("/achievements")
    @PreAuthorize("hasAuthority('USER')")
    public ResponseEntity<List<UserAchievementsDTO>> getUserAchievements() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUsername = ((TokenPrincipalModel) authentication.getPrincipal()).getEmail();
        User user = userServiceImpl.getUserByUsername(currentUsername);
        if (user == null) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        List<UserAchievementsDTO> achievements = userServiceImpl.getUserAchievements(user.getId());
        return new ResponseEntity<>(achievements, HttpStatus.OK);
    }
}
