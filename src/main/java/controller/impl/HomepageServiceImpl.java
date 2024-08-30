package controller.impl;

import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXToggleButton;
import controller.HomepageService;
import database.DBConnection;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.layout.VBox;

import java.sql.*;
import java.time.LocalDate;

public class HomepageServiceImpl implements HomepageService {
    private static Connection dbConnection;
    private static VBox taskList;
    private static JFXTextField txtTitle;
    private static JFXTextArea txtDescription;
    private static DatePicker expDate;
    private static JFXToggleButton btnCompleted;
    private static JFXToggleButton btnSortByDate;

    public HomepageServiceImpl(){
        try {
            dbConnection = DBConnection.getInstance().getConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void loadTable(VBox taskList, JFXToggleButton btnCompleted, JFXToggleButton btnSortByDate) {
        this.taskList = taskList;
        this.btnCompleted = btnCompleted;
        this.btnSortByDate = btnSortByDate;
        taskList.getChildren().clear();
        String SQL;
        if (btnCompleted.isSelected()){
            SQL = "SELECT * FROM Task INNER JOIN Task_Status ON Task.id=Task_Status.id WHERE Task_Status.is_completed='1'";
        } else {
            SQL = "SELECT * FROM Task INNER JOIN Task_Status ON Task.id=Task_Status.id";
        }
        if (btnSortByDate.isSelected()){
            SQL += " ORDER BY completion_date";
        }
        try {
            ResultSet resultSet = dbConnection.prepareStatement(SQL).executeQuery();
            while (resultSet.next()){
                JFXCheckBox newCheckBox = new JFXCheckBox(resultSet.getString("title"));
                newCheckBox.setId(resultSet.getString("id"));
                newCheckBox.setSelected(resultSet.getBoolean("is_completed"));
                newCheckBox.setOnAction(actionEvent -> {
                    if (newCheckBox.isSelected()){
                        taskCompleted(true, newCheckBox.getId());
                    } else {
                        taskCompleted(false, newCheckBox.getId());
                    }
                });
                taskList.getChildren().add(newCheckBox);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void addTask(String title, String description, LocalDate completion_date) {
        try {
            ResultSet resultSet = dbConnection.prepareStatement("SELECT * FROM Task_next_index").executeQuery();
            resultSet.next();
            Integer nextIndex = resultSet.getInt("next_index");
            PreparedStatement preparedStatement01 = dbConnection.prepareStatement("INSERT INTO Task VALUES(?,?,?,?)");
            preparedStatement01.setObject(1,nextIndex);
            preparedStatement01.setObject(2,title);
            preparedStatement01.setObject(3,description);
            preparedStatement01.setDate(4, Date.valueOf(completion_date));
            PreparedStatement preparedStatement02 = dbConnection.prepareStatement("UPDATE Task_next_index SET next_index='" + (nextIndex + 1) + "' WHERE pk=1");
            PreparedStatement preparedStatement03 = dbConnection.prepareStatement("INSERT INTO Task_status VALUES(" + nextIndex + "," + false + ")");
            if (preparedStatement01.executeUpdate()>0 && preparedStatement02.executeUpdate()>0 && preparedStatement03.executeUpdate()>0){
                new Alert(Alert.AlertType.INFORMATION,"New Task Added!").show();
                clearAll(txtTitle,txtDescription,expDate);
                loadTable(taskList, btnCompleted, btnSortByDate);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void clearAll(JFXTextField txtTitle, JFXTextArea txtDescription, DatePicker expDate) {
        this.txtTitle = txtTitle;
        this.txtDescription = txtDescription;
        this.expDate = expDate;
        txtTitle.setText(null);
        txtDescription.setText(null);
        expDate.setValue(null);
    }

    private void taskCompleted(boolean isCompleted, String id) {
        try {
            if (isCompleted){
                dbConnection.prepareStatement("UPDATE Task_status SET is_completed='1' WHERE id='"+id+"'").executeUpdate();
            } else {
                dbConnection.prepareStatement("UPDATE Task_status SET is_completed='0' WHERE id='"+id+"'").executeUpdate();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
