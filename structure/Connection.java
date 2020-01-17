package org.optaplanner.examples.cneat.structure;

import org.optaplanner.examples.common.domain.AbstractPersistable;

public class Connection extends AbstractPersistable {

    private Node startNode;
    private Node endNode;
    private boolean active;     // 是否激活
    private double weight;      // 权重系数
    private double meanValue;   // 权重的均值
    private double variance;    // 权重的方差

    // getter and setter
    public Node getStartNode() {
        return startNode;
    }
    public void setStartNode(Node startNode) {
        this.startNode = startNode;
    }

    public Node getEndNode() {
        return endNode;
    }
    public void setEndNode(Node endNode) {
        this.endNode = endNode;
    }

    public boolean isActive() {
        return active;
    }
    public void setActive(boolean active) {
        this.active = active;
    }

    public double getWeight() {
        return weight;
    }
    public void setWeight(double weight) {
        this.weight = weight;
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

    @Override
    public String toString() {
        return "Connection-" + id + "(" + startNode.getId() + "->" + endNode.getId() + ")";
    }

}
