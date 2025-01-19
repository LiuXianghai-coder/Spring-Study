package com.example.demo.algorithm;

/**
 * 超平面的类型定义
 *
 *@author lxh
 */
public abstract class React {

    /**
     * 根据维度的部分，划分对应的左节点超平面
     *
     * @param point 当前待处理的点
     * @param dimension 当前的划分维度
     * @return 经过构造后得到的左超平面
     */
    public abstract React buildLeftReact(Point point, int dimension);

    /**
     * 与 {@link #buildLeftReact(Point, int)} 类似，区别在于构造的是右边的超平面
     *
     * @param point 当前待处理的点
     * @param dimension 当前的划分维度
     * @return 经过构造后得到的右超平面
     */
    public abstract React buildRightReact(Point point, int dimension);

    /**
     * 当前超超平面距离点的距离（一般实现可以考虑为 <a href="https://zh.wikipedia.org/wiki/%E6%AC%A7%E5%87%A0%E9%87%8C%E5%BE%97%E8%B7%9D%E7%A6%BB">欧几里得距离</a>）的平方
     * 作为返回结果
     *
     * @param point 当前在查询的点
     * @return 当前超超平面距离点的距离
     */
    public abstract double distanceSquaredTo(Point point);

    /**
     * 检查当前的超平面中是否包含当前待查询的点
     *
     * @param point 待查询的点
     * @return 如果当前超平面包含待查询的点，则返回 {@code true}, 否则返回 {@code false}
     */
    public abstract boolean contains(Point point);

    /**
     * 检查当前超平面是否与待查询的超平面相交
     *
     * @param react 当前待查询的超平面
     * @return 如果但那给钱超平面与待查询的超平面相交，则返回 {@code true}, 否则，返回 {@code false}
     */
    public abstract boolean intersects(React react);
}
