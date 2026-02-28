
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FoodController 
{
    private Food[] foods;

    public FoodController(Food[] foods) 
    {
        this.foods = foods;
    }

    public Food[] getFoods() 
    {
        return foods;
    }

    public void setFoods(Food[] foods) 
    {
        this.foods = foods;
    }



/**
 * Portable utility function for loading Food objects from a CSV.
 * Fields expected: name, quantity, area
 */
public static List<Food> loadFoods(String filePath) {
    List<Food> foodsList = new ArrayList<>();

    try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
        String line;
        
        // Skip header row
        br.readLine(); 

        while ((line = br.readLine()) != null) {
            // Split by comma, handling potential surrounding whitespace
            String[] values = line.split(",");
            
            if (values.length >= 3) {
                String name = values[0].trim();
                int quantity = Integer.parseInt(values[1].trim());
                String area = values[2].trim();

                foodsList.add(new Food(name, quantity, area));
            }
        }
    } catch (IOException e) {
        System.err.println("Could not read file: " + e.getMessage());
    } catch (NumberFormatException e) {
        System.err.println("Skipping row: Invalid number format in quantity column.");
    }

    return foodsList;
}

public static void addFoods(String filePath, Food newFood) {
    List<Food> foodsList = loadFoods(filePath);
    foodsList.add(newFood);
    saveFoods(filePath, foodsList);

}

public static void saveFoods(String filePath, List<Food> foodsList) {
    try (java.io.FileWriter fw = new java.io.FileWriter(filePath, false)) {
        // Write header
        fw.write("name,quantity,area\n");
        for (Food food : foodsList) {
            String row = String.format("%s,%d,%s\n", food.getName(), food.getQuantity(), food.getArea());
            fw.write(row);
        }
    } catch (IOException e) {
        System.err.println("Could not write to file: " + e.getMessage());
    }

}

public static void deleteFood(String filePath, int indexValue) {
    List<Food> foodsList = loadFoods(filePath);
    foodsList.remove(indexValue);
    saveFoods(filePath, foodsList);
}}