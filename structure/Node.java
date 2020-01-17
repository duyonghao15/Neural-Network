package org.optaplanner.examples.cneat.structure;

import org.optaplanner.examples.common.domain.AbstractPersistable;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Node extends AbstractPersistable {

    private String layerType;                    // sensor or hidden
    private String inOutPut;                     // input or output (sensor)
    private double biasValue;                    // 偏好值
    private double meanValue;                    // 偏好值的均值
    private double variance;                     // 偏好值的方差
    private String activationFunction;           // 激励函数(log-sigmoid, tan-sigmoid, relu)
    private List<String> activationFunctionList;

    private double inputValue;                   // 输入值
    private Set<Connection> inConnectionSet;     // 哪些connection指向该node
    private Set<Connection> outConnectionSet;    // 改node指出哪些connection

    // getter and setter
    public String getLayerType() {
        return layerType;
    }
    public void setLayerType(String layerType) {
        this.layerType = layerType;
    }

    public String getInOutPut() {
        return inOutPut;
    }
    public void setInOutPut(String inOutPut) {
        this.inOutPut = inOutPut;
    }

    public double getBiasValue() {
        return biasValue;
    }
    public void setBiasValue(double biasValue) {
        this.biasValue = biasValue;
    }

    public double getMeanValue() {
        return meanValue;
    }
    public void setMeanValue(double meanValue) {
        this.meanValue = meanValue;
    }

    public double getVariance() {
        return variance;
    }
    public void setVariance(double variance) {
        this.variance = variance;
    }

    public String getActivationFunction() {
        return activationFunction;
    }
    public void setActivationFunction(String activationFunction) {
        this.activationFunction = activationFunction;
    }

    public List<String> getActivationFunctionList() {
        return activationFunctionList;
    }
    public void setActivationFunctionList(List<String> activationFunctionList) {
        this.activationFunctionList = activationFunctionList;
    }

    public double getInputValue() {
        return inputValue;
    }
    public void setInputValue(double inputValue) {
        this.inputValue = inputValue;
    }

    public Set<Connection> getInConnectionSet() {
        return inConnectionSet;
    }
    public void setInConnectionSet(Set<Connection> inConnectionSet) {
        this.inConnectionSet = inConnectionSet;
    }

    public Set<Connection> getOutConnectionSet() {
        return outConnectionSet;
    }
    public void setOutConnectionSet(Set<Connection> outConnectionSet) {
        this.outConnectionSet = outConnectionSet;
    }


    // 获取一个node的输出
    public double getOutput(long[] visitedIdSet) {
        // 0. 防止产生自闭循环
        int selfLoop = 0;
        long[] newVisitedIdSet = new long[visitedIdSet.length + 1];
        for(int i = 0 ; i < visitedIdSet.length ; i ++) {
            if(visitedIdSet[i] == id) {
                // System.out.println("warning: 出现自闭环: " + Arrays.toString(visitedIdSet) + " " + id);
                selfLoop = 1;
                break;
            }
            newVisitedIdSet[i] = visitedIdSet[i];
        }
        newVisitedIdSet[visitedIdSet.length] = id;


        // 1. 先计算node的input
        double input = 0;
        if(inOutPut.equals("input")) {          // 若为输入node, 直接读取
            input = inputValue;
            // System.out.println(Arrays.toString(newVisitedIdSet));
        } else {                                // 否则从指向该node的connection中读取
            for(Connection connection : inConnectionSet) {
                if(connection.isActive()) {     // 只有激活了的才算
                    Node startNode = connection.getStartNode();
                    double weight = connection.getWeight();
                    if(selfLoop == 0) {
                        input += weight * startNode.getOutput(newVisitedIdSet);
                    } else {
                        input += 0;
                    }
                }
            }
        }
        // 2. 加上biasValue
        double output = biasValue + input;

        // 3. 导入激励函数
        switch (activationFunction) {
            case "log-sigmoid":
                output = 1 / (1 + Math.exp(- output));
                break;
            case "tan-sigmoid":
                output = 2 / (1 + Math.exp(- output * 2)) - 1;
                break;
            case "relu":
                output = Math.max(0, output);
                break;
            default:
                break;
        }
        return output;
    }

}
