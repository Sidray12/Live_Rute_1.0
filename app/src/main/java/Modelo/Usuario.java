package Modelo;

public class Usuario {
    private String uid;
    private String Nombre;
    private String Correo;
    private String Contraseña;
    private String rol;

    public Usuario() {
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String nombre) {
        Nombre = nombre;
    }

    public String getCorreo() {
        return Correo;
    }

    public void setCorreo(String correo) {
        Correo = correo;
    }

    public String getContraseña() {
        return Contraseña;
    }

    public void setContraseña(String contraseña) {
        Contraseña = contraseña;
    }

    public String getRol() { return rol; }

    public void setRol(String rol) {this.rol = rol;}

    @Override
    public String toString() {
        return Nombre;
    }
}
