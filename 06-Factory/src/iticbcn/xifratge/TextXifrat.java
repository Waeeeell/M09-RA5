package iticbcn.xifratge;

import java.util.Arrays;

public class TextXifrat {
    private byte[] dades;

    public TextXifrat(byte[] dades) {
        this.dades = Arrays.copyOf(dades, dades.length);
    }

    public byte[] getBytes() {
        return Arrays.copyOf(dades, dades.length);
    }

    @Override
    public String toString() {
        return new String(dades);
    }
}
