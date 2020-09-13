package com.teamrocket.fanlinc.responses;

public class AddJoinedFandomResponse {

    private String type;
    private String level;

    public AddJoinedFandomResponse(String type, String level) {
        this.type = type;
        this.level = level;
    }

    public String getLevel() {
        return level;
    }

    public String getType() {
        return type;
    }
}
