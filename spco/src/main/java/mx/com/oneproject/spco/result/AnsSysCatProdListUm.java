package mx.com.oneproject.spco.result;

import java.util.List;

import mx.com.oneproject.spco.modelo.SysCatProd;

public class AnsSysCatProdListUm {

	private String cr;
	private String descripcion;
	int page;
	int perPage;
	int total;
	int totalPages;
	public List<SysCatProd> objetoItem;
	public List<String> uMDescripcion;
	
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
	public int getPage() {
		return page;
	}
	public void setPage(int page) {
		this.page = page;
	}
	public int getPerPage() {
		return perPage;
	}
	public void setPerPage(int perPage) {
		this.perPage = perPage;
	}
	public int getTotal() {
		return total;
	}
	public void setTotal(int total) {
		this.total = total;
	}
	public int getTotalPages() {
		return totalPages;
	}
	public void setTotalPages(int totalPages) {
		this.totalPages = totalPages;
	}
	public List<SysCatProd> getObjetoItem() {
		return objetoItem;
	}
	public void setObjetoItem(List<SysCatProd> objetoItem) {
		this.objetoItem = objetoItem;
	}
	public List<String> getuMDescripcion() {
		return uMDescripcion;
	}
	public void setuMDescripcion(List<String> uMDescripcion) {
		this.uMDescripcion = uMDescripcion;
	}
	
	
}
