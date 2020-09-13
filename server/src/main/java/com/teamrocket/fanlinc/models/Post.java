package com.teamrocket.fanlinc.models;


import org.neo4j.ogm.annotation.GeneratedValue;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.typeconversion.DateLong;

import java.util.Date;

@NodeEntity
public class Post {

    @GeneratedValue
    @Id
    private Long id;
    private String title;
    private String content;
    private String level;
    private String type;
    private String postedBy;
    private String fandomName;
    @DateLong
    private Date postedTime;
    // private String displayPhotoUrl;
    private String postPhotoUrl;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPostedBy() {
        return postedBy;
    }

    public void setPostedBy(String postedBy) {
        this.postedBy = postedBy;
    }

    public String getFandomName() {
        return fandomName;
    }

    public void setFandomName(String fandomName) {
        this.fandomName = fandomName;
    }

    public Date getPostedTime() {
        return postedTime;
    }

    public void setPostedTime(Date postedTime) {
        this.postedTime = postedTime;
    }

    public String getPostPhotoUrl() {
        return postPhotoUrl;
    }

    public void setPostPhotoUrl(String postPhotoUrl) {
        this.postPhotoUrl = postPhotoUrl;
    }
}
