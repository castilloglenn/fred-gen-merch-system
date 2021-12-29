package pos;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import utils.Utility;


public class Receipt {

	/**
	 * Singleton instance
	 */
	private static Receipt singletonInstance = null;
	
	private final String BR = "\n";
	private final int WIDTH = 50;
    private String horizontalLine = "";
	
	private String businessName1 = Utility.SYSTEM_TITLE;
	private String businessName2 = Utility.BUSINESS_TITLE;
	private String businessAddress1 = Utility.BUSINESS_ADDRESS_1;
	private String businessAddress2 = Utility.BUSINESS_ADDRESS_2;
	private String tinNo = Utility.BUSINESS_TIN;
	private String footer = Utility.RECEIPT_FOOTER;
	private String legalNotice = Utility.RECEIPT_LEGAL_NOTICE;
	
	private DateFormat sdf = new SimpleDateFormat("yyyy/MM/dd hh:mm:ss aa");
	private String dateTime;
	
	private long transactionNo;
	
	private Object[] cashier;
	private Object[] customer;
	
	private String cashierName;
	private String customerName = "REGULAR";

	private ArrayList<CartItem> cartList;
	
	private double subTotal, tax, total, totalItems;
	private double discount = 0.0;
	private double amountTendered, change;
	
	private Utility utility;

	private Receipt(Object[] user, ArrayList<CartItem> cartList) {
		setHorizontalLine();
		this.utility = Utility.getInstance();
		
		cashier = user;
		this.cartList = cartList;
		
		cashierName = cashier[1] + " " + cashier[3].toString().substring(0, 1) + ".";
	}
	
	public static Receipt getInstance(Object[] user, ArrayList<CartItem> cartList) {
		if (singletonInstance == null) {
			singletonInstance = new Receipt(user, cartList);
		}
		
		return singletonInstance;
	}
    
    public String get(boolean generateID) {
    	StringBuilder receipt = new StringBuilder();
    	
    	if (generateID) {
    		setDateTime();
    	}
    	
    	receipt.append(center(businessName1) + BR);
    	receipt.append(center(businessName2) + BR);
    	receipt.append(center(businessAddress1) + BR);
    	receipt.append(center(businessAddress2) + BR);
    	receipt.append(center(tinNo) + BR);
    	receipt.append(horizontalLine + BR);
    	receipt.append(leftAlign("Date: " + dateTime) + BR);
    	receipt.append(leftAlign("Transaction No: " + transactionNo) + BR);
    	receipt.append(leftAlign("Cashier: " + cashierName) + BR);
    	receipt.append(leftAlign("Customer: " + customerName) + BR);
    	receipt.append(horizontalLine + BR);
    	receipt.append(center(cartList));
    	receipt.append(horizontalLine + BR);
    	receipt.append(center(footer) + BR + BR);
    	receipt.append(center(legalNotice) + BR);
    	
    	return receipt.toString();
    }

    public long getTransactionID() {
    	return transactionNo;
    }
    
    public void setCustomer(Object[] customer) {
    	this.customer = customer;
    	
    	if (customer != null) {
			customerName = customer[3].toString() + " " 
					+ customer[4].toString() + " " 
					+ customer[5].toString();
		} else {
			customerName = "REGULAR";
		}
    }
    
    public void setTotalItems(double totalItems) {
    	this.totalItems = totalItems;
    }
    
    public void setSubTotal(double subTotal) {
    	this.subTotal = subTotal;
    }
    
    public void setTax(double tax) {
    	this.tax = tax;
    }
    
    public void setDiscount(double discount) {
    	this.discount = discount;
    }
    
    public void setAmountTendered(double amount) {
    	amountTendered = amount;
    	change = amountTendered - total;
    }
    
    public void setTotal(double total) {
    	this.total = total;
    }
	
	private void setHorizontalLine() {
        for (int space = 0; space < WIDTH; space++) {
            horizontalLine += "=";
        }
    }
	
	private void setDateTime() {
		dateTime = sdf.format(Calendar.getInstance().getTime());
		transactionNo = utility.generateTransactionID();
	}
	
	private String leftAlign(String message) {
		int length = message.length();
        if (length == WIDTH - 4) return "  " + message + "  ";
        
        if (length > WIDTH - 4) {
            return "  " + message.substring(0, WIDTH - 4) + "\n" + 
                center(message.substring(WIDTH - 4));
        }
        
        return "  " + message;
	}
	
	private String center(String message) {
        int length = message.length();
        if (length == WIDTH - 4) return "  " + message + "  ";
        
        if (length > WIDTH - 4) {
            return "  " + message.substring(0, WIDTH - 4) + "\n" + 
                center(message.substring(WIDTH - 4));
        }
        
        int padding = (WIDTH - length) / 2;
        for (int space = 0; space < padding; space++) {
            message = " " + message;
        }
        
        return message;
    }

    private String center(ArrayList<CartItem> cartList) {
        StringBuilder purchaseList = new StringBuilder();
        
        for (int cartIndex = 0; cartIndex < cartList.size(); cartIndex++) {
        	CartItem cartItem = cartList.get(cartIndex);
        	Object[] product = cartItem.getProduct();

        	int order = cartIndex + 1;
        	double quantity = cartItem.getQuantity();
        	String name = product[2].toString();
        	double price = (double) product[7];
        	
            String firstLine = String.format("  %03d: %-" + name.length() + "s", 
            		order, quantity + " " + name);
            String individual = String.format("Php %,.2f", price);
            String totalPrice = String.format("Php %,.2f", quantity * price);

            String secondLine = String.format("    @ %s ", individual);
            int centerSpace = WIDTH - (11 + individual.length() + totalPrice.length());
            String totalLine = "";
            for (int dot = 0; dot < centerSpace; dot++) {
                totalLine += ".";
            }
            totalLine += " ";

            purchaseList.append(
                (firstLine.length() > WIDTH - 4)
                ? firstLine.substring(0, WIDTH - 5) + "..\n"
                : firstLine + "\n"
            );
            
            purchaseList.append(secondLine + totalLine + totalPrice + "\n");
        }
        
        String subTotalFormat = String.format("Php %,.2f", subTotal);
        String taxFormat = String.format("Php %,.2f", tax);
        String discountFormat = String.format("Php %,.2f", discount);
        String totalFormat = String.format("Php %,.2f", total);
        String amountTenderedFormat = String.format("Php %,.2f", amountTendered);
        String changeFormat = String.format("Php %,.2f", change);
        
        String discountType = "     ";
        if (customer != null) {
        	if (customer[1].toString().charAt(0) == 'S') {
        		discountType = "(SC) ";
        	} else {
        		discountType = "(PWD)";
        	}
        }

        purchaseList.append(
    		String.format("\n  TOTAL ITEMS %," + (WIDTH - 16) + ".2f\n", 
    			totalItems));
        purchaseList.append(
        	String.format("\n  SUB-TOTAL %" + (WIDTH - 14) + "s\n", 
        		subTotalFormat));
        purchaseList.append(
        	String.format("  VAT %" + (WIDTH - 8) + "s\n",
        		taxFormat));
        purchaseList.append(
        	String.format("  DISCOUNT " + discountType + "%" + (WIDTH - 18) + "s\n",
    			discountFormat));
        purchaseList.append(
    		String.format("  TOTAL %" + (WIDTH - 10) + "s\n\n", 
    			totalFormat));
        purchaseList.append(
        	String.format("  AMOUNT TENDERED %" + (WIDTH - 20) + "s\n",
    			amountTenderedFormat));
        purchaseList.append(
        	String.format("  CHANGE %" + (WIDTH - 11) + "s\n",
    			changeFormat));

        return purchaseList.toString();
    }
}
