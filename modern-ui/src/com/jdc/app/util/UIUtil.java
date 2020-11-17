package com.jdc.app.util;

import java.util.function.Consumer;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Tooltip;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class UIUtil {

	public static <T extends ModalController<E>, E> void show(Class<T> type, E data, Consumer<E> listener) {

		try {
			Stage stage = new Stage();
			FXMLLoader loader = new FXMLLoader(type.getResource(String.format("%s.fxml", type.getSimpleName())));
			stage.setScene(new Scene(loader.load()));
			stage.initModality(Modality.APPLICATION_MODAL);

			T controller = loader.getController();
			controller.init(data, listener);
			stage.show();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void setTooltip(Node node, String text) {
		Tooltip tooltip = new Tooltip(text);
		Tooltip.install(node, tooltip);
	}
	
	public static interface ModalController<E> {
		void init(E data, Consumer<E> listener);
	}

}
