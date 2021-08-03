package ru.cft.dao;

import ru.cft.model.User;

import java.util.Optional;

public interface UsersDAO {

    Optional<User> getUserByLogin(String login);
    Optional<User> getAllUsers();
}
