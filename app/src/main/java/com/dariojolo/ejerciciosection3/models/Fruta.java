package com.dariojolo.ejerciciosection3.models;

/**
 * Created by Dario on 12/3/2017.
 */

public class Fruta {
    private String nombre;
    private String descripcion;
    private int imgFruta;
    private int imgIcono;
    private int cantidad;

    public static final int LIMIT_QUANTITY = 10;
    public static final int RESET_VALUE_QUANTITY = 0;

    public Fruta() {
    }

    public Fruta(String nombre, String descripcion, int imgFruta, int imgIcono, int cantidad) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.imgFruta = imgFruta;
        this.imgIcono = imgIcono;
        this.cantidad = cantidad;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

   public int getImgFruta() {
        return imgFruta;
    }

    public void setImgFruta(int imgFruta) {
        this.imgFruta = imgFruta;
    }

    public void setImgIcono(int imgIcono) {
        this.imgIcono = imgIcono;
    }

    public int getImgIcono() {
        return imgIcono;
    }


    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }
    //Añadir cantidad
    public void addQuantity(int quantity){
        if (this.cantidad < LIMIT_QUANTITY){
            this.cantidad += quantity;
        }
    }
    //Reset cantidad
    public void resetQuantity(){
        this.cantidad = RESET_VALUE_QUANTITY;
    }
}
