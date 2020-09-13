package com.teamrocket.fanlinc.builders;

import com.teamrocket.fanlinc.models.Fandom;
import com.teamrocket.fanlinc.models.Joined;
import com.teamrocket.fanlinc.models.User;

public class JoinedBuilder {

    private String level;
    private String type;
    private Fandom fandom;
    private User user;

    public JoinedBuilder level(String level){
        this.level = level;
        return this;
    }

    public JoinedBuilder type(String type){
        this.type = type;
        return this;
    }

    public JoinedBuilder fandom(Fandom fandom){
        this.fandom = fandom;
        return this;
    }

    public JoinedBuilder user(User user){
        this.user = user;
        return this;
    }

    public Joined build(){
        Joined newJoined = new Joined();
        newJoined.setType(this.type);
        newJoined.setLevel(this.level);
        newJoined.setFandom(this.fandom);
        newJoined.setUser(this.user);
        return newJoined;
    }

}
