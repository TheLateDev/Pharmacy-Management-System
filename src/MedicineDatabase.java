import java.util.Vector;

public class MedicineDatabase {
 public Vector<Medicine> all_medicines = new Vector<Medicine>();

 public MedicineDatabase() {
 }

 public void addMedicine(Medicine b) {
  all_medicines.add(b);
  System.out.println("Medicine successfully added to the database!");
 }

 public void deleteMedicine(String title) {
  boolean check_med = false;
  for (int i = 0; i < all_medicines.size(); i++) {
   if (title.equalsIgnoreCase(all_medicines.get(i).name)) {
    all_medicines.remove(i);
    check_med = true;
    System.out.println("Medicine successfully deleted from database!");
    i--;
   }
  }
  if (!check_med) {
   System.out.println("Medicine not available in database!");
  }
 }

 public Medicine getMeds(String Name, int quantities) {
  for (Medicine i : all_medicines) {
   if (Name.equalsIgnoreCase(i.name) && quantities <= i.quantity) {
    i.quantity -= quantities;
    return new Medicine(i.name, i.company, quantities, i.expiry_date, i.price);
   }
  }
  return new Medicine();
 }

 public void displayDatabase() {
  if (all_medicines.isEmpty()) {
   System.out.println("\nThe Database is Empty!");
   return;
  }
  System.out.println("\nName\t\tCompany\t\tQuantity\tExpiry\t\tPrice");
  for (int i = 0; i < all_medicines.size(); i++)
   all_medicines.get(i).getMedicineDetails();
 }
}