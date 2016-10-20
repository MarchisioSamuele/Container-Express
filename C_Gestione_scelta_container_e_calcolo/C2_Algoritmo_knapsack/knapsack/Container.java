package knapsack_package;

/**
 * Created by Luca on 14/10/2016.
 */
public class Container {
    private double lenght;
    private double height;
    private double width;


    private double weight;



    private double volume;

    public double getWeight() {
        return weight;
    }

    private int maxWeight;

    public Container(double lenght, double height, double width, double weight) {
        this.lenght = lenght;
        this.height = height;
        this.width = width;
        this.weight = weight;
        volume = lenght * height * width;
    }

    public double getLenght() {
        return lenght;
    }

    public double getHeight() {
        return height;
    }

    public double getWidth() {
        return width;
    }

    public double getVolume() {
        return volume;
    }

    public void setVolume(double volume) {
        this.volume = volume;
    }
    public void incVolume(double volume) {
        this.volume += volume;
    }
    public void setWeight(double weight) {
        this.weight = weight;
    }

    public void incWeight(double weight) {
        this.weight += weight;
    }



}
