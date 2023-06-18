package com.louie.coding.entity;

import java.util.Date;

public class Postcard {
    private Long id;
    private Long userId;
    private String name;
    private Date createTime;
    private Date updateTime;

    private Integer currVersion;
    private String currContent;

    public Postcard() {
    }

    public Postcard(Long id) {
        this.id = id;
    }

    public String getCurrContent() {
        return currContent;
    }

    public void setCurrContent(String currContent) {
        this.currContent = currContent;
    }

    public Integer getCurrVersion() {
        return currVersion;
    }

    public void setCurrVersion(Integer currVersion) {
        this.currVersion = currVersion;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

}
