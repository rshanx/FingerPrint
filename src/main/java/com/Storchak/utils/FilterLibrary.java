package com.Storchak.utils;

import java.awt.image.WritableRaster;

public class FilterLibrary {


    public static double[][] orientation(int[][] din) {
        double[][] dout = new double[din.length][din[0].length];

        int[][] xgrad = xgradient(din);
        int[][] ygrad = ygradient(din);

        for (int j = 0; j < din.length; j++) {
            for (int i = 0; i < din[0].length; i++) {
                dout[j][i] = Math.atan2((double) xgrad[j][i], (double) ygrad[j][i]);
                //System.out.println(i+","+j+" >> " + dout[j][i] + "  >> " + xgrad[j][i] + " >>> " + ygrad[j][i]);
            }
        }
        return dout;
    }

    public static int[][] xgradient(int[][] din) {
        int[][] sobelX = {{-1, 0, 1}, {-2, 0, 2}, {-1, 0, 1}};

        return convolution2D(din, sobelX);
    }

    public static int[][] ygradient(int[][] din) {
        int[][] sobelY = {{-1, -2, -1}, {0, 0, 0}, {+1, +2, +1}};

        return convolution2D(din, sobelY);
    }

    public static int[][] convolution2D(int[][] image, int[][] kernel) {
        double[][] tf = new double[image.length + 2][image[0].length + 2];


        for (int j = 0; j < tf.length; j++) {
            for (int i = 0; i < tf[0].length; i++) {
                //where (i,j) is the center point of the kernel
                for (int v = 0; v < kernel.length; v++) {
                    for (int u = 0; u < kernel[0].length; u++) {
                        int im_i = i - 2 + u;
                        int im_j = j - 2 + v;
                        if (im_i >= 0 && im_i < image[0].length && im_j >= 0 && im_j < image.length)
                            tf[j][i] += image[im_j][im_i] * kernel[v][u];
                    }
                }

            }

        }

        int[][] ret = new int[image.length][image[0].length];
        for (int j = 1; j < tf.length - 1; j++)
            for (int i = 1; i < tf[0].length - 1; i++)
                ret[j - 1][i - 1] = (int) tf[j][i];

        return ret;
    }

    public static boolean filt1(int w, int h, WritableRaster newImageRaster) {
        boolean result = false;
        for (int i = 10; i < w - 10; i++)
            for (int j = 10; j < h - 10; j++) {
                if ((j + 1 < h) && (j - 1 >= 0) && (i + 1 < w) && (i - 1 >= 0)) {
                    if (newImageRaster.getSample(i, j, 0) == 0) {
                        if ((newImageRaster.getSample(i - 1, j, 0) == 0) && (newImageRaster.getSample(i, j + 1, 0) == 0) &&
                                (newImageRaster.getSample(i, j - 1, 0) == 255) && (newImageRaster.getSample(i + 1, j, 0) == 255) &&
                                (newImageRaster.getSample(i + 1, j - 1, 0) == 255)) {
                            result = true;
                            newImageRaster.setSample(i, j, 0, 255);
                        }
                        if ((newImageRaster.getSample(i - 1, j, 0) == 0) && (newImageRaster.getSample(i, j - 1, 0) == 0) &&
                                (newImageRaster.getSample(i, j + 1, 0) == 255) && (newImageRaster.getSample(i + 1, j, 0) == 255) &&
                                (newImageRaster.getSample(i + 1, j + 1, 0) == 255)) {
                            result = true;
                            newImageRaster.setSample(i, j, 0, 255);
                        }
                        if ((newImageRaster.getSample(i, j - 1, 0) == 0) && (newImageRaster.getSample(i + 1, j, 0) == 0) &&
                                (newImageRaster.getSample(i - 1, j, 0) == 255) && (newImageRaster.getSample(i, j + 1, 0) == 255) &&
                                (newImageRaster.getSample(i - 1, j + 1, 0) == 255)) {
                            result = true;
                            newImageRaster.setSample(i, j, 0, 255);
                        }
                        if ((newImageRaster.getSample(i, j + 1, 0) == 0) && (newImageRaster.getSample(i + 1, j, 0) == 0) &&
                                (newImageRaster.getSample(i - 1, j, 0) == 255) && (newImageRaster.getSample(i, j - 1, 0) == 255) &&
                                (newImageRaster.getSample(i - 1, j - 1, 0) == 255)) {
                            result = true;
                            newImageRaster.setSample(i, j, 0, 255);
                        }
                        if ((newImageRaster.getSample(i - 1, j, 0) == 255) && (newImageRaster.getSample(i - 1, j - 1, 0) == 255) &&
                                (newImageRaster.getSample(i, j - 1, 0) == 255) && (newImageRaster.getSample(i + 1, j - 1, 0) == 255) &&
                                (newImageRaster.getSample(i + 1, j, 0) == 255) && (newImageRaster.getSample(i + 1, j + 1, 0) == 255) &&
                                (newImageRaster.getSample(i, j + 1, 0) == 255) && (newImageRaster.getSample(i - 1, j + 1, 0) == 255)) {
                            newImageRaster.setSample(i, j, 0, 255);
                            result = true;
                        }
                        if ((newImageRaster.getSample(i - 1, j, 0) == 255) && (newImageRaster.getSample(i - 1, j - 1, 0) == 255) &&
                                (newImageRaster.getSample(i, j - 1, 0) == 255) && (newImageRaster.getSample(i + 1, j - 1, 0) == 255) &&
                                (newImageRaster.getSample(i + 1, j, 0) == 255) && (newImageRaster.getSample(i + 1, j + 1, 0) == 0) &&
                                (newImageRaster.getSample(i, j + 1, 0) == 0) && (newImageRaster.getSample(i - 1, j + 1, 0) == 0)) {
                            newImageRaster.setSample(i, j, 0, 255);
                            result = true;
                        }
                        if ((newImageRaster.getSample(i - 1, j, 0) == 255) && (newImageRaster.getSample(i - 1, j - 1, 0) == 255) &&
                                (newImageRaster.getSample(i, j - 1, 0) == 255) && (newImageRaster.getSample(i + 1, j - 1, 0) == 255) &&
                                (newImageRaster.getSample(i + 1, j, 0) == 255) && (newImageRaster.getSample(i + 1, j + 1, 0) == 255) &&
                                (newImageRaster.getSample(i, j + 1, 0) == 0) && (newImageRaster.getSample(i - 1, j + 1, 0) == 0)) {
                            newImageRaster.setSample(i, j, 0, 255);
                            result = true;
                        }
                        if ((newImageRaster.getSample(i - 1, j, 0) == 255) && (newImageRaster.getSample(i - 1, j - 1, 0) == 255) &&
                                (newImageRaster.getSample(i, j - 1, 0) == 255) && (newImageRaster.getSample(i + 1, j - 1, 0) == 255) &&
                                (newImageRaster.getSample(i + 1, j, 0) == 255) && (newImageRaster.getSample(i + 1, j + 1, 0) == 0) &&
                                (newImageRaster.getSample(i, j + 1, 0) == 0) && (newImageRaster.getSample(i - 1, j + 1, 0) == 255)) {
                            newImageRaster.setSample(i, j, 0, 255);
                            result = true;
                        }


                        if ((newImageRaster.getSample(i - 1, j, 0) == 0) && (newImageRaster.getSample(i - 1, j - 1, 0) == 0) &&
                                (newImageRaster.getSample(i, j - 1, 0) == 255) && (newImageRaster.getSample(i + 1, j - 1, 0) == 255) &&
                                (newImageRaster.getSample(i + 1, j, 0) == 255) && (newImageRaster.getSample(i + 1, j + 1, 0) == 255) &&
                                (newImageRaster.getSample(i, j + 1, 0) == 255) && (newImageRaster.getSample(i - 1, j + 1, 0) == 0)) {
                            newImageRaster.setSample(i, j, 0, 255);
                            result = true;
                        }
                        if ((newImageRaster.getSample(i - 1, j, 0) == 255) && (newImageRaster.getSample(i - 1, j - 1, 0) == 0) &&
                                (newImageRaster.getSample(i, j - 1, 0) == 0) && (newImageRaster.getSample(i + 1, j - 1, 0) == 0) &&
                                (newImageRaster.getSample(i + 1, j, 0) == 255) && (newImageRaster.getSample(i + 1, j + 1, 0) == 255) &&
                                (newImageRaster.getSample(i, j + 1, 0) == 255) && (newImageRaster.getSample(i - 1, j + 1, 0) == 255)) {
                            newImageRaster.setSample(i, j, 0, 255);
                            result = true;
                        }
                        if ((newImageRaster.getSample(i - 1, j, 0) == 255) && (newImageRaster.getSample(i - 1, j - 1, 0) == 255) &&
                                (newImageRaster.getSample(i, j - 1, 0) == 255) && (newImageRaster.getSample(i + 1, j - 1, 0) == 0) &&
                                (newImageRaster.getSample(i + 1, j, 0) == 0) && (newImageRaster.getSample(i + 1, j + 1, 0) == 0) &&
                                (newImageRaster.getSample(i, j + 1, 0) == 255) && (newImageRaster.getSample(i - 1, j + 1, 0) == 255)) {
                            newImageRaster.setSample(i, j, 0, 255);
                            result = true;
                        }


                        if ((newImageRaster.getSample(i - 1, j, 0) == 0) && (newImageRaster.getSample(i - 1, j - 1, 0) == 0) &&
                                (newImageRaster.getSample(i, j - 1, 0) == 255) && (newImageRaster.getSample(i + 1, j - 1, 0) == 255) &&
                                (newImageRaster.getSample(i + 1, j, 0) == 255) && (newImageRaster.getSample(i + 1, j + 1, 0) == 255) &&
                                (newImageRaster.getSample(i, j + 1, 0) == 255) && (newImageRaster.getSample(i - 1, j + 1, 0) == 255)) {
                            newImageRaster.setSample(i, j, 0, 255);
                            result = true;
                        }
                        if ((newImageRaster.getSample(i - 1, j, 0) == 255) && (newImageRaster.getSample(i - 1, j - 1, 0) == 255) &&
                                (newImageRaster.getSample(i, j - 1, 0) == 0) && (newImageRaster.getSample(i + 1, j - 1, 0) == 0) &&
                                (newImageRaster.getSample(i + 1, j, 0) == 255) && (newImageRaster.getSample(i + 1, j + 1, 0) == 255) &&
                                (newImageRaster.getSample(i, j + 1, 0) == 255) && (newImageRaster.getSample(i - 1, j + 1, 0) == 255)) {
                            newImageRaster.setSample(i, j, 0, 255);
                            result = true;
                        }
                        if ((newImageRaster.getSample(i - 1, j, 0) == 255) && (newImageRaster.getSample(i - 1, j - 1, 0) == 255) &&
                                (newImageRaster.getSample(i, j - 1, 0) == 255) && (newImageRaster.getSample(i + 1, j - 1, 0) == 255) &&
                                (newImageRaster.getSample(i + 1, j, 0) == 0) && (newImageRaster.getSample(i + 1, j + 1, 0) == 0) &&
                                (newImageRaster.getSample(i, j + 1, 0) == 255) && (newImageRaster.getSample(i - 1, j + 1, 0) == 255)) {
                            newImageRaster.setSample(i, j, 0, 255);
                            result = true;
                        }


                        if ((newImageRaster.getSample(i - 1, j, 0) == 0) && (newImageRaster.getSample(i - 1, j - 1, 0) == 255) &&
                                (newImageRaster.getSample(i, j - 1, 0) == 255) && (newImageRaster.getSample(i + 1, j - 1, 0) == 255) &&
                                (newImageRaster.getSample(i + 1, j, 0) == 255) && (newImageRaster.getSample(i + 1, j + 1, 0) == 255) &&
                                (newImageRaster.getSample(i, j + 1, 0) == 255) && (newImageRaster.getSample(i - 1, j + 1, 0) == 0)) {
                            newImageRaster.setSample(i, j, 0, 255);
                            result = true;
                        }
                        if ((newImageRaster.getSample(i - 1, j, 0) == 255) && (newImageRaster.getSample(i - 1, j - 1, 0) == 0) &&
                                (newImageRaster.getSample(i, j - 1, 0) == 0) && (newImageRaster.getSample(i + 1, j - 1, 0) == 255) &&
                                (newImageRaster.getSample(i + 1, j, 0) == 255) && (newImageRaster.getSample(i + 1, j + 1, 0) == 255) &&
                                (newImageRaster.getSample(i, j + 1, 0) == 255) && (newImageRaster.getSample(i - 1, j + 1, 0) == 255)) {
                            newImageRaster.setSample(i, j, 0, 255);
                            result = true;
                        }
                        if ((newImageRaster.getSample(i - 1, j, 0) == 255) && (newImageRaster.getSample(i - 1, j - 1, 0) == 255) &&
                                (newImageRaster.getSample(i, j - 1, 0) == 255) && (newImageRaster.getSample(i + 1, j - 1, 0) == 0) &&
                                (newImageRaster.getSample(i + 1, j, 0) == 0) && (newImageRaster.getSample(i + 1, j + 1, 0) == 255) &&
                                (newImageRaster.getSample(i, j + 1, 0) == 255) && (newImageRaster.getSample(i - 1, j + 1, 0) == 255)) {
                            newImageRaster.setSample(i, j, 0, 255);
                            result = true;
                        }
                    }
                }
            }
        return result;
    }

    public static boolean filt2(int w, int h, WritableRaster imageRaster) {
        boolean result = false;
        for (int i = 10; i < w - 10; i++)
            for (int j = 10; j < h - 10; j++) {
                if ((j + 1 < h) && (j - 1 >= 0) && (i + 1 < w) && (i - 1 >= 0)) {
                    if (imageRaster.getSample(i, j, 0) == 0) {
                        if ((imageRaster.getSample(i - 1, j, 0) == 0) && (imageRaster.getSample(i + 1, j, 0) == 0) &&
                                (imageRaster.getSample(i, j + 1, 0) == 0) && (imageRaster.getSample(i - 1, j - 1, 0) == 255) &&
                                (imageRaster.getSample(i, j - 1, 0) == 255) && (imageRaster.getSample(i + 1, j - 1, 0) == 255)) {
                            imageRaster.setSample(i, j, 0, 255);
                            result = true;
                        }
                        if ((imageRaster.getSample(i - 1, j, 0) == 0) && (imageRaster.getSample(i, j + 1, 0) == 0) &&
                                (imageRaster.getSample(i, j - 1, 0) == 0) && (imageRaster.getSample(i + 1, j - 1, 0) == 255) &&
                                (imageRaster.getSample(i + 1, j, 0) == 255) && (imageRaster.getSample(i + 1, j + 1, 0) == 255)) {
                            imageRaster.setSample(i, j, 0, 255);
                            result = true;
                        }
                        if ((imageRaster.getSample(i, j - 1, 0) == 0) && (imageRaster.getSample(i, j + 1, 0) == 0) &&
                                (imageRaster.getSample(i + 1, j, 0) == 0) && (imageRaster.getSample(i - 1, j - 1, 0) == 255) &&
                                (imageRaster.getSample(i - 1, j, 0) == 255) && (imageRaster.getSample(i - 1, j + 1, 0) == 255)) {
                            imageRaster.setSample(i, j, 0, 255);
                            result = true;
                        }
                        if ((imageRaster.getSample(i - 1, j, 0) == 0) && (imageRaster.getSample(i, j - 1, 0) == 0) &&
                                (imageRaster.getSample(i + 1, j, 0) == 0) && (imageRaster.getSample(i - 1, j + 1, 0) == 255) &&
                                (imageRaster.getSample(i, j + 1, 0) == 255) && (imageRaster.getSample(i + 1, j + 1, 0) == 255)) {
                            imageRaster.setSample(i, j, 0, 255);
                            result = true;
                        }

                        if ((imageRaster.getSample(i - 1, j, 0) == 0) && (imageRaster.getSample(i + 1, j, 0) == 0) &&
                                (imageRaster.getSample(i, j + 1, 0) == 0) && (imageRaster.getSample(i - 1, j - 1, 0) == 255) &&
                                (imageRaster.getSample(i, j - 1, 0) == 255) && (imageRaster.getSample(i + 1, j - 1, 0) == 255)) {
                            imageRaster.setSample(i, j, 0, 255);
                            result = true;
                        }
                        if ((imageRaster.getSample(i - 1, j, 0) == 0) && (imageRaster.getSample(i, j + 1, 0) == 0) &&
                                (imageRaster.getSample(i, j - 1, 0) == 0) && (imageRaster.getSample(i + 1, j - 1, 0) == 255) &&
                                (imageRaster.getSample(i + 1, j, 0) == 255) && (imageRaster.getSample(i + 1, j + 1, 0) == 255)) {
                            imageRaster.setSample(i, j, 0, 255);
                            result = true;
                        }
                        if ((imageRaster.getSample(i, j - 1, 0) == 0) && (imageRaster.getSample(i, j + 1, 0) == 0) &&
                                (imageRaster.getSample(i + 1, j, 0) == 0) && (imageRaster.getSample(i - 1, j - 1, 0) == 255) &&
                                (imageRaster.getSample(i - 1, j, 0) == 255) && (imageRaster.getSample(i - 1, j + 1, 0) == 255)) {
                            imageRaster.setSample(i, j, 0, 255);
                            result = true;
                        }
                        if ((imageRaster.getSample(i - 1, j, 0) == 0) && (imageRaster.getSample(i, j - 1, 0) == 0) &&
                                (imageRaster.getSample(i + 1, j, 0) == 0) && (imageRaster.getSample(i - 1, j + 1, 0) == 255) &&
                                (imageRaster.getSample(i, j + 1, 0) == 255) && (imageRaster.getSample(i + 1, j + 1, 0) == 255)) {
                            imageRaster.setSample(i, j, 0, 255);
                            result = true;
                        }
                        if ((imageRaster.getSample(i - 1, j, 0) == 255) && (imageRaster.getSample(i - 1, j - 1, 0) == 255) &&
                                (imageRaster.getSample(i, j - 1, 0) == 255) && (imageRaster.getSample(i + 1, j - 1, 0) == 255) &&
                                (imageRaster.getSample(i + 1, j, 0) == 255) && (imageRaster.getSample(i + 1, j + 1, 0) == 255) &&
                                (imageRaster.getSample(i, j + 1, 0) == 255) && (imageRaster.getSample(i - 1, j + 1, 0) == 255)) {
                            imageRaster.setSample(i, j, 0, 255);
                            result = true;
                        }
                        if ((imageRaster.getSample(i - 1, j, 0) == 255) && (imageRaster.getSample(i - 1, j - 1, 0) == 255) &&
                                (imageRaster.getSample(i, j - 1, 0) == 255) && (imageRaster.getSample(i + 1, j - 1, 0) == 255) &&
                                (imageRaster.getSample(i + 1, j, 0) == 255) && (imageRaster.getSample(i + 1, j + 1, 0) == 0) &&
                                (imageRaster.getSample(i, j + 1, 0) == 0) && (imageRaster.getSample(i - 1, j + 1, 0) == 0)) {
                            imageRaster.setSample(i, j, 0, 255);
                            result = true;
                        }
                        if ((imageRaster.getSample(i - 1, j, 0) == 255) && (imageRaster.getSample(i - 1, j - 1, 0) == 255) &&
                                (imageRaster.getSample(i, j - 1, 0) == 255) && (imageRaster.getSample(i + 1, j - 1, 0) == 255) &&
                                (imageRaster.getSample(i + 1, j, 0) == 255) && (imageRaster.getSample(i + 1, j + 1, 0) == 255) &&
                                (imageRaster.getSample(i, j + 1, 0) == 0) && (imageRaster.getSample(i - 1, j + 1, 0) == 0)) {
                            imageRaster.setSample(i, j, 0, 255);
                            result = true;
                        }
                        if ((imageRaster.getSample(i - 1, j, 0) == 255) && (imageRaster.getSample(i - 1, j - 1, 0) == 255) &&
                                (imageRaster.getSample(i, j - 1, 0) == 255) && (imageRaster.getSample(i + 1, j - 1, 0) == 255) &&
                                (imageRaster.getSample(i + 1, j, 0) == 255) && (imageRaster.getSample(i + 1, j + 1, 0) == 0) &&
                                (imageRaster.getSample(i, j + 1, 0) == 0) && (imageRaster.getSample(i - 1, j + 1, 0) == 255)) {
                            imageRaster.setSample(i, j, 0, 255);
                            result = true;
                        }


                        if ((imageRaster.getSample(i - 1, j, 0) == 0) && (imageRaster.getSample(i - 1, j - 1, 0) == 0) &&
                                (imageRaster.getSample(i, j - 1, 0) == 255) && (imageRaster.getSample(i + 1, j - 1, 0) == 255) &&
                                (imageRaster.getSample(i + 1, j, 0) == 255) && (imageRaster.getSample(i + 1, j + 1, 0) == 255) &&
                                (imageRaster.getSample(i, j + 1, 0) == 255) && (imageRaster.getSample(i - 1, j + 1, 0) == 0)) {
                            imageRaster.setSample(i, j, 0, 255);
                            result = true;
                        }
                        if ((imageRaster.getSample(i - 1, j, 0) == 255) && (imageRaster.getSample(i - 1, j - 1, 0) == 0) &&
                                (imageRaster.getSample(i, j - 1, 0) == 0) && (imageRaster.getSample(i + 1, j - 1, 0) == 0) &&
                                (imageRaster.getSample(i + 1, j, 0) == 255) && (imageRaster.getSample(i + 1, j + 1, 0) == 255) &&
                                (imageRaster.getSample(i, j + 1, 0) == 255) && (imageRaster.getSample(i - 1, j + 1, 0) == 255)) {
                            imageRaster.setSample(i, j, 0, 255);
                            result = true;
                        }
                        if ((imageRaster.getSample(i - 1, j, 0) == 255) && (imageRaster.getSample(i - 1, j - 1, 0) == 255) &&
                                (imageRaster.getSample(i, j - 1, 0) == 255) && (imageRaster.getSample(i + 1, j - 1, 0) == 0) &&
                                (imageRaster.getSample(i + 1, j, 0) == 0) && (imageRaster.getSample(i + 1, j + 1, 0) == 0) &&
                                (imageRaster.getSample(i, j + 1, 0) == 255) && (imageRaster.getSample(i - 1, j + 1, 0) == 255)) {
                            imageRaster.setSample(i, j, 0, 255);
                            result = true;
                        }


                        if ((imageRaster.getSample(i - 1, j, 0) == 0) && (imageRaster.getSample(i - 1, j - 1, 0) == 0) &&
                                (imageRaster.getSample(i, j - 1, 0) == 255) && (imageRaster.getSample(i + 1, j - 1, 0) == 255) &&
                                (imageRaster.getSample(i + 1, j, 0) == 255) && (imageRaster.getSample(i + 1, j + 1, 0) == 255) &&
                                (imageRaster.getSample(i, j + 1, 0) == 255) && (imageRaster.getSample(i - 1, j + 1, 0) == 255)) {
                            imageRaster.setSample(i, j, 0, 255);
                            result = true;
                        }
                        if ((imageRaster.getSample(i - 1, j, 0) == 255) && (imageRaster.getSample(i - 1, j - 1, 0) == 255) &&
                                (imageRaster.getSample(i, j - 1, 0) == 0) && (imageRaster.getSample(i + 1, j - 1, 0) == 0) &&
                                (imageRaster.getSample(i + 1, j, 0) == 255) && (imageRaster.getSample(i + 1, j + 1, 0) == 255) &&
                                (imageRaster.getSample(i, j + 1, 0) == 255) && (imageRaster.getSample(i - 1, j + 1, 0) == 255)) {
                            imageRaster.setSample(i, j, 0, 255);
                            result = true;
                        }
                        if ((imageRaster.getSample(i - 1, j, 0) == 255) && (imageRaster.getSample(i - 1, j - 1, 0) == 255) &&
                                (imageRaster.getSample(i, j - 1, 0) == 255) && (imageRaster.getSample(i + 1, j - 1, 0) == 255) &&
                                (imageRaster.getSample(i + 1, j, 0) == 0) && (imageRaster.getSample(i + 1, j + 1, 0) == 0) &&
                                (imageRaster.getSample(i, j + 1, 0) == 255) && (imageRaster.getSample(i - 1, j + 1, 0) == 255)) {
                            imageRaster.setSample(i, j, 0, 255);
                            result = true;
                        }


                        if ((imageRaster.getSample(i - 1, j, 0) == 0) && (imageRaster.getSample(i - 1, j - 1, 0) == 255) &&
                                (imageRaster.getSample(i, j - 1, 0) == 255) && (imageRaster.getSample(i + 1, j - 1, 0) == 255) &&
                                (imageRaster.getSample(i + 1, j, 0) == 255) && (imageRaster.getSample(i + 1, j + 1, 0) == 255) &&
                                (imageRaster.getSample(i, j + 1, 0) == 255) && (imageRaster.getSample(i - 1, j + 1, 0) == 0)) {
                            imageRaster.setSample(i, j, 0, 255);
                            result = true;
                        }
                        if ((imageRaster.getSample(i - 1, j, 0) == 255) && (imageRaster.getSample(i - 1, j - 1, 0) == 0) &&
                                (imageRaster.getSample(i, j - 1, 0) == 0) && (imageRaster.getSample(i + 1, j - 1, 0) == 255) &&
                                (imageRaster.getSample(i + 1, j, 0) == 255) && (imageRaster.getSample(i + 1, j + 1, 0) == 255) &&
                                (imageRaster.getSample(i, j + 1, 0) == 255) && (imageRaster.getSample(i - 1, j + 1, 0) == 255)) {
                            imageRaster.setSample(i, j, 0, 255);
                            result = true;
                        }
                        if ((imageRaster.getSample(i - 1, j, 0) == 255) && (imageRaster.getSample(i - 1, j - 1, 0) == 255) &&
                                (imageRaster.getSample(i, j - 1, 0) == 255) && (imageRaster.getSample(i + 1, j - 1, 0) == 0) &&
                                (imageRaster.getSample(i + 1, j, 0) == 0) && (imageRaster.getSample(i + 1, j + 1, 0) == 255) &&
                                (imageRaster.getSample(i, j + 1, 0) == 255) && (imageRaster.getSample(i - 1, j + 1, 0) == 255)) {
                            imageRaster.setSample(i, j, 0, 255);
                            result = true;
                        }
                    }
                }
            }
        return result;
    }

}
