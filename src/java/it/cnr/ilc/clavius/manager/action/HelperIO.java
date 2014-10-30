/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.cnr.ilc.clavius.manager.action;

import ilc.cnr.it.clavius.utils.TextUtils;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

/**
 *
 * @author Angelo Del Grosso
 */
public class HelperIO {

    public static void writeOut(StringBuilder sb, String pathFile) {
        if (null != sb && null != pathFile) {
            System.err.println("in writeOut");
            BufferedWriter writer = null;
            try {
                File outFile = new File(pathFile);
                writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(outFile), "UTF-8"));
                writer.write(sb.toString());

            } catch (FileNotFoundException fe) {

                System.err.println(fe.getStackTrace());

            } catch (IOException ioe) {

                System.err.println(ioe.getStackTrace());

            } finally {

                try {
                    writer.flush();
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }
    }

    public static void fromDomToFile(Document doc, String filePath) {
        try {
            File parent = new File(filePath).getParentFile();
            parent.mkdirs();
            XMLOutputter xo = new XMLOutputter(Format.getPrettyFormat());
            xo.output(doc, new BufferedWriter(new OutputStreamWriter(new FileOutputStream(filePath), "UTF-8")));
        } catch (IOException e) {
            e.printStackTrace();
        }
        
    }

}
