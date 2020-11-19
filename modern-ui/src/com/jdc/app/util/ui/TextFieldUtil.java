package com.jdc.app.util.ui;

import com.jdc.app.util.StringUtil;

import javafx.scene.control.TextField;

public class TextFieldUtil {
	
	public static int getPriceValue(TextField textField) {
		if(textField.getText().isEmpty() || textField.getText() instanceof String) {
			if(StringUtil.isNumeric(textField.getText()))
				return Integer.parseInt(textField.getText());
		}
		return 0;
	}

}
