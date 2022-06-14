package com.example.planningpoker.domain;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Data
public class User {
    private String name;
    private String password;
    @Getter
    @Setter
    private String sessionId;
    private List<Room> roomGamesHistory;

    public User(String name, String password) {
        this.name = name;
        this.password = password;
        this.roomGamesHistory = new ArrayList<>();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return name.equals(user.name) && password.equals(user.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, password);
    }
}
