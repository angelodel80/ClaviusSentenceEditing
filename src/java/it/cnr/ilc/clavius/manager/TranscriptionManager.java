/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package it.cnr.ilc.clavius.manager;

import it.cnr.ilc.clavius.domain.Sentence;
import it.cnr.ilc.clavius.utils.DocumentHandler;
import java.io.IOException;
import java.io.StringReader;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jdom2.Document;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;

/**
 *
 * @author Angelo Del Grosso
 */
public class TranscriptionManager {
    Document docTemplate;

    public Document getDocTemplate() {
        return docTemplate;
    }

    public void setDocTemplate(Document docTemplate) {
        this.docTemplate = docTemplate;
    }
    public String getContentDefault(){
        return Sentence.DEF_CONTENT;
    }

    public void save(Sentence sentence, String template) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        System.err.println(sentence.getContent());
        SAXBuilder builder = new SAXBuilder();
        it.cnr.ilc.clavius.utils.Sentence sent = new it.cnr.ilc.clavius.utils.Sentence(sentence.getContent(), sentence.getIdentificator());
        try {
            docTemplate = builder.build(new StringReader(template));
        } catch (JDOMException ex) {
            Logger.getLogger(TranscriptionManager.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(TranscriptionManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        DocumentHandler.insertSentence(sent, docTemplate);
    }
}
