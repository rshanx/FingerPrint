package com.Storchak.dto;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class FPTemplateEndPoint {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private int x;
    private int y;

    public FPTemplateEndPoint(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public FPTemplateEndPoint() {
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
