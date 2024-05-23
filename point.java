package rtree;

public class Pin {
    private double[] coordinates;

    public Pin (double[] coordinates){
        this.coordinates = coordinates;
    }

    public double[] getCoordinates(){
        return coordinates;
    }

    public int getDimension(){
        return coordinates.length;
    }
}