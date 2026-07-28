package com.dyj.idle.utils;

import java.util.*;

/**
 * 页面置换算法实验：FIFO + LRU
 * 物理内存32K，页面大小1K → 页框数32
 */
public class PageReplacement {
    // 核心参数：页框数量、页面访问序列长度、局部访问范围参数
    private static final int FRAME_COUNT = 32; // 物理页框数（32K/1K）
    private static final int SEQ_LENGTH = 1000; // 页面访问序列总长度
    private static final int LOCAL_RANGE = 20; // 局部访问范围长度
    private static final int LOCAL_OFFSET = 5; // 局部范围起始页偏移
    private static final double LOCAL_PROB = 0.8; // 局部范围内访问概率

    public static void main(String[] args) {
        // 1. 生成满足局部访问特性的页面访问序列
        List<Integer> pageSeq = generatePageSequence();
        System.out.println("生成的页面访问序列（前50个）：" + pageSeq.subList(0, 50));

        // 2. 执行FIFO算法
        System.out.println("\n===== 先进先出（FIFO）置换算法 =====");
        fifo(pageSeq);

        // 3. 执行LRU算法
        System.out.println("\n===== 最近最久未使用（LRU）置换算法 =====");
        lru(pageSeq);
    }

    /**
     * 生成满足局部访问特性的页面访问序列
     * 局部范围：[LOCAL_OFFSET, LOCAL_OFFSET+LOCAL_RANGE)，范围内访问概率80%，范围外20%
     */
    private static List<Integer> generatePageSequence() {
        Random random = new Random();
        List<Integer> seq = new ArrayList<>();
        int maxPageNum = LOCAL_OFFSET + LOCAL_RANGE + 10; // 最大页号（局部外扩展10页）

        for (int i = 0; i < SEQ_LENGTH; i++) {
            double prob = random.nextDouble();
            int page;
            if (prob < LOCAL_PROB) {
                // 局部范围内生成页号
                page = LOCAL_OFFSET + random.nextInt(LOCAL_RANGE);
            } else {
                // 局部范围外生成页号（避免和局部范围重叠）
                page = LOCAL_OFFSET + LOCAL_RANGE + random.nextInt(10);
            }
            seq.add(page);
        }
        return seq;
    }

    /**
     * FIFO置换算法实现
     * @param pageSeq 页面访问序列
     */
    private static void fifo(List<Integer> pageSeq) {
        Queue<Integer> frameQueue = new LinkedList<>(); // 记录页框中页面的进入顺序
        Set<Integer> frameSet = new HashSet<>(); // 快速判断页面是否在内存中
        int hitCount = 0; // 命中次数
        int replaceCount = 0; // 置换次数

        for (int page : pageSeq) {
            if (frameSet.contains(page)) {
                // 页面命中
                hitCount++;
                continue;
            }
            // 页面未命中，需要装入/置换
            if (frameQueue.size() < FRAME_COUNT) {
                // 内存未满，直接装入
                frameQueue.offer(page);
                frameSet.add(page);
                System.out.printf("装入页面：%d | 当前页框：%s%n", page, frameQueue);
            } else {
                // 内存已满，淘汰最早进入的页面
                int removedPage = frameQueue.poll();
                frameSet.remove(removedPage);
                // 装入新页面
                frameQueue.offer(page);
                frameSet.add(page);
                replaceCount++;
                System.out.printf("淘汰页面：%d | 装入页面：%d | 当前页框：%s%n", removedPage, page, frameQueue);
            }
        }

        // 计算命中率
        double hitRate = (double) hitCount / pageSeq.size() * 100;
        System.out.println("\nFIFO算法统计：");
        System.out.println("总访问次数：" + pageSeq.size());
        System.out.println("命中次数：" + hitCount);
        System.out.println("置换次数：" + replaceCount);
        System.out.printf("命中率：%.2f%%%n", hitRate);
    }

    /**
     * LRU置换算法实现（基于LinkedHashMap按访问顺序排序）
     * @param pageSeq 页面访问序列
     */
    private static void lru(List<Integer> pageSeq) {
        // LinkedHashMap：accessOrder=true → 按访问顺序排序（最近访问的在尾部）
        Map<Integer, Integer> frameMap = new LinkedHashMap<>(FRAME_COUNT, 0.75f, true) {
            @Override
            protected boolean removeEldestEntry(Map.Entry<Integer, Integer> eldest) {
                // 当页框数超过限制时，移除最久未访问的元素（链表头部）
                return size() > FRAME_COUNT;
            }
        };
        int hitCount = 0;
        int replaceCount = 0;

        for (int page : pageSeq) {
            if (frameMap.containsKey(page)) {
                // 页面命中，LinkedHashMap自动更新访问顺序
                hitCount++;
                continue;
            }
            // 页面未命中
            boolean needReplace = frameMap.size() >= FRAME_COUNT;
            int removedPage = -1;
            if (needReplace) {
                // 记录被淘汰的页面（最久未访问的元素）
                removedPage = frameMap.keySet().iterator().next();
                replaceCount++;
            }
            // 装入新页面
            frameMap.put(page, page);
            if (needReplace) {
                System.out.printf("淘汰页面：%d | 装入页面：%d | 当前页框：%s%n", removedPage, page, frameMap.keySet());
            } else {
                System.out.printf("装入页面：%d | 当前页框：%s%n", page, frameMap.keySet());
            }
        }

        // 计算命中率
        double hitRate = (double) hitCount / pageSeq.size() * 100;
        System.out.println("\nLRU算法统计：");
        System.out.println("总访问次数：" + pageSeq.size());
        System.out.println("命中次数：" + hitCount);
        System.out.println("置换次数：" + replaceCount);
        System.out.printf("命中率：%.2f%%%n", hitRate);
    }
}