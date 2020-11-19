package com.jdc.app.util.ui;

import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.layout.HBox;
import javafx.util.Callback;

public abstract class TableCellFactory<P> implements Callback<TableColumn<P, Void>, TableCell<P, Void>>, ControllerFactory {

	@Override
	public TableCell<P, Void> call(TableColumn<P, Void> param) {
		final TableCell<P, Void> cell = new TableCell<P, Void>() {

        	final UpdateCellNode node = new UpdateCellNode(new HBox(), new HBox());
        	
        	{
        		node.getEditBox().setOnMouseClicked(e -> edit());
        		node.getDeleteBox().setOnMouseClicked(e -> delete());
        	}

            @Override
            public void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(node);
                }
            }
        };
        return cell;
	}

}
