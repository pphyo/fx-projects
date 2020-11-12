package com.jdc.pos.view.page;

public enum Page {
	Home("Progress Chart", "PosHome"),
	Sale("Sale Management", "SaleManagement"),
	Category("Category Management", "CategoryManagement"),
	Product("Product Management", "ProductManagement"),
	SaleHistory("Sale History", "SaleHistory"),
	Employee("Employee", "EmployeeList");
	
	private String title;
	private String viewName;
	
	private Page(String title, String viewName) {
		this.title = title;
		this.viewName = viewName;
	}
	
	public String getTitle() {
		return title;
	}
	
	public String getViewName() {
		return viewName.concat(".fxml");
	}
}
