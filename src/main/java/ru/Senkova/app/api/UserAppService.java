package ru.Senkova.app.api;

import ru.Senkova.domain.UserApp;

import java.util.List;
import java.util.Set;

public interface UserAppService {
    List<UserApp> allUsers();
    boolean createUser(String login, String firstName, String patronymic, String lastName, String password, String email);
    boolean deleteUser(Long userId);
    UserApp findUserById(Long userId);
    UserApp findByLogin(String login);



}
