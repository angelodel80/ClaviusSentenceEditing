package it.cnr.ilc.clavius.domain;

import java.util.List;
import java.util.Map;

/**
 *
 * @author oakgen
 */

public class Resource{

    private ResourceType type;
    private String title;
    private String text;
    private Resource parent;
    private List<Resource> children;
   
    private Map<String, String> attributes;
    private Account account;

    public ResourceType getType() {
        return type;
    }

    public void setType(ResourceType type) {
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }


    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }


    public Resource getParent() {
        return parent;
    }

    public void setParent(Resource parent) {
        this.parent = parent;
    }

    public List<Resource> getChildren() {
        return children;
    }

    public void setChildren(List<Resource> children) {
        this.children = children;
    }


    public void setAttributes(Map<String, String> attributes) {
        this.attributes = attributes;
    }


    public void setAccount(Account account) {
        this.account = account;
    }

}
