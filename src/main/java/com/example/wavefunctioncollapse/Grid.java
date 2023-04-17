package com.example.wavefunctioncollapse;

import java.security.SecureRandom;
import java.util.ArrayList;

public class Grid {
    private final int width;
    private final int height;
    private final ArrayList<Cell> grid = new ArrayList<>();
    private final ArrayList<int[]> options;

    public Grid(int width, int height, ArrayList<int[]>options) {
        this.width = width;
        this.height = height;
        this.options = options;
        setupGrid();
    }

    private void setupGrid(){
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                grid.add(new Cell(options));
            }
        }
    }

    private Cell getCellByHeuristic(){
        ArrayList<Cell> mapOrdered = (ArrayList<Cell>) grid.clone();
        mapOrdered.removeIf(Cell::isCollapsed);
        if(mapOrdered.isEmpty()){
            return null;
        }
        mapOrdered.sort(Cell::compareTo);
        Cell leastEntropyCell = mapOrdered.get(0);
        mapOrdered.removeIf(cell -> {
            return cell.entropy() != leastEntropyCell.entropy();
        });
        return mapOrdered.get(new SecureRandom().nextInt(mapOrdered.size()));
    }

    public boolean collapse(){
        Cell cell = getCellByHeuristic();
        if(cell != null){
            cell.collapse();
        } else {
            return false;
        }

        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                //Find current cell
                int index = i * width + j;
                Cell currentCell = grid.get(index);
                if(!currentCell.isCollapsed()){

                    ArrayList<int[]> cumulativeValidOptions = currentCell.getOptions();
                    //Check up
                    int indexUP = index - width;
                    if( indexUP >= 0){
                        cumulativeValidOptions = findAllowedOptions(cumulativeValidOptions, grid.get(indexUP).getOptions(), 0);
                    }
                    //Check down
                    int indexDown = index + width;
                    if( indexDown < grid.size()){
                        cumulativeValidOptions = findAllowedOptions(cumulativeValidOptions, grid.get(indexDown).getOptions(), 2);
                    }
                    //Check left
                    if(index % width != 0){
                        int indexLeft = index - 1;
                        cumulativeValidOptions = findAllowedOptions(cumulativeValidOptions, grid.get(indexLeft).getOptions(), 3);
                    }
                    //Check right
                    if((index + 1) % width != 0){
                        int indexRight = index + 1;
                        cumulativeValidOptions = findAllowedOptions(cumulativeValidOptions, grid.get(indexRight).getOptions(), 1);
                    }
                    currentCell.setOptions(cumulativeValidOptions);
                }
            }
        }
        return true;
    }

    protected   ArrayList<int[]> findAllowedOptions(ArrayList<int[]> cumulativeValidOptions,  ArrayList<int[]> comparativeCellOptions, int position){
        ArrayList<int[]> newCumulativeValidOptions = new ArrayList<>();

        cumulativeValidOptions.forEach(option -> {
            for (int[] comparativeCellOption: comparativeCellOptions) {
                if(option[position] == comparativeCellOption[(position + 2) % 4]){
                    newCumulativeValidOptions.add(option);
                    break;
                }
            }
        });
        return  newCumulativeValidOptions;
    }

    public ArrayList<Cell> getGrid() {
        return grid;
    }
}
