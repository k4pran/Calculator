package sample;

import java.util.ArrayList;

public class KeyTracker {

    private ArrayList<String> keysPressed;

    public KeyTracker() {
        this.keysPressed = new ArrayList<>();
    }

    public void addKey(String key) {
        if (!key.equalsIgnoreCase("=")) {
            keysPressed.add(key);
        }
        else {
            // todo pass to make calculation
        }
    }
}
