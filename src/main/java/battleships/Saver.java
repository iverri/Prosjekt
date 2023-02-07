package battleships;

import java.io.IOException;

public interface Saver {
    
    public void save() throws IOException;

    public Game load() throws IOException;
}
