package dairy.dairybe.controller;

import dairy.dairybe.dto.LoginRequestDTO;
import dairy.dairybe.entity.User;
import dairy.dairybe.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequestDTO loginRequest, HttpSession session) {
        User user = userService.authenticate(loginRequest.getEmail(), loginRequest.getPassword());
        if (user != null) {
            session.setAttribute("USER_ID", user.getId());
            Map<String, Object> response = new HashMap<>();
            response.put("message", "login_success");
            Map<String, Object> data = new HashMap<>();
            data.put("user_id", user.getId());
            response.put("data", data);
            return ResponseEntity.ok(response);
        } else {
            Map<String, Object> response = new HashMap<>();
            response.put("message", "login_fail");
            response.put("data", null);
            return ResponseEntity.ok(response);
        }
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(HttpSession session) {
        session.invalidate();
        Map<String, Object> response = new HashMap<>();
        response.put("message", "logout_success");
        return ResponseEntity.ok(response);
    }
}
