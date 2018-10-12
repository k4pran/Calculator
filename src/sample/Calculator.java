package sample;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;

public class Calculator extends Application {

    private static TextField screen;
    private static StringBuilder screenText;
    private static KeyTracker keyTracker;
    private static boolean continuousMode;
    private static boolean displayingResult;
    private static HashSet<String> operators;

    @Override
    public void init() throws Exception {
        super.init();
        screenText = new StringBuilder();
        keyTracker = new KeyTracker();
        continuousMode = true;
        displayingResult = false;
        operators = new HashSet<String>() {{
            add("(");
            add(")");
            add("%");
            add("/");
            add("-");
            add("+");
            add("X");
        }};
    }

    @Override
    public void start(Stage primaryStage) throws Exception{
        Pane root = new Pane();
        StackPane calcView = new StackPane();
        calcView.setTranslateX(200);
        calcView.setTranslateY(200);

        Button modeToggle = new Button("Continuous");
        modeToggle.setOnAction(e -> changeMode(e));
        root.getChildren().add(calcView);
        root.getChildren().add(modeToggle);

        Rectangle calcOutline = new Rectangle(0, 0, 300, 300);
        calcOutline.setFill(new Color(0.1, 0.3, 0.9, 0.2));
        calcOutline.setStroke(Color.BLACK);
        calcOutline.setArcWidth(70);
        calcOutline.setArcHeight(70);

        GridPane calcKeys = new GridPane();
        calcKeys.setAlignment(Pos.BOTTOM_CENTER);
        calcKeys.setPadding(new Insets(0, 0, 20, 0));
        calcKeys.setVgap(20);
        calcKeys.setHgap(40);
        calcKeys.setMinSize(50, 50);

        VBox vBox = new VBox();
        vBox.setAlignment(Pos.CENTER);
        vBox.setSpacing(20);
        screen = new TextField();
        screen.setDisable(true);
        screen.setAlignment(Pos.CENTER_RIGHT);
        screen.setMaxWidth(150);
        screen.setMinHeight(20);
        screen.setTranslateX(0);
        screen.setTranslateY(0);
        vBox.getChildren().add(screen);
        vBox.getChildren().add(calcKeys);

        calcView.getChildren().add(calcOutline);
        calcView.getChildren().add(vBox);



        // node - row index - column index

        ArrayList<String> keySymbols = getSymbols();
        int keyIndex = 0;
        for (int row = 0; row < 5; row++) {
            for (int col = 0; col < 4; col++) {
                Button btn = new Button(keySymbols.get(keyIndex));
                btn.setOnAction(e -> handleKeyPress(e));
                btn.setMinWidth(35);
                calcKeys.add(btn, col, row);
                keyIndex++;
            }
        }

        primaryStage.setTitle("Calculator");
        primaryStage.setScene(new Scene(root, 800, 800));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

    public static void handleKeyPress(ActionEvent event) {
        try {
            Button btn = (Button) event.getSource();
            String keyPressed = btn.getText();
            keyTracker.addKey(keyPressed);
            if (keyPressed.equalsIgnoreCase("=")) {
                getResult();
            }
            else if (keyPressed.equalsIgnoreCase("ac")) {
                clearScreen();
            }
            else {
                if (continuousMode) {
                    if (displayingResult) {
                        clearScreen();
                        displayingResult = false;
                    }
                    appendToScreen(keyPressed);
                }
                else {
                    if (operators.contains(keyPressed)) {
                        getResult();
                        displayingResult = true;
                    } else {
                        appendToScreen(keyPressed);
                    }
                }
            }
        }
        catch (ClassCastException e) {
            e.printStackTrace();
        }
    }

    public static void appendToScreen(String keyPressed) {
        if (displayingResult) {
            screenText = new StringBuilder();
            displayingResult = false;
        }
        screenText.append(keyPressed);
        screen.setText(screenText.toString());
    }

    public static void getResult() {
        Random random = new Random();
        screenText = new StringBuilder();
        screenText.append(Integer.toString(random.nextInt(50)));
        displayingResult = true;
        screen.setText(screenText.toString());
    }

    public static void clearScreen() {
        screenText = new StringBuilder();
        screen.setText("");
    }

    public static void changeMode(ActionEvent event) {
        continuousMode = !continuousMode;
        try {
            Button btn = (Button) event.getSource();
            if (continuousMode) {
                btn.setText("continuous");
            }
            else {
                btn.setText("step-wise");
            }
        }
        catch (ClassCastException e) {
            e.printStackTrace();
        }
    }

    private static ArrayList<String> getSymbols() {
        ArrayList<String> keySymbols = new ArrayList<String>() {{
            add("("); add(")"); add("%"); add("ac");
            add("7"); add("8"); add("9"); add("/");
            add("4"); add("5"); add("6"); add("X");
            add("1"); add("2"); add("3"); add("-");
            add("0"); add("."); add("="); add("+");
        }};
        return keySymbols;
    }
}
