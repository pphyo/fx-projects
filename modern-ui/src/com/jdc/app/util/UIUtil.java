package com.jdc.app.util;

import javafx.scene.Node;
import javafx.scene.control.Tooltip;

public class UIUtil {
	
	public static void setTooltip(Node node, String text) {
		Tooltip tooltip = new Tooltip(text);
		Tooltip.install(node, tooltip);
	}

}
