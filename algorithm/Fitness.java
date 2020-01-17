package org.optaplanner.examples.cneat.algorithm;

import org.optaplanner.examples.cneat.structure.Network;
import org.optaplanner.examples.cneat.structure.Node;

import java.util.List;

public class Fitness {


    // ************ 1. 计算network的适应度函数 ************
    public static double fitness(Network network, List<double[]> inputsList, List<double[]> outputsList) {
        List<Node> nodeList = network.getNodeList();

        double fitness = 0;
        for(int i = 0 ; i < inputsList.size() ; i ++) {      // 遍历list (一个list存一组样本数据)
            double[] inputs = inputsList.get(i);
            double[] outputs = outputsList.get(i);

            for(int j = 0 ; j < inputs.length ; j ++) {      // 把样本输入
                Node node = nodeList.get(j);
                node.setInputValue(inputs[j]);               // 前面几个都是input
            }

            for(int j = 0 ; j < outputs.length ; j ++) {     // 计算网络输出和样本的差值
                Node node = nodeList.get(j + inputs.length); // 输出节点
                double dif = node.getOutput(new long[0]) - outputs[j]; // 后面紧跟着的几个是output (output函数里有个set存储以防止有自循环)
                dif = Math.abs(dif);
                fitness += dif;
            }
        }
        network.setFitness(fitness);
        /* function ends */
        return fitness;
    }


    // ************ 2. 计算population的适应度函数 ************
    public static double fitness(List<Network> networkList, List<double[]> inputsList, List<double[]> outputsList) {
        double totalFitness = 0;
        for(Network network : networkList) {
            double fitness = fitness(network, inputsList, outputsList);   // 计算每个network的适应度值
            network.setFitness(fitness);                                  // 并赋给network, 作为其属性
            totalFitness += fitness;                                      // 种群适应度累加
        }
        System.out.println("适应度值: " + totalFitness + "\n");
        return totalFitness;
    }


/* class ends */
}
