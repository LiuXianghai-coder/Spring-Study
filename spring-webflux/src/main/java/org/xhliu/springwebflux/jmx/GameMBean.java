package org.xhliu.springwebflux.jmx;

public interface GameMBean {
    void playFootball(String clubName);

    String getPlayName();

    void setPlayName(String playName);
}
