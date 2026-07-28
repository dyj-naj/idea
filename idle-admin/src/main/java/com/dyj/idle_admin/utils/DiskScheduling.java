package com.dyj.idle_admin.utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 磁盘调度算法：SCAN（电梯算法） + C-SCAN（循环扫描算法）
 */
public class DiskScheduling {
    // 磁盘最大柱面号（0-199）
    private static final int MAX_CYLINDER = 199;
    // 磁盘最小柱面号
    private static final int MIN_CYLINDER = 0;

    public static void main(String[] args) {
        // 1. 初始化实验数据
        int currentCylinder = 110; // 当前磁头位置
        // 等待处理的柱面请求列表
        List<Integer> requestList = new ArrayList<>();
        Collections.addAll(requestList, 186, 137, 33, 171, 39, 152, 18, 123, 175, 140, 87, 99, 12);

        // 2. 执行SCAN算法
        System.out.println("===== 扫描算法（SCAN/电梯算法） =====");
        int scanTotalDistance = scanAlgorithm(currentCylinder, new ArrayList<>(requestList));

        // 3. 执行C-SCAN算法
        System.out.println("\n===== 循环扫描算法（C-SCAN） =====");
        int cScanTotalDistance = cScanAlgorithm(currentCylinder, new ArrayList<>(requestList));
    }

    /**
     * 扫描算法（SCAN）实现
     * @param current 初始磁头位置
     * @param requests 待处理请求列表
     * @return 磁头移动总距离
     */
    private static int scanAlgorithm(int current, List<Integer> requests) {
        // 步骤1：拆分请求为「≥当前磁头」和「<当前磁头」两部分
        List<Integer> higher = new ArrayList<>(); // ≥current的请求
        List<Integer> lower = new ArrayList<>();  // <current的请求
        for (int req : requests) {
            if (req >= current) {
                higher.add(req);
            } else {
                lower.add(req);
            }
        }

        // 步骤2：排序 - 高段升序（向MAX_CYLINDER移动），低段降序（向MIN_CYLINDER移动）
        Collections.sort(higher); // 升序：123,137,140,152,171,175,186
        Collections.sort(lower, Collections.reverseOrder()); // 降序：99,87,39,33,18,12

        // 步骤3：模拟磁头移动，记录访问序列和移动距离
        List<Integer> accessSequence = new ArrayList<>(); // 访问序列
        int totalDistance = 0; // 总移动距离
        int currentHead = current; // 实时磁头位置

        // 3.1 处理高段请求（向MAX_CYLINDER移动）
        for (int cyl : higher) {
            accessSequence.add(cyl);
            totalDistance += Math.abs(cyl - currentHead); // 累加移动距离
            currentHead = cyl; // 更新磁头位置
        }

        // 3.2 到达MAX_CYLINDER，记录移动距离（从最后一个高段请求到199）
        accessSequence.add(MAX_CYLINDER);
        totalDistance += Math.abs(MAX_CYLINDER - currentHead);
        currentHead = MAX_CYLINDER;

        // 3.3 处理低段请求（向MIN_CYLINDER移动）
        for (int cyl : lower) {
            accessSequence.add(cyl);
            totalDistance += Math.abs(cyl - currentHead);
            currentHead = cyl;
        }

        // 步骤4：输出结果
        System.out.println("磁头初始位置：" + current);
        System.out.println("访问序列：" + accessSequence);
        System.out.println("磁头移动总距离：" + totalDistance);

        return totalDistance;
    }

    /**
     * 循环扫描算法（C-SCAN）实现
     * @param current 初始磁头位置
     * @param requests 待处理请求列表
     * @return 磁头移动总距离
     */
    private static int cScanAlgorithm(int current, List<Integer> requests) {
        // 步骤1：拆分请求为「≥当前磁头」和「<当前磁头」两部分
        List<Integer> higher = new ArrayList<>();
        List<Integer> lower = new ArrayList<>();
        for (int req : requests) {
            if (req >= current) {
                higher.add(req);
            } else {
                lower.add(req);
            }
        }

        // 步骤2：排序 - 高段升序（向MAX_CYLINDER移动），低段升序（跳回MIN_CYLINDER后移动）
        Collections.sort(higher); // 升序：123,137,140,152,171,175,186
        Collections.sort(lower); // 升序：12,18,33,39,87,99

        // 步骤3：模拟磁头移动
        List<Integer> accessSequence = new ArrayList<>();
        int totalDistance = 0;
        int currentHead = current;

        // 3.1 处理高段请求（向MAX_CYLINDER移动）
        for (int cyl : higher) {
            accessSequence.add(cyl);
            totalDistance += Math.abs(cyl - currentHead);
            currentHead = cyl;
        }

        // 3.2 到达MAX_CYLINDER，记录移动距离
        accessSequence.add(MAX_CYLINDER);
        totalDistance += Math.abs(MAX_CYLINDER - currentHead);
        currentHead = MAX_CYLINDER;

        // 3.3 跳回MIN_CYLINDER（C-SCAN核心：不反转方向，直接跳回）
        accessSequence.add(MIN_CYLINDER);
        totalDistance += Math.abs(MIN_CYLINDER - currentHead);
        currentHead = MIN_CYLINDER;

        // 3.4 处理低段请求（沿原方向移动）
        for (int cyl : lower) {
            accessSequence.add(cyl);
            totalDistance += Math.abs(cyl - currentHead);
            currentHead = cyl;
        }

        // 步骤4：输出结果
        System.out.println("磁头初始位置：" + current);
        System.out.println("访问序列：" + accessSequence);
        System.out.println("磁头移动总距离：" + totalDistance);

        return totalDistance;
    }
}