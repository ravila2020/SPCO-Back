package mx.com.oneproject.spco.respuesta;

import java.util.List;

import mx.com.oneproject.spco.modelo.SysAduFact;

public class AnsSysAduFactCListaString {

	private String cr;
	private String descripcion;
	private List<String> contenido;
	
	public String getCr() {
		return cr;
	}
	public void setCr(String cr) {
		this.cr = cr;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public List<String> getContenido() {
		return contenido;
	}
	public void setContenido(List<String> contenido) {
		this.contenido = contenido;
	}
	
	
	
}