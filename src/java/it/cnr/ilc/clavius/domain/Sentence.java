/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package it.cnr.ilc.clavius.domain;

/**
 *
 * @author Angelo Del Grosso
 */
public class Sentence {
    private String identificator;
    private String content;
    
    public static final String DEF_CONTENT = "transcribe the sentence above....";

    public Sentence() {
    this.identificator = "1";
    }
    
    
    
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
    
    public String getIdentificator() {
        return identificator;
    }

    public void setIdentificator(String identificator) {
        this.identificator = identificator;
    }
    
}
