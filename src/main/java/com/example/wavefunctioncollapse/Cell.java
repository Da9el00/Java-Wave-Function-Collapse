package com.example.wavefunctioncollapse;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Collections;

public class Cell implements Comparable<Cell>{
    private ArrayList<int[]> options;
    private boolean collapsed;
    private SecureRandom random = new SecureRandom();

    public Cell(ArrayList<int[]> options) {
        this.options = options;
        collapsed = false;
    }

    public int entropy(){
        return options.size();
    }

    public void update(){
        collapsed = options.size() <= 1;
    }

    public void collapse(){
        options = new ArrayList<>(Collections.singletonList(options.get(random.nextInt(options.size()))));
        collapsed = true;
    }

    public boolean isCollapsed() {
        return collapsed;
    }

    public void setOptions(ArrayList<int[]> options) {
        this.options = options;
    }

    public ArrayList<int[]> getOptions() {
        return options;
    }

    @Override
    public int compareTo(Cell o) {
        return Integer.compare(entropy(), o.entropy());
    }
}
