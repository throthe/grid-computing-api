package client;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author thomas_r
 */
import java.net.MalformedURLException;
import java.net.URL;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.security.Policy;
import java.util.logging.Level;
import java.util.logging.Logger;
import ui.ClientGui;
import interfaces.IRemoteConnection;

public class GridClient {

    protected static final Logger LOG = Logger.getLogger(GridClient.class.getName());

    private static String clientId = null;

    public static String getClientId() {
        return clientId;
    }

    public static void main(String[] args) {
        LOG.info("GridClient is running...");

        URL policyUrl = Thread.currentThread().getContextClassLoader().getResource("java.policy");
        LOG.log(Level.INFO, "Policy URL <{0}>", policyUrl);

        Policy.getPolicy().refresh();

        IRemoteConnection rc;

        try {
            rc = (IRemoteConnection) Naming.lookup("rmi://localhost" + "/GridServer");

            if (rc == null) {
                System.out.println("rc is null!");
                return;
            }

            clientId = rc.getClientId();
            System.out.println(clientId);

            ClientGui ui = new ClientGui(rc);
            ui.setVisible(true);

        } catch (MalformedURLException | NotBoundException | RemoteException e) {
            System.out.println(e.toString());
        } finally {
            rc = null;
        }
    }
}
