
import java.io.IOException;
import java.util.HashMap;
import manager.BaseTaskManager;

public class StringCodingTaskManager extends BaseTaskManager {

    private final String[] string2Code = new String[]{
                "Drumstick beef spare ribs filet mignon, pig tail flank short loin sausage ham hock picanha pancetta meatball. Drumstick boudin shoulder hamburger. Tail capicola frankfurter, swine beef burgdoggen ground round drumstick ball tip tongue corned beef pork bacon beef ribs pork chop. Landjaeger swine pastrami tri-tip burgdoggen chicken rump frankfurter.",
                "Ball tip jerky venison bacon kielbasa capicola. Drumstick hamburger flank, doner boudin prosciutto tenderloin pork chop. Ball tip landjaeger ground round fatback turducken, buffalo pastrami cupim kevin chuck shank pork loin. Chicken meatloaf prosciutto, sirloin ribeye picanha landjaeger rump. Ground round short loin ribeye t-bone boudin.",
                "Kielbasa ground round tri-tip, doner t-bone ball tip leberkas frankfurter beef jowl. Corned beef short ribs sausage chuck shoulder pastrami ham hock, tri-tip venison. Landjaeger ham picanha rump. Meatball kevin prosciutto, jerky cow tail corned beef drumstick turkey bacon pork salami tri-tip tenderloin. Ribeye short loin ham ball tip cupim kielbasa beef ribs jowl swine pig venison porchetta jerky. Salami tenderloin short ribs sirloin biltong. Cow kielbasa landjaeger hamburger ground round cupim.",
                "Ball tip kielbasa shankle burgdoggen kevin capicola fatback short loin tongue t-bone. Tongue hamburger shank, venison landjaeger short loin ham capicola doner turducken ground round boudin tail kevin. Beef ribs hamburger kevin meatball, shank tongue fatback. Tenderloin chicken biltong chuck bacon, meatloaf hamburger pastrami buffalo t-bone porchetta swine. Alcatra short ribs ham hock pork chop. Turkey pork chop kielbasa bresaola bacon chuck venison landjaeger beef ribs cow strip steak short loin.",
                "Does your lorem ipsum text long for something a little meatier? Give our generator a try… it’s tasty!"};
    private int counter = 0;

    @Override
    public void finishedResult(HashMap<String, Object> params, Object result) {
        try {
            this.getStorageManager().store(result);
            System.out.println("Resultat gespeichert!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public HashMap<String, Object> getNextParams() {
        if (this.counter < string2Code.length) {
            HashMap<String, Object> params = new HashMap<>();
            params.put("string", string2Code[counter]);
            this.counter++;
            return params;
        }
        return null;
    }

    @Override
    public boolean isStatusQueryAccepted() {
        return true;
    }

    @Override
    public int getTaskProgress() {
        return 0;
    }
}
