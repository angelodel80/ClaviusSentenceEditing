/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.cnr.ilc.clavius.domain;

import java.util.Map;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.Namespace;

/**
 *
 * @author Angelo Del Grosso
 */
public class HierarchicalTeiDocument {

    Document teiDocument;
    Map<String, String> sentences;
    Element header;
    Element text;

    public Element getHeader() {
        return header;
    }

    public void setHeader(Element header) {
        this.header = header;
    }

    public Element getText() {
        return text;
    }

    public void setText(Element text) {
        this.text = text;
    }

    public Document getTeiDocument() {
        return teiDocument;
    }

    public void setTeiDocument(Document teiDocument) {
        this.teiDocument = teiDocument;
        setHeader(this.teiDocument.getRootElement().getChildren("teiHeader", this.teiDocument.getRootElement().getNamespace()).get(0));
        setText(this.teiDocument.getRootElement().getChildren("text", this.teiDocument.getRootElement().getNamespace()).get(0));
    }

    public Map<String, String> getSentences() {
        return sentences;
    }

    public void setSentences(Map<String, String> sentences) {
        this.sentences = sentences;
    }

    public String getSentencesFullText() {
        StringBuilder sBuilder = null;

        if (null != sentences) {
            sBuilder = new StringBuilder();
            Object[] sents = sentences.values().toArray();
            for (Object sent : sents) {
                String s = (String) sent;
                sBuilder.append(s.trim() + "\n");
                //System.out.print(s.trim()+"\n");
            }
            return sBuilder.toString();
        }
        else return "";
    }

}
