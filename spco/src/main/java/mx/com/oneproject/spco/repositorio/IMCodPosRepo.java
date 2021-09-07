package mx.com.oneproject.spco.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import mx.com.oneproject.spco.modelo.CodPost;

public interface IMCodPosRepo extends JpaRepository<CodPost, String>{
	
	@Query("select distinct (m.dEstado) from CodPost m where  m.cEstado = :claveEdo")
	String findByClaveEstado(@Param("claveEdo") String claveEdo);

	@Query("select distinct (m.dCiudad) from CodPost m where  m.cCveCiudad = :claveCd and  m.cEstado = :claveEdo")
	String findByClaveCd(@Param("claveCd") String claveCd, @Param("claveEdo") String claveEdo);

	@Query("select distinct (m.dMnpio) from CodPost m where  m.cMnpio = :claveMpio and m.cCveCiudad = :claveCd and  m.cEstado = :claveEdo")
	String findByClaveMpio(@Param("claveMpio") String claveMpio, @Param("claveCd") String claveCd, @Param("claveEdo") String claveEdo);

}
