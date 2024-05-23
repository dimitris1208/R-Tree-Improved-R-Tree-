package rtree;

import java.util.ArrayList;
import java.util.List;

public class Node {
    private List<Registry> registries;
    private boolean isLeaf;
    private Node parent;

    public Node(boolean isLeaf){
        this.isLeaf = isLeaf;
        this.registries = new ArrayList<>();
    }

    public boolean isLeaf(){
        return isLeaf;
    }

    public List<Registry> getRegistries() {
        return registries;
    }

    public void addRegistry(Registry registry){
        registries.add(registry);
    }

    public void removeRegistry(Registry registry){
        registries.remove(registry);
    }

    public Node getParent(){
        return parent;
    }

    public void setParent(Node parent){
        this.parent = parent;
    }

    public Rectangle getRect(){
        if (registries.isEmpty()){
            return null;
        }
        Rectangle rect = registries.get(0).getRect();
        for (Registry registry : registries){
            rect = combineRectangles(rect , registry.getRect());
        }
        return rect;
    }

    private Rectangle combineRectangles(Rectangle r1 , Rectangle r2){
        double[] minCoords = new double[r1.minPin.getDimension()];
        double[] maxCoords = new double[r1.minPin.getDimension()];

        for (int i = 0; i < r1.minPoint.getDimension(); i++) {
            minCoords[i] = Math.min(r1.minPin.getCoordinates()[i], r2.minPin.getCoordinates()[i]);
            maxCoords[i] = Math.max(r1.maxPin.getCoordinates()[i], r2.maxPin.getCoordinates()[i]);
        }

        return new Rectangle(new Pin(minCoords), new Pin(maxCoords));
    }
}