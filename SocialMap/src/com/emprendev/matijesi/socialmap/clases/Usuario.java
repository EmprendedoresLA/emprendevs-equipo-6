package com.emprendev.matijesi.socialmap.clases;

public class Usuario {
	String nombre;
    String apellido;
    String telefono;
    String mail;
    String fechaNac;
    String sexo;
    String localidad;
    double latitud;
    double longitud;
    String fechaUltGeoPosicion;
    
	public Usuario(String nombre, String apellido, String telefono,
			String mail, String fechaNac, String sexo, String localidad,
			double latitud, double longitud, String fechaUltGeoPosicion) {
		super();
		this.nombre = nombre;
		this.apellido = apellido;
		this.telefono = telefono;
		this.mail = mail;
		this.fechaNac = fechaNac;
		this.sexo = sexo;
		this.localidad = localidad;
		this.latitud = latitud;
		this.longitud = longitud;
		this.fechaUltGeoPosicion = fechaUltGeoPosicion;
	}

	public Usuario() {
		super();
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellido() {
		return apellido;
	}

	public void setApellido(String apellido) {
		this.apellido = apellido;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	public String getFechaNac() {
		return fechaNac;
	}

	public void setFechaNac(String fechaNac) {
		this.fechaNac = fechaNac;
	}

	public String getSexo() {
		return sexo;
	}

	public void setSexo(String sexo) {
		this.sexo = sexo;
	}

	public String getLocalidad() {
		return localidad;
	}

	public void setLocalidad(String localidad) {
		this.localidad = localidad;
	}

	public double getLatitud() {
		return latitud;
	}

	public void setLatitud(double latitud) {
		this.latitud = latitud;
	}

	public double getLongitud() {
		return longitud;
	}

	public void setLongitud(double longitud) {
		this.longitud = longitud;
	}

	public String getFechaUltGeoPosicion() {
		return fechaUltGeoPosicion;
	}

	public void setFechaUltGeoPosicion(String fechaUltGeoPosicion) {
		this.fechaUltGeoPosicion = fechaUltGeoPosicion;
	}
}