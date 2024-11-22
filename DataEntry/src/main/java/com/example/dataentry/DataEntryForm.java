package com.example.dataentry;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.time.LocalDate;
import java.util.ArrayList;

public class DataEntryForm extends Application {

    private final ArrayList<Person> personList = new ArrayList<>();
    private TextField nameField = new TextField();
    private TextField fatherNameField = new TextField();
    private TextField cnicField = new TextField();
    private DatePicker datePicker = new DatePicker();
    private ComboBox<String> cityComboBox = new ComboBox<>();
    private ToggleGroup genderGroup = new ToggleGroup();
    private ImageView imageView = new ImageView();

    private Scene startScene, mainScene, confirmationScene;

    @Override
    public void start(Stage primaryStage) {
        // Start Scene
        VBox startLayout = new VBox(20);
        startLayout.setAlignment(Pos.CENTER);
        startLayout.setStyle("-fx-background-color: linear-gradient(to bottom, #3498db, white);");

        Label startBanner = new Label("Welcome to the Data Dairy");
        startBanner.setStyle("-fx-font-size: 28px; -fx-text-fill:#2d0f7e; -fx-font-weight: bold;");
        Button startButton = new Button("Continue");
        startButton.setStyle("-fx-font-size: 18px; -fx-background-color: #3498db; -fx-text-fill: white; " +
                "-fx-font-weight: bold; -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.75), 4, 0.5, 1, 1);");
        startButton.setOnAction(e -> primaryStage.setScene(mainScene));

        startLayout.getChildren().addAll(startBanner, startButton);
        startScene = new Scene(startLayout, 600, 400);

        // Main Scene
        VBox root = new VBox();
        root.setPadding(new Insets(20));
        root.setSpacing(15);

        Label banner = new Label("DATA ENTRY FORM");
        banner.setStyle("-fx-font-size: 24px; -fx-font-family: 'Times New Roman'; -fx-font-weight: bold; " +
                "-fx-text-fill: white; -fx-background-color: linear-gradient(to right, #3498db, #2980b9); " +
                "-fx-padding: 10px; -fx-alignment: center;");
        banner.setPrefWidth(Double.MAX_VALUE);

        GridPane formLayout = new GridPane();
        formLayout.setHgap(10);
        formLayout.setVgap(10);
        formLayout.setPadding(new Insets(10));

        // Form Fields
        formLayout.add(createStyledLabel("Name:"), 0, 0);
        formLayout.add(nameField, 1, 0);

        formLayout.add(createStyledLabel("Father Name:"), 0, 1);
        formLayout.add(fatherNameField, 1, 1);

        formLayout.add(createStyledLabel("CNIC:"), 0, 2);
        formLayout.add(cnicField, 1, 2);

        formLayout.add(createStyledLabel("Date of Birth:"), 0, 3);
        datePicker.setDayCellFactory(picker -> new DateCell() {
            @Override
            public void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);
                setDisable(date.isAfter(LocalDate.of(2024, 12, 31)));
            }
        });
        formLayout.add(datePicker, 1, 3);

        formLayout.add(createStyledLabel("Gender:"), 0, 4);
        HBox genderBox = new HBox(10);
        RadioButton maleButton = new RadioButton("Male");
        RadioButton femaleButton = new RadioButton("Female");
        RadioButton otherButton = new RadioButton("Other");
        maleButton.setToggleGroup(genderGroup);
        femaleButton.setToggleGroup(genderGroup);
        otherButton.setToggleGroup(genderGroup);
        maleButton.setSelected(true);
        genderBox.getChildren().addAll(maleButton, femaleButton, otherButton);
        styleRadioButtons(maleButton, femaleButton, otherButton);
        formLayout.add(genderBox, 1, 4);

        formLayout.add(createStyledLabel("City:"), 0, 5);
        cityComboBox.getItems().addAll("Islamabad", "Lahore", "Quetta", "Karachi", "Peshawar");
        cityComboBox.setValue("City name");
        formLayout.add(cityComboBox, 1, 5);

        Label imageLabel = new Label("Image:");
        Button uploadImageButton = new Button("Upload Image");
        style3DButton(uploadImageButton, 15);
        uploadImageButton.setOnAction(e -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg"));
            File selectedFile = fileChooser.showOpenDialog(primaryStage);
            if (selectedFile != null) {
                Image image = new Image(selectedFile.toURI().toString());
                imageView.setImage(image);
                imageView.setFitWidth(150);
                imageView.setFitHeight(150);
                imageView.setPreserveRatio(true);
            }
        });

        VBox imageBox = new VBox(5, imageLabel, uploadImageButton, imageView);
        formLayout.add(imageBox, 2, 0, 1, 6);

        // Buttons Layout
        Button saveButton = new Button("Save");
        Button exitButton = new Button("Exit");
        style3DButton(saveButton, 16);
        style3DButton(exitButton, 16);

        saveButton.setOnAction(e -> {
            saveInformation();
            primaryStage.setScene(confirmationScene);
        });

        exitButton.setOnAction(e -> primaryStage.close());

        HBox buttonBox = new HBox(15, saveButton, exitButton);
        buttonBox.setAlignment(Pos.CENTER);

        root.getChildren().addAll(banner, formLayout, buttonBox);
        mainScene = new Scene(root, 600, 400);

        // Confirmation Scene
        confirmationScene = createConfirmationScene(primaryStage);

        primaryStage.setTitle("Data Entry Application");
        primaryStage.setScene(startScene);
        primaryStage.show();
    }

    private Label createStyledLabel(String text) {
        Label label = new Label(text);
        label.setStyle("-fx-font-size: 14px; -fx-font-family: 'Arial'; -fx-text-fill: #34495e;");
        return label;
    }

    private void styleRadioButtons(RadioButton... buttons) {
        for (RadioButton button : buttons) {
            button.setStyle("-fx-font-size: 14px; -fx-font-family: 'Times New Roman'; -fx-text-fill: #34495e;");
        }
    }

    private void style3DButton(Button button, int fontSize) {
        button.setStyle("-fx-font-size: " + fontSize + "px; -fx-background-color: #3498db; -fx-text-fill: white; " +
                "-fx-font-weight: bold; -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.75), 4, 0.5, 1, 1);");
    }

    private Scene createConfirmationScene(Stage primaryStage) {
        VBox confirmationLayout = new VBox(20);
        confirmationLayout.setAlignment(Pos.CENTER);
        confirmationLayout.setStyle("-fx-background-color: linear-gradient(to bottom, #3498db, white);");

        Label confirmationMessage = new Label("Information Saved Successfully!");
        confirmationMessage.setStyle("-fx-font-size: 20px; -fx-text-fill: #2d0f7e ; -fx-font-family: 'Times New Roman'; -fx-font-weight:bold");

        Button okButton = new Button("OK");
        Button exitButton = new Button("Exit");
        style3DButton(okButton, 16);
        style3DButton(exitButton, 16);

        okButton.setOnAction(e -> {
            clearForm();
            primaryStage.setScene(mainScene);
        });

        exitButton.setOnAction(e -> primaryStage.close());

        HBox buttonBox = new HBox(15, okButton, exitButton);
        buttonBox.setAlignment(Pos.CENTER);

        confirmationLayout.getChildren().addAll(confirmationMessage, buttonBox);

        return new Scene(confirmationLayout, 400, 200);
    }

    private void saveInformation() {
        String name = nameField.getText();
        String fatherName = fatherNameField.getText();
        String cnic = cnicField.getText();
        LocalDate dob = datePicker.getValue();
        String gender = ((RadioButton) genderGroup.getSelectedToggle()).getText();
        String city = cityComboBox.getValue();

        Person person = new Person(name, fatherName, cnic, dob, gender, city);
        personList.add(person);
        System.out.println("Saved Person: " + person);
    }

    private void clearForm() {
        nameField.clear();
        fatherNameField.clear();
        cnicField.clear();
        datePicker.setValue(null);
        genderGroup.selectToggle(null);
        cityComboBox.setValue("City name");
        imageView.setImage(null);
    }

    public static void main(String[] args) {
        launch(args);
    }
}