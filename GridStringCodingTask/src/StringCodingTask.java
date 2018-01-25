
import util.UserTask;


public class StringCodingTask extends UserTask {

    private static final long serialVersionUID = -4739086436921175357L;

    private String codedString;
    private int lenght;
    private int position;

    @Override
    public Object getResult() {
        return codedString;
    }

    @Override
    public void run() {
        String string2Code = (String) this.getParameterByName("string");
        lenght = string2Code.length();
        System.out.println("orignal string [" + lenght + "]: " + string2Code);
        
        StringBuilder stringBuilder = new StringBuilder();

        for (int i = 0; i < string2Code.length(); i++) {
            position = i;
            stringBuilder.append((char) (string2Code.charAt(i) + 1));
        }

        codedString = stringBuilder.toString();
        System.out.println("encrypted string: " + codedString);
    }

    @Override
    public boolean isProgressBarRequired() {
        return true;
    }

    @Override
    public boolean isTextOutputAreaRequired() {
        return true;
    }

    @Override
    public int getMaxProgressValue() {
        int position = (int)this.getParameterByName("position");
        int length = (int)this.getParameterByName("length");
        return (length % 100) * position;
    }
}
