package org.example.app.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.util.*;

public class registerController {
    int otp;
    boolean isOtpPiVerified=false;
    boolean isOtpAddVerified=false;


    ObservableList<String> docs = FXCollections.observableArrayList(
            "Aadhaar card", "birth certificate", "passport", "Driving Licence",
            "voter id", "Pan card"
    );

    @FXML
    private AnchorPane personsl_info,Banking_Details;

    @FXML
    private TitledPane t1,t2,t3;

    @FXML
    private Accordion accordion;


    @FXML
    private Label otpVerified,otpaddvarify, imageDocName,birthDocName,idDocName,addressDocName,panDocName;

    @FXML
    private TextField fullName,panNumber,phoneNumber,otpPI,otpBD,email,address,po,city,pincode,addhar;
    @FXML
    private DatePicker dob;
    @FXML
    private RadioButton male,female,other,cAcc,sAcc;
    @FXML
    private ComboBox<String> occupation,state,district,BirthProof,identityProofe,AddressProof,initDipos,MLimit,selfimage;
    @FXML
    private Button submit,cancel,clearPI,pISV,
            sendOtpToNumber,verify,sendOtpToAddhar,
            verifyAdd,svBD,clrBD,cancelBD;

    @FXML
    private CheckBox aggBD,aggD;

    private Map<String, ObservableList<String>> stateDistrictMap;
    private  ToggleGroup sex = new ToggleGroup();
    private  ToggleGroup AccountType = new ToggleGroup();
   // private  ToggleGroup tGroup3 = new ToggleGroup();





    @FXML
    public void initialize() {

        accordion.setExpandedPane(t1);

        male.setToggleGroup(sex);
        female.setToggleGroup(sex);
        other.setToggleGroup(sex);

        cAcc.setToggleGroup(AccountType);
        sAcc.setToggleGroup(AccountType);

        sex.selectedToggleProperty().addListener((e1, e2, e3)->{
            gender = ((RadioButton)e3).getText();
        });

        AccountType.selectedToggleProperty().addListener((e1, e2, e3)->{
           accType = ((RadioButton)e3).getText();
        });





        BirthProof.setItems(docs);
        identityProofe.setItems(docs);
        AddressProof.setItems(docs);




        initDipos.getItems().addAll("1000","5000","10000","15000","20000");
        MLimit.getItems().addAll("50000","100000","200000","500000","1000000");

        BirthProof.getItems().addAll();

        stateDistrictMap = new HashMap<>();

        // West Bengal
        stateDistrictMap.put("West Bengal", FXCollections.observableArrayList(
                "Kolkata", "Howrah", "Hooghly", "Nadia",
                "North 24 Parganas", "South 24 Parganas",
                "Darjeeling", "Malda"
        ));

        // Maharashtra
        stateDistrictMap.put("Maharashtra", FXCollections.observableArrayList(
                "Mumbai", "Pune", "Nagpur", "Nashik",
                "Thane", "Aurangabad", "Solapur"
        ));

        // Uttar Pradesh
        stateDistrictMap.put("Uttar Pradesh", FXCollections.observableArrayList(
                "Lucknow", "Kanpur", "Agra", "Varanasi",
                "Prayagraj", "Noida", "Ghaziabad"
        ));
        // Load states
        state.getItems().addAll(stateDistrictMap.keySet());

        // When state changes → load districts
        state.setOnAction(e -> {
            String selectedState = state.getValue();
            district.setItems(stateDistrictMap.get(selectedState));
        });

        occupation.getItems().addAll(
                "Student",
                "Private Job",
                "Government Job",
                "Self-Employed",
                "Business",
                "Farmer",
                "Homemaker",
                "Unemployed",
                "Retired",
                "Other"
        );

        pISV.setDisable(true);

    }

    // ================================================================================
    private File selfImgDoc;
    private File birthDoc;
    private File idDoc;
    private File addressDoc;
    private File panDoc;
    private String gender="";
    private String accType;

    protected Map<String,String> personalDetails = new HashMap<>();


    @FXML
    public void submit() {}

    @FXML
    public void fc() {
        System.out.println("ji");
        pISV.getStyleClass().add("rf");
    }

    @FXML
    public void fv() {
        pISV.getStyleClass().removeAll("rf");
    }

    @FXML
    public void dc() {
        pISV.getStyleClass().add("dc");
    }
    @FXML
    public void cd() {
        pISV.getStyleClass().removeAll("dc");
    }



    @FXML
    public void savePI(ActionEvent e) {
        if ((e.getSource() == svBD)?isOtpAddVerified:isOtpPiVerified) {
            ObservableList<Node> list;
            if(e.getSource() == svBD) {
                if(aggBD.isSelected()) {
                    list = Banking_Details.getChildren();
                }
                else {
                    notFill(aggBD);
                    return;
                }
            } else {
                list=personsl_info.getChildren();
            }


            for (Node n : list) {
                if (!(n instanceof Button)) {
                    if (n instanceof TextField) {
                        if (!(n.getId().equals("panNumber") || n.getId().equals("otpPI"))) {
                            if (!(((TextField) n).getText().isEmpty()))
                                personalDetails.put("_" + n.getId(), ((TextField) n).getText());
                            else {
                                notFill(n);
                                break;
                            }
                        }
                    } else if (n instanceof ComboBox) {
                        if (((ComboBox<?>) n).getValue() != null)
                            personalDetails.put("_" + n.getId(), ((ComboBox<?>) n).getValue().toString());
                        else {
                            notFill(n);
                            break;
                        }
                    } else if (n instanceof RadioButton) {
                        if(e.getSource() == pISV) {
                            if (sex.getSelectedToggle().isSelected())
                                personalDetails.put("_sex", sex.getSelectedToggle().toString());
                            else {
                                notSelect();
                                break;
                            }
                        }
                        else {

                            if (AccountType.getSelectedToggle().isSelected())
                                personalDetails.put("_AccountType", AccountType.getSelectedToggle().toString());
                            else {
                                notSelect();
                                break;
                            }
                        }
                    } else if (n instanceof DatePicker) {
                        if (((DatePicker) n).getValue() != null)
                            personalDetails.put("_" + n.getId(), ((DatePicker) n).getValue().toString());
                        else {
                            notFill(n);
                            break;
                        }
                    }
                }
                // for open next titlepane
                if (n.equals(list.getLast())){
                    if (e.getSource() == pISV) accordion.setExpandedPane(t2);
                    else if (e.getSource() == svBD) accordion.setExpandedPane(t3);
                }
            }
        }
        else {
            Dialog<Button> dialog = new Dialog<>();
            if (e.getSource() == pISV) dialog.setContentText("Please Verify Your Mobile Number");
            else dialog.setContentText("Please Verify Your Addhar Number");
            dialog.setTitle("requirements");
            dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK);
            dialog.show();
        }



    }

    public void notFill(Node n){
        n.getStyleClass().add("not-fill");
    }
    public void notSelect() {
        male.setTextFill(Color.RED);
        female.setTextFill(Color.RED);
        other.setTextFill(Color.RED);
    }

    @FXML
    public void getFocus(MouseEvent e) {
        Node source = (Node) e.getSource();
        source.getStyleClass().removeAll("not-fill");

    }
    @FXML
    public void getFocusRadio(){
        male.setTextFill(Color.BLACK);
        female.setTextFill(Color.BLACK);
        other.setTextFill(Color.BLACK);
    }



    @FXML
    public void cancel(ActionEvent event) {
    }
    @FXML
    public void sendOtpNumber(ActionEvent event) {
        if(!phoneNumber.getText().isEmpty()) {
            otpPI.setVisible(true);
            otpVerified.setVisible(false);
            verify.setVisible(true);

            otp = (int) ((Math.random() + 1) * 1000);
            Dialog<ButtonType> dialog = new Dialog<>();
            dialog.setContentText(" otp is : " + otp);
            dialog.setHeaderText("otp from AmarBank for verify your Phone Number");
            dialog.setTitle("OTP send");
            dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK);
            dialog.show();
        }
        else {
            notFill(phoneNumber);
        }
    }
    @FXML
    public void otpverify(ActionEvent e) {
        if(((Button)e.getSource()).getId().equals("verify")) {
            if (otpPI.getText().equals(String.valueOf(otp))) {
                otp=0;
                otpPI.setVisible(false);
                verify.setVisible(false);
                sendOtpToNumber.setVisible(false);
                phoneNumber.setPrefWidth(177);
                otpVerified.setVisible(true);
                otpVerified.setText("OTP verified");
                otpVerified.setStyle("-fx-text-fill: green;");
                otpVerified.setFont(Font.font("arial", FontWeight.BOLD, 20));
                otpVerified.setLayoutY(169);
                isOtpPiVerified = true;
                pISV.setDisable(false);
            } else {
                otpPI.setVisible(true);
                verify.setVisible(true);
                otpVerified.setVisible(true);
                otpVerified.setText(" Wrong OTP ");
                otpVerified.setStyle("-fx-text-fill: red;");
                otpVerified.setFont(Font.font("arial", FontWeight.BOLD, 15));
                otpVerified.setLayoutY(192);
                otpPI.clear();
            }
        }
        else {
            if (otpBD.getText().equals(String.valueOf(otp))) {
                otp=0;
                otpBD.setVisible(false);
                verifyAdd.setVisible(false);
                sendOtpToNumber.setVisible(false);
                phoneNumber.setPrefWidth(177);
                otpaddvarify.setVisible(true);
                otpaddvarify.setText("OTP verified");
                otpaddvarify.setStyle("-fx-text-fill: green;");
                otpaddvarify.setFont(Font.font("arial", FontWeight.BOLD, 20));
                otpaddvarify.setLayoutY(197);
                isOtpAddVerified = true;
                svBD.setDisable(false);
            } else {
                otpBD.setVisible(true);
                verifyAdd.setVisible(true);
                otpaddvarify.setVisible(true);
                otpaddvarify.setText(" Wrong OTP ");
                otpaddvarify.setStyle("-fx-text-fill: red;");
                otpaddvarify.setFont(Font.font("arial", FontWeight.BOLD, 15));
                otpaddvarify.setLayoutY(221);
                otpBD.clear();
            }
        }
    }



    @FXML
    public void sendotpAddhar(ActionEvent event) {
        if(!addhar.getText().isEmpty()) {
            otpBD.setVisible(true);
            otpaddvarify.setVisible(false);
            verifyAdd.setVisible(true);

            otp = (int) ((Math.random() + 1) * 1000);
            Dialog<ButtonType> dialog = new Dialog<>();
            dialog.setContentText(" otp is : " + otp);
            dialog.setHeaderText("otp from AmarBank for verify your Addhar Number");
            dialog.setTitle("OTP send");
            dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK);
            dialog.show();
        }
        else {
            notFill(addhar);
        }
    }
    @FXML
    public void chooseFile(ActionEvent event) {

        String id = ((Button) event.getSource()).getId();
        Stage stgc = new Stage();

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Resource File");
        FileChooser.ExtensionFilter extFilter1 = new FileChooser.ExtensionFilter("pdf Files", "*.pdf");
        FileChooser.ExtensionFilter extFilter3 = new FileChooser.ExtensionFilter("png Files", "*.png");
        FileChooser.ExtensionFilter extFilter4 = new FileChooser.ExtensionFilter("jpeg Files", "*.jpeg");
        fileChooser.getExtensionFilters().addAll(extFilter1, extFilter3, extFilter4);

        switch (id) {
            case "birthDoc":
                birthDoc = fileChooser.showOpenDialog(stgc);
                birthDocName.setText(birthDoc.getName());

                break;
            case "idDoc":
                idDoc = fileChooser.showOpenDialog(stgc);
                imageDocName.setText(idDoc.getName());
                break;
            case "addressDoc":
                addressDoc = fileChooser.showOpenDialog(stgc);
                addressDocName.setText(addressDoc.getName());
                break;
            case "panDoc":
                panDoc = fileChooser.showOpenDialog(stgc);
                panDocName.setText(panDoc.getName());
                break;
            case "selfImgDoc":
                selfImgDoc = fileChooser.showOpenDialog(stgc);
                imageDocName.setText(selfImgDoc.getName());
                break;
        }
    }

}
