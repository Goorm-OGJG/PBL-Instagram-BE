package ogjg.instagram.user.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import lombok.RequiredArgsConstructor;
import ogjg.instagram.config.security.jwt.JwtUserDetails;
import ogjg.instagram.user.domain.User;
import ogjg.instagram.user.dto.UserAuthNumberRequestDto;
import ogjg.instagram.user.dto.SignupRequestDto;
import ogjg.instagram.user.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@RequestBody @Valid SignupRequestDto signupRequestDto) {
        return userService.registerUser(signupRequestDto);
    }

    @PostMapping("/token")
    public ResponseEntity<?> generateToken(HttpServletRequest request, HttpServletResponse response) {
        userService.generateToken(request, response);
        return new ResponseEntity<>("Access Token 재발급 성공", HttpStatus.OK);
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<?> checkEmail(@PathVariable @Email String email) {
        if (userService.isEmailAlreadyInUse(email)) {
            throw new IllegalArgumentException("이미 사용되고 있는 이메일입니다.");
        }
        return new ResponseEntity<>("사용 가능한 이메일입니다.", HttpStatus.OK);
    }

    @GetMapping("/nickname/{nickname}")
    public ResponseEntity<?> checkNickname(@PathVariable String nickname) {
        if (userService.isNicknameAlreadyInUse(nickname)) {
            throw new IllegalArgumentException("이미 사용되고 있는 닉네임입니다.");
        }
        return new ResponseEntity<>("사용 가능한 닉네임입니다.", HttpStatus.OK);
    }

    @GetMapping("/logout")
    public ResponseEntity<?> logout(
            @AuthenticationPrincipal JwtUserDetails userDetails,
            HttpServletResponse response
    ) {
        userService.logout(userDetails.getUsername(), response);
        return new ResponseEntity<>("로그아웃 되었습니다.", HttpStatus.OK);
    }

    @PostMapping("/exist")
    public ResponseEntity<?> generateAuthenticationNumber(
            @RequestBody UserAuthNumberRequestDto userAuthNumberRequestDto) {
        User user = userService.findMemberIfExists(userAuthNumberRequestDto);
        userService.generateAuthenticationNumber(user);

        return new ResponseEntity<>("인증번호가 메일로 전송 되었습니다.", HttpStatus.OK);
    }
}
