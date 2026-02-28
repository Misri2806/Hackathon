
import java.util.List;

import io.javalin.Javalin;

public class runner {
    public static void main(String[] args) { // Changed 'runner' to 'main'
        String filePath = "C:\\Misri\\Hackathon\\food.csv"; 
        List<Food> foods = FoodController.loadFoods(filePath);
        
        for (Food food : foods) {
            System.out.println("Name: " + food.getName() + ", Quantity: " + food.getQuantity() + ", Area: " + food.getArea());
        }

        // Initialize and start Javalin
        Javalin app = Javalin.create().start(7000);

        // Define the route
        app.get("/foods", ctx -> {
            ctx.json(foods);
        });
    }
}