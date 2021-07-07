package ma.pragmatic.authenticationsystem.controller;

import ma.pragmatic.authenticationsystem.config.jwt.JwtProvider;
import ma.pragmatic.authenticationsystem.model.User;
import ma.pragmatic.authenticationsystem.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {

    @Autowired
    private UserService userService;

    @Autowired
    private JwtProvider jwtProvider;

    @PostMapping("register")
    public ResponseEntity<String> registerUser(@RequestBody User user) {
        userService.addOrUpdate(user);
        return ResponseEntity.status(HttpStatus.CREATED).body("User was successfully registered");
    }

    @PostMapping("login")
    public ResponseEntity<String> loginUser(@RequestBody LoginRequest loginRequest) {
        User loggedInUser = userService.getByEmailAndPassword(loginRequest.getEmail(), loginRequest.getPassword());
        String generatedToken = jwtProvider.generateToken(loggedInUser.getEmail());
        return ResponseEntity.ok().body(generatedToken);
    }
}
