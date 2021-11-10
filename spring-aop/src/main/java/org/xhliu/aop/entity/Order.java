package org.xhliu.aop.entity;

/**
 * @author xhliu2
 * @create 2021-11-10 16:38
 **/
public class Order {
    private final String userName;

    private final String product;

    public Order(String userName, String product) {
        this.userName = userName;
        this.product = product;
    }

    public static class Builder {
        private String userName;
        private String product;

        public Builder userName(String userName) {
            this.userName = userName;
            return this;
        }

        public Builder product(String product) {
            this.product = product;
            return this;
        }

        public Order build() {
            return new Order(this.userName, this.product);
        }
    }

    public String getUserName() {
        return userName;
    }

    public String getProduct() {
        return product;
    }

    @Override
    public String toString() {
        return "Order{" +
                "userName='" + userName + '\'' +
                ", product='" + product + '\'' +
                '}';
    }
}
