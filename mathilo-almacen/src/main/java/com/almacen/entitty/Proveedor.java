package com.almacen.entitty;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;

@Entity
@Table(name = "proveedor", uniqueConstraints = { @UniqueConstraint(columnNames = { "nombre" }) })
public class Proveedor {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column(nullable = false, length = 60)
	private Long numtransferencia;

	@Column(nullable = false, length = 100)
	private String nombre;

	@Email
	@Column(length = 100)
	private String correo;

	@Column(length = 15)
	private String telefono;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getNumtransferencia() {
		return numtransferencia;
	}

	public void setNumtransferencia(Long numtransferencia) {
		this.numtransferencia = numtransferencia;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getCorreo() {
		return correo;
	}

	public void setCorreo(String correo) {
		this.correo = correo;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public Proveedor() {
		super();
	}

	public Proveedor(Long id, Long numtransferencia, String nombre, @Email String correo, String telefono) {
		super();
		this.id = id;
		this.numtransferencia = numtransferencia;
		this.nombre = nombre;
		this.correo = correo;
		this.telefono = telefono;
	}

}
