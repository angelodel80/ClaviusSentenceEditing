/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package it.cnr.ilc.clavius.manager;

import eu.cophi.model.api.Document;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author Angelo Del Grosso
 */
public class CophiManager {
    
    public List<String> getTokens(){
    String[] tokens = null;
    Document mydoc = Document.getDefaultDoc();
    tokens = mydoc.getTokens();
    return Arrays.asList(tokens);
    }
}
