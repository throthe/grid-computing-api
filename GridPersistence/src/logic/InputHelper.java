/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logic;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Parser for arbritrary input from command line. 
 * If any input cant be parsed to the expected value NumberFormatException will 
 * be thrown.
 * 
 * @author Thomas Rothe
 */
public class InputHelper {
    
    public static String getString(String promt) {
        BufferedReader stdin = new BufferedReader(new InputStreamReader(System.in));
        
        System.out.println(promt);
        System.out.flush();    
        
        try {
            return stdin.readLine();
        } catch (IOException e) {
            return "Error: " + e.getMessage();
        }         
    }
    
     /**
     * Converts string to double. 
     * @param   prompt   expected double as string
     * @return     A valid double value.
     * @exception  NumberFormatException  If expected value is not double
     */
    public static double getDoubleInput(String prompt) throws NumberFormatException {
        String in = getString(prompt);
        return Double.parseDouble(in);
    }
    
     /**
     * Converts string to float. 
     * @param   prompt   expected float as string
     * @return     A valid float value.
     * @exception  NumberFormatException  If expected value is not float
     */
    public static float getFloatInput(String prompt) throws NumberFormatException {
        String in = getString(prompt);
        return Float.parseFloat(in);
    }
    
     /**
     * Converts string to long. 
     * @param   prompt   expected long as string
     * @return     A valid long value.
     * @exception  NumberFormatException  If expected value is not long
     */
    public static long getLongInput(String prompt) throws NumberFormatException {
        String in = getString(prompt);
        return Long.parseLong(in);
    }
    
     /**
     * Converts string to integer. 
     * @param   prompt   expected integer as string
     * @return     A valid integer value.
     * @exception  NumberFormatException  If expected value is not integer
     */
    public static int getIntegerInput(String prompt) throws NumberFormatException {
        String in = getString(prompt);
        return Integer.parseInt(in);
    }
    
    /**
     * Converts string to short. 
     * @param   prompt   expected short as string
     * @return     A valid short value.
     * @exception  NumberFormatException  If expected value is not short
     */
    public static short getShortInput(String prompt) throws NumberFormatException {
        String in = getString(prompt);
        return Short.parseShort(in);
    }
    
     /**
     * Converts string to byte. 
     * @param   prompt   expected byte as string
     * @return     A valid byte value.
     * @exception  NumberFormatException  If expected value is not byte
     */
    public static byte getByteInput(String prompt) throws NumberFormatException {
        String in = getString(prompt);
        return Byte.parseByte(in);
    }
    
     /**
     * Converts string to boolean. 
     * @param   prompt   expected boolean as string
     * @return     A valid boolean value.
     * @exception  NumberFormatException  If expected value is not boolean
     */
    public static boolean getBooleanInput(String prompt) throws NumberFormatException {
        String in = getString(prompt);
        return Boolean.parseBoolean(in);
    }
    
     /**
     * Converts string to char. 
     * @param   prompt   expected char as string
     * @return     A valid char value.
     * @exception  NumberFormatException  If expected value is not char
     */
    public static char getCharInput(String prompt) throws NumberFormatException {
        String in = getString(prompt);
        if(in.length() < 1)
            throw new NumberFormatException("Input is empty");
        else if(in.length() > 1)
            throw new NumberFormatException("Input is string, not char.");
        else
            return in.charAt(0);
    }
}
