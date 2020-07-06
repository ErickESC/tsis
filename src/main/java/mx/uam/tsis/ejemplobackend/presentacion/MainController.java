package mx.uam.tsis.ejemplobackend.presentacion;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sun.tools.sjavac.Log;

import lombok.extern.slf4j.Slf4j;

/**
 * Controlador web
 * 
 * @author humbertocervantes
 *
 */
@Controller
@Slf4j
public class MainController {
	
	@GetMapping("/")
	public String index() {
		
		log.info("Se invocó el método index()");
		
		return "index";
	}

	@RequestMapping("/ejemplo")
	@ResponseBody
	public String ejemplo() {
		
	
		return "esto es un ejemplo";
	}

}
