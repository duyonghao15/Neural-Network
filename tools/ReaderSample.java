package org.optaplanner.examples.cneat.tools;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ReaderSample {


    public static void main(String[] args) throws Exception {
        List<double[]> inputsList = readSampleInputs("C:/Users/Think/Desktop/data.csv");
        inputsList = normalization(inputsList);
        List<double[]> outputsList = readSampleOutputs("C:/Users/Think/Desktop/data.csv");
    }


    // ************ 1. 读取输入 ************
    public static List<double[]> readSampleInputs(String path) throws Exception {
        System.out.println("正在导入样本输入 ......");
        DataInputStream in = new DataInputStream(new FileInputStream(new File(path)));
        BufferedReader br = new BufferedReader(new InputStreamReader(in, "GBK"));
        br.readLine();   // 去掉标题行
        String line;
        String[] segments;

        List<double []> inputsList = new ArrayList<>();
        while ((line = br.readLine()) != null) {
            segments = line.split("\"")[0].split(",");

            double i_1 = Double.valueOf(segments[0]);
            double i_2 = Double.valueOf(segments[2]);
            double i_3 = Double.valueOf(segments[1]);
            double i_4 = Double.valueOf(segments[5]);
            double i_6 = Double.valueOf(segments[29]);
            double i_5 = i_3 - i_6;
            double i_7 = Double.valueOf(segments[28]);
            double i_8 = Double.valueOf(segments[26]);
            double i_9 = Double.valueOf(segments[17]);

            double i_10 = Double.valueOf(segments[12]);
            double i_11 = Double.valueOf(segments[13]);
            double i_12 = Double.valueOf(segments[14]);
            double i_13 = Double.valueOf(segments[15]);

            double i_14 = Double.valueOf(segments[22]);
            double i_15 = Double.valueOf(segments[23]);
            double i_16 = Double.valueOf(segments[24]);
            double i_17 = Double.valueOf(segments[25]);

            double[] inputs = {i_1, i_2, i_3, i_4, i_5, i_6, i_7, i_8, i_9, i_10, i_11, i_12, i_13, i_14, i_15, i_16, i_17};
            inputsList.add(inputs);
        }
        System.out.println("已导入 " + inputsList.size() + " 条样本输入.\n");
        /* function ends */
        return inputsList;
    }


    // ************ 2. 读取输出(标签) ************
    public static List<double[]> readSampleOutputs(String path) throws Exception {
        System.out.println("正在导入样本标签 ......");
        DataInputStream in = new DataInputStream(new FileInputStream(new File(path)));
        BufferedReader br = new BufferedReader(new InputStreamReader(in, "GBK"));
        br.readLine();   // 去掉标题行
        String line;
        String[] segments;

        List<double []> outputsList = new ArrayList<>();
        while ((line = br.readLine()) != null) {
            segments = line.split("\"")[0].split(",");

            double o_1 = Double.valueOf(segments[33]);

            double[] outputs = {o_1};
            outputsList.add(outputs);
        }
        System.out.println("已导入 " + outputsList.size() + " 条样本标签.\n");
        /* function ends */
        return outputsList;
    }


    // ************ 3. 数据标准化函数 ************
    public static List<double[]> normalization(List<double[]> originData) throws Exception {
        List<double []> normalizedData = new ArrayList<>();
        List<Double> maxNumberList = new ArrayList<>();
        int vectorLength = originData.get(0).length;


        for(int i = 0 ; i < vectorLength ; i ++) {
            double maxNumber = 0;
            for(double[] vector : originData) {
                double number = vector[i];
                maxNumber = Math.max(number, maxNumber);
            }
            maxNumberList.add(maxNumber);    // 第i列数据的最大值
        }

        for(double[] vector : originData) {
            double[] newVector = new double[vector.length];
            for(int i = 0 ; i < vector.length ; i ++) {
                newVector[i] = vector[i] / maxNumberList.get(i);   // 0-1化
            }
            normalizedData.add(newVector);
        }
        /* function ends */
        return normalizedData;
    }

/* class ends */
}
