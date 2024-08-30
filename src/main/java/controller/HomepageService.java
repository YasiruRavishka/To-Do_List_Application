package controller;

import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXToggleButton;
import javafx.scene.control.DatePicker;
import javafx.scene.layout.VBox;

import java.time.LocalDate;

public interface HomepageService {
    void loadTable(VBox taskList, JFXToggleButton btnCompleted, JFXToggleButton btnSortByDate);

    void addTask(String title, String description, LocalDate completion_date);

    void clearAll(JFXTextField txtTitle, JFXTextArea txtDescription, DatePicker expDate);
}
