package lsj.disruptor2;

/**
 * @author TF016519
 * @description:
 * @date 2018-1-22 17:24
 * @version:1.0.0
 */
public class Order {
    private String id;
    private String name;
    private double price;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
