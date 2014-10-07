package it.cnr.ilc.clavius.utils;

//import org.jdom2.Document;
//import org.jdom2.output.Format;
//import org.jdom2.output.XMLOutputter;
//import org.xmldb.api.DatabaseManager;
//import org.xmldb.api.base.Collection;
//import org.xmldb.api.base.Database;
//import org.xmldb.api.base.XMLDBException;
//import org.xmldb.api.modules.XMLResource;

public class ExistHandler {

//	private String context;
//
//	protected final String driver = "org.exist.xmldb.DatabaseImpl";
//	protected final String collection = "xmldb:exist://127.0.0.1:8080/exist/xmlrpc/db/clavius/documents/";
//	private Collection root = null;
//	
//	public ExistHandler() {
//		// TODO Auto-generated constructor stub
//		this.context = context;
//
//	}
//	
//	/**
//	 * 
//	 */
//	public boolean connectAndSave(Document xml) {
//		try {
//
//			Class<?> c = Class.forName(driver);
//			Database db = (Database) c.newInstance();
//			DatabaseManager.registerDatabase(db);
//			root = DatabaseManager.getCollection(collection.concat(context),
//					"admin", "claviusproject");
//			// System.err.println(root.getName());
//
//			// String[] resources = root.listResources();
//			// for(String res: resources){
//			// System.out.println(res);
//			// }
//
//			XMLResource xmlFile = (XMLResource) root.createResource(context
//					+ "-lexico-semantic-remoto.xml", "XMLResource");
//			XMLOutputter xmlOutput = new XMLOutputter();
//			xmlOutput.setFormat(Format.getPrettyFormat());
//			xmlFile.setContent(new XMLOutputter().outputString(xml));
//			root.storeResource(xmlFile);
//			root.close();
//
//			// System.out.println(letterDir.getName());
//
//			return true;
//
//		} catch (ClassNotFoundException ex) {
//			ex.printStackTrace();
//		} catch (InstantiationException ex) {
//			ex.printStackTrace();
//		} catch (IllegalAccessException ex) {
//			ex.printStackTrace();
//		} catch (XMLDBException ex) {
//			ex.printStackTrace();
//		} catch (Exception ex) {
//			ex.printStackTrace(System.err);
//		}
//
//		return false;
//	}


}
