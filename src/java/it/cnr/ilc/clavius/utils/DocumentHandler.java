package it.cnr.ilc.clavius.utils;

import org.jdom2.Content;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.filter.Filters;
import org.jdom2.xpath.XPathExpression;
import org.jdom2.xpath.XPathFactory;

public class DocumentHandler {

	public DocumentHandler() {
		// TODO Auto-generated constructor stub
	}
	
	public static Document insertSentence(Sentence sentence, Document templateDoc){
		Document ret = templateDoc;
		XPathFactory xpfac = XPathFactory.instance();
		XPathExpression<Element> xp = xpfac.compile("//s[@n='"+sentence.getId()+"']", Filters.element());
		Element sentenceElement = xp.evaluateFirst(templateDoc);
		sentenceElement.setText(sentence.getText());
		return ret;
	}

}
