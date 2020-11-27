package com.jdc.app.util.ui;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;

import javafx.geometry.Bounds;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;

public class AutoCompleteUtil {

    public static<T> void attach(TextField textField, Function<String, List<T>> searcher, Consumer<T> consumer,int suggestStartLength) {

        ContextMenu suggestions = new ContextMenu();
        
        textField.widthProperty().addListener((a,b,c) -> {
        	suggestions.setStyle(String.format("-fx-pref-width: %f;", c));
        	suggestions.setStyle("-fx-background-color: #dcdcdc");
        	suggestions.setStyle("-fx-text-fill: #051937");
        });
               
        Consumer<T> executor = s -> {
            textField.setText(s.toString());
            textField.positionCaret(s.toString().length());
            if(null != consumer) {
                consumer.accept(s);
            }
            suggestions.hide();
        };

        textField.focusedProperty().addListener((a,b,c) -> suggestions.hide());
        textField.textProperty().addListener((a,b,c) -> {
            if(textField.getText().length() >= suggestStartLength) {

                suggestions.getItems().clear();

                searcher.apply(textField.getText()).stream().limit(10).forEach(s -> {
                    MenuItem item = new MenuItem(s.toString());

                    item.setOnAction(event -> {
                        executor.accept(s);
                    });

                    suggestions.getItems().add(item);
                });

                if(!suggestions.getItems().isEmpty()) {
                    if(!suggestions.isShowing()) {
                        Bounds bounds = textField.localToScreen(textField.getLayoutBounds());
                        suggestions.show(textField, bounds.getMinX(), bounds.getMaxY());
                    }
                } else {
                    suggestions.hide();
                }
            } else {
                suggestions.hide();
            }
        });
    }

    public static<T> void attach(TextField textField, Function<String, List<T>> mapper) {
        attach(textField, mapper, null);
    }

    public static<T> void attach(TextField textField, Function<String, List<T>> mapper, Consumer<T> consumer) {
        attach(textField, mapper, consumer, 1);
    }

}