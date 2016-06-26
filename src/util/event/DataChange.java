package util.event;

/**
 * Created by Ilija on 6/26/2016.
 */
public class DataChange {
    public enum Type {KATEGORIJA, PODKATEGORIJA, STAVKA}

    private Type type;

    public DataChange(Type type) {
        this.type = type;
    }

    public Type getType() {
        return type;
    }
}
