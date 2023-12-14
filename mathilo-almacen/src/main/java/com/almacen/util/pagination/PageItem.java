package com.almacen.util.pagination;

public class PageItem {

    private int numero; // Número de la página
    private boolean actual; // Indica si es la página actual

    // Constructor que inicializa el número de página y si es la página actual
    public PageItem(int numero, boolean actual) {
        super();
        this.numero = numero;
        this.actual = actual;
    }

    // Getters y setters para los atributos de la clase

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public boolean isActual() {
        return actual;
    }

    public void setActual(boolean actual) {
        this.actual = actual;
    }
}
