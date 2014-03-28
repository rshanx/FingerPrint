package com.Storchak.utils;


import com.Storchak.dto.FPTemplate;


public class FPTemplateMatcher {


    public static boolean compare(FPTemplate dataBaseTemplate, FPTemplate testTemplate) {

        int p = dataBaseTemplate.getFpTemplateEndPointList().size() + dataBaseTemplate.getFpTemplateBranchingPointList().size();
        int q = testTemplate.getFpTemplateEndPointList().size() + testTemplate.getFpTemplateBranchingPointList().size();
        int d = 0;
        for (int i = 0; i < dataBaseTemplate.getFpTemplateEndPointList().size(); i++) {
            for (int j = 0; j < testTemplate.getFpTemplateEndPointList().size(); j++) {
                if (((dataBaseTemplate.getFpTemplateEndPointList().get(i).getX() >= testTemplate.getFpTemplateEndPointList().get(j).getX()) && (dataBaseTemplate.getFpTemplateEndPointList().get(i).getX() <= testTemplate.getFpTemplateEndPointList().get(j).getX() - 20) && (dataBaseTemplate.getFpTemplateEndPointList().get(i).getY() >= testTemplate.getFpTemplateEndPointList().get(j).getY()) && (dataBaseTemplate.getFpTemplateEndPointList().get(i).getY() <= testTemplate.getFpTemplateEndPointList().get(j).getY() - 20)) ||
                        ((dataBaseTemplate.getFpTemplateEndPointList().get(i).getX() >= testTemplate.getFpTemplateEndPointList().get(j).getX()) && (dataBaseTemplate.getFpTemplateEndPointList().get(i).getX() <= testTemplate.getFpTemplateEndPointList().get(j).getX() - 20) && (dataBaseTemplate.getFpTemplateEndPointList().get(i).getY() >= testTemplate.getFpTemplateEndPointList().get(j).getY() - 20) && (dataBaseTemplate.getFpTemplateEndPointList().get(i).getY() <= testTemplate.getFpTemplateEndPointList().get(j).getY())) ||
                        ((dataBaseTemplate.getFpTemplateEndPointList().get(i).getX() >= testTemplate.getFpTemplateEndPointList().get(j).getX() - 20) && (dataBaseTemplate.getFpTemplateEndPointList().get(i).getX() <= testTemplate.getFpTemplateEndPointList().get(j).getX()) && (dataBaseTemplate.getFpTemplateEndPointList().get(i).getY() >= testTemplate.getFpTemplateEndPointList().get(j).getY() - 20) && (dataBaseTemplate.getFpTemplateEndPointList().get(i).getY() <= testTemplate.getFpTemplateEndPointList().get(j).getY())) ||
                        ((dataBaseTemplate.getFpTemplateEndPointList().get(i).getX() >= testTemplate.getFpTemplateEndPointList().get(j).getX() - 20) && (dataBaseTemplate.getFpTemplateEndPointList().get(i).getX() <= testTemplate.getFpTemplateEndPointList().get(j).getX()) && (dataBaseTemplate.getFpTemplateEndPointList().get(i).getY() >= testTemplate.getFpTemplateEndPointList().get(j).getY()) && (dataBaseTemplate.getFpTemplateEndPointList().get(i).getY() <= testTemplate.getFpTemplateEndPointList().get(j).getY() - 20))) {
                    d++;
                    break;
                }
            }
        }
        for (int i = 0; i < dataBaseTemplate.getFpTemplateBranchingPointList().size(); i++) {
            for (int j = 0; j < testTemplate.getFpTemplateBranchingPointList().size(); j++) {
                if (((dataBaseTemplate.getFpTemplateBranchingPointList().get(i).getX() >= testTemplate.getFpTemplateBranchingPointList().get(j).getX()) && (dataBaseTemplate.getFpTemplateBranchingPointList().get(i).getX() <= testTemplate.getFpTemplateBranchingPointList().get(j).getX() - 20) && (dataBaseTemplate.getFpTemplateBranchingPointList().get(i).getY() >= testTemplate.getFpTemplateBranchingPointList().get(j).getY()) && (dataBaseTemplate.getFpTemplateBranchingPointList().get(i).getY() <= testTemplate.getFpTemplateBranchingPointList().get(j).getY() - 20)) ||
                        ((dataBaseTemplate.getFpTemplateBranchingPointList().get(i).getX() >= testTemplate.getFpTemplateBranchingPointList().get(j).getX()) && (dataBaseTemplate.getFpTemplateBranchingPointList().get(i).getX() <= testTemplate.getFpTemplateBranchingPointList().get(j).getX() - 20) && (dataBaseTemplate.getFpTemplateBranchingPointList().get(i).getY() >= testTemplate.getFpTemplateBranchingPointList().get(j).getY() - 20) && (dataBaseTemplate.getFpTemplateBranchingPointList().get(i).getY() <= testTemplate.getFpTemplateBranchingPointList().get(j).getY())) ||
                        ((dataBaseTemplate.getFpTemplateBranchingPointList().get(i).getX() >= testTemplate.getFpTemplateBranchingPointList().get(j).getX() - 20) && (dataBaseTemplate.getFpTemplateBranchingPointList().get(i).getX() <= testTemplate.getFpTemplateBranchingPointList().get(j).getX()) && (dataBaseTemplate.getFpTemplateBranchingPointList().get(i).getY() >= testTemplate.getFpTemplateBranchingPointList().get(j).getY() - 20) && (dataBaseTemplate.getFpTemplateBranchingPointList().get(i).getY() <= testTemplate.getFpTemplateBranchingPointList().get(j).getY())) ||
                        ((dataBaseTemplate.getFpTemplateBranchingPointList().get(i).getX() >= testTemplate.getFpTemplateBranchingPointList().get(j).getX() - 20) && (dataBaseTemplate.getFpTemplateBranchingPointList().get(i).getX() <= testTemplate.getFpTemplateBranchingPointList().get(j).getX()) && (dataBaseTemplate.getFpTemplateBranchingPointList().get(i).getY() >= testTemplate.getFpTemplateBranchingPointList().get(j).getY()) && (dataBaseTemplate.getFpTemplateBranchingPointList().get(i).getY() <= testTemplate.getFpTemplateBranchingPointList().get(j).getY() - 20))) {
                    d++;
                    break;
                }
            }
        }
        if (d >= ((p + q) / 2) / 1.8) {
            return true;
        }
        return false;
    }
}
