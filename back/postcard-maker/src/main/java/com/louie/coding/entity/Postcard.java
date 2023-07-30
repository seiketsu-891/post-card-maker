package com.louie.coding.entity;

import java.util.Date;

public class Postcard {
    private Boolean undoFlag = false;
    private Boolean redoFlag = false;
    private Long id;
    private Long userId;
    private String name;
    private Date createTime;
    private Date updateTime;
    private Long currVersion;
    private String currContent;
    private String undoHistory;
    private String redoHistory;

    private String snapshot;

    public Postcard() {
    }

    public Postcard(Long id) {
        this.id = id;
    }

    public Boolean getUndoFlag() {
        return undoFlag;
    }

    public Boolean getRedoFlag() {
        return redoFlag;
    }

    public String getUndoHistory() {
        return undoHistory;
    }

    public void setUndoHistory(String undoHistory) {
        this.undoHistory = undoHistory;
    }

    public String getRedoHistory() {
        return redoHistory;
    }

    public void setRedoHistory(String redoHistory) {
        this.redoHistory = redoHistory;
    }

    public String getCurrContent() {
        return currContent;
    }

    public void setCurrContent(String currContent) {
        this.currContent = currContent;
    }

    public Long getCurrVersion() {
        return currVersion;
    }

    public void setCurrVersion(Long currVersion) {
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

    public boolean isUndoFlag() {
        return undoFlag;
    }

    public void setUndoFlag(Boolean undoFlag) {
        this.undoFlag = undoFlag;
    }

    public void setUndoFlag(boolean undoFlag) {
        this.undoFlag = undoFlag;
    }

    public String getSnapshot() {
        return snapshot;
    }

    public void setSnapshot(String snapshot) {
        this.snapshot = snapshot;
    }

    public boolean isRedoFlag() {
        return redoFlag;
    }

    public void setRedoFlag(Boolean redoFlag) {
        this.redoFlag = redoFlag;
    }

    public void setRedoFlag(boolean redoFlag) {
        this.redoFlag = redoFlag;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

}
