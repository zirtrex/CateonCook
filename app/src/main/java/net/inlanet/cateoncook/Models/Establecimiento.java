package net.inlanet.cateoncook.Models;


import java.util.Map;

public class Establecimiento {

    public String caducidad;
    public String codigo;
    public String codigo2MesesGracia;
    public Map<String, String> telefonos;
    public String nota;

    public Establecimiento() {
    }

    public Establecimiento(String caducidad, String codigo, String codigo2MesesGracia, Map<String, String> telefonos, String nota) {
        this.caducidad = caducidad;
        this.codigo = codigo;
        this.codigo2MesesGracia = codigo2MesesGracia;
        this.telefonos = telefonos;
        this.nota = nota;
    }

    public String getCaducidad() {
        return caducidad;
    }

    public void setCaducidad(String caducidad) {
        this.caducidad = caducidad;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getCodigo2MesesGracia() {
        return codigo2MesesGracia;
    }

    public void setCodigo2MesesGracia(String codigo2MesesGracia) {
        this.codigo2MesesGracia = codigo2MesesGracia;
    }

    public Map<String, String> getTelefonos() {
        return telefonos;
    }

    public void setTelefonos(Map<String, String> telefonos) {
        this.telefonos = telefonos;
    }

    public String getNota() {
        return nota;
    }

    public void setNota(String nota) {
        this.nota = nota;
    }

}
