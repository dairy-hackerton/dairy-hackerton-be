package dairy.dairybe.dto;

import lombok.Getter;
import org.springframework.stereotype.Service;

@Getter @Service
public class LoginDTO {
    private String email;
    private String password;
}
