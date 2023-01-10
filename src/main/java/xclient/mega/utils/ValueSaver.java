package xclient.mega.utils;

public record ValueSaver<T>(T anything) {
    @Override
    public String toString() {
        return "ValueSaver{" +
                "value=" + anything +
                '}';
    }
}