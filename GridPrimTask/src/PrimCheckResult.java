/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author thomas_r
 */
import java.io.Serializable;
import java.util.List;

public class PrimCheckResult implements Serializable {

    private static final long serialVersionUID = 200L;

    public List<Integer> primeNumbers;

    @Override
    public String toString() {
        if (primeNumbers.size() > 0) {
            
            StringBuilder buf = new StringBuilder();
            buf.append("Prime number sequence: ");
            
            primeNumbers.forEach((prime) -> {
                buf.append(prime).append(", ");
            });
            
            return buf.toString();
                    
        } else {
            return "No Prime numbers found!";
        }
    }
}
