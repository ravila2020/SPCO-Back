package mx.com.oneproject.spco.respuesta;

import java.util.List;

import mx.com.oneproject.spco.modelo.SysAduTrasp;

public class AnsSysAduTrans {

	private String cr;
	private String descripcion;
	private List<SysAduTrasp> contenido;
	
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
	public List<SysAduTrasp> getContenido() {
		return contenido;
	}
	public void setContenido(List<SysAduTrasp> contenido) {
		this.contenido = contenido;
	}
	

	
}
