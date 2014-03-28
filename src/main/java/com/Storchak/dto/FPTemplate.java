package com.Storchak.dto;


import javax.persistence.*;
import java.util.List;

@Entity
public class FPTemplate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @ManyToOne
    private User user;
    @OneToMany
    @JoinColumn(name = "FPTEMPLATE_ID")
    List<FPTemplateEndPoint> fpTemplateEndPointList;
    @OneToMany
    @JoinColumn(name = "FPTEMPLATE_ID")
    List<FPTemplateBranchingPoint> fpTemplateBranchingPointList;

    public FPTemplate(User user, List<FPTemplateEndPoint> fpTemplateEndPointList, List<FPTemplateBranchingPoint> fpTemplateBranchingPointList) {
        this.user = user;
        this.fpTemplateEndPointList = fpTemplateEndPointList;
        this.fpTemplateBranchingPointList = fpTemplateBranchingPointList;
    }

    public FPTemplate(List<FPTemplateEndPoint> fpTemplateEndPointList, List<FPTemplateBranchingPoint> fpTemplateBranchingPointList) {
        this.fpTemplateEndPointList = fpTemplateEndPointList;
        this.fpTemplateBranchingPointList = fpTemplateBranchingPointList;
    }

    public FPTemplate() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<FPTemplateEndPoint> getFpTemplateEndPointList() {
        return fpTemplateEndPointList;
    }

    public void setFpTemplateEndPointList(List<FPTemplateEndPoint> fpTemplateEndPointList) {
        this.fpTemplateEndPointList = fpTemplateEndPointList;
    }

    public List<FPTemplateBranchingPoint> getFpTemplateBranchingPointList() {
        return fpTemplateBranchingPointList;
    }

    public void setFpTemplateBranchingPointList(List<FPTemplateBranchingPoint> fpTemplateBranchingPointList) {
        this.fpTemplateBranchingPointList = fpTemplateBranchingPointList;
    }
}
