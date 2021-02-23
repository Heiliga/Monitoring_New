package ru.Senkova.app.impl.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import ru.Senkova.adapter.hibernate.RoleRepository;
import ru.Senkova.adapter.hibernate.UserAppRepository;
import ru.Senkova.app.api.UserAppService;
import ru.Senkova.domain.Role;
import ru.Senkova.domain.RoleName;
import ru.Senkova.domain.UserApp;
import ru.Senkova.exception.UserAppRegistrationException;

import java.util.*;

import static ru.Senkova.exception.ResponseCodeException.*;

@Service ("userAppService")
public class UserAppServiceImpl implements UserAppService {

    @Autowired
    private UserAppRepository userAppRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public UserApp findByLogin(String login){
        return userAppRepository.findByLogin(login);
    }

    @Override
    public UserApp findUserById(Long userId) {
        Optional<UserApp> userFromDb = userAppRepository.findById(userId);
        return userFromDb.orElse(new UserApp());
    }

    @Override
    public List<UserApp> allUsers() {
        return userAppRepository.findAll();
    }

    @Override
    public boolean createUser(String login, String firstName, String patronymic, String lastName, String password, String email) {
        String trimmedLoginInLowerCase = login.trim().toLowerCase();

        validateRegisteredLogin(trimmedLoginInLowerCase);
        validateRegisteredEmail(email);

        UserApp user = new UserApp(trimmedLoginInLowerCase, firstName, patronymic, lastName, bCryptPasswordEncoder.encode(password), email);

        //метод должен быть заменен в случае расширения ролей у пользователей
        if(trimmedLoginInLowerCase == "admin")
            transformationSetRole(RoleName.USER_ROLE,RoleName.ADMIN_ROLE);
        else
            transformationSetRole(RoleName.USER_ROLE);

        userAppRepository.save(user);

        List<UserApp> users = allUsers();
        for (UserApp element : users){
            if(element.getLogin()==trimmedLoginInLowerCase)
                return true;
        }
        return false;
    }

    @Override
    public boolean deleteUser(Long userId) {
        if (userAppRepository.findById(userId).isPresent()) {
            userAppRepository.deleteById(userId);
            return true;
        }
        return false;
    }

    // ===================================================================================================================
    // = Implementation
    // ===================================================================================================================


    private void validateRegisteredLogin(String login) {
        if (userAppRepository.existsByLogin(login)) {
            throw new UserAppRegistrationException(INT_CODE_1_LOGIN_ALREADY_EXISTS);
        }
    }

    private void validateRegisteredEmail(String email) {
        if (userAppRepository.existsByEmail(email)) {
            throw new UserAppRegistrationException(INT_CODE_2_EMAIL_ALREADY_EXISTS);
        }
    }

    private Set<Role> transformationSetRole (RoleName... roleNames){
        Set <Role> registeredRoles = new HashSet<>();
        for(RoleName roleName : roleNames){
            Role role = new Role();
            role.setName(roleName);
            registeredRoles.add(role);
        }
        return registeredRoles;
    }
/*    private Set<Role> validateAndGetRegisteredRoles(Set<String> rolesStrings) {
        Set<Role> registeredRoles = new HashSet<>();

        rolesStrings.forEach(roleString -> {
            RoleName registeredRoleName = extractRoleNameFromRoleString(roleString);
            Role registeredRole = roleRepository.findByName(registeredRoleName)
                    .orElseThrow(() -> new UserAppRegistrationException(INT_CODE_5_NOT_NAME_WITH_ROLE_DB));
            registeredRoles.add(registeredRole);
        });

        return registeredRoles;
    }

    private RoleName extractRoleNameFromRoleString(String roleString) {
        switch (roleString.trim().toLowerCase()) {
            case "admin":
                return RoleName.ADMIN_ROLE;
            case "user":
                return RoleName.USER_ROLE;
            default:
                throw new UserAppRegistrationException(INT_CODE_4_INVALID_ROLE_REGISTRATION);
        }
    }*/

}