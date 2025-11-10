import java.util.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class FilkomMart3 {
    static class Item {
        private final String name;
        private final int quantity;
        private final int unitPrice;
        
        public Item(String name, int quantity, int unitPrice) {
            this.name = name;
            this.quantity = quantity;
            this.unitPrice = unitPrice;
        }
        
        public int getTotal() {
            return quantity * unitPrice;
        }
        
        public String getName() {
            return name;
        }
        
        public int getQuantity() {
            return quantity;
        }
        
        public int getUnitPrice() {
            return unitPrice;
        }
    }

    static class Receipt {
        private final String cashierName;
        private final List<Item> items;
        private final double payment;
        private final Random rand = new Random();
        
        public Receipt(String cashierName, List<Item> items, double payment) {
            this.cashierName = cashierName;
            this.items = items;
            this.payment = payment;
        }
        
        public double calculateSubtotal() {
            return items.stream().mapToDouble(Item::getTotal).sum();
        }
        
        public double calculateDiscount() {
            double subtotal = calculateSubtotal();
            if (subtotal >= 500000) return subtotal * 0.2;
            if (subtotal >= 100000) return subtotal * 0.1;
            if (subtotal >= 50000) return subtotal * 0.05;
            return 0;
        }
        
        public void printReceipt() {
            double subtotal = calculateSubtotal();
            double discount = calculateDiscount();
            double total = subtotal - discount;
            double change = payment - total;
            
            System.out.println("\nFILKOM-MART");
            System.out.println("JL VETERAN MALANG");
            System.out.println("TELP. 0341-577911");
            System.out.println(LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd MMMM yyyy HH:mm")));
            System.out.println("Receipt Number : " + (100000 + rand.nextInt(900000)));
            System.out.println("Order ID : FM" + (100 + rand.nextInt(900)));
            System.out.println("Collected by : " + cashierName);
            System.out.println("=================================");
            
            for (Item item : items) {
                System.out.println(item.getName());
                System.out.printf("%dx @%,d %,d\n", item.getQuantity(), item.getUnitPrice(), item.getTotal());
            }
            
            System.out.println("=================================");
            System.out.printf("Subtotal Rp. %,d\n", (int)subtotal);
            System.out.printf("Diskon %.0f%% -Rp. %,.0f\n", (discount/subtotal)*100, discount);
            System.out.println("=================================");
            System.out.printf("Total Rp %,.0f\n", total);
            System.out.printf("Bayar Rp %,.0f\n", payment);
            System.out.println("=================================");
            System.out.printf("Kembali - Rp %,.0f\n", change);
            System.out.println("=================================");
            System.out.println("Terima Kasih Telah Berbelanja di FILKOM-MART");
        }
    }

    public static void main(String[] args) {
        try (Scanner input = new Scanner(System.in)) {
            System.out.print("Masukkan nama kasir: ");
            String cashierName = input.nextLine();
            
            System.out.print("Masukkan jumlah barang yang dibeli: ");
            int itemCount = input.nextInt();
            input.nextLine();
            
            List<Item> items = new ArrayList<>();
            
            for (int i = 0; i < itemCount; i++) {
                System.out.print("Nama barang " + (i + 1) + ": ");
                String name = input.nextLine();
                
                System.out.print("Jumlah unit barang " + (i + 1) + ": ");
                int quantity = input.nextInt();
                
                System.out.print("Harga satuan barang " + (i + 1) + ": ");
                int unitPrice = input.nextInt();
                input.nextLine();
                
                items.add(new Item(name, quantity, unitPrice));
            }
            
            Receipt receipt = new Receipt(cashierName, items, 0);
            double total = receipt.calculateSubtotal() - receipt.calculateDiscount();
            
            System.out.printf("Total yang harus dibayar: Rp %,.0f\n", total);
            System.out.print("Jumlah pembayaran: ");
            double payment = input.nextDouble();
            
            receipt = new Receipt(cashierName, items, payment);
            receipt.printReceipt();
        }
    }
}