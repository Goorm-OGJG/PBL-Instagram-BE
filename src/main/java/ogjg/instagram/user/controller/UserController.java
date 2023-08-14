package ogjg.instagram.user.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import ogjg.instagram.user.dto.SignupRequestDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UsersService usersService;

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@RequestBody @Valid SignupRequestDto signupRequestDto) {
        return usersService.registerUser(signupRequestDto);
    }

    @PostMapping("/token")
    public ResponseEntity<?> generateToken(HttpServletRequest request, HttpServletResponse response) {
        usersService.generateToken(request, response);
        return new ResponseEntity<>("Access Token 재발급 성공", HttpStatus.OK);
    }

}
