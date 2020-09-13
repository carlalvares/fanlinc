package com.teamrocket.fanlinc.requests;

import javax.validation.constraints.NotEmpty;

public class LeaveFandomRequest {
    @NotEmpty
    private String fandomName;

    @NotEmpty
    private String username;

    public LeaveFandomRequest(String fandomName, String username) {
        this.fandomName = fandomName;
        this.username = username;
    }

    public String getFandomName() {
        return fandomName;
    }

    public String getUsername() {
        return username;
    }
}
