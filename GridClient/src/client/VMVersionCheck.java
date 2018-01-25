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
import java.util.logging.Level;
import java.util.logging.Logger;
import util.JavaVersion;

public class VMVersionCheck {

    protected static final Logger LOG = Logger.getLogger(VMVersionCheck.class.getName());

    public static JavaVersion getJVMVersionSupport() {
        int majorVersion = -1;
        int minorVersion = -1;

        JavaVersion version = JavaVersion.VERSION_1_8;
        //Version v = Runtime.version();

        try {
            majorVersion = 1;
            minorVersion = 8;
        } catch (Exception e) {

            try {
                String[] java_version = System.getProperty("java.version").split(".");
                majorVersion = Integer.parseInt(java_version[0]);
                minorVersion = Integer.parseInt(java_version[1]);
            } catch (NumberFormatException e2) {
                version = JavaVersion.UNKNOWN;
            }
        }

        version = evaluateJVMVersionSupport(majorVersion, minorVersion);
        return version;
    }

    private static JavaVersion evaluateJVMVersionSupport(int majorVersion, int minorVersion) {
        if (majorVersion == 1 && minorVersion != -1) {

            switch (minorVersion) {
                case 5:
                    return JavaVersion.VERSION_1_5;
                case 6:
                    return JavaVersion.VERSION_1_6;
                case 7:
                    return JavaVersion.VERSION_1_7;
                case 8:
                    return JavaVersion.VERSION_1_8;
                case 9:
                    return JavaVersion.VERSION_1_9;
            }

            if (minorVersion > -1 && minorVersion < 5) {
                return JavaVersion.UNSUPPORTED;
            }
        }
        return JavaVersion.UNKNOWN;
    }

    public static String getJVMVersion() {
        return System.getProperty("java.version");
    }

    public static Boolean isCompatibleVersion(JavaVersion programVersion) {
        JavaVersion clientVersion = getJVMVersionSupport();

        LOG.log(Level.INFO, "Task Java Version <{0}> : Client Java Version <{1}>", new Object[]{programVersion, clientVersion});

        if (clientVersion == JavaVersion.UNKNOWN || programVersion == JavaVersion.UNKNOWN) {
            return null;
        } else if (clientVersion == programVersion
                || clientVersion.getCompatibilityRating() >= programVersion.getCompatibilityRating()) {
            return true;
        }
        return false;
    }

    public static boolean isEqualVersion(JavaVersion programVersion) {
        return programVersion.equals(getJVMVersionSupport());
    }
}
