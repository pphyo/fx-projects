package com.jdc.app.views;

import com.jdc.app.entity.Category;

import javafx.fxml.FXML;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;

public class CategoryManagement {

    @FXML
    private TextField txtName;
    @FXML
    private VBox tool1;
    @FXML
    private VBox tool2;
    @FXML
    private VBox tool3;
    @FXML
    private TableView<Category> tblCategoryList;

    @FXML
    void add(MouseEvent event) {
    	
    }

    @FXML
    void search(MouseEvent event) {

    }

    @FXML
    void upload(MouseEvent event) {

    }

}
