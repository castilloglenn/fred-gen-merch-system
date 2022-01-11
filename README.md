# fred-gen-merch-system
Thesis project for the subject INSY 55 - System Analysis And Design.

Members:

Lead developer: Castillo, Allen Glenn E.

UI Designer: Garcia, Sebastian Raphael B.

Documentator/Researcher: Cabigat, Ernest John C.


Point of Sales design
![1](https://user-images.githubusercontent.com/55197203/137610436-84e46f99-7b69-431c-8a9f-ab0f4ec23243.PNG)
![2](https://user-images.githubusercontent.com/55197203/137610440-ca8be651-354f-4693-b189-dc58563c21be.PNG)

Features:

Login Interface
Allows the user to use the system when he/she input the correct username and password
User Level of Access for the System:
•	Admin – can access administrador module of the system excluding the business transaction part.
•	Manager – can access administrator mode and the business transaction of the system.
•	Clerk – Can only use the sytem for business transaction.

 
Pop-up message
Pop-up message triggers when the user interact with the system, the pop-up message comes with our friendly cute robot.
 
System logs
System logs can be only accessed both for manager and the administrator, System logs records the events done in the system. 
 
User Management
User management can be accessed by both of the administrator and manager. Here we can create new, manage and delete the credentials of the employee.
 
Main Portal
This portal allows the user to choose what system to use either Inventory System or the Point of Sales System, This portal is only used for business transactions which can be only access by the manager and the store clerk.
 
 
Inventory System Statistics
In this section, the Inventory statistic records which products are the best and least selling, The products with least stocks and the best-selling products per category.

 
Supplier Panel
In this section, we can see the list of the supplier of Fred’s General Merchandise store business as well as their information so they can contact them later for restocking of the products. They can also create, manage and remove the credentials of the supplier by using the buttons below the table.

 
New Supplier
This section you can create new supplier by entering their information, Supplier ID is automatically generated by the system, Contact Number text field only accepts 11 characters of numerical value. The Name and Address text fields accepts up to 255 characters.
   
Manage Supplier
Manage Supplier panel allows the user to manage the credential of the supplier, to use this the user must click the supplier they want to manage in the JTable then click the manage button below then he/she proceeds to edit the credential of the supplier, failing to do so will trigger our robot pop-up message stating that you need to click a row then click the manage button.
 
 
Remove Supplier
Remove Supplier function is same as the Manage Supplier that the user needs to click a row then click the Remove button, failing to do so automatically triggers the popup message stating that you need to click a row to delete


 
 
Manage Inventory
Manage Inventory allows you see the list of your products, in this section you can create manage and remove a product. We can also see the details of the product as well as receiving the stocks from suppliers and pulling out of the product.
 
 
 
Add Product
In this section we can create new products, The Product ID is automatically generated by the system, we can select what category the product is, as well as whose supplier the product came from. Unit of measurement is how you sell the product and how much you bought the product and how much you will sell the product. You can also select the icon of the product by clicking the “Select Icon” button which is a JfileChooser and it only accept 32 x 32 pixels of icon.
 
Manage Product
Manage Product allows you to manage the information of the product, to do so you need to click the existing product in the table then click the manage button and proceeds to editing the information of the product.
 
 
Remove Product
Remove Product allows the user to delete an existing product, to do so the user must click an existing product in the table then proceeds to delete it, failing to do so automatically triggers the robot pop up message.
 
 
Receive Stocks
Receive stocks buttons allows the user to add more stocks from the specific supplier when the product has been delivered to them, The user must click and existing product to receive then proceeds to input the quantity of product they received from their supplier.
 
 
Pull Out 
Pull out section allows the user to decrease the quantity of stocks by clicking the existing stocks in the table and inputting the number of stocks they want to pull out or decrease.
 
 
Point of Sale System
This Section allows the user to sell the product they created in the Inventory System, The “Today Transaction #1” above automatically change and generate whenever the user does new transaction, We can also search the product we want to sell by using the search bar and the “how many?” textfield allows the user to enter how much specific product they want to sell. In the right panel is the list of the product the user entered and to be sold. In the lower section we can see the checkout and cancel button as well as the details of the cashier that sells the product, total product and its cart contents and the total price to be paid by customer. The panel containing the product uses ArrayList for sorting the products since it the system uses dynamic sizing.
 
 
Check Out System
In this section, this is the checkout section for the POS, here we can select customer types: Regular, PWD/Senior for Medical groceries which gives 20% discount and non-VAT, and PWD/Senior for groceries with the limit of 1300 PHP which gives 5% discount with VAT. In the right side of the panel, we can see the model of our receipt which contains the transaction number, date and the cashier’s name who conducted the transaction, the list of items and amount to be paid. The lower left panel contains how the details of transaction and the textfield contains the amount tendered which is the payment that the customer to be made. The receipts of the transaction automatically save in the system as a file and ready to be printed.
 
 
Register Customer Panel
In this panel we can register new customer as well as their discount type depending on their priveledge if they are Senior Citizen or PWD.
 
 
Transaction History Panel
In this section we can view the transaction history of the customer and the user made by inputting the transaction number found within the receipt of the customer bought in the store. If there’s no receipt appeared when the user searched the transaction id then there’s no transaction have been made that day. There’s also statistic that records the total of transaction made within specific timestamp.
 
  
Reports Panel
This Section Contains the reports of the Point-of-Sales System, it contains the best-selling and least selling products, Top employees that conducted transactions, most product sold by category, lastly the generate reports. Generate report button allows the user to generate an accurate report of the POS transactions. The Report automatically stored in the system as a text file.
