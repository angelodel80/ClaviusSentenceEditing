/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package it.cnr.ilc.clavius.manager;

import it.cnr.ilc.clavius.domain.Sentence;
import it.cnr.ilc.clavius.utils.DocumentHandler;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import org.jdom2.Document;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;

/**
 *
 * @author Angelo Del Grosso
 */
public class TranscriptionManager {
    private Document docTemplate;
    private SAXBuilder builder;
    private Map<String,Document> MapTemplates;

   
    @PostConstruct
    public void init(){
        builder = new SAXBuilder();
        MapTemplates = initTemplates();
    }
    
     public Map<String, Document> getMapTemplates() {
        return MapTemplates;
    }

    public void setMapTemplates(Map<String, Document> MapTemplates) {
        this.MapTemplates = MapTemplates;
    }

    public Document getDocTemplate(String templateIdentificator) {
        setDocTemplate(MapTemplates.get(templateIdentificator));
        return docTemplate;
    }

    public void setDocTemplate(Document docTemplate) {
        this.docTemplate = docTemplate;
    }
    public String getContentDefault(){
        return Sentence.DEF_CONTENT;
    }

    public void save(Sentence sentence) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        //System.err.println(sentence.getContent());
        //SAXBuilder builder = new SAXBuilder();
        it.cnr.ilc.clavius.utils.Sentence sent = new it.cnr.ilc.clavius.utils.Sentence(sentence.getContent(), sentence.getIdentificator());
        DocumentHandler.insertSentence(sent, docTemplate);
    }

    private Map<String, Document> initTemplates() {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        Map<String,Document> docTemplates = new HashMap<String, Document>();
        InputStream firstTemplateStream = this.getClass().getResourceAsStream("/it/cnr/ilc/clavius/manager/resources/136-transcription_template.xml");
        InputStream secondTemplateStream = this.getClass().getResourceAsStream("/it/cnr/ilc/clavius/manager/resources/139-transcription_template.xml");
        InputStream threeTemplateStream = this.getClass().getResourceAsStream("/it/cnr/ilc/clavius/manager/resources/147-transcription_template.xml");
        InputStream fourTemplateStream = this.getClass().getResourceAsStream("/it/cnr/ilc/clavius/manager/resources/149-transcription_template.xml");
        InputStream fiveTemplateStream = this.getClass().getResourceAsStream("/it/cnr/ilc/clavius/manager/resources/153-transcription_template.xml");
        
        try {
                docTemplates.put("136", builder.build(firstTemplateStream));
                docTemplates.put("139", builder.build(secondTemplateStream));
                docTemplates.put("147", builder.build(threeTemplateStream));
                docTemplates.put("149", builder.build(fourTemplateStream));
                docTemplates.put("153", builder.build(fiveTemplateStream));
                
            } catch (JDOMException ex) {
                Logger.getLogger(TranscriptionManager.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(TranscriptionManager.class.getName()).log(Level.SEVERE, null, ex);
            }
        
        return docTemplates;
        
    }
}
