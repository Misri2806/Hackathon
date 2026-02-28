
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
}