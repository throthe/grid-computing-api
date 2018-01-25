/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package xml;

/**
 *
 * @author thomas_r
 */
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class XStreamUtils {

    public static void saveAsXml(Object obj, File dest) throws IOException {
        XStreamInstance.getInstance().getXStream().toXML(obj, new BufferedOutputStream(new FileOutputStream(dest)));
    }

    public static Object loadFromXml(File src) throws IOException {
        return XStreamInstance.getInstance().getXStream().fromXML(new BufferedInputStream(new FileInputStream(src)));
    }
}
