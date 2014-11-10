package it.cnr.ilc.clavius.utils;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.jdom2.Document;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;
import org.xmldb.api.DatabaseManager;
import org.xmldb.api.base.Collection;
import org.xmldb.api.base.Database;
import org.xmldb.api.base.XMLDBException;
import org.xmldb.api.modules.XMLResource;

public class ExistConnection {

	private String context;

	protected final String driver = "org.exist.xmldb.DatabaseImpl";
	protected final String collection = "xmldb:exist://claviusontheweb.it:8080/exist/xmlrpc/db/clavius/documents/";
	private Collection connectionAccessPoint = null;

	public ExistConnection() {
		// TODO Auto-generated constructor stub
            this.context="";
	}

	public ExistConnection(String context) {
		this.context = context;
	}

	public boolean connect() {
            System.err.println(" in connection...");
		boolean ret = false;

		try {

			Class<?> c = Class.forName(driver);
			Database db = (Database) c.newInstance();
			DatabaseManager.registerDatabase(db);
                        System.err.println(collection.concat(context));
			
                        Collection coll = DatabaseManager.getCollection(collection.concat(context),
					"admin", "claviusproject");
                        
                         System.err.println("subito dopo la coll..");
                        
                        setConnectionAccessPoint(coll);
                       
                        System.err.println("dopo la set... ");
			ret = true;
			

			// String[] resources = root.listResources();
			// for(String res: resources){
			// System.out.println(res);
			// }

		} catch (XMLDBException eXML) {
			eXML.printStackTrace();
                         System.err.println(eXML.getMessage());
                         System.err.println("eccezione EXIST dalla connection");
		} catch (IncompatibleClassChangeError cce){
                    System.err.println(cce.getMessage());
			cce.printStackTrace();
                         System.err.println("eccezione EXIST Class Change dalla connection");
                }
                
                catch (ClassNotFoundException eRefl) {
			// TODO Auto-generated catch block
                     System.err.println(eRefl.getMessage());
			eRefl.printStackTrace();
		} catch (InstantiationException eInst) {
			// TODO Auto-generated catch block
                    System.err.println(eInst.getMessage());
			eInst.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
                    System.err.println(e.getMessage());
			e.printStackTrace();
		}
		return ret;
	}

	public Collection getConnectionAccessPoint() {
	try {                
                System.err.println("in get Connection point..: " + connectionAccessPoint.getName());
            } catch (XMLDBException ex) {
                System.err.println(ex.getMessage());
                ex.printStackTrace();
            }	
            return connectionAccessPoint;
            
	}

	public void setConnectionAccessPoint(Collection connectionAccessPoint) {
            try {
                System.err.println("in set Connection point..: " + connectionAccessPoint.getName());
            } catch (XMLDBException ex) {
                System.err.println(ex.getMessage());
                ex.printStackTrace();
            }
		this.connectionAccessPoint = connectionAccessPoint;
	}
        
        // liberare le risorse.. TODO
        
        public static void main(String[] args){
            ExistConnection connection = new ExistConnection("147");
            XMLResource xmlFile = null;
            System.err.println(connection.connect());
            try {
                System.err.println(connection.getConnectionAccessPoint().getName());
                xmlFile = (XMLResource) connection.getConnectionAccessPoint().createResource("test" + "-Modify.xml", "XMLResource");
                xmlFile.setContent("<ciao>ciao</ciao>");
                connection.getConnectionAccessPoint().storeResource(xmlFile);

            } catch (XMLDBException ex) {
                Logger.getLogger(ExistConnection.class.getName()).log(Level.SEVERE, null, ex);
            } finally{
                try{
                    connection.getConnectionAccessPoint().close();
                } catch (XMLDBException xe){
                     Logger.getLogger(ExistConnection.class.getName()).log(Level.SEVERE, null, xe);
                }
            }
        }
	
}
