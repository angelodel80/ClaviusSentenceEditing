/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package it.cnr.ilc.clavius.controller;

import it.cnr.ilc.clavius.domain.Sentence;
import it.cnr.ilc.clavius.manager.TranscriptionManager;
import java.io.Serializable;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import org.jdom2.Document;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

/**
 *
 * @author Angelo Del Grosso
 */
@SessionScoped
@Named
public class TranscriptionController extends BaseController implements Serializable {
    private String template="<TEI><s n=\"1\"></s><s n=\"2\"></s><s n=\"3\"></s><s n=\"4\"></s><s n=\"5\"></s></TEI>"; // to be fixed
 
    @Inject
    private transient Sentence sentence;
    
    @Inject
    private transient TranscriptionManager transcriptionManager;

    public String getSentence() {
        if(!"".equals(sentence.getContent()) && null!=sentence.getContent())
            return sentence.getContent();
        else
            return Sentence.DEF_CONTENT;
    }

    public void setSentence(String sentence) {
        this.sentence.setContent(sentence);
    }
    
     public String getTemplate() {
        if(null==transcriptionManager.getDocTemplate())
            return template;
        else{
            Document doc = transcriptionManager.getDocTemplate();
            XMLOutputter xop = new XMLOutputter(Format.getPrettyFormat());
            return xop.outputString(doc);
        }
            
    }

    public void setTemplate(String template) {
        this.template = template;
    }
   
    public void selectSentence(String id, String idLetter){
        //System.err.println("selectSentence: " + id + " -- letter: " + idLetter);
        sentence.setIdentificator(id);
        sentence.setLetterIdentificator(idLetter);
    }
    
    public String getSentenceIdentificator(){
        return this.sentence.getIdentificator();
    }
    
    public String getLetterIdentificator(){
        return this.sentence.getLetterIdentificator();
    }
    
    public void save(){
        transcriptionManager.save(sentence, template);
    }
}
