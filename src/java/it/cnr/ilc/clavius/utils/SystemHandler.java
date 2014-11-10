/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.cnr.ilc.clavius.utils;

import ilc.cnr.it.clavius.constants.HandleConstants;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Scanner;

/**
 *
 * @author Angelo Del Grosso
 */
public class SystemHandler {

    public static String runProcess(String letter) {
        String ret = "";
        String command = "java";
        ProcessBuilder procBuild = new ProcessBuilder(command, "-jar", "C:/tmp/Clavius/integrazioneWebApp/storeExist.jar");
        final Process proc;

        try {

            proc = procBuild.start();
            OutputStream os = proc.getOutputStream();

//			os.write(inMsg.getBytes());
//			os.write("\n\n".getBytes());
//			os.flush();
            os.close();
            proc.waitFor();
            InputStream is = proc.getInputStream();
            Scanner s = new Scanner(is).useDelimiter("\\A");
            ret = ret + "\n" + (s.hasNext() ? s.next() : "");
            is.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (InterruptedException ie) {
            // TODO Auto-generated catch block
            ie.printStackTrace();
        } finally {

        }

        return ret;
    }
}
