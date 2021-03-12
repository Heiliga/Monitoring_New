package ru.Senkova.security;

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

import static ru.Senkova.exception.ResponseCodeException.CONTROLLER_REGISTRATION;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping(SecurityController.BASE_MAPPING)
public class SecurityController {


    //TODO: Переделать на бины

    protected static final String BASE_MAPPING = "/Monitoring/security";
    private static final String AUTHENTICATION = "/login";
    private static final String REGISTRATION = "/registration";

    private UserAppService userAppService;

    private final AuthenticationManager authenticationManager;

    private final JwtProvider jwtProvider;

    public SecurityController(AuthenticationManager authenticationManager, JwtProvider jwtProvider, UserAppService userAppService) {
        this.authenticationManager = authenticationManager;
        this.jwtProvider = jwtProvider;
        this.userAppService = userAppService;
    }

    @PostMapping(AUTHENTICATION)
    public ResponseEntity<JwtResponse> authenticateUser(@RequestBody LoginFormDto loginFormDto) {
        String trimmedLoginInLowerCase = loginFormDto.getLogin().trim().toLowerCase();

        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(trimmedLoginInLowerCase, loginFormDto.getPassword());

        Authentication authentication = authenticationManager.authenticate(authToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwtToken = jwtProvider.generateJwtToken(authentication);

        return ResponseEntity.ok(new JwtResponse(jwtToken));
    }

    @PostMapping(REGISTRATION)
    public ResponseEntity<String> addUserApp(@RequestBody SignUpFormDto signUpRequest) {
        try {
            userAppService.createUser(signUpRequest);
        } catch (UserAppRegistrationException e) {
            e = new UserAppRegistrationException(CONTROLLER_REGISTRATION);
            return ResponseEntity.badRequest().body(e.getMessage());
        }

        return ResponseEntity.ok("User registered successfully!");
    }
}

