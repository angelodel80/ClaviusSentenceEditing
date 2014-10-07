package it.cnr.ilc.clavius.utils;

import java.io.IOException;
import java.io.StringReader;
import java.net.URISyntaxException;

import org.jdom2.Document;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

public class TestClaviusDocumentHandler {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String HttpRemoteUrl = "http://localhost:8080/exist/rest//db/claviusTemplate/testTEIperClavius.xml"; // as main parameter 
		
		String template = null;
		Document docTemplate = null;
		
		
		XMLOutputter xop = new XMLOutputter(Format.getPrettyFormat());
		
		Sentence inSentence = new Sentence();
		inSentence.setId(String.valueOf(2));
		inSentence.setText("testo inserito dall'esterno");
		
		SAXBuilder builder = new SAXBuilder();
		
		try {
			template = "<TEI></TEI>"; //ExistManager.FromRemoteFileToString(HttpRemoteUrl);
			docTemplate = builder.build(new StringReader(template));
		} catch (JDOMException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
                }
//		} catch (URISyntaxException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		System.out.println("PRIMA DELL'INSERIMENTO");
		System.out.println(xop.outputString(docTemplate));
		System.out.println("*****************");
		
		docTemplate = DocumentHandler.insertSentence(inSentence, docTemplate);
		System.out.println("*****************");

		System.out.println("DOPO L'INSERIMENTO");
		System.out.println(xop.outputString(docTemplate));
		System.out.println("*****************");
		
		//ExistConnection claviusConnection = new ExistConnection("");
		//claviusConnection.connect();
		//ExistManager.save(claviusConnection, docTemplate, "angelo");
		

	}

}
