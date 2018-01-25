/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package exception;

/**
 *
 * @author thomas_r
 */
public class IdentifierNotFoundException extends Exception {

    private static final long serialVersionUID = -694166561482659288L;

    public IdentifierNotFoundException() {
        super();
    }

    public IdentifierNotFoundException(String msg) {
        super(msg);
    }
}
