package com.lch.ccims.demo.algorithm;

import java.util.*;

/**
 * 算法解决方案
 */
public class Solution {

    /**
     * 如何判断一个数是否为2的整数次幂
     * <p>
     * 解决方案：采用位与运算
     * 问题分析
     * 如果一个整数是2的整数次幂，那么当它转化成二进制时，只有最高位是1，其他位都是0！
     * 如果2的整数次幂一旦减1，它的二进制数字就全部变成了1！
     * 而0和1按位与运算的结果是0，所以凡是2的整数次幂和它本身减1的结果进行与运算，结果都必定是0。
     * 反之，如果一个整数不是2的整数次幂，结果一定不是0！
     * 最后，对于一个整数n，只需要计算n&(n-1)的结果是不是0。这个方法的时间复杂度只有O（1）。
     *
     * @param num
     * @return
     */
    public static boolean isPowerOf2(int num) {
        return (num & num - 1) == 0;
    }

    /**
     * 归并排序
     * <p>
     * 问题分析
     * 归并排序就像是组织一场元素之间的“比武大会”，这场比武大会分成两个阶段：
     * 1.分组: 假设集合一共有n个元素，算法将会对集合进行逐层的折半分组。
     * 2.归并: 当每个小组内部比较出先后顺序以后，小组之间会展开进一步的比较和排序，合并成一个大组；
     * 大组之间继续比较和排序，再合并成更大的组......最终，所有元素合并成了一个有序的集合。
     */
    public static void mergeSort(int[] array, int start, int end) {
        if (start < end) {
            //折半成两个小集合，分别进行递归
            int mid = (start + end) / 2;
            mergeSort(array, start, mid);
            mergeSort(array, mid + 1, end);
            // 把两个有序小集合，归并成一个大集合
            merge(array, start, mid, end);
        }

    }

    private static void merge(int[] array, int start, int mid, int end) {
        //开辟额外大集合，设置指针
        int[] tempArray = new int[end - start + 1];
        int p1 = start; //左指针
        int p2 = mid + 1; //右指针
        int p = 0; //数组临时指针
        //比较两个小集合的元素，依次放入大集合
        while (p1 <= mid && p2 <= end) {
            if (array[p1] <= array[p2]) {
                tempArray[p++] = array[p1++];
            } else {
                tempArray[p++] = array[p2++];
            }
        }
        //左侧小集合还有剩余，依次放入大集合尾部
        while (p1 <= mid) {
            tempArray[p++] = array[p1++];
        }
        //右侧小集合还有剩余，依次放入大集合尾部
        while (p2 <= end) {
            tempArray[p++] = array[p2++];
        }
        //把大集合的元素复制回原数组
        for (int i = 0; i < tempArray.length; i++) {
            array[i + start] = tempArray[i];
        }
    }

    /**
     * 问题：找出不大于N的最大的2的幂指数
     * 解决方案：采用位运算
     *
     * @return
     */
    public static int findN(int n) {
        n |= n >> 1;
        n |= n >> 2;
        n |= n >> 4;
        n |= n >> 8; // 整型一般是 32 位，上面我是假设 8 位
        return (n + 1) >> 1;
    }

    /**
     * 问题：交换两个数
     * 解决方案：采用位运异或运算
     *
     * @param x
     * @param y
     */
    public static void swop(int x, int y) {
        x = x ^ y;
        y = x ^ y;
        x = x ^ y;
        System.out.println("x = " + x + "; y = " + y);
    }

    /**
     * 给你一组整型数据，这些数据中，其中有一个数只出现了一次，其他的数都出现了两次，让你来找出一个数。
     * 解决方案：采用位运异或运算
     *
     * @param array
     * @return
     */
    public static int findNumber(int[] array) {
        int temp = array[0];
        for (int i = 1; i < array.length; i++) {
            temp = temp ^ array[i];
        }
        return temp;
    }

    /**
     * 大整数求和
     *
     * @param bigNumberA 大整数A
     * @param bigNumberB 大整数B
     * @return
     */
    public static String bigNumberSum(String bigNumberA, String bigNumberB) {
        //1.把两个大整数用数组逆序存储，数组长度等于较大整数位数+1
        int maxLength = bigNumberA.length() > bigNumberA.length() ? bigNumberA.length() : bigNumberB.length();
        int[] arrayA = new int[maxLength + 1];
        int[] arrayB = new int[maxLength + 1];
        for (int i = 0; i < bigNumberA.length(); i++) {
            //“ - '0'”是将String型转化为int型
            arrayA[i] = bigNumberA.charAt(bigNumberA.length() - 1 - i) - '0';
        }
        for (int i = 0; i < bigNumberB.length(); i++) {
            //“ - '0'”是将String型转化为int型
            arrayB[i] = bigNumberB.charAt(bigNumberB.length() - 1 - i) - '0';
        }
        //2.构建result数组，数组长度等于较大整数位数+1
        int[] result = new int[maxLength + 1];
        //3.遍历数组，按位相加
        for (int i = 0; i < result.length; i++) {
            int temp = result[i];
            temp += arrayA[i];
            temp += arrayB[i];
            //判断是否进位
            if (temp > 10) {
                temp = temp - 10;
                result[i + 1] = 1;
            }
            result[i] = temp;
        }

        //4.把result数组再次逆序并转成String
        StringBuilder sb = new StringBuilder();
        //是否找到大整数的最高有效位
        boolean findFirst = false;
        for (int i = 0; i < result.length; i++) {
            if (!findFirst) {
                if (result[i] == 0) {
                    continue;
                }
                findFirst = true;
            }
            sb.append(result[i]);
        }
        return sb.toString();
    }

    /**
     * 题目描述: 现在有五十亿个int类型的正整数，要从中找出重复的数并返回
     * 分析： 判断50亿个数有哪些是重复和判断一个数置否存在50亿个数当中是否存在，其实是一样的。
     * 我们采用bitmap算法来做。不过这里50亿个数，别人肯定是以文件流的形式给你的。
     * 这样我们为了方便，我们就假设这些数是以存在int型数组的形式给我们的。
     *
     * @param array
     * @return
     */
    public static Set<Integer> bigNumberFind(int[] array) {
        int j = 0;
        //用来把重复的数返回，存在Set里，这样避免返回重复的数
        Set<Integer> output = new HashSet<>();
        BitSet bitSet = new BitSet(Integer.MAX_VALUE);
        int i = 0;
        while (i < array.length) {
            int value = array[i];
            //判断该数是否存在bitSet里
            if (bitSet.get(value)) {
                output.add(value);
            } else {
                bitSet.set(value, true);
            }
            i++;
        }
        return output;
    }


    /**
     * 构建二叉树
     *
     * @param inputList 输入序列
     * @return
     */
    public static TreeNode createBinaryTree(LinkedList<Integer> inputList) {
        TreeNode node = null;
        if (inputList == null || inputList.isEmpty()) {
            return null;
        }
        Integer data = inputList.removeFirst();
        if (data != null) {
            node = new TreeNode(data);
            node.leftChild = createBinaryTree(inputList);
            node.rightChild = createBinaryTree(inputList);
        }
        return node;
    }

    /**
     * 二叉树前序遍历
     *
     * @param node 二叉树节点
     */
    public static void preOrderTraveral(TreeNode node) {
        if (node == null) {
            return;
        }
        System.out.println(node.data);
        preOrderTraveral(node.leftChild);
        preOrderTraveral(node.rightChild);
    }

    /**
     * 二叉树中序遍历
     *
     * @param node 二叉树节点
     */
    public static void inOrderTraveral(TreeNode node) {
        if (node == null) {
            return;
        }
        inOrderTraveral(node.leftChild);
        System.out.println(node.data);
        inOrderTraveral(node.rightChild);
    }

    /**
     * 二叉树后序遍历
     *
     * @param node 二叉树节点
     */
    public static void postOrderTraveral(TreeNode node) {
        if (node == null) {
            return;
        }
        postOrderTraveral(node.leftChild);
        postOrderTraveral(node.rightChild);
        System.out.println(node.data);
    }

    /**
     * 二叉树节点
     */
    private static class TreeNode {
        int data;
        TreeNode leftChild;
        TreeNode rightChild;

        TreeNode(int data) {
            this.data = data;
        }
    }

    /**
     * 二叉树非递归前序遍历
     *
     * @param root 二叉树根节点
     */
    public static void preOrderTraveralWithStack(TreeNode root) {
        Stack<TreeNode> stack = new Stack<>();
        TreeNode treeNode = root;
        while (treeNode != null || !stack.isEmpty()) {
            while (treeNode != null) {
                System.out.println(treeNode.data);
                stack.push(treeNode);
                treeNode = treeNode.leftChild;
            }
            if (!stack.isEmpty()) {
                treeNode = stack.pop();
                treeNode = treeNode.rightChild;
            }
        }
    }

    /**
     * 二叉树层序遍历
     *
     * @param root 二叉树根节点
     */
    public static void levelOrderTraversal(TreeNode root) {
        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);
        while (!queue.isEmpty()) {
            TreeNode node = queue.poll();
            System.out.println(node.data);
            if (node.leftChild != null) {
                queue.offer(node.leftChild);
            }
            if (node.rightChild != null) {
                queue.offer(node.rightChild);
            }
        }
    }

    /**
     * 二分查找法
     *
     * @param array
     * @param target
     * @return
     */
    public static int binarySearch(int[] array, int target) {
        //查找范围起点
        int start = 0;
        //查找范围终点
        int end = array.length - 1;
        //查找范围中位数
        int mid;
        while (start <= end) {
            //mid=(start+end)/2 有可能溢出
            mid = start + (end - start) / 2;
            if (array[mid] == target) {
                return mid;
            } else if (array[mid] < target) {
                start = mid + 1;
            } else {
                end = mid - 1;
            }
        }
        return -1;
    }


    public static void main(String[] args) {

        // 二分查找法
//        int[] array = new int[1000];
//        for(int i=0; i<1000;i++){
//            array[i] = i;
//        }
//        System.out.println(binarySearch(array, 173));
//
//        LinkedList<Integer> inputList = new LinkedList<>(
//                Arrays.asList(new Integer[]{3,2,9,null,null,10,null,null,8,null,4}));
//        TreeNode treeNode = createBinaryTree(inputList);
//        System.out.println(" 前序遍历："  );
//        preOrderTraveral(treeNode);
//        System.out.println(" 中序遍历：");
//        inOrderTraveral(treeNode);
//        System.out.println(" 后序遍历：");
//        postOrderTraveral(treeNode);
//        System.out.println(" 二叉树非递归前序遍历: ");
//        preOrderTraveralWithStack(treeNode);
//        System.out.println(" 二叉树层序遍历: ");
//        levelOrderTraversal(treeNode);

        // 如何判断一个数是否为2的整数次幂
//        System.out.println(isPowerOf2(1024));

        // 给一个数组，归并排序
//        int[] array = {5, 8, 6, 3, 9, 2, 1, 7};
//        mergeSort(array,0,array.length-1);
//        System.out.println(Arrays.toString(array));

        // 找出不大于N的最大的2的幂指数
//        System.out.println( findN(521));
        // 交换两个数
//        int x = 1;
//        int y = 2;
//        swop(x, y);
        // 给你一组整型数据，这些数据中，其中有一个数只出现了一次，其他的数都出现了两次，让你来找出一个数。
//        int[] array2 = {1,2,3,4,6,7,9,6,8};
//        System.out.println(findNumber(array2));
//        // 有1亿个数字，其中有2个是重复的，快速找到它，时间和空间要最优
//        System.out.println(bigDataFindOneNum(array2));
        // 位图法
        //将随机数放入bitset中
        /**BitSet bit = new BitSet();
         Random random = new Random();
         for (int i = 0; i < 1000000000; i++) {
         bit.set(random.nextInt(1000));
         }
         System.out.println(bit.toString());
         System.out.println(bit.get(100));*/

        //大整数求和
        //System.out.println(bigNumberSum("426709752318", "95481253129"));

        // 现在有五十亿个int类型的正整数，要从中找出重复的数并返回
//        int[] bigArray = {1,2,3,4,5,6,7,8,3,4};
//        Set<Integer> t2 = bigNumberFind(bigArray);
//        System.out.println(t2);

        // 如何将一个链表“逆序”
        Node head = new Node(1);
        head.next = new Node(2);
        Node temp = head.next;
        temp.next = new Node(3);
        temp = temp.next;
        temp.next = new Node(4);
        temp = temp.next;
        temp.next = new Node(7);
        // 逆序前输出链表
        temp = head;
        while (temp != null) {
            System.out.println(temp.data);
            temp = temp.next;
        }

        //逆序链表
        reverseLinkedList(head);

        // 逆序后输出链表
        temp = head;
        while (temp != null) {
            System.out.println(temp.data);
            temp = temp.next;
        }

    }

    /**
     * 逆转链表
     */
    private static void reverseLinkedList(Node head) {
        if (head == null || head.next == null) {
            return;
        }
        Node p1 = head;
        Node p2 = head.next;
        Node p3 = null;
        while (p2 != null) {
            p3 = p2.next;
            p2.next = p1;
            p1 = p2;
            p2 = p3;
        }
        // 下一节点指向空
        head.next = null;
        //
        head = p1;

    }

    private static class Node {
        int data;
        Node next;

        Node(int data) {
            this.data = data;
        }
    }

    /**
     * 有1亿个数字，其中有2个是重复的，快速找到它，时间和空间要最优
     * 用位图法解决
     *
     * @param array 一亿个数据的数据源
     * @return 返回重复的数字或不存在返回-1
     */
    public static int bigDataFindOneNum(int[] array) {
        // 最大数 + 1
        int max = 100000001;
        // 初始化位图
        BitSet bitSet = new BitSet(max);
        int i = 0;
        while (i < array.length) {
            int value = array[i];
            //判断该数是否存在bitSet里
            if (bitSet.get(value)) {
                // 返回重复的数
                return value;
            } else {
                bitSet.set(value, true);
            }
            i++;
            System.out.println(i);
        }
        // 不存在返回-1
        return -1;
    }

}
