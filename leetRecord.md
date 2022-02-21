# 数组

## 704 二分查找 1

```java
class Solution {
    public int search(int[] nums, int target) {
        int lowIndex = 0, highIndex = nums.length - 1, midIndex = (lowIndex + highIndex) / 2;
        while (nums[midIndex] != target) {
            if (midIndex == lowIndex) {	// 说明已经只有两个数要进行判断了，可以退出了
                break;
            }
            if (nums[midIndex] > target){
                highIndex = midIndex - 1; //边界条件减1，该值已经进行了判断
                midIndex = (lowIndex + highIndex) / 2;
            }else{
                lowIndex = midIndex + 1;	//加1
                midIndex = (lowIndex + highIndex) / 2;
            }
        }            
        if (nums[midIndex] == target) {	// 如果是因为相等而退出，则进行返回该序号
            return midIndex;
        }else if (nums[highIndex] == target) { 	//如果是因为只有两个数而退出，则需要再进行一次右侧判断
            return highIndex;
        }else{
            return -1;
        }
    }
}
```

注意要点：退出的临界条件需要充分考虑，否则容易造成死循环或者错过目标值。

错误一：``midIndex``退出条件设置为最开始的左右边界，但是不一定查找到这两个值，所以要动态设置为左值，因为除法只有可能为左值，这时代表查找到最后两个值了，可以退出。

错误二：`` if (midIndex == lowIndex)``这一行判断语句一开始放在while循环末尾，考虑只有一个数进行判断时，会出现多进行了一次边界加减而导致错过该条件，下一次则会数组越界。

标答：空间复杂度更低，代码量更简洁，条件判断更少。

```java
class Solution {
    public int search(int[] nums, int target) {
        // 避免当 target 小于nums[0] nums[nums.length - 1]时多次循环运算
        if (target < nums[0] || target > nums[nums.length - 1]) {
            return -1;
        }
        int left = 0, right = nums.length - 1;
        while (left <= right) {
            int mid = left + ((right - left) >> 1);
            if (nums[mid] == target)
                return mid;
            else if (nums[mid] < target)
                left = mid + 1;
            else if (nums[mid] > target)
                right = mid - 1;
        }
        return -1;
    }
}
```

## 27 移除元素 1

```java
class Solution {	//快慢指针法
    public int removeElement(int[] nums, int val) {
        int newLength = 0, index = 0;
        while (index < nums.length) {
            if (nums[index] != val) {
                nums[newLength] = nums[index];
                newLength ++;
            }
            index ++;    
        }
        return newLength;
    }
}
```

## 977 有序数组的平方 3

```java
class Solution {	//双指针
    public int[] sortedSquares(int[] nums) {
        int left, right = 0, index = 0, i;
        int[] result = new int[nums.length];	//这里出现过一次错误，不能用引用赋值
        for (i = 0; i < nums.length; i++) {
            if (nums[i] >= 0) {
                right = i;
                break;
            }
        }
        if (i == nums.length) {	//这里出现过一次错误，因为前面有可能最后一次过了之后没有赋值
            right = i -1;
        }
        left = right - 1;
        while (left >= 0 ) {	//这里出现过一次错误，只用作一侧判断就够了，两侧判断更复杂且条件过多
            if (right < nums.length) {
                if (fang(nums[left]) <= fang(nums[right])){
                    result[index++] = fang(nums[left--]);
                }else {
                    result[index++] = fang(nums[right++]);
                }
            }else {
                result[index++] = fang(nums[left--]);
            }
        }
        while (right < nums.length) {
            result[index++] = fang(nums[right++]);
        }
        return result;
    }
    
    private int fang(int num) {
        return num * num;
    }
}
```

标答：

```Java
class Solution {
    public int[] sortedSquares(int[] nums) {
        int right = nums.length - 1;
        int left = 0;
        int[] result = new int[nums.length];
        int index = result.length - 1;
        while (left <= right) {
            if (nums[left] * nums[left] > nums[right] * nums[right]) {
                result[index--] = nums[left] * nums[left];
                ++left;
            } else {
                result[index--] = nums[right] * nums[right];
                --right;
            }
        }
        return result;
    }
}
```

与标答的区别在于：标答先填充的最大值，最大值总是出现在两端，所以可以利用这个进行双指针移动，而我采用的是最小值填充，这样需要先找到最小值，更加麻烦一点。

## 209 长度最小的子数组 2

```java
class Solution {
    public int minSubArrayLen(int target, int[] nums) {
        int i = 0, j = 0, sum = 0, result = Integer.MAX_VALUE, length = 0, window = Integer.MAX_VALUE;
        while (j < nums.length){
            sum = caSum(nums, i, j);
            if(sum >= target) {
                length = j - i + 1;
                window = length - 1;
                result = result < length? result : length;
                i++;
                j = i + window -1;
                continue;
            }
            j++;
            i = (j-window + 1) >= 0? (j - window + 1) : 0; //设置变量便于计算
        }
        return result == Integer.MAX_VALUE? 0:result;
    }
    
    private int caSum(int[] nums, int i, int j) {
        int sum = 0;
        for (int index = i; index <= j; index++) {
            sum += nums[index];
        }
        return sum;
    }
}
//103ms, 40MB
```

标答：

```java
class Solution {

    // 滑动窗口
    public int minSubArrayLen(int s, int[] nums) {
        int left = 0;
        int sum = 0;
        int result = Integer.MAX_VALUE;
        for (int right = 0; right < nums.length; right++) {
            sum += nums[right];
            while (sum >= s) {
                result = Math.min(result, right - left + 1);
                sum -= nums[left++];
            }
        }
        return result == Integer.MAX_VALUE ? 0 : result;
    }
}
//1ms, 41.4MB
```

暴力解法：

```java
class Solution {

    // 滑动窗口
    public int minSubArrayLen(int s, int[] nums) {
        int result = Integer.MAX_VALUE; // 最终的结果
        int sum = 0; // 子序列的数值之和
        int subLength = 0; // 子序列的长度
        for (int i = 0; i < nums.length; i++) { // 设置子序列起点为i
            sum = 0;
            for (int j = i; j < nums.length; j++) { // 设置子序列终止位置为j
                sum += nums[j];
                if (sum >= s) { // 一旦发现子序列和超过了s，更新result
                    subLength = j - i + 1; // 取子序列的长度
                    result = result < subLength ? result : subLength;
                    break; // 因为我们是找符合条件最短的子序列，所以一旦符合条件就break
                }
            }
        }
        // 如果result没有被赋值的话，就返回0，说明没有符合条件的子序列
        return result == Integer.MAX_VALUE ? 0 : result;
    }
}
//138ms, 41,6M
```

## 59 螺旋矩阵Ⅱ

```java
class Solution {	//统一规则方法值得学习，每次画一条变都采用统一的规则，减少边界条件
    public int[][] generateMatrix(int n) {
        int[][] res = new int[n][n];

        // 循环次数
        int loop = n / 2;

        // 定义每次循环起始位置
        int startX = 0;
        int startY = 0;

        // 定义偏移量
        int offset = 1;

        // 定义填充数字
        int count = 1;

        // 定义中间位置
        int mid = n / 2;
        while (loop > 0) {
            int i = startX;
            int j = startY;

            // 模拟上侧从左到右
            for (; j<startY + n -offset; ++j) {
                res[startX][j] = count++;
            }

            // 模拟右侧从上到下
            for (; i<startX + n -offset; ++i) {
                res[i][j] = count++;
            }

            // 模拟下侧从右到左
            for (; j > startY; j--) {
                res[i][j] = count++;
            }

            // 模拟左侧从下到上
            for (; i > startX; i--) {
                res[i][j] = count++;
            }

            loop--;

            startX += 1;
            startY += 1;

            offset += 2;
        }

        if (n % 2 == 1) {
            res[mid][mid] = count;
        }

        return res;
    }
}
```

# 链表

## 203 移除链表元素 1

```java
/**
 * Definition for singly-linked list.
 * public class ListNode {
 *     int val;
 *     ListNode next;
 *     ListNode() {}
 *     ListNode(int val) { this.val = val; }
 *     ListNode(int val, ListNode next) { this.val = val; this.next = next; }
 * }
 */
class Solution {
    public ListNode removeElements(ListNode head, int val) {
        if (head == null) {
            return head;
        }
        if (head.val == val) {
            return removeElements(head.next, val);	//这里不能直接return head.next，否则只删掉了一个就结束了
        }
        head.next = removeElements(head.next, val);
        return head;
    }
}
```

标答：

```java
/**
 * 添加虚节点方式
 * 时间复杂度 O(n)
 * 空间复杂度 O(1)
 * @param head
 * @param val
 * @return
 */
public ListNode removeElements(ListNode head, int val) {
    if (head == null) {
        return head;
    }
    // 因为删除可能涉及到头节点，所以设置dummy节点，统一操作
    ListNode dummy = new ListNode(-1, head);
    ListNode pre = dummy;
    ListNode cur = head;
    while (cur != null) {
        if (cur.val == val) {
            pre.next = cur.next;
        } else {
            pre = cur;
        }
        cur = cur.next;
    }
    return dummy.next;
}
/**
 * 不添加虚拟节点方式
 * 时间复杂度 O(n)
 * 空间复杂度 O(1)
 * @param head
 * @param val
 * @return
 */
public ListNode removeElements(ListNode head, int val) {
    while (head != null && head.val == val) {
        head = head.next;
    }
    // 已经为null，提前退出
    if (head == null) {
        return head;
    }
    // 已确定当前head.val != val
    ListNode pre = head;
    ListNode cur = head.next;
    while (cur != null) {
        if (cur.val == val) {
            pre.next = cur.next;
        } else {
            pre = cur;
        }
        cur = cur.next;
    }
    return head;
}
```

## 707 设计链表 9

```java
class MyLinkedList {
        int val;
        MyLinkedList next;
    public MyLinkedList() {
        next = null;
    }
    
    public int get(int index) {
        if (index < 0) {
            return -1;
        }
        int getIndex = -1;
        MyLinkedList now = next;
        while (now != null) {
            getIndex ++;
            if (getIndex == index) {
                return now.val;
            }
            now = now.next;
        }
        return -1;
    }
    
    public void addAtHead(int val) {
        MyLinkedList newNode = new MyLinkedList();
        newNode.val = val;
        newNode.next = next;
        next = newNode;
    }
    
    public void addAtTail(int val) {
        if (next == null){
            addAtHead(val);
            return;
        }
        MyLinkedList now = next;
        while(now.next != null){
            now = now.next;
        }
        MyLinkedList newNode = new MyLinkedList();
        newNode.val = val; 
        now.next = newNode;      
    }
    
    public void addAtIndex(int index, int val) {
        if (index < 0){
            addAtHead(val);
            return;
        }
        int addIndex = -1;
        MyLinkedList now = this;
        while (now != null) {
            addIndex++;
            if (addIndex == index) {
                MyLinkedList newNode = new MyLinkedList();
                newNode.val = val;                    
                newNode.next = now.next;
                now.next = newNode; 
                return;
            }
            now = now.next;
        }
    }
    
    public void deleteAtIndex(int index) {
        if (index < 0){
            return;
        }
        MyLinkedList now = this;
        int num = -1;
        while (now.next != null) {
            num++;
            if (num == index) {
                now.next = now.next.next;
                return;
            }
            now = now.next;
        }
    }
}

/**
 * Your MyLinkedList object will be instantiated and called as such:
 * MyLinkedList obj = new MyLinkedList();
 * int param_1 = obj.get(index);
 * obj.addAtHead(val);
 * obj.addAtTail(val);
 * obj.addAtIndex(index,val);
 * obj.deleteAtIndex(index);
 */
```

标答：

```Java
class ListNode {
    int val;
    ListNode next;
    ListNode(){}
    ListNode(int val) {
        this.val=val;
    }
}
class MyLinkedList {
    //size存储链表元素的个数
    int size;
    //虚拟头结点
    ListNode head;

    //初始化链表
    public MyLinkedList() {
        size = 0;
        head = new ListNode(0);
    }

    //获取第index个节点的数值
    public int get(int index) {
        //如果index非法，返回-1
        if (index < 0 || index >= size) {
            return -1;
        }
        ListNode currentNode = head;
        //包含一个虚拟头节点，所以查找第 index+1 个节点
        for (int i = 0; i <= index; i++) {
            currentNode = currentNode.next;
        }
        return currentNode.val;
    }

    //在链表最前面插入一个节点
    public void addAtHead(int val) {
        addAtIndex(0, val);
    }

    //在链表的最后插入一个节点
    public void addAtTail(int val) {
        addAtIndex(size, val);
    }

    // 在第 index 个节点之前插入一个新节点，例如index为0，那么新插入的节点为链表的新头节点。
    // 如果 index 等于链表的长度，则说明是新插入的节点为链表的尾结点
    // 如果 index 大于链表的长度，则返回空
    public void addAtIndex(int index, int val) {
        if (index > size) {
            return;
        }
        if (index < 0) {
            index = 0;
        }
        size++;
        //找到要插入节点的前驱
        ListNode pred = head;
        for (int i = 0; i < index; i++) {
            pred = pred.next;
        }
        ListNode toAdd = new ListNode(val);
        toAdd.next = pred.next;
        pred.next = toAdd;
    }

    //删除第index个节点
    public void deleteAtIndex(int index) {
        if (index < 0 || index >= size) {
            return;
        }
        size--;
        ListNode pred = head;
        for (int i = 0; i < index; i++) {
            pred = pred.next;
        }
        pred.next = pred.next.next;
    }
}
```

标答采用的方式更加规则，链表节点单独设置为一个类，这样链表类可以设置长度，简化了代码的一些条件判断。

## 206 链表反转 1

```java
/**
 * Definition for singly-linked list.
 * public class ListNode {
 *     int val;
 *     ListNode next;
 *     ListNode() {}
 *     ListNode(int val) { this.val = val; }
 *     ListNode(int val, ListNode next) { this.val = val; this.next = next; }
 * }
 */
class Solution { 最优简递归
    public ListNode reverseList(ListNode head) {
        if (head == null || head.next ==null) {
            return head;	//注意这里要返回head，记录最后一个位置作为返回对象
        }
        ListNode cur = reverseList(head.next);
        head.next.next = head;
        head.next=null;
        return cur;
    }
}
```

其他解答：

```java
// 双指针
class Solution {
    public ListNode reverseList(ListNode head) {
        ListNode prev = null;
        ListNode cur = head;
        ListNode temp = null;
        while (cur != null) {
            temp = cur.next;// 保存下一个节点
            cur.next = prev;
            prev = cur;
            cur = temp;
        }
        return prev;
    }
}

// 另一种递归 
class Solution {
    public ListNode reverseList(ListNode head) {
        return reverse(null, head);
    }

    private ListNode reverse(ListNode prev, ListNode cur) {
        if (cur == null) {
            return prev;
        }
        ListNode temp = null;
        temp = cur.next;// 先保存下一个节点
        cur.next = prev;// 反转
        // 更新prev、cur位置
        // prev = cur;
        // cur = temp;
        return reverse(cur, temp);
    }
}
```

注意递归的退出条件以及返回值很关键。

## 19 删除链表的倒数第N个节点 2

```java
class Solution {
    public ListNode removeNthFromEnd(ListNode head, int n) {
        ListNode pre = head, del = head, cur = head;
        while (n > 1) {
            cur = cur.next;
            n--;
        }
        if (cur.next == null){
            return head.next;   //删除节点为头节点的情况，这里犯了错误，没考虑到，记住：使用虚拟头节点会简化这重代码
        }
        while (cur.next != null) {
            pre = del;  //保持待删除结点的前一节点
            del = del.next;
            cur = cur.next;
        }
        pre.next = del.next;
        return head;
    }
}
```

标答使用了虚拟节点：

```java
class Solution {
    public ListNode removeNthFromEnd(ListNode head, int n) {
        ListNode dummy = new ListNode(-1);
        dummy.next = head;

        ListNode slow = dummy;
        ListNode fast = dummy;
        while (n-- > 0) {
            fast = fast.next;
        }
        // 记住 待删除节点slow 的上一节点
        ListNode prev = null;
        while (fast != null) {
            prev = slow;
            slow = slow.next;
            fast = fast.next;
        }
        // 上一节点的next指针绕过 待删除节点slow 直接指向slow的下一节点
        prev.next = slow.next;
        // 释放 待删除节点slow 的next指针, 这句删掉也能AC
        slow.next = null;

        return dummy.next;
    }
}
```

## 02.07 链表相交 1

```java
public class Solution {	//消除差距，指针同步
    public ListNode getIntersectionNode(ListNode headA, ListNode headB) {
        if (headA == null || headB == null) {
            return null;
        }
        int num1 = 0, num2 = 0;
        ListNode cur1 = headA, cur2 = headB;
        while (cur1 != null) {
            num1++;
            cur1 = cur1.next;
        }
        while (cur2 != null) {
            num2++;
            cur2 = cur2.next;
        }
        cur1 = headA;
        cur2 = headB;
        if (num1 > num2) {
            while (num1 - num2 > 0) {
                cur1 = cur1.next;
                num1--;
            }
        }else {
            while (num2 - num1 > 0) {
                cur2 = cur2.next;
                num2--;
            }
        }
        while (cur1 != null && cur1 != cur2){
            cur1 = cur1.next;
            cur2 = cur2.next;
        }
        return cur1;
    }
}
```

## 142 环形链表

```java
public class Solution {	//快慢指针法
    public ListNode detectCycle(ListNode head) {
        if (head == null){
            return null;
        }
        int flag = 0;
        ListNode fast = head, slow = head;
        while (fast != null && fast.next != null  ){
            fast = fast.next.next;
            slow = slow.next;
            if (fast == slow) {	//如果有，快指针一定会在循环某处相遇
                flag = 1;
                break;
            }
        }
        if (flag == 1) {
            while (head != slow) {	//再相遇点同步走，再相遇则是入口点，可话链表循环图根据x，y，z长度推导
                head = head.next;
                slow = slow.next;
            }
            return head;
        }else{
            return null;
        }
    }
}
```

标答：

```java
public class Solution {
    public ListNode detectCycle(ListNode head) {
        ListNode slow = head;
        ListNode fast = head;
        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
            if (slow == fast) {// 有环
                ListNode index1 = fast;
                ListNode index2 = head;
                // 两个指针，从头结点和相遇结点，各走一步，直到相遇，相遇点即为环入口
                while (index1 != index2) {
                    index1 = index1.next;
                    index2 = index2.next;
                }
                return index1;
            }
        }
        return null;
    }
}
```

# 哈希表

需要判断一个元素是否出现过的场景也应该第一时间想到哈希法！

##  242.有效的字母异位词 1

```Java
class Solution {
    public boolean isAnagram(String s, String t) {
        int[] record = new int[26];
        for(char s1 : s.toCharArray()) {	//for each 语法
            record[(int)s1 - (int)'a'] ++;	//哈希表对应位置值+1
        }
        for(char s2 :t.toCharArray()) {
            record[(int)s2 - (int)'a'] --;	//哈希表对应位置值-1
        }
        for(int i : record) {
            if (i!=0){
                return false;	//如果出现多余或少的字母，则不是异味词
            }
        }
        return true;
    }
}
```

## 349. 两个数组的交集 3

```java
class Solution {
    public int[] intersection(int[] nums1, int[] nums2) {
        int[] record = new int[1001], record2 = new int[1001];	//两个哈希表存储，1个用于哈希存储，1个用于哈希存储是否是交集的数
        int num = 0;
        for (int i = 0; i < nums1.length; i++) {
            record[nums1[i]] ++;
        }
        for (int i = 0; i < nums2.length; i++) {
            if (record[nums2[i]] > 0) {		//说明表1存在这个数，需要记录
                record2[nums2[i]] ++;		
                if (record2[nums2[i]] == 1)		//判断是否是同一个数，只记录一次！！这里犯过错误
                    num ++;
            }
        }
        int[] result = new int[num];		//存储交集的数
        for (int i = 0; i < record2.length; i++) { 	//不能用for each语法，因为要记录下标
            if (record2[i] > 0)
                result[num---1] = i;
        }
        return result;
    }
}
```

标答：采用`HashSet`,但比上诉代码多花1ms.

```java
import java.util.HashSet;

class Solution {
    public int[] intersection(int[] nums1, int[] nums2) {
        HashSet <Integer> set1 = new HashSet <Integer>(), set2 = new HashSet <Integer>();
        for (int a : nums1) {
            set1.add(a);
        }
        for (int a : nums2) {
            if (set1.contains(a)) {
                set2.add(a);
            }
        }
        int[] result = new int[set2.size()];
        int i=0;
        for (int a : set2) {
            result[i++] = a;
        }
        return result;
    }
}
```

## 202 快乐数 1

```Java
// import java.util.HashSet;

class Solution {
    public boolean isHappy(int n) {
        Set<Integer> record = new HashSet<Integer>();
        int sum = 0;
        while (n != 1) {
            int temp = n;
            while (temp >= 10){
                int num = temp % 10;
                sum += num * num;
                temp /= 10;
            }
            sum += temp * temp;
            if (record.contains(sum)) {	//如果出现存在的数，则不可能到1；
                return false;
            }else {
                record.add(sum);
            }
            n = sum;
            sum = 0;
        }
        return true;
    }
}
```

## 1 两数之和 1

```Java
class Solution {
    public int[] twoSum(int[] nums, int target) {
        Map< Integer, Integer> record = new HashMap< Integer, Integer>();
        int[] result = new int[2];
        for (int i = 0; i < nums.length; i++) {
            if (record.containsKey(target - nums[i])) {	//map中是否存在一个数满足，否则则加入这个数。
                result[0] = i;
                result[1] = record.get(target - nums[i]);
                return result;
            }else {
                record.put(nums[i], i);		//加入这个数，看是否后面的数满足
            }
        }
        return result;
    }
}
```

## 454 四数相加II 2

```Java
class Solution {
    public int fourSumCount(int[] nums1, int[] nums2, int[] nums3, int[] nums4) {
        Map<Integer, Integer> record = new HashMap<Integer, Integer>();
        int count = 0;
        for (int a : nums1) {
            for (int b : nums2) {
                if (!record.containsKey(a + b)) {
                    record.put(a + b, 1);
                }else {
                    record.put(a + b, record.get(a + b) + 1);	//覆盖原来的值，key+1
                }
            }
        }
        for (int c : nums3) {
            for (int d : nums4) {
                if (record.containsKey(0-c-d)) {
                    count += record.get(0-c-d);	//满足条件，计算有多少个
                }
            }
        }
        return count;
    }
}
```

## 383  赎金信 1

```Java
class Solution {	//固定的数组且很小，用数组代替map更快。
    public boolean canConstruct(String ransomNote, String magazine) {
        Map<Character, Integer> record = new  HashMap<Character, Integer>();
        for (char c : magazine.toCharArray()) {
            if (!record.containsKey(c)) {
                record.put(c, 1);
            }else {
                record.put(c, record.get(c) + 1);
            }
        }
        for (char c : ransomNote.toCharArray()) {
            if (record.containsKey(c) && record.get(c) > 0) {
                record.put(c, record.get(c) - 1);
            }else {
                return false;
            }
        }
        return true;
    }
}	
```

## 15 三数之和 3

```Java
class Solution {
    public List<List<Integer>> threeSum(int[] nums) {
        List<List<Integer>> result = new ArrayList<>();
        Arrays.sort(nums);	//记住
        int sum = 0, right, left;
        for (int i = 0; i < nums.length - 2; i++) {
            if (nums[i] > 0) return result;	//剪枝
            if (i > 0 && nums[i] == nums[i-1]) continue;	//去重
            right = nums.length-1;
            left = i + 1;
            while (right > left) {
                sum = nums[i] + nums[left] + nums[right];
                if (sum == 0) {
                    result.add(List.of(nums[i], nums[left], nums[right]));   
                    while (nums[right] == nums[right-1] && right - 1 > left ) {
                        right--;	//去重判断
                    }
                    while (nums[left] == nums[left+1] && left + 1 < right) {
                        left++;
                    }
                    right--;
                    left++;     //这里的移动很重要，否则一旦sum等于0时，进入死循环，指针锁死。
                }else if (sum > 0) {
                    right --;	//这里和下面其实还可以加去重判断
                }else {
                    left ++;	
                }
            }
        }
        return result;
    }
}
```

## 18 四数之和 2

```Java
class Solution {	//本题类似三数之和，多一重循环，5数6数就继续加循环，双指针只是减少一重循环
    public List<List<Integer>> fourSum(int[] nums, int target) {
        List<List<Integer>> result = new ArrayList<>();
        Arrays.sort(nums);
        int left, right,sum;
        for (int i = 0; i < nums.length - 3; i ++) {
            // if (nums[i] > target)      return result;  //这里不能加，因为存在负数，右边继续负数相加其实可以等于target
            if (i > 0 && nums[i] == nums[i-1])     continue;
            for (int j = i + 1; j < nums.length - 2; j ++) {
                if (j > i + 1 && nums[j] == nums[j-1])   continue;
                for (left = j + 1, right = nums.length -1; left < right;) {
                    sum = nums[i] + nums[j] + nums[left] + nums[right];
                    if (sum > target) {
                        right --;
                        while (left < right) {
                            if (nums[right + 1] == nums[right]) right --;
                            else break;
                        }                        
                    } else if (sum < target) {
                        left ++;
                        while (left < right) {
                            if (nums[left - 1] == nums[left]) left ++;
                            else break;
                        }
                    }else {
                        result.add(List.of(new Integer(nums[i]), new Integer(nums[j]), new Integer(nums[left]), new Integer(nums[right])));
                        left ++;
                        right --;
                        while (left < right) {
                            if (nums[right + 1] == nums[right]) right --;
                            else break;
                        }
                        while (left < right) {
                            if (nums[left - 1] == nums[left]) left ++;
                            else break;
                        }                                                   
                    }
                }
            }
        }
        return result;
    }
}
```

## 344.反转字符串

```java
class Solution {
    public void reverseString(char[] s) {
        int left = 0, right = s.length - 1;
        while (left < right) {
            char temp = s[left];
            s[left] = s[right];
            s[right] = temp;
            right --;
            left ++;
        }
    }
}
```

123

