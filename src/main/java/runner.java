
import java.util.List;

import io.javalin.Javalin;

public class runner {
    public static void main(String[] args) { // Changed 'runner' to 'main'
        String filePath = "food.csv"; 
        

        // Initialize and start Javalin
        Javalin app = Javalin.create().start(7000);

        // Define the route
        app.get("/foods", ctx -> {
            List<Food> foods = FoodController.loadFoods(filePath);
            ctx.json(foods);
        
        });
        app.post("/addFood", ctx -> {
            System.out.println("Received POST request to add food: " + ctx.body());
            Food newFood = ctx.bodyAsClass(Food.class);
            FoodController.addFoods(filePath, newFood);
            ctx.status(201).result("Food added successfully");
        });


        app.delete("/removeFood", ctx -> {
            System.out.println("Received DELETE request to remove food: " + ctx.body());
            int indexValue = Integer.parseInt(ctx.queryParam("index")); // Get index from query parameter
            FoodController.deleteFood(filePath, indexValue);
            ctx.status(200).result("Food removed successfully");
        });

    }
}