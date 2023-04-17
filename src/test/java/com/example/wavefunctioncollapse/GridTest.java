package com.example.wavefunctioncollapse;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GridTest {

    int[] optionA = new int[]{0,0,0,0};
    int[] optionB = new int[]{1,1,0,1};
    int[] optionC = new int[]{1,1,1,0};
    int[] optionD = new int[]{0,1,1,1};
    int[] optionE = new int[]{1,0,1,1};
    ArrayList<int[]> optionsCell = new ArrayList<>(Arrays.asList(optionA, optionB, optionC, optionD, optionE));
    Grid grid = new Grid(2,2,optionsCell);


    @Test
    public void optionComparisonUpTest(){
        ArrayList<int[]> optionsCell = new ArrayList<>(Arrays.asList(optionA, optionB, optionC, optionD, optionE));
        ArrayList<int[]> optionsComparative = new ArrayList<>(Arrays.asList(optionA, optionB, optionC, optionD));
        int position = 0;

        ArrayList<int[]> result = grid.findAllowedOptions(optionsCell, optionsComparative, position);

        ArrayList<int[]> expectedResult = new ArrayList<>(Arrays.asList(optionA, optionB, optionC, optionD, optionE));

        Assertions.assertEquals(expectedResult, result);
    }

    @Test
    public void optionComparisonUpRightTest(){
        ArrayList<int[]> optionsCell = new ArrayList<>(Arrays.asList(optionA, optionB, optionC, optionD, optionE));
        ArrayList<int[]> optionsComparativeUp = new ArrayList<>(Arrays.asList(optionD));
        ArrayList<int[]> optionsComparativeRight = new ArrayList<>(Arrays.asList(optionD));

        ArrayList<int[]> preResult = grid.findAllowedOptions(optionsCell, optionsComparativeUp, 0);
        ArrayList<int[]>result = grid.findAllowedOptions(preResult, optionsComparativeRight, 1);

        ArrayList<int[]> expectedResult = new ArrayList<>(Arrays.asList(optionB, optionC));

        Assertions.assertEquals(expectedResult, result);
    }

    @Test
    public void optionComparisonLeftTest(){
        ArrayList<int[]> optionsCell = new ArrayList<>(Arrays.asList(optionA, optionB, optionC, optionD, optionE));
        ArrayList<int[]> optionsComparativeRight = new ArrayList<>(Arrays.asList(optionD));

        ArrayList<int[]> result = grid.findAllowedOptions(optionsCell, optionsComparativeRight, 3);

        ArrayList<int[]> expectedResult = new ArrayList<>(Arrays.asList(optionB, optionD, optionE));

        Assertions.assertEquals(expectedResult, result);
    }

    @Test
    public void optionComparisonRightOnlyE(){
        ArrayList<int[]> optionsCell = new ArrayList<>(Arrays.asList(optionA, optionB, optionC, optionD, optionE));
        ArrayList<int[]> optionsComparativeRight = new ArrayList<>(Arrays.asList(optionE));

        ArrayList<int[]> result = grid.findAllowedOptions(optionsCell, optionsComparativeRight, 3);

        ArrayList<int[]> expectedResult = new ArrayList<>(Arrays.asList(optionA, optionC));

        Assertions.assertEquals(expectedResult, result);
    }

    @Test
    public void optionComparisonLeftRightTest(){
        ArrayList<int[]> optionsCell = new ArrayList<>(Arrays.asList(optionA, optionB, optionC, optionD, optionE));
        ArrayList<int[]> optionsComparativeLeft = new ArrayList<>(Arrays.asList(optionD));
        ArrayList<int[]> optionsComparativeUp = new ArrayList<>(Arrays.asList(optionA));
        ArrayList<int[]> optionsComparativeRight = new ArrayList<>(Arrays.asList(optionE));

        ArrayList<int[]> result1 = grid.findAllowedOptions(optionsCell, optionsComparativeLeft, 3);
        ArrayList<int[]> result2 = grid.findAllowedOptions(result1, optionsComparativeUp, 0);
        ArrayList<int[]> result = grid.findAllowedOptions(result2, optionsComparativeRight, 1);

        ArrayList<int[]> expectedResult = new ArrayList<>(Arrays.asList(optionD));

        Assertions.assertEquals(expectedResult, result);
    }

    @Test
    public void collapseTest(){
        ArrayList<int[]> optionsCell = new ArrayList<>(Arrays.asList(optionA, optionB, optionC, optionD, optionE));
        ArrayList<int[]> optionsComparativeRight = new ArrayList<>(Arrays.asList(optionD));
        ArrayList<int[]> optionsComparativeDown = new ArrayList<>(Arrays.asList(optionA, optionB, optionC, optionD, optionE));
        ArrayList<int[]> optionsComparativeLeft = new ArrayList<>(Arrays.asList(optionE));

        ArrayList<int[]> result1 = grid.findAllowedOptions(optionsCell, optionsComparativeRight, 1);
        ArrayList<int[]> result2 = grid.findAllowedOptions(result1, optionsComparativeDown, 2);
        ArrayList<int[]> result = grid.findAllowedOptions(result2, optionsComparativeLeft, 3);

        ArrayList<int[]> expectedResult = new ArrayList<>(Arrays.asList(optionC));

        Assertions.assertEquals(expectedResult, result);
    }

    @Test
    public void collapseTestEmpty(){
        ArrayList<int[]> optionsCell = new ArrayList<>(Arrays.asList(optionA, optionB, optionC, optionD, optionE));
        ArrayList<int[]> optionsComparativeUp = new ArrayList<>(Arrays.asList(optionE));
        ArrayList<int[]> optionsComparativeRight = new ArrayList<>(Arrays.asList(optionC));
        ArrayList<int[]> optionsComparativeDown = new ArrayList<>(Arrays.asList(optionB));
        ArrayList<int[]> optionsComparativeLeft = new ArrayList<>(Arrays.asList(optionA));

        ArrayList<int[]> result1 = grid.findAllowedOptions(optionsCell, optionsComparativeUp, 1);
        ArrayList<int[]> result2 = grid.findAllowedOptions(result1, optionsComparativeRight, 1);
        ArrayList<int[]> result3 = grid.findAllowedOptions(result2, optionsComparativeDown, 2);
        ArrayList<int[]> result = grid.findAllowedOptions(result3, optionsComparativeLeft, 3);

        ArrayList<int[]> expectedResult = new ArrayList<>(List.of());

        Assertions.assertEquals(expectedResult, result);
    }

    @Test
    public void collapseTestEmptyDown(){
        ArrayList<int[]> optionsCell = new ArrayList<>(Arrays.asList(optionA, optionB, optionC, optionD, optionE));
        ArrayList<int[]> optionsComparativeUp = new ArrayList<>(Arrays.asList(optionA));
        ArrayList<int[]> optionsComparativeRight = new ArrayList<>(Arrays.asList(optionD));
        ArrayList<int[]> optionsComparativeLeft = new ArrayList<>(Arrays.asList(optionE));

        ArrayList<int[]> result1 = grid.findAllowedOptions(optionsCell, optionsComparativeUp, 1);
        ArrayList<int[]> result2 = grid.findAllowedOptions(result1, optionsComparativeRight, 1);
        ArrayList<int[]> result = grid.findAllowedOptions(result2, optionsComparativeLeft, 3);

        ArrayList<int[]> expectedResult = new ArrayList<>(List.of());

        Assertions.assertEquals(expectedResult, result);
    }

    @Test
    public void collapseTestEmptyDownNoRight(){
        ArrayList<int[]> optionsCell = new ArrayList<>(Arrays.asList(optionA, optionB, optionC, optionD, optionE));
        ArrayList<int[]> optionsComparativeUp = new ArrayList<>(Arrays.asList(optionA));
        ArrayList<int[]> optionsComparativeLeft = new ArrayList<>(Arrays.asList(optionE));

        ArrayList<int[]> result1 = grid.findAllowedOptions(optionsCell, optionsComparativeUp, 1);
        ArrayList<int[]> result = grid.findAllowedOptions(result1, optionsComparativeLeft, 3);

        ArrayList<int[]> expectedResult = new ArrayList<>(List.of(optionA));

        Assertions.assertEquals(expectedResult, result);
    }

}
