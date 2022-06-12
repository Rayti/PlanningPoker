package com.example.planningpoker.service;


import com.example.planningpoker.domain.User;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class DefaultUserService implements UserService {

    private List<User> registeredUsers = new ArrayList<>();
    private List<User> loggedUsers = new ArrayList<>();

    @Override
    public boolean registerUser(String name, String password) {
        if (userExists(name)) {
            return false;
        }

        //MD5Encoder.encode(password.getBytes(StandardCharsets.UTF_8))
        User user = new User(name, password);
        registeredUsers.add(user);
        return true;
    }

    @Override
    public boolean deleteUser(String name) {
        if(!userExists(name)){
            return false;
        }
        registeredUsers = registeredUsers.stream().filter(user -> !user.getName().equals(name)).collect(Collectors.toList());
        return true;
    }

    @Override
    public User getRegisteredUser(String name) {
        return registeredUsers.stream().filter(user -> user.getName().equals(name)).findFirst().orElse(null);
    }

    @Override
    public boolean userExists(String name) {
        return registeredUsers.stream().anyMatch(user -> user.getName().equals(name));
    }

    @Override
    public String logIn(String userName, String password) {
        Optional<User> optionalUser = registeredUsers.stream().filter(user -> user.getName().equals(userName) && user.getPassword().equals(password))
                .findFirst();

        if (optionalUser.isEmpty() || loggedUserExists(userName)){
            return "";
        }
        User user = optionalUser.get();
        user.setSessionId(UUID.randomUUID().toString());

        loggedUsers.add(user);
        return loggedUsers.stream().filter(filteredUser -> filteredUser.getName().equals(userName) && filteredUser.getPassword().equals(password))
                .map(User::getSessionId)
                .findFirst().orElse(null);
    }

    @Override
    public boolean logOut(String userName) {
        if(!loggedUserExists(userName)){
            return false;
        }
        loggedUsers = loggedUsers.stream().filter(user -> !user.getName().equals(userName)).collect(Collectors.toList());
        return true;
    }

    @Override
    public boolean checkSessionId(String sessionId, String userName) {
        return loggedUsers.stream().anyMatch(user -> user.getName().equals(userName) && user.getSessionId().equals(sessionId));
    }

    @Override
    public boolean loggedUserExists(String userName) {
        return loggedUsers.stream().anyMatch(user -> user.getName().equals(userName));
    }
}
