package controller;

import com.jfoenix.controls.*;
import controller.impl.HomepageServiceImpl;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.DatePicker;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ResourceBundle;

public class HomepageController implements Initializable {

    @FXML
    private JFXToggleButton btnCompleted;

    @FXML
    private JFXRadioButton btnSortByDate;

    @FXML
    private JFXRadioButton btnSortByName;

    @FXML
    private DatePicker expDate;

    @FXML
    private VBox taskList;

    @FXML
    private JFXTextArea txtDescription;

    @FXML
    private JFXTextField txtTitle;


    private HomepageService service;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        service = new HomepageServiceImpl();
        service.clearAll(txtTitle,txtDescription,expDate);
        service.loadTable(taskList, btnCompleted);
    }

    @FXML
    void btnAddOnAction(ActionEvent event) {
        try {
            service.addTask(txtTitle.getText(),txtDescription.getText(),expDate.getValue());
        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
            throw new RuntimeException(e);
        }
    }

    @FXML
    void btnClearOnAction(ActionEvent event) {
        service.clearAll(txtTitle,txtDescription,expDate);
    }

    @FXML
    void btnCompletedOnAction(ActionEvent event) {
        service.loadTable(taskList,btnCompleted);
    }

    @FXML
    void btnReloadOnAction(ActionEvent event) {
        service.loadTable(taskList,btnCompleted);
    }

    @FXML
    void btnSortByDateOnAction(ActionEvent event) {

    }

    @FXML
    void btnSortByNameOnAction(ActionEvent event) {

    }
}
