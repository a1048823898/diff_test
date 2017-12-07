package com.yzl.diff.difftest.bean;

/**
 * Created by 沈小建 on 2017-12-07.
 */

public class MockData {

    private int id;

    private String name;

    public MockData(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
