package org.xhliu.springwebflux.jmx;

public class Game implements GameMBean{
    private String playerName;

    @Override
    public void playFootball(String clubName) {
        System.out.println(this.playerName + " playing football for " + clubName);
    }

    @Override
    public String getPlayName() {
        System.out.println("return playerName to value " + this.playerName);
        return this.playerName;
    }

    @Override
    public void setPlayName(String playName) {
        System.out.println("Set playerName to value " + playName);
        this.playerName = playName;
    }
}
