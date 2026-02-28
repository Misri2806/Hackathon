
public class Food
{
    private String name;
    private int quantity;
    private String area;

    public Food(String name, int quantity, String area)
    {
        this.name = name;
        this.quantity = quantity;
        this.area = area;
    }
    public String getName()
    {
        return name;
    }
    public int getQuantity()
    {
        return quantity;
    }
    public String getArea()
    {
        return area;
    }   
}