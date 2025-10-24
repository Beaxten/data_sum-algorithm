import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.time.format.TextStyle;

public class data_general_wise {
    public static HashMap<String, HashMap<Integer, ArrayList<Integer>>> data_price(int choice){
        HashMap<String, HashMap<Integer, ArrayList<Integer>>> result = new HashMap<>();

         File myfile = new File("data.txt");
        try(Scanner fi = new Scanner(myfile)){
             while(fi.hasNextLine()){
                String line = fi.nextLine();
                ArrayList<String> parts = new ArrayList<>(Arrays.asList(line.split("\t")));

                int unit_price = 0;
                int qty = 0;

               try{
                      unit_price = Integer.parseInt(parts.get(4));
                        qty = Integer.parseInt(parts.get(5));

                        if(choice == 5){
                            String day = date_arrange(parts.get(0) , false);
                            result.putIfAbsent(day,new HashMap<>());
                            result.get(day).putIfAbsent(unit_price, new ArrayList<>());
                            result.get(day).get(unit_price).add(qty);
                        }

                        else if (choice == 4){
                            String month = date_arrange(parts.get(0) , true);
                            result.putIfAbsent(month,new HashMap<>());
                            result.get(month).putIfAbsent(unit_price, new ArrayList<>());
                            result.get(month).get(unit_price).add(qty);  
                        }else{ String item = parts.get(4-choice);
                            result.putIfAbsent(item, new HashMap<>());
                            result.get(item).putIfAbsent(unit_price, new ArrayList<>());
                            result.get(item).get(unit_price).add(qty); }        
               }
               catch(NumberFormatException e){
                    continue;}
                parts.clear();             
            }
        }
        catch(IOException e){
            e.printStackTrace();
        }
       return result;
    }

    public static String date_arrange(String dateString ,boolean choice  ){   
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MMM-yyyy", Locale.ENGLISH);
        DateTimeFormatter formatter2 = DateTimeFormatter.ofPattern("d-MMM-yyyy", Locale.ENGLISH);   
              
        try{
            return formatter(dateString ,choice , formatter);
            
        }
        catch(Exception e){
           return formatter(dateString ,choice , formatter2);
          }         
    }

    public static String formatter(String dateString ,boolean choice , DateTimeFormatter formatter){
             LocalDate date = LocalDate.parse(dateString, formatter);
                if(choice == true){
                    String month = date.getMonth().getDisplayName(TextStyle.FULL, Locale.ENGLISH);

                    return month;      
                }
                else{
                    String dayName = date.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.ENGLISH);
                    return dayName;      
                }
    }

    public static void select_option(int choice ,String item  ){
         HashMap<String, HashMap<Integer, ArrayList<Integer>>> result = data_price(choice);
         Scanner scanner2 = new Scanner(System.in);
         int total_amount = 0;

          for(String obj : result.keySet()){
                    System.out.println(item+": " + obj);
                }
                System.out.print("choose a "+ item +" from the above list: ");                
                String obj_choice = scanner2.nextLine();
                if(result.containsKey(obj_choice)){
                   
                   for (Map.Entry<Integer, ArrayList<Integer>> entry : result.get(obj_choice).entrySet()) {
                        int unit_price = entry.getKey();
                        ArrayList<Integer> qtys = entry.getValue();
                        for (int qty : qtys) {
                            total_amount += unit_price * qty;
                        }
                    }    
                }
                else{
                    System.out.println("Invalid " + item + " choice.");
                }
                 System.out.println("The total sales amount is: " + total_amount ); 
    }

      public static void main(String[] args){
        
        System.out.print(
    "Product wise total sales amount: type '1'\n" +
    "ID wise total sales amount: type '2'\n" +
    "Region wise total sales amount: type '3'\n" +
    "Month wise total sales amount: type '4'\n" +
    "Date wise total sales amount: type 5\n" +
    "type your choice: "
);
        Scanner scanner = new Scanner(System.in);
        
        int choice = scanner.nextInt();
        
           
            if(choice == 1){
                select_option(choice , "product"  );
            }
            
            else if(choice == 2){
                select_option(choice , "ID"  );
            }

            if(choice == 3){
                select_option(choice , "region"  );
            }   

        else if (choice == 4){
           select_option(choice , "Month"  );
        }
        else if(choice == 5){
            select_option(choice, "Date");
        }
       
    }
}
