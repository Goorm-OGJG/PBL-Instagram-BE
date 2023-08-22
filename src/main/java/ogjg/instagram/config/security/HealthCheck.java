package ogjg.instagram.config.security;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HealthCheck {
    @GetMapping("/health")
    public ResponseEntity<?> responseHealth() {
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
