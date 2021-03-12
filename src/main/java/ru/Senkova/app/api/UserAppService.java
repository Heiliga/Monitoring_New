package ru.Senkova.app.api;

import ru.Senkova.adapter.rest.service.dto.SignUpFormDto;
import ru.Senkova.domain.UserApp;

import java.util.List;
import java.util.Set;

public interface UserAppService {
    List<UserApp> allUsers();
    void createUser(SignUpFormDto dto);
    void deleteUser(Long userId);
    UserApp findByLogin(String login);
}
