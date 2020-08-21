package model;

import java.util.ArrayList;
import java.util.List;

public class NestedInteger {
    private List<NestedInteger> list = new ArrayList<>();
    private Integer integer;

    // Constructor initializes a single integer.
    public NestedInteger(Integer value){
        this.integer = value;
    }

    // @return true if this NestedInteger holds a single integer, rather than a nested list
    public boolean isInteger() {
        return this.integer != null;
    }

    // @return the single integer that this NestedInteger holds, if it holds a single integer
    // Return null if this NestedInteger holds a nested list
    public Integer getInteger() {
        return this.integer != null ? this.integer : null;
    }

    // Set this NestedInteger to hold a single integer.
    public void setInteger(int value) {
        this.integer = value;
    }

    // Set this NestedInteger to hold a nested list and adds a nested integer to it.
    public void add(NestedInteger ni) {
        this.list.add(ni);
    }

    // @return the nested list that this NestedInteger holds, if it holds a nested list
    // Return null if this NestedInteger holds a single integer
    public List<NestedInteger> getList() {
        return integer != null ? list : null;
    }
}
