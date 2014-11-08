/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.cnr.ilc.clavius.controller;

import ilc.cnr.it.clavius.constants.HandleConstants;
import it.cnr.ilc.clavius.domain.HierarchicalTeiDocument;
import it.cnr.ilc.clavius.domain.PosTaggedToken;
import it.cnr.ilc.clavius.domain.Sentence;
import it.cnr.ilc.clavius.manager.ProofReaderManager;
import it.cnr.ilc.clavius.manager.action.HelperIO;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;
import javax.faces.event.ValueChangeEvent;
import javax.inject.Inject;
import javax.inject.Named;
import org.apache.commons.lang3.StringUtils;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;
import org.primefaces.event.CellEditEvent;
import org.primefaces.model.TreeNode;

/**
 *
 * @author Angelo Del Grosso
 */
@SessionScoped
@Named
public class ProofReaderController extends BaseController implements Serializable {

    private String content = "PLUTO";
    private HierarchicalTeiDocument documentTei;

    @Inject
    private transient Sentence sentence;

    @Inject
    private transient ProofReaderManager proofReaderManager;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public HierarchicalTeiDocument getDocumentTei() {
        return documentTei;
    }

    public void setDocumentTei(HierarchicalTeiDocument documentTei) {
        this.documentTei = documentTei;
    }

    private boolean buttonDisabled = false;
    private transient String translatedString;
    private String currSentence;
    private String currLetter;
    private Integer currSentenceSize;
    private Integer currSentenceNumber;
    private Integer currLetterSize;
    private String countsentence;
    private List<PosTaggedToken> res = new ArrayList<PosTaggedToken>();
    private TreeNode root;
    private String[] posTagsList = {"n", "v", "t", "a", "d", "c", "r", "p", "m", "i", "e", "u", "-"};
    private String[] personList = {"1", "2", "3", "-"};
    private String[] numberList = {"s", "p", "-"};
    private String[] tenseList = {"p", "i", "r", "l", "t", "f", "-"};
    private String[] moodList = {"i", "s", "n", "m", "p", "d", "g", "u", "-"};
    private String[] voiceList = {"a", "p", "-"};
    private String[] genderList = {"m", "f", "n", "-"};
    private String[] caseList = {"n", "g", "d", "a", "b", "v", "l", "-"};
    private String[] degreeList = {"c", "s", "-"};
    private boolean dis = false;

    public void setResults() {
        res.clear();
        setCountsentence(" ");
        setCurrSentence(" ");
    }

    public void letterChanging(ValueChangeEvent e) {
        setTranslatedString(e.getNewValue().toString());
    }

    public void letterChanged(AjaxBehaviorEvent event) {
        setCurrSentence(getTranslatedString());
    }

    public void prevSentence() {
        setValue2(false);
        if (getCurrSentenceNumber() != 0) {
            setCurrSentenceNumber(getCurrSentenceNumber() - 1);
            loadSentence(getCurrSentenceNumber());
        } else {
            setCurrSentenceNumber(getCurrLetterSize() - 1);
            loadSentence(getCurrSentenceNumber());
        }
        int partial = getCurrSentenceNumber() + 1;
        setCountsentence(partial + "/" + getCurrLetterSize());
    }

    public void nextSentence() {
        setValue2(false);
        int letterSize = getCurrLetterSize();
        if (getCurrSentenceNumber() < (letterSize - 1)) {
            setCurrSentenceNumber(getCurrSentenceNumber() + 1);
            loadSentence(getCurrSentenceNumber());
        } else {
            setCurrSentenceNumber(0);
            loadSentence(getCurrSentenceNumber());
        }
        int partial = getCurrSentenceNumber() + 1;
        setCountsentence(partial + "/" + getCurrLetterSize());
    }

    public String getTranslatedString() {
        return translatedString;
    }

    public void setTranslatedString(String s) {
        translatedString = s;
    }

    public TreeNode getRoot() {
        return root;
    }

    public List<PosTaggedToken> getResults() {
        return res;
    }

//	public void posTagger(Long algorithm) {
//		res.clear();
//		root = new DefaultTreeNode("root", null);
//		try {
//			posProcess();
//		} catch (IOException e) {
//			e.printStackTrace();
//		} catch (TreeTaggerException e) {
//			e.printStackTrace();
//		}
//	}
//	private void posProcess() throws IOException, TreeTaggerException {
//		if (translatedString != null) {
//
//			// System.setProperty("treetagger.home",
//			// "/home/andrea/TreeTagger/");
//
//			String treeTaggerHome = System.getProperty("treetagger.home");
//			if (!treeTaggerHome.endsWith("/")) {
//				treeTaggerHome += "/";
//			}
//
//			final TreeTaggerWrapper<String> tt = new TreeTaggerWrapper<String>();
//			try {
//				tt.setProbabilityThreshold(0.01);
//				// tt.setModel("/home/andrea/TreeTagger/lib/italian-utf8.par");
//				tt.setModel(treeTaggerHome + "lib/italian-utf8.par");
//				tt.setHandler(new ProbabilityHandler<String>() {
//
//					private String token;
//
//					public void token(String aToken, String aPos, String aLemma) {
//						token = aToken;
//						if (tt.getProbabilityThreshold() == null) {
//							System.out.println(aToken + " " + aPos + " " + aLemma);
//						}
//					}
//
//					public void probability(String pos, String lemma, double probability) {
//						System.out.println(token + " " + pos + " " + lemma + " " + probability);
//						if (res.size() > 0) {
//							if (res.get(res.size() - 1).getToken().equals(token)) {
//								res.get(res.size() - 1).getPosTag().add(pos);
//							} else {
//								PosTaggedToken tk = new PosTaggedToken();
//								tk.setLemma(lemma);
//								tk.setPosTag(pos);
//								tk.setToken(token);
//								tk.setProb(probability);
//								res.add(tk);
//							}
//						} else {
//							PosTaggedToken tk = new PosTaggedToken();
//							tk.setLemma(lemma);
//							tk.setPosTag(pos);
//							tk.setToken(token);
//							tk.setProb(probability);
//							res.add(tk);
//						}
//					}
//
//				});
//				// tt.process(asList(new String[] { "Oggi", "lui", "ha",
//				// "mangiato",
//				// "bene", "." }));
//				tt.process(asList(translatedString.split(" ")));
//				// System.out.println("************** " +
//				// translatedString.split(" "));
//			} finally {
//				tt.destroy();
//			}
//		}
//	}
    public void onCellEdit(CellEditEvent event) {
		// Object oldValue = event.getOldValue();
        // Object newValue = event.getNewValue();
        //
        // if(newValue != null && !newValue.equals(oldValue)) {
        // FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO,
        // "Cell Changed", "Old: " + oldValue + ", New:" + newValue);
        // FacesContext.getCurrentInstance().addMessage(null, msg);
        // }

        System.err.println("cellEdit..");

    }
    
    //public void onPageChange(Event )

    public boolean getButtonDisabled() {
        return buttonDisabled;
    }

    public String getCurrSentence() {
        return currSentence;
    }

    public void setCurrSentence(String currSentence) {
        this.currSentence = currSentence;
    }

    public String getCurrLetter() {
        return currLetter;
    }

    public void setCurrLetter(String currLetter) {
        this.currLetter = currLetter;
    }

    private void loadSentence(int sentNum) {
        setCurrSentenceNumber(sentNum);
        String token, lemma, morpho, sentence = "";
        res.clear();
      //  InputStream currLetterStream = null;
        //  SAXBuilder builder = new SAXBuilder();

        //getClass().getClassLoader().getResourceAsStream("147.xml");
        try {
            //     currLetterStream = new FileInputStream(HandleConstants.getWorkDir() + "Letter" + HandleConstants.getLetterRif() + "_anOUT.xml");
            Document document = getDocumentTei().getAnalysis();//(Document) builder.build(currLetterStream);
            Element rootNode = document.getRootElement();
            List sentenceList = rootNode.getChildren("sentence");
            Element sentenceNode = (Element) sentenceList.get(sentNum);
            List tokenList = sentenceNode.getChildren("token");
            for (int i = 0; i < tokenList.size(); i++) {
                Element tokenNode = (Element) tokenList.get(i);
                token = tokenNode.getAttributeValue("form");
                lemma = tokenNode.getAttributeValue("lemma");
                morpho = tokenNode.getAttributeValue("morphoCode");
                setMorphoElement(token, lemma, morpho);
                sentence = sentence + " " + token;
                System.out.println("SENTENZA: " + sentence);
            }
            setCurrSentence(sentence);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void loadLetter() {
        String token, lemma, morpho, sentence = "";
        res.clear();
        InputStream currLetterStream = null;
        SAXBuilder builder = new SAXBuilder();
        setCurrSentence("");
        setDis(true);

        try {
            currLetterStream = new FileInputStream(HandleConstants.getWorkDir() + "Letter" + HandleConstants.getLetterRif() + "_anOUT.xml");
            Document document = (Document) builder.build(currLetterStream);
            getDocumentTei().setAnalysis(document);
            Element rootNode = document.getRootElement();
            List sentenceList = rootNode.getChildren("sentence");
            setCurrLetterSize(sentenceList.size());
            Element sentenceNode = (Element) sentenceList.get(0);
            setCurrSentenceNumber(0);
            List tokenList = sentenceNode.getChildren("token");
            for (int i = 0; i < tokenList.size(); i++) {
                Element tokenNode = (Element) tokenList.get(i);
                token = tokenNode.getAttributeValue("form");
                lemma = tokenNode.getAttributeValue("lemma");
                morpho = tokenNode.getAttributeValue("morphoCode");
                sentence = sentence + " " + token;
                setMorphoElement(token, lemma, morpho);
            }
            setCurrSentence(sentence);
            setCountsentence("1/" + getCurrLetterSize());
        } catch (IOException io) {
            System.out.println(io.getMessage());
        } catch (JDOMException jdomex) {
            System.out.println(jdomex.getMessage());
        }
    }

    private void setMorphoElement(String token, String lemma, String morpho) {
        String pos = String.valueOf(morpho.charAt(0));
        String person = String.valueOf(morpho.charAt(1));
        String number = String.valueOf(morpho.charAt(2));
        String tense = String.valueOf(morpho.charAt(3));
        String mood = String.valueOf(morpho.charAt(4));
        String voice = String.valueOf(morpho.charAt(5));
        String gender = String.valueOf(morpho.charAt(6));
        String cases = String.valueOf(morpho.charAt(7));
        String degree = String.valueOf(morpho.charAt(8));
        PosTaggedToken tk = new PosTaggedToken();
        tk.setToken(token);
        tk.setLemma(lemma);
        tk.setPosTag(pos);
        for (int i = 0; i < posTagsList.length; i++) {
            if (!posTagsList[i].equals(pos)) { //??
                tk.setPosTag(posTagsList[i]);
            }
        }
        tk.setCases(cases);
        for (int i = 0; i < caseList.length; i++) {
            if (!caseList[i].equals(cases)) {
                tk.setCases(caseList[i]);
            }
        }
        tk.setDegree(degree);
        for (int i = 0; i < degreeList.length; i++) {
            if (!degreeList[i].equals(degree)) {
                tk.setDegree(degreeList[i]);
            }
        }
        tk.setGender(gender);
        for (int i = 0; i < genderList.length; i++) {
            if (!genderList[i].equals(gender)) {
                tk.setGender(genderList[i]);
            }
        }
        tk.setMood(mood);
        for (int i = 0; i < moodList.length; i++) {
            if (!moodList[i].equals(mood)) {
                tk.setMood(moodList[i]);
            }
        }
        tk.setNumber(number);
        for (int i = 0; i < numberList.length; i++) {
            if (!numberList[i].equals(number)) {
                tk.setNumber(numberList[i]);
            }
        }
        tk.setPerson(person);
        for (int i = 0; i < personList.length; i++) {
            if (!personList[i].equals(person)) {
                tk.setPerson(personList[i]);
            }
        }
        tk.setTense(tense);
        for (int i = 0; i < tenseList.length; i++) {
            if (!tenseList[i].equals(tense)) {
                tk.setTense(tenseList[i]);
            }
        }
        tk.setVoice(voice);
        for (int i = 0; i < voiceList.length; i++) {
            if (!voiceList[i].equals(voice)) {
                tk.setVoice(voiceList[i]);
            }
        }
        res.add(tk);
    }

    public void saveProof() {
        System.err.println("salva proofreader.." + getCurrSentenceNumber());

        Document analysis = this.getDocumentTei().getAnalysis();

        Element rootNode = analysis.getRootElement();
        List<Element> sentenceList = rootNode.getChildren("sentence");
        Element sentenceNode = sentenceList.get(getCurrSentenceNumber().intValue());
        List<Element> tokenList = sentenceNode.getChildren("token");
        
        for (int i = 0; i < tokenList.size(); i++) {
                Element tokenNode = (Element) tokenList.get(i);
                PosTaggedToken tk = res.get(i);
                tokenNode.setAttribute("lemma", tk.getLemma());
                tokenNode.setAttribute("morphoCode", 
                        tk.getPosTag().get(0)
                        .concat(tk.getPerson().get(0))
                        .concat(tk.getNumber().get(0))
                        .concat(tk.getTense().get(0))
                        .concat(tk.getMood().get(0))
                        .concat(tk.getVoice().get(0))
                        .concat(tk.getGender().get(0))
                        .concat(tk.getCases().get(0))
                        .concat(tk.getDegree().get(0))
                    
                );
            }

        XMLOutputter outputter = new XMLOutputter(Format.getPrettyFormat());
        String newAnalysis = outputter.outputString(analysis);
        StringBuilder sb = new StringBuilder(newAnalysis);

        HelperIO.writeOut(sb, HandleConstants.getWorkDir().concat(HandleConstants.getLetterRif()).concat("-linguistic-dev.xml"));

    }

    public Integer getCurrSentenceSize() {
        return currSentenceSize;
    }

    public void setCurrSentenceSize(Integer currSentenceSize) {
        this.currSentenceSize = currSentenceSize;
    }

    public Integer getCurrLetterSize() {
        return currLetterSize;
    }

    public void setCurrLetterSize(Integer currLetterSize) {
        this.currLetterSize = currLetterSize;
    }

    public Integer getCurrSentenceNumber() {
        return currSentenceNumber;
    }

    public void setCurrSentenceNumber(Integer currSentenceNumber) {
        this.currSentenceNumber = currSentenceNumber;
    }

    public String getCountsentence() {
        return countsentence;
    }

    public void setCountsentence(String countsentence) {
        this.countsentence = countsentence;
    }

    private boolean value1;
    private boolean value2;

    public boolean isValue1() {
        return value1;
    }

    public void setValue1(boolean value1) {
        this.value1 = value1;
    }

    public boolean isValue2() {
        return value2;
    }

    public void setValue2(boolean value2) {
        this.value2 = value2;
    }

    public void addMessage() {
        String summary = value2 ? "Checked" : "Unchecked";
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(summary));
    }

    public boolean isDis() {
        return dis;
    }

    public void setDis(boolean dis) {
        this.dis = dis;
    }
    
    public List<String> getSentences(){
         List<String> ret = new ArrayList<String>();
        for (String sent : getDocumentTei().getSentences().values()) {
            ret.add(StringUtils.abbreviateMiddle(sent, "...", 40));
        }
        
        return ret;
    }

}
