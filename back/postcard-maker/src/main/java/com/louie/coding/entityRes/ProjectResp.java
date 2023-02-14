package com.louie.coding.entityRes;

import java.util.List;

public class ProjectResp {
    private Long id;
    private String name;
    private CanvasResp canvas;
    private List<EleShapeResp> shapes;
    private List<EleIllustrationResp> illustrations;
    private List<EleTextResp> texts;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public CanvasResp getCanvas() {
        return canvas;
    }

    public void setCanvas(CanvasResp canvas) {
        this.canvas = canvas;
    }

    public List<EleShapeResp> getShapes() {
        return shapes;
    }

    public void setShapes(List<EleShapeResp> shapes) {
        this.shapes = shapes;
    }

    public List<EleIllustrationResp> getIllustrations() {
        return illustrations;
    }

    public void setIllustrations(List<EleIllustrationResp> illustrations) {
        this.illustrations = illustrations;
    }

    public List<EleTextResp> getTexts() {
        return texts;
    }

    public void setTexts(List<EleTextResp> texts) {
        this.texts = texts;
    }
}
