package com.jdc.app.views.page;

public enum Page {

	Dashboard("POS Dashboard", "Dashboard"),
	SaleManagement("Sale", "SaleManagement"),
	CartOrders("Sale", "CartOrders"),
	CartInvoices("Sale", "CartInvoices"),
	CategoryManagement("Categories", "CategoryManagement"),
	ProductManagement("Products", "ProductManagement"),
	SaleHistory("Sale History", "SaleHistory"),
	EmployeeManagement("Employees", "EmployeeManagement"),
	CustomerManagement("Customers", "CustomerManagement");

	private String title;
	private String viewName;

	private Page(String title, String viewName) {
		this.title = title;
		this.viewName = viewName;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getViewName() {
		return viewName.concat(".fxml");
	}

	public void setViewName(String viewName) {
		this.viewName = viewName;
	}

}