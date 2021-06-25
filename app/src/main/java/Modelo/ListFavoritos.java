package Modelo;

public class ListFavoritos {
    public String color;
    public String ruta;

    public ListFavoritos(String ruta) {
        this.ruta = ruta;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getRuta() {
        return ruta;
    }

    public void setRuta(String ruta) {
        this.ruta = ruta;
    }
}
