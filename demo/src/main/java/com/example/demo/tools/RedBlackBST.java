package com.example.demo.tools;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.atomic.AtomicInteger;

public class RedBlackBST<T extends Comparable<T>> {
    private final static boolean RED    =   Boolean.TRUE;
    private final static boolean BLACK  =   Boolean.FALSE;

    private static class Node<T extends Comparable<T>>
            implements Comparable<T> {
        private T val;

        private Node<T> left, right;

        private boolean color;

        private int size;

        public Node(T val) {
            this.val =  val;
        }

        public T getVal() {return this.val;}

        public void setLeft(Node<T> left) {this.left = left;}

        public Node<T> getLeft() {return this.left;}

        public Node<T> getRight() {return right;}

        public void setRight(Node<T> right) {this.right = right;}

        public void setSize(final int size) {this.size = size;}

        public int getSize() {return this.size;}

        @Override
        public int compareTo(T o) {
            return this.getVal().compareTo(o);
        }

        public boolean isColor() {
            return color;
        }

        public void setColor(boolean color) {
            this.color = color;
        }
    }

    private Node<T> root;

    public RedBlackBST() {
        this.root = null;
    }

    public void add(Node<T> node) {
        root = add(root, node);
        root.color = BLACK;
    }

    /*
        添加一个节点，添加时将这个节点的颜色置为红色，对于一个添加的节点，可能有以下几种情况
        1. 添加的位置为左子节点
            1> 对于父节点为黑链接的情况，直接插入即可
            2> 对于父节点为红链接的情况，首先需要对父节点进行一次右旋转，再进行一次颜色转换
                parent                   left                      left
              //      \                //     \\                  /    \
            left     right   ======> node    parent     ====>   node   parent
            //                                   \                        \
           node                                  right                    right

        2. 添加的位置为右子节点
           1> 如果父节点的左子节点为黑链接，只需对父节点进行一次左旋转即可
               parent                    parent
                    \\                 //
                    node  =====>     node

           2> 如果父节点的左子节点为红链接，那么需要进行一次颜色转换
               parent                   parent
             //     \\                 /     \
            left     node  =====>    left    node

           3> 如果父节点为红链接，那么需要首先对父节点进行一次左旋转，
              再对父节点的父节点进行一次右旋转，再进行一次颜色转换
               //                      //
             parent                  parent              parent               parent
                 \\                //                  //    \\              /     \
                 node   ======>  node         ====>  node          =====>   node
     */
    private Node<T> add(Node<T> parent, Node<T> node) {
        if (parent == null) {
            Node<T> node1 = new Node<>(node.val);
            node1.size = 1;
            node1.color = RED;
            return node1;
        }

        int compare = parent.compareTo(node.val);
        if (compare > 0)
            parent.left = add(parent.left, node);
        else if (compare < 0)
            parent.right = add(parent.right, node);
        else
            parent.val = node.val;

        if (isRed(parent.left) && isRed(parent.right))
            flipColor(parent);
        if (isRed(parent.right) && !isRed(parent.left))
            parent = rotateLeft(parent, parent.right);
        if (isRed(parent.left) && isRed(parent.left.left))
            parent = rotateRight(parent, parent.left);

        parent.size = getSize(parent.left) + getSize(parent.right) + 1;

        return parent;
    }

    public void delMin() {
        if (root == null) return;

        if (!isRed(root.left) && !isRed(root.right))
            root.color = RED;

        root = delMin(root);

        if (isEmpty()) root.color = BLACK;
    }

    /*
        如果当左子节点为一个 2- 节点，并且它的兄弟节点不是一个 2- 节点，此时将左子节点的
        兄弟节点中的一个键移动到左子节点中，使得左子节点成为一个 3- 节点
        这种情况如下图所示：

                parent                        leftB
               /      \                      /      \
           leftA     rightA       ====>   parent   rightA
                    //    \\              //           \\
                  leftB  rightB         leftA          rightB

        如果当前节点的左子节点和它的兄弟节点都是 2- 节点，
        那么需要将左子节点、父节点中的最小节点和左子节点最近的一个兄弟节点合并成为一个 4- 节点，
        使得父节点从一个 3- 节点转换为 2- 节点或者从一个 4- 节点转换为 3- 节点
        这种情况如下图所示：

                 node                    node
                //   \\                 /    \\
              parent  ?    =======>  parent   ?
             /      \               //    \\
           leftA   rightA         leftA   rightA

        到最后会得到将待删除的节点放到一个 3- 节点或者 4- 节点中，
        在这些节点中删除节点不会影响树的平衡
     */
    private Node<T> delMin(Node<T> parent) {
        if (parent.left == null)
            return null;

        /*
            如果当前的左子节点是一个单纯的 2- 节点时，需要将它组合为一个 3- 节点
         */
        if (!isRed(parent.left) && !isRed(parent.left.left))
            parent = moveRedLeft(parent);

        parent.left = delMin(parent.left);

        return balance(parent);
    }

    public void delMax() {
        if (root == null) return;
        if (!isRed(root.left) && isRed(root.right))
            root.color = RED;

        root = delMax(root);

        if (isEmpty()) root.color = BLACK;
    }

    /*
        移除当前节点树的最大节点
     */
    private Node<T> delMax(Node<T> parent) {
        /*
            确保 right 节点对于存在左子节点的节点来讲始终是存在的
         */
        if (isRed(parent.left))
            parent = rotateRight(parent, parent.left);

        if (parent.right == null)
            return null;

        /*
            如果当前的右子节点是一个单纯的 2- 节点，
            那么需要从兄弟节点和父节点找到一个节点来组成 3- 节点或者 4- 节点
         */
        if (!isRed(parent.right) && !isRed(parent.right.left))
            parent = moveRedRight(parent);

        parent.right = delMax(parent.right);

        return balance(parent);
    }

    public void delete(Node<T> node) {
        if (root == null) {
            throw new RuntimeException("当前根节点为空");
        }

        if (!isRed(root.left) && !isRed(root.right))
            root.color = RED;

        root = delete(root, node);

        if (isEmpty()) root.color = BLACK;
    }

    private Node<T> delete(Node<T> parent, Node<T> node) {
        if (node.compareTo(parent.val) < 0) {
            if (!isRed(parent.left) && !isRed(parent.left.left))
                parent = moveRedLeft(parent);
            parent.left = delete(parent.left, node);
        } else {
            if (isRed(parent.left))
                parent = rotateRight(parent, parent.left);

            if (node.compareTo(parent.val) == 0 && parent.right == null)
                return null;

            if (!isRed(parent.right) && !isRed(parent.right.left))
                parent = moveRedRight(parent);

            if (node.compareTo(parent.val) == 0) {
                Node<T> x = min(parent.right);
                parent.val = x.val;
                parent.right = delMin(parent.right);
            } else {
                parent.right = delete(parent.right, node);
            }
        }

        return balance(parent);
    }

    private T get(Node<T> x, T key) {
        while (x != null) {
            int cmp = key.compareTo(x.val);

            if (cmp < 0) x = x.left;
            else if (cmp > 0) x = x.right;
            else return x.val;
        }

        return null;
    }

    private Node<T> min(Node<T> parent) {
        if (parent.left == null)
            return parent;
        else
            return min(parent.left);
    }

    /*
        这里的操作就是移动左子节点的兄弟节点（当前的左子节点是一个 2- 节点），
        使得左子节点为一个 3- 节点（移动兄弟节点为父节点，将父节点组合左子节点为一个 3- 节点）

        如果当前的节点的左子节点是一个 2- 节点并且兄弟节点不是一个 2- 节点，
        那么将兄弟节点的一个节点移动左子节点中，使得左子节点成为一个 3- 节点
        此时的情况如下所示：

            parent                    parent                        parent                     leftB
           /      \                  //    \\                      //     \\                  /     \
         leftA   rightA    ====>   leftA   rightA       ====>   leftA     leftB     ===>   parent   rightA
                //    \\                   //   \\                            \\           //          \\
              leftB    ?                 leftB   ?                            rightA      leftA         ?
                                                                                 \\
                                                                                  ?
        如果当前的节点的左右子节点都是 2- 节点，
        那么将父节点进行转换，使得父节点、左右子节点组合成为一个新的 4- 节点
        此时的情况如下所示：

            parent                  parent
           /     \      ====>     //      \\
         leftA  rightA          leftA     rightA

        父节点在向下遍历的过程中不会是一个 2- 节点，
        因为之前的遍历保证当前的父节点是一个 3- 节点或者 4- 节点
     */
    private Node<T> moveRedLeft(Node<T> parent) {
        // 在向下遍历的过程中要分解根节点，避免处理时父节点为 4- 节点
        flipColor(parent);

        if (isRed(parent.right.left)) {
            parent.right = rotateRight(parent.right, parent.right.left);
            parent = rotateLeft(parent, parent.right);
            flipColor(parent);
        }

        return parent;
    }

    /*
        与 moveRedLeft 相反，移动右子节点的兄弟节点，
        使得右子节点成为一个 3- 节点或者 4- 节点

        如果当前的节点的右子节点是一个 2- 节点并且兄弟节点不是一个 2- 节点，
        那么将兄弟节点的一个节点移动右子节点中，使得右子节点成为一个 3- 节点
        此时的情况如下所示：

            parent                    parent
           /      \                  //    \\
         leftA   rightA    ====>   leftA   rightA
         //                        //
       leftB                     leftB
     */
    private Node<T> moveRedRight(Node<T> parent) {
        /*
            确保父节点不会是一个 4- 节点
         */
        flipColor(parent);

        if (!isRed(parent.left.left))
            parent = rotateRight(parent, parent.left);

        return parent;
    }

    /*
        重新平衡当前节点
     */
    private Node<T> balance(Node<T> parent) {
        if (parent == null) {
            throw new RuntimeException("试图重新平衡一个空的节点");
        }

        if (isRed(parent.right) && !isRed(parent.left))
            parent = rotateLeft(parent, parent.right);
        if (isRed(parent.left) && isRed(parent.left.left))
            parent = rotateRight(parent, parent.left);
        if (isRed(parent.left) && isRed(parent.right))
            flipColor(parent);

        parent.size = getSize(parent.left) + getSize(parent.right) + 1;
        return parent;
    }

    public Node<T> search(T val) {
        return search(root, val);
    }

    private Node<T> search(Node<T> parent, T val) {
        if (null == parent) return null;

        int compare = parent.compareTo(val);

        if (compare == 0) return parent;
        else if (compare < 0) return search(parent.right, val);
        else return search(parent.left, val);
    }

    /*
        中序遍历该树对象以得到改树的基本形状
     */
    public void trace() {
        // 创建对应的 dot 文件以便生成对应的树图
        StringBuilder sb = new StringBuilder();
        sb.append("digraph redBlack {\n");
        firstTraverse(root, sb);
        sb.append("}");
        try {
            File file = new File("D:/tmp/Graphviz");
            if (!file.exists()) {
                if (file.mkdir()) {
                    System.out.println("mkdir success...");
                }
                file = new File("D:/tmp/Graphviz/redBlack.dot");

                if (!file.exists()) {
                    if (file.createNewFile()) {
                        System.out.println("create avl.dot file success....");
                    }
                }
            }

            /*
                生成二插树的脚本文件：
https://gist.githubusercontent.com/nanpuyue/b5950f20937f01aa43227d269aa83918/raw/dec7bc293ef051ca159c546d14c6caed75be111c/tree.g
                保存该文件为 tree.g
                执行：dot ./redBlack.dot | gvpr -c -f tree.g | neato -n -Tpng -o redblack.png 即可
             */
            Path path = Paths.get("D:/tmp/Graphviz/redBlack.dot");
            Files.write(path, sb.toString().getBytes(StandardCharsets.UTF_8));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private final static AtomicInteger index = new AtomicInteger();

    private void firstTraverse(Node<T> node, StringBuilder builder) {
        if (node == null) return;

        if (node.left == null && node.right == null)
            return;

        if (node.left != null) {
            builder.append("\t")
                    .append(node.val.toString())
                    .append("->")
                    .append(node.left.val.toString());
            if (node.left.color == RED)
                builder.append("[color=\"red\"]");
            builder.append(";\n");
        } else {
            builder.append("\t")
                    .append(node.val.toString())
                    .append(String.format("-> null%d[style=invis];null%d[style=invis]\n",
                                    index.get(),
                                    index.getAndIncrement()
                            )
                    );
        }
        if (node.right != null) {
            builder.append("\t")
                    .append(node.val.toString())
                    .append("->")
                    .append(node.right.val.toString());
            if (node.right.color == RED)
                builder.append("[color=\"red\"]");
            builder.append(";\n");
        } else {
            builder.append("\t")
                    .append(node.val.toString())
                    .append(String.format("-> null%d[style=invis];null%d[style=invis]\n",
                                    index.get(),
                                    index.getAndIncrement()
                            )
                    );
        }

        firstTraverse(node.left, builder);
        firstTraverse(node.right, builder);
    }

    /*
        对 above 节点进行一次 左旋转

            above                              below
           /     \\                           //    \
          A      below    ============>    above     C
                 /   \                     /    \
                B     C                   A      B
     */
    private Node<T> rotateLeft(Node<T> above, Node<T> below) {
        above.right  =   below.left;
        below.left   =   above;

        below.color  =   above.color;
        above.color  =   RED;

        below.size   =  above.size;
        above.size   =  getSize(above.left)
                + getSize(above.right)
                + 1;

        return below;
    }


    /*
        对 above 节点进行一次右旋转

            above                        below
           //    \                      /     \\
         below    C    =========>      A      above
        /     \                               /    \
       A       B                             B      C
     */
    private Node<T> rotateRight(Node<T> above, Node<T> below) {
        above.left      =   below.right;
        below.right     =   above;

        below.color     =   above.color;
        above.color     =   RED;

        below.size      =   above.size;
        above.size      =   getSize(above.left)
                + getSize(above.right)
                + 1;
        return below;
    }

    private void flipColor(Node<T> parent) {
        if (null == parent) return;

        parent.color = !parent.color;

        parent.left.color   = !parent.left.color;
        parent.right.color  = !parent.right.color;
    }

    private int getSize(Node<T> node) {
        if (null == node) return 0;
        return node.size;
    }

    private boolean isRed(Node<T> node) {
        if (null == node) return false;

        return node.color == RED;
    }

    public boolean isEmpty() {
        return root != null;
    }

    public static void main(String[] args) {
        RedBlackBST<Integer> tree = new RedBlackBST<>();

        tree.add(new Node<>(8));
        tree.add(new Node<>(10));
        tree.add(new Node<>(14));
        tree.add(new Node<>(6));
        tree.add(new Node<>(7));
        tree.add(new Node<>(1));
        tree.add(new Node<>(4));
        tree.add(new Node<>(23));
        tree.add(new Node<>(3));

        tree.delete(new Node<>(10));
        tree.delMin();
//        tree.delMin();

        tree.trace();
    }
}
