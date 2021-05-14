package org.menu.model;

public class Empty {
    private int good;

    public Empty(int good) {
        this.good = good;
    }

    public int getGood() {
        return good;
    }

    public void setGood(int good) {
        this.good = good;
    }

    @Override
    public String toString() {
        return "Empty{" +
                "good=" + good +
                '}';
    }
}
