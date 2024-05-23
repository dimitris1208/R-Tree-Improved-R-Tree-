package rtree;

public class Rectangle {
    private Pin minPin;
    private Pin maxPin;

    public Rectangle(Pin minPin , Pin maxPin){
        this.minPin = minPin;
        this.maxPin = maxPin;
    }

    public boolean isInside(Pin pin){
        for (int i = 0 ; i < pin.getDimension(); i++){
            if (pin.getCoordinates()[i] < minPin.getCoordinates()[i] || pin.getCoordinates()[i] > maxPin.getCoordinates()[i]){
                return false;
            }
            else return true;
        }
    }

    public boolean intersects(Rectangle another){
        for(int i = 0 ; i < minPin.getDimension() ; i++){
            if (this.maxPin.getCoordinates()[i] < another.minPin.getCoordinates()[i] ||
                this.minPin.getCoordinates()[i] < another.maxPin.getCoordinates()[i]){
                return false;
            }
            else return true;
        }
    }

}