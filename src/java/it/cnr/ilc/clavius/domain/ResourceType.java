package it.cnr.ilc.clavius.domain;



/**
 *
 * @author oakgen
 */
public class ResourceType{

    private String name;
    private ResourceType father;
    private ResourceType child;
    private String color;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ResourceType getFather() {
        return father;
    }

    public void setFather(ResourceType father) {
        this.father = father;
    }

    public ResourceType getChild() {
        return child;
    }

    public void setChild(ResourceType child) {
        this.child = child;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }
}
