package mx.com.oneproject.spco.rest;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;

import io.jsonwebtoken.Jwts;
import mx.com.oneproject.spco.exception.ApiRequestException;
import mx.com.oneproject.spco.log.logRegistra;
import mx.com.oneproject.spco.modelo.AppUser;
import mx.com.oneproject.spco.modelo.VigenciaToken;
import mx.com.oneproject.spco.repositorio.IMAppUserRepo;
import mx.com.oneproject.spco.repositorio.IMLogTransacctionRepo;
import mx.com.oneproject.spco.repositorio.IMVigTokenRepo;
import mx.com.oneproject.spco.respuesta.AppUserPag;
import mx.com.oneproject.spco.result.AnsUserPag;
import mx.com.oneproject.spco.result.AnsUserPagList;
import mx.com.oneproject.spco.result.AnsUserPagOpc;

@CrossOrigin(origins = "*",maxAge = 3600)
@RestController
@RequestMapping("/Users")
public class RestAppUserController {

	@Autowired
	private IMAppUserRepo repAppUser;

	@Autowired
	private IMAppUserRepo repAppUserBack;

	@Autowired
	private BCryptPasswordEncoder codificador;
	
	@Autowired
	private IMLogTransacctionRepo repLog;

	@Autowired
	private logRegistra registrar;

	@Autowired
	private IMVigTokenRepo vigencia;
//	@GetMapping
//	public List<AppUser> listar(HttpServletRequest peticion){
//		boolean enabled = true;
//		
//		String token = peticion.getHeader("Authorization");
//		System.out.print("\n\n + RestAppUserController token: " + token + "\n ");
//		if (token != null) {
//			String user = Jwts.parser()
//					.setSigningKey("0neProj3ct")
//					.parseClaimsJws(token.replace("Bearer",  ""))
//					.getBody()
//					.getSubject();
//			System.out.print("\n\n + RestAppUserController Usuario: " + user + "\n ");
//		}
//		
//		
//		return repAppUser.findByEnabled(enabled);
//	}

	
	// Consulta de lista de todos los usuarios con validacion de token.
	
	@GetMapping
	public AnsUserPag listar(HttpServletRequest peticion){
		boolean enabled = true;
        AnsUserPag respuesta = new AnsUserPag();
        
		String token = peticion.getHeader("Authorization");
		System.out.print("\n\n + RestAppUserController token: " + token + "\n ");
		if (token != null) {
			String user = Jwts.parser()
					.setSigningKey("0neProj3ct")
					.parseClaimsJws(token.replace("Bearer",  ""))
					.getBody()
					.getSubject();
			String secuencia = Jwts.parser()
					.setSigningKey("0neProj3ct")
					.parseClaimsJws(token.replace("Bearer",  ""))
					.getBody()
					.getId();

			System.out.print("\n +++++ RestAppUserController Usuario: " + user + " Elemento: " + secuencia + "\n Token : "+ token+ "\n ");
		    Optional<VigenciaToken> Vtoken = vigencia.findById(Integer.valueOf(secuencia));
			System.out.print("\n +++++ RestAppUserController Usuario: " + user + " Elemento: " + secuencia + "\n Token : "+ Vtoken.get().getToken()+ "\n ");
			if(token.equals("Bearer" + Vtoken.get().getToken()))
				{System.out.print("\n OK \n");}
			else 
				{System.out.print("\n NoOk \n");}
			respuesta.setCr("00");
			respuesta.setDescripcion("Correcto");	
			respuesta.setContenido(repAppUser.findByEnabled(enabled));			
		}else
		{
			respuesta.setCr("99");
			respuesta.setDescripcion("Petición sin token");		
			}
		return respuesta;
	}

	// Consulta de un usuario con validacion de token.
	
    @GetMapping(path = {"/{id}"})
    public AnsUserPagOpc listarId(HttpServletRequest peticion, @PathVariable("id") String id){
		boolean enabled = true;
        AnsUserPagOpc respuesta = new AnsUserPagOpc();

		String token = peticion.getHeader("Authorization");
		System.out.print("\n\n + RestAppUserController token: " + token + "\n ");
		if (token != null) {
			String user = Jwts.parser()
					.setSigningKey("0neProj3ct")
					.parseClaimsJws(token.replace("Bearer",  ""))
					.getBody()
					.getSubject();
			System.out.print("\n\n + RestAppUserController Usuario: " + user + "\n ");
			respuesta.setCr("00");
			respuesta.setDescripcion("Correcto");	
			respuesta.setContenido(repAppUser.findByUsername(id));			
		}else
		{
			respuesta.setCr("99");
			respuesta.setDescripcion("Petición sin token");		
			}
        return respuesta;
    }

    
	// Consulta de un usuario con filtro y validacion de token.
   
    @GetMapping(path = {"like/{id}"})
    public AnsUserPag listarLike(HttpServletRequest peticion, @PathVariable("id") String id){
    	String like = "%" + id + "%";
    	AnsUserPag respuesta = new AnsUserPag();
    	System.out.print(" + RestAppUserController listarId id: " + like + "\n ");
    	
		String token = peticion.getHeader("Authorization");
		System.out.print("\n\n + RestAppUserController token: " + token + "\n ");
		if (token != null) {
			String user = Jwts.parser()
					.setSigningKey("0neProj3ct")
					.parseClaimsJws(token.replace("Bearer",  ""))
					.getBody()
					.getSubject();
			System.out.print("\n\n + RestAppUserController Usuario: " + user + "\n ");
	    	respuesta.setCr("00");
	    	respuesta.setDescripcion("Correcto");
	    	respuesta.setContenido(repAppUser.findByFirstname(like));		
		}else
		{
			respuesta.setCr("99");
			respuesta.setDescripcion("Petición sin token");		
			}

        return respuesta;
    }
    
    
    
 /*   @GetMapping(path = {"/unic"})
    public Optional<Integer> listarDistinctId(@PathVariable("id") String id){
    	System.out.print(" + RestAppUserController listarId id: " + id + "\n ");
        return repAppUser.findDistinctByName();
    }
*/
	// Consulta de usuarios con paginacion y validacion de token.
    @GetMapping(path = {"/pag"})
    public AnsUserPagList listarPag(@RequestParam(required = false, value = "page") int page,
    		                    @RequestParam(required = false, value = "perpage") int perPage, 
    		                    HttpServletRequest peticion) {
    	
    	AnsUserPagList respuesta = new AnsUserPagList();
    	String token = peticion.getHeader("Authorization");
		System.out.print("\n\n + RestAppUserController token: " + token + "\n ");
		if (token != null) {
			String user = Jwts.parser()
					.setSigningKey("0neProj3ct")
					.parseClaimsJws(token.replace("Bearer",  ""))
					.getBody()
					.getSubject();
			System.out.print("\n\n + RestAppUserController Usuario: " + user + "\n ");
		}	else	{
			respuesta.setCr("99");
			respuesta.setDescripcion("Petición sin token");		
			return respuesta;
			}
		boolean enabled = true;
		AppUser usuarioCero = new AppUser();
		Long todos = (long) 0;
		double paginas = (float) 0.0;
		Integer pagEntero = 0;
		List<AppUser> todosUsuarios;
		List<AppUser> paginaUsuarios; 
		Integer usuarioInicial, usuarioFinal;
		
    	AppUserPag resultado = new AppUserPag();
    	
    	System.out.print(" + RestAppUserController listarPag page: " + page + " perpage: " + perPage +"\n ");

    	// obtener el total.
//         todos = repAppUser.count();
         todos = repAppUser.countByEnabled(true);
         paginas = (double) todos / perPage;
         pagEntero = (int) paginas;
         if ((paginas-pagEntero)>0)
         {
        	 pagEntero++;
         }
         // Obtener la lista solicitada
         usuarioInicial = (perPage  * (page - 1) );
         usuarioFinal   = (usuarioInicial + perPage) - 1;
         todosUsuarios  = repAppUser.findByEnabled(enabled);
         paginaUsuarios = repAppUser.findByEnabled(enabled);
         paginaUsuarios.clear();
         for (int i=0; i<todos;i++) {
        	 System.out.print("\n " + "          + RestAppUserController Usuario: " + i + " - " + todosUsuarios.get(i).getUsername() );
        	 if(i>=usuarioInicial && i<=usuarioFinal)
        	 {
        		 usuarioCero = todosUsuarios.get(i);
        		 paginaUsuarios.add(usuarioCero);
        		 System.out.print("  -- En lista  --" + usuarioCero.getUsername() );
        	 }
         }
         
         
     	System.out.print("\n + RestAppUserController listarPag todos: " + todos + " paginas: " + paginas + "  " + (paginas-pagEntero ) +"\n ");
         //
         resultado.setPage(page);
         resultado.setPerPage(perPage);
         resultado.setTotal((int) repAppUser.countByEnabled(true));
         resultado.setTotalPages(pagEntero);
         resultado.setUsers(paginaUsuarios);

	 	 respuesta.setContenido(resultado);
		 respuesta.setCr("00");
		 respuesta.setDescripcion("Correcto");
         return respuesta;
    }
    
	// Consulta de usuarios por filtro con paginacion y validacion de token.
    @GetMapping(path = {"/paglike"})
    public AnsUserPagList listarPagLike(@RequestParam(required = false, value = "page") int page,
    		                    @RequestParam(required = false, value = "perpage") int perPage, 
    		                    @RequestParam(required = false, value = "id") String id, 
    		                    HttpServletRequest peticion) {
    	String like = "%" + id + "%";
    	AnsUserPagList respuesta = new AnsUserPagList();
    	String token = peticion.getHeader("Authorization");
		System.out.print("\n\n + RestAppUserController token: " + token + "\n ");
		if (token != null) {
			String user = Jwts.parser()
					.setSigningKey("0neProj3ct")
					.parseClaimsJws(token.replace("Bearer",  ""))
					.getBody()
					.getSubject();
			String secuencia = Jwts.parser()
					.setSigningKey("0neProj3ct")
					.parseClaimsJws(token.replace("Bearer",  ""))
					.getBody()
					.getId();
			System.out.print("\n\n + RestAppUserController Usuario: " + user + "\n ");
			
			System.out.print("\n +++++ RestAppUserController Usuario: " + user + " Elemento: " + secuencia + "\n Token : "+ token+ "\n ");
		    Optional<VigenciaToken> Vtoken = vigencia.findById(Integer.valueOf(secuencia));
			System.out.print("\n +++++ RestAppUserController Usuario: " + user + " Elemento: " + secuencia + "\n Token : "+ Vtoken.get().getToken()+ "\n ");
			if(token.equals("Bearer" + Vtoken.get().getToken()))
				{System.out.print("OK");}
			else 
				{System.out.print("NoOk");
				respuesta.setCr("95");
				respuesta.setDescripcion("Token no valido");		
				return respuesta;
				}
		}	else	{
			respuesta.setCr("99");
			respuesta.setDescripcion("Petición sin token");		
			return respuesta;
			}
		boolean enabled = true;
		AppUser usuarioCero = new AppUser();
		Long todos = (long) 0;
		double paginas = (float) 0.0;
		Integer pagEntero = 0;
		List<AppUser> todosUsuarios;
		List<AppUser> paginaUsuarios; 
		Integer usuarioInicial, usuarioFinal;
		
    	AppUserPag resultado = new AppUserPag();
    	
    	System.out.print(" + RestAppUserController listarPag page: " + page + " perpage: " + perPage +"\n ");
    	// obtener el total.
         todos = repAppUser.countByFirstname(like);
         paginas = (double) todos / perPage;
         pagEntero = (int) paginas;
         if ((paginas-pagEntero)>0)
         {
        	 pagEntero++;
         }
         // Obtener la lista solicitada
         usuarioInicial = (perPage  * (page - 1) );
         usuarioFinal   = (usuarioInicial + perPage) - 1;
         todosUsuarios  = repAppUser.findByFirstname(like);
         paginaUsuarios = repAppUser.findByFirstname(like);
         paginaUsuarios.clear();
         for (int i=0; i<todos;i++) {
        	 System.out.print("\n " + "          + RestAppUserController Usuario: " + i + " - " + todosUsuarios.get(i).getUsername() );
        	 if(i>=usuarioInicial && i<=usuarioFinal)
        	 {
        		 usuarioCero = todosUsuarios.get(i);
        		 paginaUsuarios.add(usuarioCero);
        		 System.out.print("  -- En lista  --" + usuarioCero.getUsername() );
        	 }
         }
         
         
     	System.out.print("\n + RestAppUserController listarPag todos: " + todos + " paginas: " + paginas + "  " + (paginas-pagEntero ) +"\n ");
         //
         resultado.setPage(page);
         resultado.setPerPage(perPage);
         resultado.setTotal((int) repAppUser.countByFirstname(like));
         resultado.setTotalPages(pagEntero);
         resultado.setUsers(paginaUsuarios);

	 	 respuesta.setContenido(resultado);
		 respuesta.setCr("00");
		 respuesta.setDescripcion("Correcto");
         return respuesta;
    }

	// Alta de usuario con validacion de token y registro de log.    
	@PostMapping
	public  AnsUserPagOpc  creaUsuario(HttpServletRequest peticion,
			                          @RequestBody AppUser NuevoUsuario){
		AnsUserPagOpc respuesta = new AnsUserPagOpc();
    	String token = peticion.getHeader("Authorization");
		System.out.print("\n\n + RestAppUserController token: " + token + "\n ");

		registrar.registra("creaUsuario", "AppUser", "/Users", NuevoUsuario);
	
		if (token != null) {
			String user = Jwts.parser()
					.setSigningKey("0neProj3ct")
					.parseClaimsJws(token.replace("Bearer",  ""))
					.getBody()
					.getSubject();
			System.out.print("\n\n + RestAppUserController Usuario: " + user + "\n ");
		}	else	{
			respuesta.setCr("99");
			respuesta.setDescripcion("Petición sin token");		
			return respuesta;
			}

    	try {
			String idAppUser = NuevoUsuario.getUsername();
	    	String CryptoPass = codificador.encode(NuevoUsuario.getPassword());
	    	NuevoUsuario.setPassword(CryptoPass);
	    	
	    	//-------------existe el uauario?
			if (!repAppUser.findByUsername(idAppUser).isPresent())
			{
	    	//-------------
			AppUser usuarioProc = repAppUser.save(NuevoUsuario);
			System.out.print(" + RestAppUserController insertar id: " + usuarioProc.getId() + " " + usuarioProc.getUsername() + "\n ");
			respuesta.setCr("00");
			respuesta.setDescripcion("Correcto");
			respuesta.setContenido(repAppUser.findByUsername(usuarioProc.getUsername()));
	        return respuesta;
				}
		        else {
					respuesta.setCr("83");
					respuesta.setDescripcion("Ya existe el usuario");
			        return respuesta;
		    	}
	    	} catch (Exception ex) {
	    		throw new ApiRequestException("Upsi");
	    	} 
		}

	// Modificacion de password con validacion de token y registro de log.    
	@PutMapping(path = {"/{id}"})
	public AnsUserPagOpc cambiaPassword(HttpServletRequest peticion,
			                             @PathVariable("id") String id,
			                             @RequestBody AppUser NuevoUsuario){
		AnsUserPagOpc respuesta = new AnsUserPagOpc();
		String Password = NuevoUsuario.getPassword();
		
    	String token = peticion.getHeader("Authorization");
		System.out.print("\n\n + RestAppUserController token: " + token + "\n ");
		if (token != null) {
			String user = Jwts.parser()
					.setSigningKey("0neProj3ct")
					.parseClaimsJws(token.replace("Bearer",  ""))
					.getBody()
					.getSubject();
			System.out.print("\n\n + RestAppUserController Usuario: " + user + "\n ");
		}	else	{
			respuesta.setCr("99");
			respuesta.setDescripcion("Petición sin token");		
			return respuesta;
			}
		
    	String CryptoPass = codificador.encode(NuevoUsuario.getPassword());
    	System.out.print(" + RestAppUserController listarId id: " + id + " Password:  " + Password + " " + CryptoPass + " \\n");
    	try {
		ObjectMapper mapper = new ObjectMapper();
		String jsonInString = mapper.writeValueAsString(NuevoUsuario);
		System.out.print(" + Objeto: " + jsonInString);	
		
		registrar.registra("modificaPassword", "AppUser", "/Users/"+id, NuevoUsuario);
		    	
		Optional<AppUser> UsuarioExistente = repAppUser.findByUsername(id);
		NuevoUsuario = UsuarioExistente.get();
    	NuevoUsuario.setPassword(CryptoPass);
		repAppUser.save(NuevoUsuario);
		respuesta.setContenido(repAppUser.findByUsername(id));
		respuesta.setCr("00");
		respuesta.setDescripcion("Correcto");	
		return respuesta;
    	} catch (Exception ex) {
    		throw new ApiRequestException("Upsi");
    	}
	}

	// Modificacion de usuario con validacion de token y registro de log. 
	@PutMapping
	public AnsUserPagOpc modificar(HttpServletRequest peticion,
			                       @RequestBody AppUser NuevoUsuario){
		AnsUserPagOpc respuesta = new AnsUserPagOpc();
		String idAppUser = NuevoUsuario.getUsername();
    	String token = peticion.getHeader("Authorization");
		System.out.print("\n\n + RestAppUserController token: " + token + "\n ");
		if (token != null) {
			String user = Jwts.parser()
					.setSigningKey("0neProj3ct")
					.parseClaimsJws(token.replace("Bearer",  ""))
					.getBody()
					.getSubject();
			System.out.print("\n\n + RestAppUserController Usuario: " + user + "\n ");
		}	else	{
			respuesta.setCr("99");
			respuesta.setDescripcion("Petición sin token");		
			return respuesta;
			}
		
    	try {
		ObjectMapper mapper = new ObjectMapper();
		String jsonInString = mapper.writeValueAsString(NuevoUsuario);
		System.out.print(" + Objeto: " + jsonInString);		
		registrar.registra("modificaUsuario", "AppUser", "/Users/", NuevoUsuario);
				
		String CryptoPass = codificador.encode(NuevoUsuario.getPassword());
	 	NuevoUsuario.setPassword(CryptoPass);

		repAppUser.save(NuevoUsuario);
		
		respuesta.setContenido(repAppUser.findByUsername(idAppUser));
		respuesta.setCr("00");
		respuesta.setDescripcion("Correcto");	
		return respuesta;

    	} catch (Exception ex) {
    		throw new ApiRequestException("Registro con incidencia");
    	}
	}
	
	// Deshabilitacion de usuario con validacion de token y registro de log. 	
    @DeleteMapping(path = {"/{id}"})
    public AnsUserPagOpc EliminarId(HttpServletRequest peticion,
                                    @PathVariable("id") String id){
    	System.out.print(" + RestAppUserController EliminarId id: " + id + " ");
    	AnsUserPagOpc respuesta = new AnsUserPagOpc();
    	
    	String token = peticion.getHeader("Authorization");
		System.out.print("\n\n + RestAppUserController token: " + token + "\n ");
		if (token != null) {
			String user = Jwts.parser()
					.setSigningKey("0neProj3ct")
					.parseClaimsJws(token.replace("Bearer",  ""))
					.getBody()
					.getSubject();
			System.out.print("\n\n + RestAppUserController Usuario: " + user + "\n ");
		}	else	{
			respuesta.setCr("99");
			respuesta.setDescripcion("Petición sin token");		
			return respuesta;
			}

    	Optional<AppUser> Usuario = repAppUser.findByUsername(id);
    	
		if (!Usuario.get().isEnabled()) {
			respuesta.setCr("97");
			respuesta.setDescripcion("Usuario ya inhabilitado");		
			return respuesta;		
			}	

    	AppUser UsuarioD = Usuario.get();
    	UsuarioD.setEnabled(false);

    	try {
    		ObjectMapper mapper = new ObjectMapper();
    		String jsonInString =id;
    		System.out.print(" + Objeto: " + jsonInString);		
    		registrar.registra("eliminaUsuario", "AppUser", "/Users/"+id, UsuarioD);
    			
    		repAppUser.save(UsuarioD);
    		respuesta.setContenido(Usuario);
    		respuesta.setCr("00");
    		respuesta.setDescripcion("Correcto");
    		return respuesta; }
    	catch (Exception e) {
    		respuesta.setDescripcion("Registro con incidencia");
    		return respuesta;}
    }	
}