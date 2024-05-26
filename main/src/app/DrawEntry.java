package app;

public class DrawEntry {
    public String textureName;
    public int xl;
    public int xr;
    public int yu;
    public int yd;

    public DrawEntry(String textureName, int xl, int xr, int yu, int yd) {
        this.textureName = textureName;
        this.xl = xl;
        this.xr = xr;
        this.yu = yu;
        this.yd = yd;
    }
}
