package com.almacen.entitty;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.*;

@Entity
public class Salida {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "fecha_salida")
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date fecha;

	@ManyToOne
	@JoinColumn(name = "articulo_id")
	private Articulo articulo;

	@Column(nullable = false)
	private int cantidad;

	@ManyToOne
	@JoinColumn(name = "pedido_id")
	private Pedido pedido;
	

	@Column(length = 60)
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

	public void setFecha(Date fecha) {
		this.fecha = fecha;
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

	public Pedido getPedido() {
		return pedido;
	}

	public void setPedido(Pedido pedido) {
		this.pedido = pedido;
	}

	public String getNota() {
		return nota;
	}

	public void setNota(String nota) {
		this.nota = nota;
	}


	public Salida(Long id, Date fecha, Articulo articulo, int cantidad, Pedido pedido, String nota) {
		super();
		this.id = id;
		this.fecha = fecha;
		this.articulo = articulo;
		this.cantidad = cantidad;
		this.pedido = pedido;
		this.nota = nota;
	}

	public Salida() {
		super();
	}

	

}
