package it.cnr.ilc.clavius.utils;

import ilc.cnr.it.clavius.constants.HandleConstants;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Hashtable;
import java.util.Vector;

import org.apache.xmlrpc.XmlRpc;
import org.apache.xmlrpc.XmlRpcClient;
import org.apache.xmlrpc.XmlRpcException;
import org.jdom2.Document;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;
import org.xmldb.api.modules.XMLResource;
import org.xmldb.api.base.Collection;
import org.xmldb.api.base.XMLDBException;

public class ExistManager {

    public static BufferedReader getReader(URL inUrl) throws IOException,
            UnsupportedEncodingException {
        return new BufferedReader(new InputStreamReader(inUrl.openStream(),
                "utf-8"));
    }

    public static String FromRemoteFileToString(String HttpRemoteUrl)
            throws IllegalArgumentException, UnsupportedEncodingException,
            FileNotFoundException, IOException, URISyntaxException {
        StringBuilder stringBuilder = null;
        BufferedReader reader = null;
        String line = null;

        reader = getReader(new URL(HttpRemoteUrl));

        stringBuilder = new StringBuilder();
        while (null != (line = reader.readLine())) {
            stringBuilder.append(line);
        }

        reader.close();

        return stringBuilder.toString();
    }
//
//	public static String FromRemoteXMLRPCtoString(String uri, String path)
//			throws MalformedURLException, XmlRpcException, IOException {
//		XmlRpc.setEncoding("UTF-8");
//		XmlRpcClient xmlrpc = new XmlRpcClient(uri);
//		Hashtable<String, String> options = new Hashtable<String, String>();
//		options.put("indent", "yes");
//		options.put("encoding", "UTF-8");
//		options.put("expand-xincludes", "yes");
//		options.put("highlight-matches", "elements");
//
//		Vector<Object> params = new Vector<Object>();
//		params.addElement(path);
//		params.addElement(options);
//		String xml = (String) xmlrpc.execute("getDocumentAsString", params);
//		return xml;
//	}
//	

    public static boolean save(ExistConnection connection, Document xml, String context) {
        boolean ret = false;
        Collection accessPoint = connection.getConnectionAccessPoint();
        XMLResource xmlFile = null;
        try {
            xmlFile = (XMLResource) accessPoint.createResource(context + "-Modify.xml", "XMLResource");
        } catch (XMLDBException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        XMLOutputter xmlOutput = new XMLOutputter();
        xmlOutput.setFormat(Format.getPrettyFormat());
        try {
            xmlFile.setContent(new XMLOutputter().outputString(xml));
        } catch (XMLDBException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        try {
            accessPoint.storeResource(xmlFile);
        } catch (XMLDBException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        try {
            accessPoint.close();
        } catch (XMLDBException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return ret;
    }

    public static boolean save(ExistConnection connection, File xml, String context) {
        System.err.println("in save con parametro path di file...");
        
        boolean ret = false;

        XMLResource xmlFile = null;
        FileInputStream currLetterStream = null;
        
        Document document = null;
        
        if (null != connection) { 
            Collection accessPoint = connection.getConnectionAccessPoint();
            try {
                xmlFile = (XMLResource) accessPoint.createResource(context + "-Modify.xml", "XMLResource");
            } catch (XMLDBException e) {
                e.printStackTrace();
            }
            XMLOutputter xmlOutput = new XMLOutputter();
            xmlOutput.setFormat(Format.getPrettyFormat());

            try {
                SAXBuilder builder = new SAXBuilder();
                currLetterStream = new FileInputStream(xml);
                 document = (Document) builder.build(currLetterStream);

            } catch (FileNotFoundException fnf){
                fnf.printStackTrace();
            } catch (JDOMException jde){
                jde.printStackTrace();
            } catch (IOException io){
                io.printStackTrace();
            }

            try {
                xmlFile.setContent(new XMLOutputter().outputString(document));
            } catch (XMLDBException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            try {
                accessPoint.storeResource(xmlFile);
            } catch (XMLDBException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            try {
                accessPoint.close();
            } catch (XMLDBException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        return ret;
    }

}
