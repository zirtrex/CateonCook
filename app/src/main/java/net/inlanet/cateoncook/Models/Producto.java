package net.inlanet.cateoncook.Models;

public class Producto {

    private String idProducto;
    private String categoriaApp;
    private String categoria;
    private String codigo;
    private String nombreProducto;
    private Double precio;
    private String imgUrl;
    //private int imgResource = R.drawable.;

    public Producto() {}

    public Producto(String nombreProducto) {
        this.nombreProducto = nombreProducto;
    }

    public Producto(String idProducto, String categoriaApp, String categoria, String codigo, String nombreProducto, Double precio, String imgUrl) {
        this.idProducto = idProducto;
        this.categoriaApp = categoriaApp;
        this.categoria = categoria;
        this.codigo = codigo;
        this.nombreProducto = nombreProducto;
        this.precio = precio;
        this.imgUrl = imgUrl;
    }

    public String getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(String idProducto) {
        this.idProducto = idProducto;
    }

    public String getCategoriaApp() {
        return categoriaApp;
    }

    public void setCategoriaApp(String categoriaApp) {
        this.categoriaApp = categoriaApp;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getNombreProducto() {
        return nombreProducto;
    }

    public void setNombreProducto(String nombreProducto) {
        this.nombreProducto = nombreProducto;
    }

    public Double getPrecio() {
        return precio;
    }

    public void setPrecio(Double precio) {
        this.precio = precio;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    @Override
    public String toString() {
        return "Producto{" +
                "idProducto='" + idProducto + '\'' +
                ", categoriaApp='" + categoriaApp + '\'' +
                ", categoria='" + categoria + '\'' +
                ", codigo='" + codigo + '\'' +
                ", nombreProducto='" + nombreProducto + '\'' +
                ", precio=" + precio +
                ", imgUrl='" + imgUrl + '\'' +
                '}';
    }
}
