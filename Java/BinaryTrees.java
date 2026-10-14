class BinaryTrees
{
    public class TreeNode
    {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode() {}
        TreeNode(int val)
        {
            this.val = val;
        }
        TreeNode(int val, TreeNode left, TreeNode right)
        {
            this.val = val;
            this.left = left;
            this.right = right;
        }
    }

    private int globalSum = 0;

    public TreeNode bstToGst(TreeNode root)
    {
        transform(root);
        return root;
    }

    private void transform(TreeNode node)
    {
        if (node == null)
        {
            return;
        }

        // 1. Visit the Right subtree (Greater values)
        transform(node.right);

        // 2. Process current node
        globalSum += node.val;
        node.val = globalSum;

        // 3. Visit the Left subtree (Smaller values)
        transform(node.left);
    }




    void main()
    {

    }
}