package org.optaplanner.examples.cneat.algorithm;

import org.optaplanner.examples.cneat.tools.FitnessComparatorUp;
import org.optaplanner.examples.cneat.structure.Connection;
import org.optaplanner.examples.cneat.structure.Network;
import org.optaplanner.examples.cneat.structure.Node;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.optaplanner.examples.cneat.algorithm.Fitness.fitness;
import static org.optaplanner.examples.cneat.algorithm.LocalSearch.localSearch;
import static org.optaplanner.examples.cneat.operators.Crossover.crossover;
import static org.optaplanner.examples.cneat.operators.FunctionalMutation.functionalMutationOfConnection;
import static org.optaplanner.examples.cneat.operators.FunctionalMutation.functionalMutationOfNode;
import static org.optaplanner.examples.cneat.operators.NumericalMutation.numericalMutationOfConnection;
import static org.optaplanner.examples.cneat.operators.NumericalMutation.numericalMutationOfNode;
import static org.optaplanner.examples.cneat.operators.StructuralMutation.*;

public class Generation {

    // ************ 0. 一次迭代 ************
    public static List<Network> generation(List<Network> population, List<double[]> inputsList, List<double[]> outputsList) {

        // 1. 交叉(根据已知fitness, 选择, 交叉, 生成新种群)
        List<Network> offspring = crossoverGeneration(population, 0.8, 0.6);

        // 2. 变异(遍历个体, 概率性变异, 只接受好解)
        mutationGeneration(offspring, 0.4, 0.3, 0.2, 0.3, 0.2, 0.2, inputsList, outputsList);

        // 3a. 评估(计算所有个体的fitness, 并赋值)
        fitness(offspring, inputsList, outputsList);

        // 3b. 局部搜索(遍历个体, 搜素n次, 接受好解)
        localSearch(offspring, inputsList, outputsList);

        return offspring;
    }



    // ************ 1. 种群交叉操作(只读fitness作选择, 不评估) ************
    public static List<Network> crossoverGeneration(List<Network> population, double selectionP, double crossoverP) {
        Collections.sort(population, new FitnessComparatorUp());                      // 按照适应度值降序排列
        int popSize = population.size();                                              // 子代种群规模
        int selectionSize = (int) (population.size() * selectionP);                   // 选择多少用于交叉
        int deleteSize = popSize - selectionSize;                                     // 删除多少差的(剩下的用于交叉)

        for (int i = 0 ; i < deleteSize; i ++) {                                      // 删除劣解
            population.remove(population.size() - 1);
        }

        List<Network> newPopulation = new ArrayList<>();                              // 用于存储新子代的list

        for(int i = 1 ; i <= popSize ; i ++) {
            if(Math.random() > crossoverP) {
                i --;
                continue;
            }
            Network parent_1 = population.get((int) (Math.random() * selectionSize)); // 随机挑两个个体
            Network parent_2 = population.get((int) (Math.random() * selectionSize));
            Network offspring = crossover(parent_1, parent_2);                        // 交叉(生成connectionList和nodeList)
            offspring.setId((long) i);                                                // 子代重新编号
            newPopulation.add(offspring);                                             // 加入新的种群中
        }
        /* function ends */
        return newPopulation;
    }


    // ************ 2. 种群变异操作(只评估被变异的个体, 整个种群后面再评估) ************
    public static void mutationGeneration(List<Network> population, double mutConnectionStru, double mutConnectionNum, double mutConnectionFun, double mutNodeStru, double mutNodeNum, double mutNodeFun, List<double[]> inputsList, List<double[]> outputsList) {

        for(int i = 0 ; i < population.size() ; i ++) {
            Network network = population.get(i);

            // 1. 结构型connection变异
            if(Math.random() < mutConnectionStru) {
                structuralMutationOfConnection(network, inputsList, outputsList);
            }

            // 2. 结构型node变异
            if(Math.random() < mutNodeStru) {
                structuralMutationOfNode(network, inputsList, outputsList);
            }

            // 3. 数值型node变异
            if(Math.random() < mutNodeNum) {
                numericalMutationOfNode(network, inputsList, outputsList);
            }

            // 4. 数值型connection变异
            if(Math.random() < mutConnectionNum) {
                numericalMutationOfConnection(network, inputsList, outputsList);
            }

            // 5. 功能型node变异
            if(Math.random() < mutNodeFun) {
                functionalMutationOfNode(network, inputsList, outputsList);
            }

            // 6. 功能型connection变异
            if(Math.random() < mutConnectionFun) {
                functionalMutationOfConnection(network, inputsList, outputsList);
            }
            /* mutations on a network ends */
        }
        /* function ends */
    }




/* class ends */
}
