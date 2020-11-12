package com.jdc.pos.view;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import com.jdc.pos.entity.Category;
import com.jdc.pos.entity.Invoice;
import com.jdc.pos.entity.Product;
import com.jdc.pos.entity.SaleDTO;
import com.jdc.pos.entity.SaleOrder;
import com.jdc.pos.service.CategoryService;
import com.jdc.pos.service.ProductService;
import com.jdc.pos.service.SaleService;
import com.jdc.pos.util.DecimalFormatConverter;
import com.jdc.pos.util.PosException;
import com.jdc.pos.util.Security;

import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.MouseEvent;

public class SaleManagement {
	
	@FXML
	private ComboBox<Category> category;
	@FXML
	private TextField product;
	@FXML
	private TableView<Product> proList;
	@FXML
	private Label headTotal;
	@FXML
	private TableView<SaleOrder> cartList;
	@FXML
	private TableColumn<SaleOrder, Integer> qtyCol;
	@FXML
	private Label subTotal;
	@FXML
	private Label taxLabel;
	@FXML
	private Label tax;
	@FXML
	private Label total;
	
	private CategoryService catService;
	private ProductService proService;
	private SaleService saleService;
	private List<SaleOrder> soList;
	private SaleOrder saleOrder;
	private SaleDTO dto;

	private int taxPercent;
	
	private static final String TAX_FILE = "tax.obj";
	
	@FXML
	private void initialize() {
		
		catService = CategoryService.getInstance();
		proService = ProductService.getInstance();
		saleService = SaleService.getInstance();
		
		category.getItems().addAll(catService.findByName(null));
		
		search();
		soList = new ArrayList<>();
		calculate();
		
		headTotal.textProperty().bind(total.textProperty());
		category.valueProperty().addListener(a -> search());
		product.textProperty().addListener(a -> search());
		
		cartList.setEditable(true);
		qtyCol.setCellFactory(TextFieldTableCell.forTableColumn(new DecimalFormatConverter()));
		qtyCol.setOnEditCommit(e -> {
			SaleOrder so = e.getRowValue();
			so.setQuantity(e.getNewValue());
			so.setTotal(so.getQuantity() * so.getUnitPrice());
			calculate();
		});		
		
		readTax();
		taxLabel.setText(String.format("%s (%d %s)", "Tax", taxPercent, "%"));
	}
	
	public void search() {
		proList.getItems().clear();
		proList.getItems().addAll(proService.find(category.getValue(), product.getText(), null));
	}
	
	public void clear() {
		category.setValue(null);
		product.clear();
	}
	
	public void addToCart(MouseEvent event) {
		if(event.getClickCount() == 2) {
			Product p = proList.getSelectionModel().getSelectedItem();
			
			saleOrder = cartList.getItems().stream().filter(so -> so.getProduct().getId() == p.getId()).findFirst().orElse(null);
			
			if(null == saleOrder) {
				saleOrder = new SaleOrder();
				saleOrder.setProduct(p);
				saleOrder.setQuantity(1);
				saleOrder.setUnitPrice(p.getPrice());
				saleOrder.setTotal(saleOrder.getQuantity() * saleOrder.getUnitPrice());
				soList.add(saleOrder);
			} else {
				saleOrder.setQuantity(saleOrder.getQuantity() + 1);
				saleOrder.setTotal(saleOrder.getQuantity() * saleOrder.getUnitPrice());
				soList = new ArrayList<SaleOrder>(cartList.getItems());
			}
		}
		prepareForNext();
		calculate();
	}
	
	private void prepareForNext() {
		saleOrder = null;
		cartList.getItems().clear();
		cartList.getItems().addAll(soList);
	}
	
	private void calculate() {
		int sbTl = cartList.getItems().stream().mapToInt(so -> so.getTotal()).sum();
		int tx = sbTl / 100 * taxPercent;
		int tl = sbTl + tx;
		subTotal.setText(DecimalFormatConverter.format(sbTl));
		tax.setText(DecimalFormatConverter.format(tx));
		total.setText(DecimalFormatConverter.format(tl));
	}
	
	public void setTaxRate() {
		TaxView.show(a -> {
			taxPercent = a;
			calculate();
			taxLabel.setText(String.format("%s (%d %s)", "Tax", taxPercent, "%"));
			
			try(ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(TAX_FILE))) {
				out.writeInt(a);
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
	}
	
	private void readTax() {
		try(ObjectInputStream in = new ObjectInputStream(new FileInputStream(new File(TAX_FILE)))) {
			taxPercent = in.readInt();
		} catch (Exception e) {
			System.out.println("First time load...");
		}
	}
	
	public void clearCartList() {
		soList.clear();
		cartList.getItems().clear();
		calculate();
	}
	
	public void paid() {
		if(cartList.getItems().size() == 0) {
			throw new PosException("Please add product to cart.");
		}
		
		if(null == dto) {
			dto = new SaleDTO();

			Invoice invoice = dto.getInvoice();
			invoice.setInvoiceDate(LocalDate.now());
			invoice.setInvoiceTime(LocalTime.now());
			invoice.setMember(Security.getMember());
			invoice.setSubTotal(Integer.parseInt(subTotal.getText().replace(",", "")));
			invoice.setTax(Integer.parseInt(tax.getText().replace(",", "")));
			invoice.setTotal(Integer.parseInt(total.getText().replace(",", "")));
		}
		
		dto.getOrders().clear();
		dto.getOrders().addAll(cartList.getItems());
		
		saleService.save(dto);
		
		clearCartList();
			
	}

}