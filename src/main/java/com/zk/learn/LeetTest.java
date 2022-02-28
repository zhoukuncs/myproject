package com.zk.learn;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import java.util.*;

public class LeetTest {
    @Test
    void leetTest(){
        Solution resolve = new Solution();
//        int[] nums = new int[]{3,0,1,0};
//        int k = 1;
//        int[] res = resolve.topKFrequent(nums, k);
        Queue<Integer> q = new LinkedList();
        q.offer(null);
        q.offer(null);
        List<? extends List<Integer>> lists = new ArrayList<ArrayList<Integer>>();
    }
}

class Solution {
    public int[] topKFrequent(int[] nums, int k) {
        HashMap<Integer, Integer> map = new HashMap<Integer, Integer>();
        for (int a : nums) {
            if (map.containsKey(a)) {
                map.put(a, map.get(a) + 1);
            } else {
                map.put(a, 1);
            }
        }
        Comparator<Map.Entry<Integer, Integer>> comparator = new Comparator<Map.Entry<Integer, Integer>>() {
            // @override
            public int compare(Map.Entry<Integer, Integer> o1, Map.Entry<Integer, Integer> o2) {
                return o1.getValue() - o2.getValue();
            }
        };
        PriorityQueue<Map.Entry<Integer, Integer>> pq = new PriorityQueue<Map.Entry<Integer, Integer>>(comparator);
        for (Map.Entry<Integer, Integer> temp : map.entrySet()) {
            pq.offer(temp);
        }
        for (int i = 0; i < pq.size() - k; i++) {
            pq.poll();
        }
        Iterator<Map.Entry<Integer, Integer>> it = pq.iterator();
        int[] res = new int[pq.size()];
        int i = 0;
        while (it.hasNext()){
            Map.Entry<Integer, Integer> temp = it.next();
            res[i++] = temp.getKey();       //注意是获取键，也就是数组的元素
        }
        return res;
    }
}