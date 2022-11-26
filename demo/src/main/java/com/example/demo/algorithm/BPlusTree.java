package com.example.demo.algorithm;

/**
 * B+ 树的 Java 实现
 *
 * @author : xhliu
 * @param <K> : B+ 树中键值对存储元素索引的 key
 * @param <V> : B+ 树中实际存储数据的载体
 */
@SuppressWarnings("unchecked")
public class BPlusTree<K extends Comparable<K>, V> {

    /**
     * B+ 树中的节点，宏观的节点，用于存储实际存储单元的容器
     */
    static class Node {
        int m;
        Entry[] children;
        // prev: 前一个叶子节点，suc: 后继叶子节点
        Node prev, suc;

        public Node(int m, int M) {
            this.m = m;
            this.children = new Entry[M + 1];
            this.children[0] = new Entry(null, null, null);
        }
    }

    /**
     * 实际存储数据的对象
     */
    static class Entry {
        private Comparable key;
        private Object val;
        private Node next;

        public Entry(Comparable key, Object val, Node next) {
            this.key = key;
            this.val = val;
            this.next = next;
        }
    }

    private final int M;

    private Node root;

    private int height;

    private int size;

    public BPlusTree(int m) {
        if (m % 2 == 0 || m < 2) {
            throw new IllegalArgumentException("阶数 M 只能是大于 1 的奇数");
        }

        M = m;
        this.root = new Node(0, M);
    }

    int height() {
        return this.height;
    }

    int size() {
        return this.size;
    }

    public V get(K key) {
        if (key == null) {
            throw new IllegalArgumentException("key 不能为 null");
        }

        return search(root, key, 0);
    }

    private V search(Node x, K key, int h) {
        if (x == null) return null;

        Entry[] entries = x.children;
        /*
            和 B 树的搜索不同的地方在于，B+ 树实际存储数据的元素只能在叶子节点上
         */
        if (h == height) {
            for (int i = 1; i <= x.m; ++i) {
                if (eq(entries[i].key, key))
                    return (V) entries[i].val;
            }
            return null;
        }

        for (int i = 1; i <= x.m; ++i) {
            if (eq(entries[i].key, key))
                return search(entries[i].next, key, h + 1);

            if (less(entries[i - 1].key, key) && less(key, entries[i].key))
                return search(entries[i - 1].next, key, h + 1);
        }

        return search(entries[x.m].next, key, h + 1);
    }

    public void put(K key, V val) {
        if (key == null) {
            throw new IllegalArgumentException("key 不能为 null");
        }

        Node result = insert(root, key, val, 0);
        size++;

        if (result == null) return;
        /*
            插入之后形成了分裂节点，此时树的高度增加，同时需要修改 root 节点指向的对象
         */
        root = result;
        height++;
    }

    public Entry delete(K key) {
        if (key == null) {
            throw new IllegalArgumentException("待删除的 key 不能为 null");
        }

        Entry entry = delete(null, root, key, 0);
        if (entry != null) size--;

        return entry;
    }

    private Node insert(Node x, K key, V val, int h) {
        int idx;
        Entry t = new Entry(key, val, null);
        Entry[] entries = x.children;

        if (h == height) {
            // 只能在叶子节点上完成元素的插入
            for (idx = 1; idx <= x.m; ++idx) {
                if (eq(entries[idx].key, key)) {
                    entries[idx].val = val;
                    size--;
                    return null;
                }
                if (less(key, entries[idx].key)) break;
            }
        } else {
            for (idx = 1; idx <= x.m; ++idx) {
                if (less(entries[idx].key, key) || eq(entries[idx].key, key)) continue;
                break;
            }

            // 插入到前一个区间元素中，因为此时的元素已经大于现有的 key 了
            Node u = insert(entries[idx - 1].next, key, val, h + 1);
            // 插入结果为 null 说明没有发生节点分裂，正常返回即可
            if (u == null) return null;

            /*
                由于此时发生了节点分裂，需要将分裂后的节点的根节点插入到当前的节点中，
                首先需要找到根节点的插入位置
            */
            for (idx = 1; idx <= x.m; ++idx)
                if (less(u.children[1].key, entries[idx].key)) break;

            entries[idx - 1].next = u.children[0].next;
            t = u.children[1]; // t 表示待插入的节点
        }

        if (M - idx >= 0)
            System.arraycopy(x.children, idx, x.children, idx + 1, M - idx);

        x.children[idx] = t;
        x.m++;

        if (x.m < M) return null;
        // 对叶子节点和索引节点采取不同的处理策略
        if (h == height) return splitLeaf(x);
        return splitIndex(x);
    }

    private Entry delete(Node parent, Node cur, K key, int h) {
        Entry entry;
        int idx;

        if (h == height) {
            for (idx = 1; idx <= cur.m; ++idx)
                if (eq(cur.children[idx].key, key)) break;

            // 如果当前叶子节点不存在这样的键值对元素，则跳过
            if (idx > cur.m) return null;
            entry = cur.children[idx];

            // 移动节点元素列表，删除元素
            if (cur.m + 1 - idx >= 0)
                System.arraycopy(cur.children, idx + 1, cur.children, idx, cur.m + 1 - idx);
            cur.m--;
        } else {
            for (idx = 1; idx <= cur.m; ++idx) {
                if (eq(cur.children[idx].key, key)) {
                    entry = delete(cur, cur.children[idx].next, key, h + 1);
                    if (cur.m < M / 2) reBalance(parent, cur, h);
                    return entry;
                }

                // 当前节点在该节点的后继节点中，递归进行删除
                if (less(key, cur.children[idx].key)) {
                    entry = delete(cur, cur.children[idx - 1].next, key, h + 1);
                    if (cur.m < M / 2) reBalance(parent, cur, h);
                    return entry;
                }
            }

            entry = delete(cur, cur.children[cur.m].next, key, h + 1);
        }

        if (cur.m < M / 2) reBalance(parent, cur, h);
        return entry;
    }

    /**
     * 分裂索引节点，由于索引节点不存储实际数据，因此直接从中间进行分裂即可
     *
     * @param x : 待分裂的节点
     * @return : 分裂之后得到的子树的根节点
     */
    private Node splitIndex(Node x) {
        Node t = new Node(M / 2, M);
        x.m = M / 2;

        Entry mid = x.children[M / 2 + 1];

        // 将 x 中的后半部分的节点放入 t 中
        for (int i = 1; i <= M / 2; ++i) {
            t.children[i] = x.children[M / 2 + i + 1];
            x.children[M / 2 + i + 1] = null;
        }

        Node p = new Node(1, M); // 分裂后形成的根节点

        // 调整相关的链接
        t.children[0].next = mid.next;
        x.children[M / 2 + 1] = null; // clear mid
        p.children[0].next = x;
        p.children[1] = new Entry(mid.key, mid.val, t);

        return p;
    }

    /**
     * 分裂叶子节点，在分裂叶子节点时需要将分裂后的叶子节点进行链接，从而满足
     * B+ 树的要求
     *
     * @param x : 待分裂的节点
     * @return : 分裂之后的子树的根节点
     */
    private Node splitLeaf(Node x) {
        Node t = new Node(M / 2 + 1, M);
        x.m = M / 2;

        Entry mid = x.children[M / 2 + 1]; // x 的中间节点，它的属性将会被作为根节点的属性

        // 将 x 中的后半部分的节点放入 t 中
        for (int i = 1; i <= M / 2 + 1; ++i) {
            t.children[i] = x.children[M / 2 + i];
            x.children[M / 2 + i] = null;
        }

        Node p = new Node(1, M); // 分裂后形成的根节点

        // 调整相关的链接
        p.children[0].next = x;
        p.children[1] = new Entry(mid.key, null, t);

        t.prev = x;
        x.suc = t;

        return p;
    }

    /**
     * 重新平衡当前节点的平衡性，具体行为如下: <br />
     * 1. 如果左右兄弟节点存在多余的元素，那么直接借用兄弟节点的一个元素来调整当前节点使得当前节点满足限制条件<br />
     * 2. 如果处理的节点是叶子节点，在调整时需要删除原有的分隔节点元素 <br />
     * 3. 如果处理的节点是索引节点，则按照一般 B 树的节点来调整当前的节点
     *
     * @param parent : 当前处理的节点的父节点
     * @param cur : 当前待处理的节点
     * @param h : 当前处理的节点的树的高度，用于判断当前处理的节点是否是叶子节点
     */
    private void reBalance(Node parent, Node cur, int h) {
        if (parent == null) return;

        int idx;
        Entry[] children = parent.children;

        for (idx = 1; idx <= parent.m; ++idx)
            if (less(cur.children[cur.m].key, children[idx].key))
                break;
        idx -= 1;

        Node left = null, right = null;
        if (idx > 0) left = children[idx - 1].next;
        if (idx < parent.m) right = children[idx + 1].next;

        if (left == null && right == null) return;

        if (left != null && left.m > M / 2) {
            reBalanceByLeft(left, cur, parent, idx);
            return;
        }

        if (right != null && right.m > M / 2) {
            reBalanceByRight(right, cur, parent, idx);
            return;
        }

        if (h == height) {
            if (left != null)
                reBalanceLeafByLeft(left, cur, parent, idx);
            else
                reBalanceLeafByRight(right, cur, parent, idx);
        } else {
            if (left != null)
                reBalanceIndexByLeft(left, cur, parent, idx);
            else
                reBalanceIndexByRight(right, cur, parent, idx);
        }
        parent.m--;

        if (parent.m == 0 && h == 1) {
            root = cur;
            height--;
        }
    }

    /**
     * 由于当前处理的节点存在左兄弟节点，因此这个时候会将左节点的最大元素插入到当前处理节点的最小元素所在的位置
     * 同时使用该移动的节点的属性覆盖掉原有的父节点的分隔元素的属性，从而实现 B+ 树节点的平衡性
     *
     * @param left : 当前处理节点的左兄弟节点
     * @param cur : 当前正在被处理的节点
     * @param parent : 当前处理的节点的父节点
     * @param idx : 当前父节点中分隔元素所在的索引位置
     */
    private void reBalanceByLeft(Node left, Node cur, Node parent, int idx) {
        Entry[] children = parent.children;

        for (int i = cur.m + 1; i > idx; --i) {
            if (cur.children[i] == null)
                cur.children[i] = new Entry(null, null, null);
            cur.children[i].key = cur.children[i - 1].key;
            cur.children[i].val = cur.children[i - 1].val;
        }

        // 复制属性到当前节点的第一个元素（从 1 开始计数）
        cur.children[1].key = children[idx].key;
        cur.children[1].val = children[idx].val;
        cur.children[1].next = cur.children[0].next;
        cur.children[0].next = left.children[left.m].next;
        cur.m++;

        // 将从左子节点借用到的元素的属性复制到父节点的分隔元素，使得树最终是有序的
        children[idx].key = left.children[left.m].key;
        children[idx].val = left.children[left.m].val;

        // 删除左子节点的最大元素
        left.children[left.m] = null;
        left.m--;
    }

    /**
     * 当前处理的节点存在右兄弟节点，因此这个时候需要将右兄弟节点的最小元素插入到当前处理节点的尾部，
     * 同时使用该元素的属性覆盖掉原有的父节点的分隔元素的属性。由于右兄弟节点被借用了一个元素，
     * 因此此时需要调整右兄弟节点所有元素的相对位置
     *
     * @param right : 当前处理节点的右兄弟节点
     * @param cur : 当前正在被处理的节点
     * @param parent : 当前处理的节点的父节点
     * @param idx : 当前处理的节点的父节点的分隔元素所在的位置
     */
    private void reBalanceByRight(Node right, Node cur, Node parent, int idx) {
        Entry[] children = parent.children;

        ++cur.m;
        // 如果此时这个位置的对象未实例化，那么首先实例化该位置的对象
        if (cur.children[cur.m] == null)
            cur.children[cur.m] = new Entry(null, null, null);

        // 单纯地复制属性到当前的节点，如果使用引用复制的话会导致出现冗余的链接，甚至出现环
        cur.children[cur.m].key = children[idx + 1].key;
        cur.children[cur.m].val = children[idx + 1].val;
        cur.children[cur.m].next = right.children[0].next;
        right.children[0].next = right.children[1].next;

        // 更新父节点的分隔元素
        children[idx + 1].key = right.children[1].key;
        children[idx + 1].val = right.children[1].val;

        // 由于右子节点被借用了一个元素，因此需要移动右子节点的元素列表使得其依旧是有序的
        if (right.m >= 0)
            System.arraycopy(right.children, 2, right.children, 1, right.m);
        right.m--;
    }

    /**
     * 当当前处理的节点的左右兄弟节点都不存在多余的元素，这种情况就需要合并一个兄弟节点成为一个新的节点
     * 由于 B+ 树的特性，需要对叶子节点和非叶子节点做不同的处理，对于叶子节点来讲，如果它存在左兄弟叶子
     * 节点，那么直接将当前的节点复制到左兄弟节点的末尾，同时删除原有的父节点的分隔元素
     *
     * @param left : 当前处理节点的左兄弟节点
     * @param cur : 当前正在处理的节点
     * @param parent : 当前处理节点的父节点
     * @param idx : 父节点中划分左兄弟节点和当前节点的元素的所在位置
     */
    private void reBalanceLeafByLeft(Node left, Node cur, Node parent, int idx) {
        Entry[] children = parent.children;

        for (int i = 1; i <= cur.m; ++i) {
            ++left.m;
            if (left.children[left.m] == null)
                left.children[left.m] = new Entry(null, null, null);

            left.children[left.m].key = cur.children[i].key;
            left.children[left.m].val = cur.children[i].val;
        }

        left.suc = cur;
        cur.prev = left;

        children[idx].next = null;
        children[idx] = null;

        if (parent.m + 1 - idx >= 0)
            System.arraycopy(children, idx + 1, children, idx, parent.m + 1 - idx);
    }

    /**
     * 当前处理的节点是一个叶子节点，并且当前处理的节点不存在左兄弟节点。
     * 由于当前处理的节点的左右兄弟节点都不存在多余的元素，同时处理的节点不存在左兄弟节点，因此需要将
     * 当前处理节点的所有元素都移动到右兄弟节点，然后删除父节点中分隔处理节点和右兄弟节点的元素
     *
     * @param right : 当前处理的节点的右兄弟节点
     * @param cur : 当前正在被处理的节点
     * @param parent : 当前处理节点的父节点
     * @param idx : 父节点中划分右兄弟节点和当前节点的元素的所在位置
     */
    private void reBalanceLeafByRight(Node right, Node cur, Node parent, int idx) {
        Entry[] children = parent.children;

        // 复制右兄弟节点的所有元素到当前的处理节点
        for (int i = 1; i <= right.m; ++i)
            cur.children[++cur.m] = right.children[i];

        cur.suc = right;
        right.prev = cur;

        if (parent.m + 1 - (idx + 1) >= 0)
            System.arraycopy(children, idx + 1 + 1, children, idx + 1, parent.m + 1 - (idx + 1));
    }

    /**
     * 由于当前处理的节点类型为索引节点，和叶子节点的处理不同，索引节点存在后继链接，因此在处理时需要对
     * 后继链接做额外的处理<br />
     * 将父节点中分隔当前节点和左兄弟节点的元素都移动到左兄弟节点，然后将当前节点的最左链接复制到移下来的
     * 分隔节点的后继链接，从而维护节点的平衡性
     *
     * @param left : 当前处理节点的左兄弟节点
     * @param cur : 当前正在处理的节点
     * @param parent : 当前正在处理的节点的父节点
     * @param idx : 父节点中划分左兄弟节点和当前节点的元素的所在位置
     */
    private void reBalanceIndexByLeft(Node left, Node cur, Node parent, int idx) {
        Entry[] children = parent.children;

        // 首先将父节点的分隔节点复制到当前节点的末尾，由于这个位置可能未实例化，因此首先实例化
        ++left.m;
        if (left.children[left.m] == null)
            left.children[left.m] = new Entry(null, null, null);

        left.children[left.m].key = children[idx].key;
        left.children[left.m].val = children[idx].val;
        left.children[left.m].next = cur.children[0].next;
        // 复制父节点的分隔节点结束。。。。

        // 再将当前节点的所有元素复制到左兄弟节点，由于位置 0 是一个哨兵元素，因此从元素 1 开始进行复制
        for (int i = 1; i <= cur.m; ++i)
            left.children[++left.m] = cur.children[i];

        // 合并之后，会出现一条多余的链接，这个链接是多余的
        children[idx].next = null;
        // 删除父节点的分隔元素之后，移动父节点的分隔元素列表，使得原有的父节点的元素依旧是有序的
        if (parent.m + 1 - idx >= 0)
            System.arraycopy(children, idx + 1, children, idx, parent.m + 1 - idx);
    }

    /**
     * 当前处理的节点是一个索引节点<br />
     * 由于当前处理的节点不存在左链接节点，因此如果该节点如果不是根节点（根节点的判断由调用该方法的程序检测），
     * 那么一定存在右兄弟节点<br />
     * 此时具体的做法为: <br />
     * 1. 父节点中的分隔元素移动到当前处理节点的末尾<br />
     * 2. 将该分隔元素的后继链接设置为右兄弟节点的最左链接 <br />
     * 3. 将右兄弟节点中的所有非哨兵节点复制到当前的节点 <br />
     * 4. 删除原有父节点中右兄弟节点的直接父节点元素<br />
     *
     * @param right : 当前处理的节点的右兄弟节点
     * @param cur : 当前正在处理的节点
     * @param parent : 当前处理的节点的父节点
     * @param idx : 当前处理的节点的父节点中的直接练级元素，即分隔当前节点和右兄弟节点的元素的前一个元素所在的位置
     */
    private void reBalanceIndexByRight(Node right, Node cur, Node parent, int idx) {
        Entry[] children = parent.children;

        ++cur.m;
        if (cur.children[cur.m] == null)
            cur.children[cur.m] = new Entry(null, null, null);
        cur.children[cur.m].key = children[idx + 1].key;
        cur.children[cur.m].val = children[idx + 1].val;
        cur.children[cur.m].next = right.children[0].next;
        children[idx + 1].next = null;

        // 复制右兄弟节点的所有元素到当前的处理节点
        for (int i = 1; i <= right.m; ++i)
            cur.children[++cur.m] = right.children[i];

        // 调整父节点的元素列表
        if (parent.m + 1 - (idx + 1) >= 0)
            System.arraycopy(children, idx + 1 + 1, children, idx + 1, parent.m + 1 - (idx + 1));
    }

    private boolean eq(Comparable<K> key1, Comparable<K> key2) {
        if (key1 == null || key2 == null) return false;
        return key1.compareTo((K) key2) == 0;
    }

    private boolean less(Comparable<K> key1, Comparable<K> key2) {
        if (key1 == null) return true;
        if (key2 == null) return false;
        return key1.compareTo((K) key2) < 0;
    }

    public String toString() {
        return toString(root, height, "") + "\n";
    }

    private String toString(Node h, int ht, String indent) {
        if (h == null) return "";
        StringBuilder s = new StringBuilder();
        Entry[] children = h.children;

        if (ht == 0) {
            for (int j = 0; j <= h.m; j++) {
                if (children[j] == null) continue;
                s.append(indent).append(children[j].key).append(" ").append(children[j].val).append("\n");
            }
        } else {
            for (int j = 0; j <= h.m; j++) {
                if (children[j] == null) continue;
                if (j > 0) s.append(indent).append("(").append(children[j].key).append(")\n");
                assert children[j] != null;
                s.append(toString(children[j].next, ht - 1, indent + "     "));
            }
        }
        return s.toString();
    }

    public static void main(String[] args) {
        BPlusTree<String, String> st = new BPlusTree<>(5);

        st.put("www.cs.princeton.edu", "128.112.136.12");
        st.put("www.cs.princeton.edu", "128.112.136.11");
        st.put("www.princeton.edu", "128.112.128.15");
        st.put("www.yale.edu", "130.132.143.21");
        st.put("www.simpsons.com", "209.052.165.60");
        st.put("www.apple.com", "17.112.152.32");
        st.put("www.amazon.com", "207.171.182.16");
        st.put("www.ebay.com", "66.135.192.87");
        st.put("www.cnn.com", "64.236.16.20");
        st.put("www.google.com", "216.239.41.99");
        st.put("www.nytimes.com", "199.239.136.200");
        st.put("www.microsoft.com", "207.126.99.140");
        st.put("www.dell.com", "143.166.224.230");
        st.put("www.slashdot.org", "66.35.250.151");
        st.put("www.espn.com", "199.181.135.201");
        st.put("www.weather.com", "63.111.66.11");
        st.put("www.yahoo.com", "216.109.118.65");


        System.out.println("cs.princeton.edu:  " + st.get("www.cs.princeton.edu"));
        System.out.println("hardvardsucks.com: " + st.get("www.harvardsucks.com"));
        System.out.println("simpsons.com:      " + st.get("www.simpsons.com"));
        System.out.println("apple.com:         " + st.get("www.apple.com"));
        System.out.println("ebay.com:          " + st.get("www.ebay.com"));
        System.out.println("dell.com:          " + st.get("www.dell.com"));
        System.out.println();

        System.out.println("size:    " + st.size());
        System.out.println("height:  " + st.height());
        System.out.println(st);
        System.out.println();

        BPlusTree<Integer, Integer> tree = new BPlusTree<>(5);
        for (int i = 1; i <= 22; ++i)
            tree.put(i, i);

        for (int i = 1; i <= 15; ++i)
            tree.delete(i);

        System.out.println("size:    " + tree.size());
        System.out.println("height:  " + tree.height());
        System.out.println(tree);
        System.out.println();
    }
}
