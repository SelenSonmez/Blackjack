import java.util.Locale;

public class Card {
    private Type type;
    private Value value;

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public Value getValue() {
        return value;
    }

    public void setValue(Value value) {
        this.value = value;
    }

    public Card(Type type, Value value) {
        this.type = type;
        this.value = value;
    }
    public String toString(){
        return type.toString().toLowerCase(Locale.ROOT) +value.toString().toLowerCase(Locale.ROOT);
    }
}
