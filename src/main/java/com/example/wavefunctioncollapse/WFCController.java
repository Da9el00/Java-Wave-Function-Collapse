package com.example.wavefunctioncollapse;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;

import java.net.URL;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.ResourceBundle;

public class WFCController implements Initializable {
    double planeWidth;
    double planeHeight;
    int tilesAcross;
    int tilesDown;
    int tileAmount;
    Grid grid;
    int gridSize = 50;

    Image cellAImage = new Image(String.valueOf(getClass().getResource("/cell/cellA.png")));
    Image cellBImage = new Image(String.valueOf(getClass().getResource("/cell/cellB.png")));
    Image cellCImage = new Image(String.valueOf(getClass().getResource("/cell/cellC.png")));
    Image cellDImage = new Image(String.valueOf(getClass().getResource("/cell/cellD.png")));
    Image cellEImage = new Image(String.valueOf(getClass().getResource("/cell/cellE.png")));

    ArrayList<Image> cellImages = new ArrayList<>(Arrays.asList(cellAImage, cellBImage,cellCImage,cellDImage,cellEImage));

    int[] optionA = new int[]{0,0,0,0};
    int[] optionB = new int[]{1,1,0,1};
    int[] optionC = new int[]{1,1,1,0};
    int[] optionD = new int[]{0,1,1,1};
    int[] optionE = new int[]{1,0,1,1};
    ArrayList<int[]> options = new ArrayList<>(Arrays.asList(optionA, optionB, optionC, optionD, optionE));


    HashMap<int[], Image> optionMap = new HashMap<>();

    @FXML
    private AnchorPane plane;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        planeWidth = plane.getPrefWidth();
        planeHeight = plane.getPrefHeight();

        optionMap.put(optionA, cellAImage);
        optionMap.put(optionB, cellBImage);
        optionMap.put(optionC, cellCImage);
        optionMap.put(optionD, cellDImage);
        optionMap.put(optionE, cellEImage);
        setupGrid();
        updateGrid(0);
    }

    private void setupGrid(){
        tilesAcross = (int) (planeWidth / gridSize);
        tileAmount = (int) ((planeWidth / gridSize) * (planeHeight / gridSize));
        tilesDown = tileAmount / tilesAcross;

        grid = new Grid(tilesAcross, tilesDown, options);
        //waveFunctionCollapse();
    }

    private void updateGrid(int type) {
        for (int i = 0; i < tileAmount; i++) {
            int x = (i % tilesAcross);
            int y = (i / tilesAcross);

            Rectangle rectangle = new Rectangle(x * gridSize, y * gridSize, gridSize, gridSize);

            if(type == 0){
                ArrayList<int[]> cellOptions = grid.getGrid().get(i).getOptions();

                if(cellOptions.isEmpty()){
                    rectangle.setFill(Color.BLACK);
                } else {
                    if(cellOptions.size() == 1) {
                        rectangle.setFill(new ImagePattern(optionMap.get(cellOptions.get(0))));
                    }
                }
            } else if (type == 1) {
                rectangle.setFill(new ImagePattern(cellImages.get(new SecureRandom().nextInt(cellImages.size()))));
            }

            plane.getChildren().add(rectangle);
        }
    }

    @FXML
    void wfcPlane(ActionEvent event) {
        plane.getChildren().clear();
        setupGrid();
        waveFunctionCollapse();
        updateGrid(0);
    }

    @FXML
    void randomPlane(ActionEvent event){
        plane.getChildren().clear();
        setupGrid();
        updateGrid(1);
    }

    private void waveFunctionCollapse(){
        boolean keepGoing = true;
        while (keepGoing){
            keepGoing = grid.collapse();
        }
    }

    public void nextPhase(ActionEvent event) {
        grid.collapse();
        updateGrid(0);
    }
}