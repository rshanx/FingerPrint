package com.Storchak.utils;


import Catalano.Imaging.FastBitmap;
import Catalano.Imaging.Filters.Blur;
import Catalano.Imaging.Filters.Convolution;
import Catalano.Imaging.Filters.GaborFilter;
import Catalano.Imaging.Filters.RosinThreshold;
import Catalano.Imaging.Tools.ConvolutionKernel;

import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class FPFilter {

    public static int[] filterImage(int[] imageData) throws IOException {

        BufferedImage image = new BufferedImage(152, 200,
                BufferedImage.TYPE_BYTE_GRAY);
        WritableRaster newImageRaster = image.getRaster();
        int counter = 0;
        for (int j = 199; j >= 0; j--)
            for (int i = 0; i <= 151; i++) {
                newImageRaster.setSample(i, j, 0, imageData[counter]);
                counter++;
            }
        FastBitmap bit = new FastBitmap(image);
        Blur blur = new Blur();
        blur.applyInPlace(bit);
        Convolution convolution = new Convolution(ConvolutionKernel.LaplacianOfGaussian);
        convolution.applyInPlace(bit);
        RosinThreshold ros = new RosinThreshold();
        ros.applyInPlace(bit);
        List<FastBitmap> images = new ArrayList<FastBitmap>();
        for (int k = 0; k < 152; k += 19)
            for (int l = 0; l < 200; l += 20) {
                BufferedImage tempImage = new BufferedImage(19, 20, BufferedImage.TYPE_BYTE_GRAY);
                WritableRaster image1Raster = tempImage.getRaster();
                for (int i = 0; i < 19; i++)
                    for (int j = 0; j < 20; j++) {
                        image1Raster.setSample(i, j, 0, newImageRaster.getSample(i + k, j + l, 0));
                    }
                FastBitmap newBitmap = new FastBitmap(tempImage);
                images.add(newBitmap);
            }
        for (int i = 0; i < images.size(); i++) {
            BufferedImage newBit = images.get(i).toBufferedImage();
            WritableRaster r = newBit.getRaster();
            int[][] imageForOri = new int[19][20];
            for (int i1 = 0; i1 < 19; i1++)
                for (int j1 = 0; j1 < 20; j1++) {
                    imageForOri[i1][j1] = r.getSample(i1, j1, 0);
                }

            FastBitmap newBitmap = new FastBitmap(newBit);
            double[][] ori;
            ori = FilterLibrary.orientation(imageForOri);
            double sum = 0;
            List<Double> neg = new ArrayList<Double>();
            List<Double> pos = new ArrayList<Double>();
            List<Double> zero = new ArrayList<Double>();
            for (double[] x : ori) {
                for (double x1 : x) {
                    if (x1 < 0) neg.add(x1);
                    if (x1 > 0) pos.add(x1);
                    if (x1 == 0) zero.add(x1);
                }
            }
            double negSum = 0;
            double posSum = 0;
            for (Double x : neg) {
                negSum += x;
            }
            for (Double x : pos) {
                posSum += x;
            }

            sum = (negSum + posSum) / (neg.size() + pos.size() + zero.size());
            if ((Math.toDegrees(posSum / pos.size()) - Math.toDegrees(negSum / neg.size())) > 200) {
                if (Math.toDegrees(posSum / pos.size()) > 120) {
                    sum = (negSum + posSum) / (neg.size() + pos.size() + zero.size());
                    if (Math.toDegrees(sum) < 0) {
                        sum = Math.toRadians(90);
                    } else {
                        sum = Math.toRadians(110) - sum;
                    }
                } else {
                    if (Math.toDegrees(sum) < 0) {
                        sum = Math.toRadians(90) + sum * (-1);
                    } else {
                        sum = Math.toRadians(90) + sum;
                    }
                }
            }
            sum *= -1;
            GaborFilter gaborFilter = new GaborFilter(5, sum, 7);
            gaborFilter.setAspectRatio(0.9);
            gaborFilter.applyInPlace(newBitmap);
            images.set(i, newBitmap);
        }

        BufferedImage finalImage = new BufferedImage(152, 200, BufferedImage.TYPE_BYTE_GRAY);
        WritableRaster finalImageRaster = finalImage.getRaster();
        int imCount = 0;
        for (int k = 0; k < 152; k += 19)
            for (int l = 0; l < 200; l += 20) {
                for (int i = 0; i < 19; i++)
                    for (int j = 0; j < 20; j++) {
                        finalImageRaster.setSample(i + k, j + l, 0, images.get(imCount).toBufferedImage().getRaster().getSample(i, j, 0));
                    }
                imCount++;
            }

        FastBitmap bit1 = new FastBitmap(finalImage);
        blur.applyInPlace(bit1);
        convolution = new Convolution(ConvolutionKernel.LaplacianOfGaussian);
        convolution.applyInPlace(bit1);
        blur.applyInPlace(bit1);
        convolution.applyInPlace(bit1);
        ros.applyInPlace(bit1);
        image = bit1.toBufferedImage();
        skeletonization(image);
        newImageRaster = image.getRaster();
        counter = 0;
        for (
                int i = 0;
                i < 152; i++)
            for (
                    int j = 0;
                    j < 200; j++)

            {
                imageData[counter] = newImageRaster.getSample(i, j, 0);
                counter++;
            }


        return imageData;
    }

    private static BufferedImage skeletonization(BufferedImage image) {
        BufferedImage tempImage = image;
        WritableRaster newImageRaster = tempImage.getRaster();
        boolean pixelDelete;
        do {
            pixelDelete = FilterLibrary.filt1(152, 200, newImageRaster);
        } while (pixelDelete);
        do {
            pixelDelete = FilterLibrary.filt2(152, 200, newImageRaster);
        } while (pixelDelete);
        do {
            pixelDelete = FilterLibrary.filt1(152, 200, newImageRaster);
        } while (pixelDelete);
        do {
            pixelDelete = FilterLibrary.filt2(152, 200, newImageRaster);
        } while (pixelDelete);
        return tempImage;
    }


}
