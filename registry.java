package rtree;

public class Registry {
    private Rectangle rect;
    private int recID;

    public Registry(Rectangle rect , int recID){
        this.rect = rect;
        this.recID = recID;
    }

    public Rectangle getRect() {
        return rect;
    }

    public int getRecID(){
        return recID;
    }
}