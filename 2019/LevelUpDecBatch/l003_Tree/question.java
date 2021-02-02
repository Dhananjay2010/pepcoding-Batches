import java.util.ArrayList;
import java.util.List;

public class question {
    public class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode(int val) {
            this.val = val;
        }
    }

    public int size(TreeNode node) {
        return node == null ? 0 : size(node.left) + size(node.right) + 1;
    }

    // Edges, for Nodes : return node == null ? 0
    public int height(TreeNode node) {
        return node == null ? -1 : Math.max(height(node.left), height(node.right)) + 1;
    }

    public int maximum(TreeNode node) {
        if (node == null)
            return -(int) 1e9;
        int lmv = maximum(node.left); // left maximum value
        int rmv = maximum(node.right); // right maximum value

        return Math.max(Math.max(lmv, rmv), node.val);
    }

    public boolean find(TreeNode node, int data) {
        if (node == null)
            return false;
        if (node.val == data) {
            return true;
        }

        return find(node.left, data) || find(node.right, data);
    }

    // ArrayList<Node>
    public boolean rootToNodePath(TreeNode node, TreeNode data, ArrayList<TreeNode> ans) {
        if (node == null)
            return false;
        if (node == data) {
            ans.add(node);
            return true;
        }

        boolean res = rootToNodePath(node.left, data, ans) || rootToNodePath(node.right, data, ans);
        if (res)
            ans.add(node);

        return res;

    }

    public ArrayList<TreeNode> rootToNodePath(TreeNode node, TreeNode data) {
        if (node == null)
            return new ArrayList<>();

        if (node == data) {
            ArrayList<TreeNode> base = new ArrayList<>();
            base.add(node);
            return base;
        }

        ArrayList<TreeNode> left = rootToNodePath(node.left, data);
        if (left.size() > 0) {
            left.add(node);
            return left;
        }

        ArrayList<TreeNode> right = rootToNodePath(node.right, data);
        if (right.size() > 0) {
            right.add(node);
            return right;
        }

        return new ArrayList<>();
    }

    // 236
    public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
        ArrayList<TreeNode> list1 = new ArrayList<>();
        ArrayList<TreeNode> list2 = new ArrayList<>();

        rootToNodePath(root, p, list1);
        rootToNodePath(root, q, list2);

        int i = list1.size() - 1;
        int j = list2.size() - 1;

        TreeNode LCA = null;

        while (i >= 0 && j >= 0) {
            if (list1.get(i) != list2.get(j)) // cpp : list1[i] == list2[j]
                break;

            LCA = list1.get(i);
            i--;
            j--;
        }

        return LCA;
    }

    public void printKDown(TreeNode node, TreeNode block, int depth, List<Integer> ans) {
        if (node == null || depth < 0 || node == block)
            return;

        if (depth == 0) {
            ans.add(node.val);
            return;
        }

        printKDown(node.left, block, depth - 1, ans);
        printKDown(node.right, block, depth - 1, ans);
    }

    // 863
    public List<Integer> distanceK(TreeNode root, TreeNode target, int K) {
        ArrayList<TreeNode> list = new ArrayList<>();
        rootToNodePath(root, target, list);

        List<Integer> ans = new ArrayList<>();
        TreeNode blockNode = null;

        for (int i = 0; i < list.size(); i++) {
            printKDown(list.get(i), blockNode, K - i, ans);
            blockNode = list.get(i);
        }
        return ans;
    }

    // better solution for k far
    public int distanceK2(TreeNode node, TreeNode target, int K, List<Integer> ans) {
        if (node == null)
            return -1;

        if (node == target) {
            printKDown(node, null, K, ans);
            return 1;
        }

        int lans = distanceK2(node.left, target, K, ans);
        if (lans != -1) {
            printKDown(node, node.left, K - lans, ans);
            return lans + 1;
        }

        int rans = distanceK2(node.right, target, K, ans);
        if (rans != -1) {
            printKDown(node, node.right, K - rans, ans);
            return rans + 1;
        }

        return -1;
    }

    public List<Integer> distanceK2(TreeNode root, TreeNode target, int K) {
        List<Integer> ans = new ArrayList<>();
        distanceK2(root, target, K, ans);
        return ans;
    }

    public static int rootToNodeDistance(TreeNode node, TreeNode data) {
        if (node == null)
            return -1;
        if (node == data)
            return 0;

        int lans = rootToNodeDistance(node.left, data);
        if (lans != -1)
            return lans + 1;

        int rans = rootToNodeDistance(node.right, data);
        if (rans != -1)
            return rans + 1;

        return -1;
    }

    // https://www.geeksforgeeks.org/burn-the-binary-tree-starting-from-the-target-node/

    public int diameterOfBinaryTree_01(TreeNode root) {
        if (root == null)
            return -1;
        int leftTreeDia = diameterOfBinaryTree_01(root.left);
        int rightTreeDia = diameterOfBinaryTree_01(root.right);

        int leftHeight = height(root.left);
        int rightHeight = height(root.right);

        return Math.max(Math.max(leftTreeDia, rightTreeDia), leftHeight + rightHeight + 2);
    }

    // {dia,height}
    public int[] diameterOfBinaryTree_02(TreeNode root) {
        if (root == null)
            return new int[] { -1, -1 };
        int[] leftAns = diameterOfBinaryTree_02(root.left);
        int[] rightAns = diameterOfBinaryTree_02(root.right);

        int[] ans = new int[2];
        ans[0] = Math.max(Math.max(leftAns[0], rightAns[0]), leftAns[1] + rightAns[1] + 2);
        ans[1] = Math.max(leftAns[1], rightAns[1]) + 1;

        return ans;
    }

    int maxDia = 0;

    public int diameterOfBinaryTree_03(TreeNode root) {
        if (root == null)
            return -1;
        int lh = diameterOfBinaryTree_03(root.left);
        int rh = diameterOfBinaryTree_03(root.right);

        maxDia = Math.max(maxDia, lh + rh + 2);

        return Math.max(lh, rh) + 1;
    }

    public int diameterOfBinaryTree(TreeNode root) {
        if (root == null)
            return 0;
        // return diameterOfBinaryTree_01(root);
        // return diameterOfBinaryTree_02(root)[0];
        diameterOfBinaryTree_03(root);
        return maxDia;
    }

}