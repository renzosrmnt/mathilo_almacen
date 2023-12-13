package com.almacen.entitty;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.*;

@Entity
public class Entrada {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "fecha_entrada")
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date fecha;

	@ManyToOne
	@JoinColumn(name = "articulo_id")
	private Articulo articulo;

	@Column(nullable = false)
	private int cantidad;

	@Column(nullable = false)
	private double precio;

	@ManyToOne
	@JoinColumn(name = "proveedor_id")
	private Proveedor proveedor;

	@Column(length = 100)
	private String nota;
	
	@PrePersist
	private void prePersist() {
		fecha = new Date();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha_entrada) {
		this.fecha= fecha_entrada;
	}

	public Articulo getArticulo() {
		return articulo;
	}

	public void setArticulo(Articulo articulo) {
		this.articulo = articulo;
	}

	public int getCantidad() {
		return cantidad;
	}

	public void setCantidad(int cantidad) {
		this.cantidad = cantidad;
	}

	public double getPrecio() {
		return precio;
	}

	public void setPrecio(double precio) {
		this.precio = precio;
	}

	public Proveedor getProveedor() {
		return proveedor;
	}

	public void setProveedor(Proveedor proveedor) {
		this.proveedor = proveedor;
	}

	public String getNota() {
		return nota;
	}

	public void setNota(String nota) {
		this.nota = nota;
	}

	public Entrada() {
		super();
	}

	public Entrada(Long id, Date fecha, Articulo articulo, int cantidad, double precio, Proveedor proveedor,
			String nota) {
		super();
		this.id = id;
		this.fecha = fecha;
		this.articulo = articulo;
		this.cantidad = cantidad;
		this.precio = precio;
		this.proveedor = proveedor;
		this.nota = nota;
	}

}
