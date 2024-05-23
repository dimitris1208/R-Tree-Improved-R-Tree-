package rstartree;

import java.util.ArrayList;
import java.util.List;

public class RStarTree {
    private Node root;
    private int maxRegistries;
    private int minRegistries;

    public RStarTree(int maxRegistries) {
        this.maxRegistries = maxRegistries;
        this.minRegistries = maxRegistries / 2;
        this.root = new Node(true);
    }

    public void insert(Registry registry) {
        Node leaf = chooseLeaf(root, registry);
        leaf.addRegistry(registry);

        if (leaf.getRegistries().size() > maxRegistries) {
            Node[] splits = splitNode(leaf);
            adjustTree(splits[0], splits[1]);
        } else {
            adjustTree(leaf, null);
        }
    }

    public void delete(Registry registry) {
        Node leaf = findLeaf(root, registry);
        if (leaf != null) {
            leaf.removeRegistry(registry);
            condenseTree(leaf);
        }
    }

    public List<Registry> rangeQuery(Rectangle rectangle) {
        List<Registry> results = new ArrayList<>();
        rangeQuery(root, rectangle, results);
        return results;
    }

    private void rangeQuery(Node node, Rectangle rectangle, List<Entry> results) {
        for (Registry registry : node.getRegistries()) {
            if (rectangle.intersects(registry.getRect())) {
                if (node.isLeaf()) {
                    if (rectangle.contains(registry.getRect().minPin)) {
                        results.add(registry);
                    }
                } else {
                    rangeQuery((Node) registry.getRect(), rectangle, results);
                }
            }
        }
    }

    // TODO: Υλοποίηση των υπόλοιπων λειτουργιών (π.χ. k-nn, skyline queries)

    private Node chooseLeaf(Node node, Registry registry) {
        if (node.isLeaf()) {
            return node;
        }

        Node bestChild = null;
        double minEnlargement = Double.MAX_VALUE;

        for (Registry e : node.getRegistries()) {
            Node child = (Node) e.getRect();
            double enlargement = getEnlargement(child.getRect(), registry.getRect());
            if (enlargement < minEnlargement) {
                minEnlargement = enlargement;
                bestChild = child;
            } else if (enlargement == minEnlargement) {
                if (getArea(child.getRect()) < getArea(bestChild.getRect())) {
                    bestChild = child;
                }
            }
        }

        return chooseLeaf(bestChild, registry);
    }

    private double getEnlargement(Rectangle r1, Rectangle r2) {
        double[] minCoords = new double[r1.minPin.getDimension()];
        double[] maxCoords = new double[r1.minPin.getDimension()];

        for (int i = 0; i < r1.minPin.getDimension(); i++) {
            minCoords[i] = Math.min(r1.minPin.getCoordinates()[i], r2.minPin.getCoordinates()[i]);
            maxCoords[i] = Math.max(r1.maxPin.getCoordinates()[i], r2.maxPin.getCoordinates()[i]);
        }

        double newArea = 1.0;
        for (int i = 0; i < minCoords.length; i++) {
            newArea *= (maxCoords[i] - minCoords[i]);
        }

        double currentArea = getArea(r1);
        return newArea - currentArea;
    }

    private double getArea(Rectangle rectangle) {
        double area = 1.0;
        for (int i = 0; i < rectangle.minPin.getDimension(); i++) {
            area *= (rectangle.maxPin.getCoordinates()[i] - rectangle.minPin.getCoordinates()[i]);
        }
        return area;
    }

    private Node[] splitNode(Node node) {
        // TODO: Υλοποίηση του αλγορίθμου split για το R*-tree
        return new Node[2];
    }

    private void adjustTree(Node node, Node splitNode) {
        if (node == root) {
            if (splitNode != null) {
                Node newRoot = new Node(false);
                newRoot.addRegistry(new Registry(node.getRect(), 0));
                newRoot.addRegistry(new Registry(splitNode.getRect(), 1));
                root = newRoot;
                node.setParent(root);
                splitNode.setParent(root);
            }
            return;
        }

        Node parent = node.getParent();
        Registry registry = new Registry(node.getRect(), node.getRegistries().indexOf(node));
        if (splitNode != null) {
            Registry splitEntry = new Registry(splitNode.getRect(), parent.getRegistries().indexOf(splitNode));
            parent.addRegistry(splitEntry);
        }

        if (parent.getEntries().size() > maxEntries) {
            Node[] splits = splitNode(parent);
            adjustTree(splits[0], splits[1]);
        } else {
            adjustTree(parent, null);
        }
    }

    private
