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
public class NotUniqueIdentifierException extends Exception {

    private static final long serialVersionUID = 4747133799923386690L;

    public NotUniqueIdentifierException() {
        super();
    }

    public NotUniqueIdentifierException(String msg) {
        super(msg);
    }
}
