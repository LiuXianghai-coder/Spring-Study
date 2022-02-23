package org.xhliu.springflowable.reflect;

/**
 * @author xhliu
 * @time 2022-02-23-下午10:00
 */
public class Child extends Parent{
    private final int val = 1;

    public Child() {
//        val = 1;
    }

    public void sayHello() {
        System.out.println("Hello..");
    }

    static {
        System.out.println("child static code block");
    }

    {
        System.out.println("child simple code block");
    }

    @Override
    public String toString() {
        return "Child{" +
                "val=" + val +
                '}';
    }
}
