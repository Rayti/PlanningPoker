package com.example.planningpoker.domain;

import java.util.ArrayList;
import java.util.List;

public class User {

    final String nick;
    private List<Task> estimatedTask;

    public User(String nick){
        this.nick = nick;
        this.estimatedTask = new ArrayList<Task>();
    }

}
