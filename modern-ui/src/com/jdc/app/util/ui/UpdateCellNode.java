package com.jdc.app.util.ui;

import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.SVGPath;

public class UpdateCellNode extends HBox {

	private HBox editBox;
	private HBox deleteBox;
	
	public UpdateCellNode() {
		
//		this.editBox = editBox;
//		this.deleteBox = deleteBox;
//		
//		UIUtil.setTooltip(deleteBox, "Delete item");
//
//		SVGPath edit = Icons.Edit.getSVGPath(Color.BROWN);
//		editBox.getChildren().add(edit);
//
//		getChildren().add(editBox);
//		
//		getStyleClass().add("update-box");
//		
//		UIUtil.setTooltip(deleteBox, "Delete item");
//
//		SVGPath delete = Icons.Delete.getSVGPath(Color.RED);
//		deleteBox.getChildren().add(delete);
//
//		getChildren().add(deleteBox);
//		
//		getStyleClass().add("update-box");
	}
	
	public void setEditBox(HBox editBox) {
		this.editBox = editBox;
		
		UIUtil.setTooltip(deleteBox, "Delete item");

		SVGPath edit = Icons.Edit.getSVGPath(Color.BROWN);
		editBox.getChildren().add(edit);

		getChildren().add(editBox);
		
		getStyleClass().add("update-box");

	}

	public HBox getEditBox() {
		return editBox;
	}

	public void setDeleteBox(HBox deleteBox) {
		this.deleteBox = deleteBox;
		
		UIUtil.setTooltip(deleteBox, "Delete item");

		SVGPath delete = Icons.Delete.getSVGPath(Color.RED);
		deleteBox.getChildren().add(delete);

		getChildren().add(deleteBox);
		
		getStyleClass().add("update-box");
	}
	
	public HBox getDeleteBox() {	
		return deleteBox;
	}

}
