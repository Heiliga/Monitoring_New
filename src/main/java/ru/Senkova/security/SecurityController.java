package ru.Senkova.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import ru.Senkova.adapter.rest.service.dto.LoginFormDto;
import ru.Senkova.adapter.rest.service.dto.SignUpFormDto;
import ru.Senkova.app.api.UserAppService;
import ru.Senkova.exception.UserAppRegistrationException;
import ru.Senkova.security.jwt.JwtProvider;
import ru.Senkova.security.jwt.JwtResponse;

import static ru.Senkova.exception.ResponseCodeException.INT_CODE_6_CONTOLLER_REGISTRATION;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/Monitoring/security")
public class SecurityController {

    private UserAppService userAppService;

    private final AuthenticationManager authenticationManager;

    private final JwtProvider jwtProvider;

    public SecurityController(AuthenticationManager authenticationManager, JwtProvider jwtProvider, UserAppService userAppService) {
        this.authenticationManager = authenticationManager;
        this.jwtProvider = jwtProvider;
        this.userAppService = userAppService;
    }

    @PostMapping("/login")
    public ResponseEntity<JwtResponse> authenticateEmployee(@RequestBody LoginFormDto loginFormDto) {
        String trimmedLoginInLowerCase = loginFormDto.getLogin().trim().toLowerCase();

        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(trimmedLoginInLowerCase, loginFormDto.getPassword());
        Authentication authentication = authenticationManager.authenticate(authToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwtToken = jwtProvider.generateJwtToken(authentication);

        return ResponseEntity.ok(new JwtResponse(jwtToken));
    }

    @PostMapping("/registration")
    public ResponseEntity<String> addUser(@RequestBody SignUpFormDto signUpRequest) {
        try {
            userAppService.createUser(
                    signUpRequest.getLogin(),
                    signUpRequest.getFirstName(),
                    signUpRequest.getPatronymic(),
                    signUpRequest.getLastName(),
                    signUpRequest.getPassword(),
                    signUpRequest.getEmail());
        } catch (UserAppRegistrationException e) {
            e = new UserAppRegistrationException(INT_CODE_6_CONTOLLER_REGISTRATION);
            return ResponseEntity.badRequest().body(e.getMessage());
        }

        return ResponseEntity.ok("User registered successfully!");
    }
}

