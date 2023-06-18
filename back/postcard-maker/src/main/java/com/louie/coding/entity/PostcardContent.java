package com.louie.coding.entity;

import java.util.Date;

public class PostcardContent {
    private Long id;
    private Long postcardId;
    private Date createTime;
    private String content;
    private String snapshot;
    private Integer version;

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public String getSnapshot() {
        return snapshot;
    }

    public void setSnapshot(String snapshot) {
        this.snapshot = snapshot;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getPostcardId() {
        return postcardId;
    }

    public void setPostcardId(Long postcardId) {
        this.postcardId = postcardId;
    }
    
    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
