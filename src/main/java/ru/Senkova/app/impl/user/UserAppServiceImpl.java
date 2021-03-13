package ru.Senkova.app.impl.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import ru.Senkova.adapter.hibernate.RoleRepository;
import ru.Senkova.adapter.hibernate.UserAppRepository;
import ru.Senkova.adapter.rest.service.dto.SignUpFormDto;
import ru.Senkova.app.api.UserAppService;
import ru.Senkova.domain.Role;
import ru.Senkova.domain.RoleName;
import ru.Senkova.domain.UserApp;
import ru.Senkova.exception.UserAppRegistrationException;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static ru.Senkova.exception.ResponseCodeException.LOGIN_ALREADY_EXISTS;
import static ru.Senkova.exception.ResponseCodeException.EMAIL_ALREADY_EXISTS;

@Service("userAppService")
public class UserAppServiceImpl implements UserAppService {

    @Autowired
    private UserAppRepository userAppRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public UserAppServiceImpl(UserAppRepository userAppRepository, RoleRepository roleRepository,
        BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userAppRepository = userAppRepository;
        this.roleRepository = roleRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    public UserApp findByLogin(String login) {
        return userAppRepository.findByLogin(login);
    }

    @Override
    public List<UserApp> allUsers() {
        return userAppRepository.findAll();
    }

    @Override
    public void createUser(SignUpFormDto dto) {
        String trimmedLoginInLowerCase = dto.getLogin().trim().toLowerCase();

        validateRegisteredLogin(trimmedLoginInLowerCase);
        validateRegisteredEmail(dto.getEmail());

        UserApp userApp = new UserApp();
        userApp.setLogin(trimmedLoginInLowerCase);
        userApp.setFirstName(dto.getFirstName());
        userApp.setPatronymic(dto.getPatronymic());
        userApp.setLastName(dto.getLastName());
        userApp.setEmail(dto.getEmail());
        userApp.setHashPassword(bCryptPasswordEncoder.encode(dto.getPassword()));
        //userApp.setRoles(transformationSetRole(RoleName.USER_ROLE));

        if(trimmedLoginInLowerCase.equals(RoleName.ADMIN_ROLE.getName()))
            userApp.setRoles(transformationSetRole(RoleName.USER_ROLE,RoleName.ADMIN_ROLE));
        else  userApp.setRoles(transformationSetRole(RoleName.USER_ROLE));

        userAppRepository.save(userApp);
    }

    @Override
    public void deleteUser(Long userId) {
        if (userAppRepository.findById(userId).isPresent()) {
            userAppRepository.deleteById(userId);
        }
    }

    // ===================================================================================================================
    // = Implementation
    // ===================================================================================================================


    private void validateRegisteredLogin(String login) {
        if (userAppRepository.existsByLogin(login)) {
            throw new UserAppRegistrationException(LOGIN_ALREADY_EXISTS);
        }
    }

    private void validateRegisteredEmail(String email) {
        if (userAppRepository.existsByEmail(email)) {
            throw new UserAppRegistrationException(EMAIL_ALREADY_EXISTS);
        }
    }

    private Set<Role> transformationSetRole(RoleName... roleNames) {
        Set<Role> registeredRoles = new HashSet<>();
        for (RoleName roleName : roleNames) {
            Role role = new Role();
            role.setName(roleName);
            registeredRoles.add(role);
        }
        return registeredRoles;
    }

}