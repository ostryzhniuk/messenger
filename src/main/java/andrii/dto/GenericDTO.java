package andrii.dto;

public class GenericDTO<E> {

    private E value;

    public GenericDTO() {
    }

    public GenericDTO(E value) {
        this.value = value;
    }

    public E getValue() {
        return value;
    }

    public void setValue(E value) {
        this.value = value;
    }
}
