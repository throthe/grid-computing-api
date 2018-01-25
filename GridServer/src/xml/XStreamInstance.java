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
import com.thoughtworks.xstream.XStream;

class XStreamInstance {

    private static final XStreamInstance INSTANCE = new XStreamInstance();
    private final XStream xstream;

    private XStreamInstance() {
        this.xstream = new XStream();
    }

    public static XStreamInstance getInstance() {
        return XStreamInstance.INSTANCE;
    }

    public XStream getXStream() {
        return xstream;
    }
}
