package projekt.helpers;

import java.util.Vector;

public class Log {

    private final Vector<String> messages;

    public Log() {
        messages = new Vector<String>();
    }

    public void insert(String message) {
        messages.add(message);
    }

    public void clear() {
        messages.clear();
    }

    public String print() {

        StringBuilder out = new StringBuilder();
        for(String message : messages)
        {
            out.append(message).append("\n");
        }
        return out.toString();
    }

}
