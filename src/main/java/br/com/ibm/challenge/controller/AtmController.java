package br.com.ibm.challenge.controller;

import br.com.ibm.challenge.service.TransacaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/atm")
@RestController
public class AtmController {
	@Autowired
	private TransacaoService transacaoService;

	@PatchMapping("/sacar")
	public ResponseEntity sacar(@RequestParam String numeroConta,
								@RequestParam Double valorSaque) throws Exception{
		try {
			transacaoService.sacar(numeroConta, valorSaque);
			return ResponseEntity.ok().build();
		} catch (Exception ex) {
			throw ex;
		}
	}

}
