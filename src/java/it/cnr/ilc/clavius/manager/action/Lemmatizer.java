/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.cnr.ilc.clavius.manager.action;

import ilc.cnr.it.clavius.ClaviusMain;
import ilc.cnr.it.clavius.HunposTagger;
import ilc.cnr.it.clavius.constants.HandleConstants;
import ilc.cnr.it.clavius.lemmata.ParseToken;
import ilc.cnr.it.clavius.utils.ClaviusUtils;
import ilc.cnr.it.clavius.utils.SentencesHandler;
import ilc.cnr.it.clavius.utils.TextUtils;
import java.io.IOException;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.jdom2.Document;
import org.jdom2.JDOMException;

/**
 *
 * @author Angelo Del Grosso
 */
final public class Lemmatizer {

    StringBuilder outBuilder = null; //FIXME!

    public StringBuilder getOutBuilder() {
        return outBuilder;
    }

    public void setOutBuilder(StringBuilder outBuilder) {
        this.outBuilder = outBuilder;
    }

    public Lemmatizer() {
        outBuilder = new StringBuilder(); //FIXME!!!
    }

    public void runLemmatization(Map<String, String> sentences, String letter) {
        if (null != sentences) {
            ClaviusMain lemmatizationRun = new ClaviusMain();
            Object[] sents = sentences.values().toArray();
            Object[] sKeys = sentences.keySet().toArray();
            for (int i = 0; i < sents.length; i++) {
                lemmatizationRun.setMsg((String) sents[i]);
                lemmatizationRun.setSentName(String.format("%s:: %s", (String) sKeys[i], lemmatizationRun.getMsg()));
                System.out.println(lemmatizationRun.getSentName());
                HunposTagger hunPos = new HunposTagger(HandleConstants.getModelforHunPos(), HandleConstants.getPathToHunPos());
                String msgTagged = hunPos.tag(lemmatizationRun.getMsg());
                String tok = "";
                String pos = "";
                String cts = lemmatizationRun.getSentName().split(":: ")[0];
                String sentence = lemmatizationRun.getSentName().split(":: ")[1];
                Pattern p = null;
                Matcher m = null;
                StringBuffer tmpBuffer = new StringBuffer();

                //System.out.println(msgTagged);
                //hunPos.printPath();
                //System.err.println("in process:" + msgTagged);
                String[] lines = msgTagged.split("\n");
                //lemmatizationRun.outBuilder.append(getSentName() + "\n");

                outBuilder.append(lemmatizationRun.getSentName() + "\n");

                /* for due to manage the cts sub references*/
                /* tmpBuffer needs for handling words repetitions */
                for (String line : lines) {
                    if (!"".equals(line)) {
                        tok = line.split("\t")[0];
                        pos = line.split("\t")[1];
                        tmpBuffer.append(tok + " ");
                        p = Pattern.compile("\\b" + tok.replaceAll("([\\?\\.\\[\\]\\\\\\)\\(])", "\\\\$1") + "\\b");
                        m = p.matcher(tmpBuffer);
                        int c = 0;
                        while (m.find()) {
                            c++;
                        }
                        if (c == 0) {
                            p = Pattern.compile(tok.replaceAll("([\\?\\.\\[\\]\\\\\\)\\(])", "\\\\$1"));
                            m = p.matcher(tmpBuffer);
                        }
                        while (m.find()) {
                            c++;
                        }
                        tok = cts.concat(String.format("@%s[%s]", tok, String.valueOf(c)));
                        outBuilder.append(String.format("%s\t%s\n", tok, pos));
                    }
                }

                outBuilder.append("\n");
            }
            
            // ATENZIONE SISTEMARE LA SCRITTURA/LETTURA DEI FILE!
            HandleConstants.setLetterRif(letter);
            HandleConstants.setWorkDir("C:/tmp/Clavius/integrazioneWebApp/"+letter+"/");
            HandleConstants.setTeiFile(letter+"-transcription.xml");
            HandleConstants.setTaggedFile(HandleConstants.getWorkDir()+letter+"-tagged.txt");
            HandleConstants.setTabFileAnalyzed(HandleConstants.getWorkDir()+letter+"-tokLemma.txt");
            HandleConstants.setLetterAnalyzed("/Letter"+letter+"sent_analized");
            HandleConstants.setFullTextFile(HandleConstants.getWorkDir()+letter+"-fullText.txt");
           // HandleConstants.setLetterAnalyzed(letter);
            
            HelperIO.writeOut(this.getOutBuilder(), HandleConstants.getTaggedFile()); //FIXME!!
            ParseToken.main(new String[]{HandleConstants.getTaggedFile(), HandleConstants.getTabFileAnalyzed()});
            try {
                Document xmlSentences = TextUtils.TabToXml(HandleConstants.getTabFileAnalyzed(), true);
                ClaviusUtils.makeSentenceXML(xmlSentences);
                // TODO build XML for integration purposes (Tokens and Linguistical_Analysis)
                ClaviusUtils.makeIntegrationXMLforAnalysis(xmlSentences);
                SentencesHandler.main(new String[]{HandleConstants.getWorkDir(), HandleConstants.getLetterRif()});
            } catch (JDOMException e) {
                e.getMessage();
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            
            lemmatizationRun = null;
        }
    }
}
