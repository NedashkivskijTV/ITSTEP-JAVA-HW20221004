package edu.itstep;

public class User {
    private String name;
    private int prizeMoney;

    public User() {
    }
    public User(String name, int prizeMoney) {
        this.name = name;
        this.prizeMoney = prizeMoney;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrizeMoney() {
        return prizeMoney;
    }

    public void setPrizeMoney(int prizeMoney) {
        this.prizeMoney = prizeMoney;
    }
}
