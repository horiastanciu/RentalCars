/**
 * Created by Horica on 3/28/2017.
 */

public class Vehicle{
    private String sipp;
    private String name;
    private double price;
    private String supplier;
    private double rating;

    public Vehicle() {
    }

    public void setSipp(String sipp){
        this.sipp = sipp;
    }

    public void setName(String name){
        this.name = name;
    }

    public void setPrice(double price){
        this.price = price;
    }

    public void setSupplier(String supplier) {
        this.supplier = supplier;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public String getCarType() {
        switch(getSipp().charAt(0)) {
            case 'M': return "Mini";
            case 'E': return "Economic";
            case 'C': return "Compact";
            case 'I': return "Intermediate";
            case 'S': return "Standard";
            case 'F': return "Full size";
            case 'P': return "Premium";
            case 'L': return "Luxury";
            case 'X': return "Special";
            default: return null;
        }
    }

    public String getDoors() {
        switch(getSipp().charAt(1)) {
            case 'B': return "2 doors";
            case 'C': return "4 doors";
            case 'D': return "5 doors";
            case 'W': return "Estate";
            case 'T': return "Convertible";
            case 'F': return "SUV";
            case 'P': return "Pick up";
            case 'V': return "Passenger Van";
            default: return null;
        }
    }

    public String getTransmission() {
        switch(getSipp().charAt(2)) {
            case 'M': return "Manual";
            case 'A': return "Automatic";
            default: return null;
        }
    }

    public String getFuelAC() {
        switch(getSipp().charAt(3)) {
            case 'N': return "Petrol/No AC";
            case 'R': return "Petrol/AC";
            default: return null;
        }
    }

    public String getFuel() {
        return getFuelAC().split("/")[0];
    }

    public String getAC() {
        return getFuelAC().split("/")[1];
    }

    public int getScore() {
        int score = 0;
        if (getTransmission().equals("Manual")) score ++;
        if (getTransmission().equals("Automatic")) score += 5;
        if (getAC().equals("AC")) score += 2;
        return score;
    }

    public String getSupplier() {
        return supplier;
    }

    public String getSipp() {
        return sipp;
    }

    public double getPrice() {
        return price;
    }

    public double getRating() {
      return rating;
    }

    public String part1() {
        return "{" + name + "} - {" + price + "}";
    }

    public String part2() {
        return "{" + name + "} - {" + sipp + "} - " +
                "{" + getCarType() + "} - {" + getDoors() + "} - " +
                "{" + getTransmission() + "} - {" + getFuel() + "} - " +
                "{" + getAC() + "}";
    }

    public String part3() {
        return "{" + name + "} - {" + getCarType() + "} - " + "{" + supplier + "} - {" + rating +  "}";
    }

    public String part4() {
        return "{" + name + "} - {" + getScore() + "} - {" + rating + "} - {" + (getScore() + rating) + "}";
    }
}