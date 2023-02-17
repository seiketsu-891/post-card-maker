package com.louie.coding.entity;

import java.util.Date;
import java.util.List;

public class Project {
    private Long id;
    private Long userId;
    private String name;
    private Date createTime;
    private Date updateTime;
    private Canvas canvas;
    private List<Element> elements;

    //    private List<EleShape> shapes;
//    private List<EleIllustration> illustrations;
//    private List<EleText> texts;
    public Project(Long id) {
        this.id = id;
    }

    public List<Element> getElements() {
        return elements;
    }

    public void setElements(List<Element> elements) {
        this.elements = elements;
    }

    public Canvas getCanvas() {
        return canvas;
    }

    public void setCanvas(Canvas canvas) {
        this.canvas = canvas;
    }

//    public List<EleShape> getShapes() {
//        return shapes;
//    }
//
//    public void setShapes(List<EleShape> shapes) {
//        this.shapes = shapes;
//    }
//
//    public List<EleIllustration> getIllustrations() {
//        return illustrations;
//    }
//
//    public void setIllustrations(List<EleIllustration> illustrations) {
//        this.illustrations = illustrations;
//    }
//
//    public List<EleText> getTexts() {
//        return texts;
//    }
//
//    public void setTexts(List<EleText> texts) {
//        this.texts = texts;
//    }

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
