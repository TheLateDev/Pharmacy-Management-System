import java.util.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

// Custom exception function
class contactException extends Exception {
 public String toString() {
  return "Invalid Contact Number! Must be 10 numeric digits.";
 }
}

public class Admin {
 String admin_name;
 int userId;
 MedicineDatabase db;
 BillRecords bdb;

 Admin(String name, int id) {
  this.admin_name = name;
  this.userId = id;
  this.db = new MedicineDatabase();
  this.bdb = new BillRecords();
 }

 void choice(Scanner sc) {
  System.out.println("\nHello " + admin_name + ",");
  boolean flag = true;
  while (flag) {
   System.out.print(
     "\n(1) Add medicine to database.\n(2) Delete Medicine from database.\n(3) Display Database\n(4) Generate Bill\n(5) Display All bills \n(6) Search for Bill\n(7) Logout\n(8) Exit\n>> ");
   int ch = -1;
   try {
    ch = Integer.parseInt(sc.nextLine().trim());
   } catch (NumberFormatException e) {
    System.out.println("Invalid choice! Please enter a number.");
    continue;
   }
   switch (ch) {
    case 1:
     addMedicine(sc);
     break;
    case 2:
     deleteMedicine(sc);
     break;
    case 3:
     db.displayDatabase();
     break;
    case 4:
     generateBill(sc);
     break;
    case 5:
     bdb.displayBills();
     break;
    case 6:
     try {
      System.out.print("Enter user contact number to search for their bill: ");
      String user_contact = sc.nextLine().trim();
      if (user_contact.length() != 10 || !isNumeric(user_contact)) {
       throw new contactException();
      }
      System.out.print("Enter the date(DD/MM/YYYY) of billing: ");
      String date = sc.nextLine().trim();
      bdb.search(user_contact, date);
     } catch (contactException e) {
      System.out.println(e.toString());
     }
     break;
    case 7:
     System.out.println("Logged out successfully!");
     return;
    case 8:
     System.out.println("Exiting system. Goodbye!");
     System.exit(0);
     break;
    default:
     System.out.println("Invalid choice! Please select 1-8.");
   }
  }
 }

 public void addMedicine(Scanner sc) {
  System.out.print("Enter medicine name: ");
  String name = sc.nextLine().trim();
  System.out.print("Enter production company name: ");
  String company = sc.nextLine().trim();
  System.out.print("Enter quantity: ");
  int quantity = 0;
  try {
   quantity = Integer.parseInt(sc.nextLine().trim());
  } catch (NumberFormatException e) {
   System.out.println("Invalid quantity! Must be an integer.");
   return;
  }
  System.out.print("Enter expiry date (DD/MM/YYYY): ");
  String expiry_date = sc.nextLine().trim();
  System.out.print("Enter price: ");
  double price = 0.0;
  try {
   price = Double.parseDouble(sc.nextLine().trim());
  } catch (NumberFormatException e) {
   System.out.println("Invalid price! Must be a number.");
   return;
  }
  Medicine med = new Medicine(name, company, quantity, expiry_date, price);
  db.addMedicine(med);
 }

 public void deleteMedicine(Scanner sc) {
  System.out.print("Enter the name of the medicine you want to delete: ");
  String name = sc.nextLine().trim();
  db.deleteMedicine(name);
 }

 public void generateBill(Scanner sc) {
  try {
   System.out.print("Enter customer name: ");
   String name = sc.nextLine().trim();
   System.out.print("Enter date of purchase (DD/MM/YYYY): ");
   String date = sc.nextLine().trim();
   System.out.print("Enter phone number: ");
   String phone_no = sc.nextLine().trim();
   if (phone_no.length() != 10 || !isNumeric(phone_no)) {
    throw new contactException();
   }
   System.out.print("Enter doctor referral: ");
   String doctor = sc.nextLine().trim();
   System.out.println("Add medicines to cart: ");
   Vector<Medicine> cust_meds = new Vector<Medicine>();
   Vector<Integer> quantity = new Vector<Integer>();
   boolean flag = true;
   while (flag) {
    purchaseMedicine(sc, cust_meds, quantity, date);
    System.out.print("Do you want more medicines? (Y/N): ");
    String med_choice = sc.nextLine().trim();
    if (!med_choice.equalsIgnoreCase("Y")) {
     flag = false;
    }
   }
   Bill b = new Bill(name, date, phone_no, doctor, cust_meds, quantity);
   if (!b.purchased_medicines.isEmpty()) {
    bdb.addBill(b);
    b.getBillDetails();
   } else {
    System.out.println("No medicines were purchased.");
   }
  } catch (contactException e) {
   System.out.println(e.toString());
  }
 }

 public void purchaseMedicine(Scanner sc, Vector<Medicine> purchased_medicines, Vector<Integer> quantitites, String date) {
  System.out.print("Enter Medicine name: ");
  String med_name = sc.nextLine().trim();
  System.out.print("Enter Medicine quantity: ");
  int med_quantity = 0;
  try {
   med_quantity = Integer.parseInt(sc.nextLine().trim());
  } catch (NumberFormatException e) {
   System.out.println("Invalid quantity! Must be an integer.");
   return;
  }
  int avail = 0;
  if (db.all_medicines.isEmpty()) {
   System.out.println("The Database is Empty!");
   return;
  } else {
   for (Medicine i : db.all_medicines) {
    if (med_name.equalsIgnoreCase(i.name) && med_quantity <= i.quantity && checkDate(date, i.expiry_date)) {
     avail = 1;
     break;
    }
   }
  }
  if (avail == 1) {
   Medicine med = db.getMeds(med_name, med_quantity);
   purchased_medicines.add(med);
   quantitites.add(med_quantity);
   System.out.println("Added the medicine to cart!");
  } else {
   System.out.println("The Medicine for given quantity is not available or has expired!");
  }
 }

 public static Boolean checkDate(String d1, String d2) {
  DateTimeFormatter fmt = DateTimeFormatter.ofPattern("d/M/yyyy");
  try {
   LocalDate purchaseDate = LocalDate.parse(d1.trim(), fmt);
   LocalDate expiryDate = LocalDate.parse(d2.trim(), fmt);
   return !purchaseDate.isAfter(expiryDate);
  } catch (Exception e) {
   return false;
  }
 }

 public static boolean isNumeric(String s) {
  try {
   Long.parseLong(s);
  } catch (NumberFormatException ex) {
   return false;
  }
  return true;
 }
}