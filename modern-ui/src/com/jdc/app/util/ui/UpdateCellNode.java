package com.jdc.app.util.ui;

import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.SVGPath;

public class UpdateCellNode extends HBox {

	private HBox editBox;
	private HBox deleteBox;

	public UpdateCellNode(HBox deleteBox) {

		this.deleteBox = deleteBox;

		UIUtil.setTooltip(deleteBox, "Delete item");

		SVGPath delete = Icons.Delete.getSVGPath(Color.RED);

		getStyleClass().add("update-box");
		getChildren().add(deleteBox);

		deleteBox.getChildren().add(delete);
	}
	
	public UpdateCellNode(HBox editBox, HBox deleteBox) {

		this.editBox = editBox;
		this.deleteBox = deleteBox;

		UIUtil.setTooltip(editBox, "Edit item");
		UIUtil.setTooltip(deleteBox, "Delete item");

		SVGPath edit = Icons.Edit.getSVGPath(Color.BROWN);

		SVGPath delete = Icons.Delete.getSVGPath(Color.RED);

		getStyleClass().add("update-box");
		getChildren().addAll(editBox, deleteBox);

		editBox.getChildren().add(edit);
		deleteBox.getChildren().add(delete);
	}

	public HBox getEditBox() {
		return editBox;
	}

	public HBox getDeleteBox() {
		return deleteBox;
	}

}
