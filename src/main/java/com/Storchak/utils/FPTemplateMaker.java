package com.Storchak.utils;


import com.Storchak.dto.FPTemplate;
import com.Storchak.dto.FPTemplateBranchingPoint;
import com.Storchak.dto.FPTemplateEndPoint;
import com.Storchak.dto.User;

import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FPTemplateMaker {

    public static FPTemplate createTemplate(int[] filteredImageData, User user) throws IOException {
        List<Integer> endPointX = new ArrayList<Integer>();
        List<Integer> endPointY = new ArrayList<Integer>();
        List<Integer> branchingPointX = new ArrayList<Integer>();
        List<Integer> branchingPointY = new ArrayList<Integer>();
        BufferedImage image = new BufferedImage(152, 200,
                BufferedImage.TYPE_BYTE_GRAY);
        WritableRaster imageRaster = image.getRaster();
        int counter = 0;

        for (int i = 0; i < 152; i++)
            for (int j = 0; j < 200; j++) {
                imageRaster.setSample(i, j, 0, filteredImageData[counter]);
                counter++;
            }

        for (int i = 35; i < image.getWidth() - 35; i++)
            for (int j = 35; j < image.getHeight() - 35; j++) {
                if ((i + 5 < image.getWidth()) && (i - 5 >= 0) && (j + 5 < image.getHeight()) && (j - 5 >= 0)) {
                    if (imageRaster.getSample(i, j, 0) == 0) {
                        int pointCounter = 0;
                        for (int k = -1; k <= 1; k++) {
                            for (int m = -1; m <= 1; m++) {
                                if ((k != 0) || (m != 0)) {
                                    if (imageRaster.getSample(i + k, j + m, 0) == 0) {
                                        pointCounter++;
                                    }
                                }
                            }
                        }
                        if (pointCounter == 3) {
                            branchingPointX.add(i);
                            branchingPointY.add(j);
                        }
                        if (pointCounter == 1) {
                            endPointX.add(i);
                            endPointY.add(j);
                        }

                    }
                }
            }

        if ((endPointX.size() != 0) && (endPointY.size() != 0)) {
            for (int i = 0; i < endPointX.size(); i++) {
                for (int j = 0; j < endPointX.size(); j++) {
                    if (j != i) {
                        if (((endPointX.get(j) >= endPointX.get(i)) && (endPointX.get(j) <= endPointX.get(i) + 8) && (endPointY.get(j) >= endPointY.get(i)) && (endPointY.get(j) <= endPointY.get(i) + 8)) ||
                                ((endPointX.get(j) >= endPointX.get(i)) && (endPointX.get(j) <= endPointX.get(i) + 8) && (endPointY.get(j) >= endPointY.get(i) - 8) && (endPointY.get(j) <= endPointY.get(i))) ||
                                ((endPointX.get(j) >= endPointX.get(i) - 8) && (endPointX.get(j) <= endPointX.get(i)) && (endPointY.get(j) >= endPointY.get(i) - 8) && (endPointY.get(j) <= endPointY.get(i))) ||
                                ((endPointX.get(j) >= endPointX.get(i) - 8) && (endPointX.get(j) <= endPointX.get(i)) && (endPointY.get(j) >= endPointY.get(i)) && (endPointY.get(j) <= endPointY.get(i) + 8))) {
                            endPointX.remove(j);
                            endPointY.remove(j);
                            j--;
                        }

                    }
                }
            }
        }

        for (int i = 0; i < branchingPointX.size(); i++) {
            for (int j = 0; j < branchingPointY.size(); j++) {
                if (j != i) {
                    if (((branchingPointX.get(j) >= branchingPointX.get(i)) && (branchingPointX.get(j) <= branchingPointX.get(i) + 8) && (branchingPointY.get(j) >= branchingPointY.get(i)) && (branchingPointY.get(j) <= branchingPointY.get(i) + 8)) ||
                            ((branchingPointX.get(j) >= branchingPointX.get(i)) && (branchingPointX.get(j) <= branchingPointX.get(i) + 8) && (branchingPointY.get(j) >= branchingPointY.get(i) - 8) && (branchingPointY.get(j) <= branchingPointY.get(i))) ||
                            ((branchingPointX.get(j) >= branchingPointX.get(i) - 8) && (branchingPointX.get(j) <= branchingPointX.get(i)) && (branchingPointY.get(j) >= branchingPointY.get(i) - 8) && (branchingPointY.get(j) <= branchingPointY.get(i))) ||
                            ((branchingPointX.get(j) >= branchingPointX.get(i) - 8) && (branchingPointX.get(j) <= branchingPointX.get(i)) && (branchingPointY.get(j) >= branchingPointY.get(i)) && (branchingPointY.get(j) <= branchingPointY.get(i) + 8))) {
                        branchingPointX.remove(j);
                        branchingPointY.remove(j);
                        j--;
                    }
                }
            }
        }

        for (int i = 0; i < endPointX.size(); i++)
            for (int j = 0; j < branchingPointX.size(); j++) {
                if ((endPointX.size() <= 0) || (endPointY.size() <= 0) || (branchingPointX.size() <= 0) || (branchingPointY.size() <= 0)) {
                    if (((endPointX.get(i) >= branchingPointX.get(j)) && (endPointX.get(i) <= branchingPointX.get(j) + 5) && (endPointY.get(i) >= branchingPointY.get(j)) && (endPointY.get(i) <= branchingPointY.get(j) + 5)) ||
                            ((endPointX.get(i) >= branchingPointX.get(j)) && (endPointX.get(i) <= branchingPointX.get(j) + 5) && (endPointY.get(i) >= branchingPointY.get(j) - 5) && (endPointY.get(i) <= branchingPointY.get(j))) ||
                            ((endPointX.get(i) >= branchingPointX.get(j) - 5) && (endPointX.get(i) <= branchingPointX.get(j)) && (endPointY.get(i) >= branchingPointY.get(j) - 8) && (endPointY.get(i) <= branchingPointY.get(j))) ||
                            ((endPointX.get(i) >= branchingPointX.get(j) - 5) && (endPointX.get(i) <= branchingPointX.get(j)) && (endPointY.get(i) >= branchingPointY.get(j)) && (endPointY.get(i) <= branchingPointY.get(j) + 5))) {
                        endPointX.remove(i);
                        endPointY.remove(i);
                        branchingPointX.remove(j);
                        branchingPointY.remove(j);
                        j--;
                    }
                }

            }

        List<FPTemplateEndPoint> fpTemplateEndPoint = new ArrayList<FPTemplateEndPoint>();
        List<FPTemplateBranchingPoint> fpTemplateBranchingPoint = new ArrayList<FPTemplateBranchingPoint>();
        for (int i = 0; i < endPointX.size(); i++) {
            fpTemplateEndPoint.add(new FPTemplateEndPoint(endPointX.get(i), endPointY.get(i)));
        }
        for (int i = 0; i < branchingPointX.size(); i++) {
            fpTemplateBranchingPoint.add(new FPTemplateBranchingPoint(branchingPointX.get(i), branchingPointY.get(i)));
        }
        if (user != null) {
            return new FPTemplate(user, fpTemplateEndPoint, fpTemplateBranchingPoint);
        } else {
            return new FPTemplate(fpTemplateEndPoint, fpTemplateBranchingPoint);
        }
    }
}
