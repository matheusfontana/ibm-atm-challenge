package br.com.ibm.challenge.controller;

import br.com.ibm.challenge.service.TransacaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RequestMapping("/atm")
@RestController
public class AtmController {
	@Autowired
	private TransacaoService transacaoService;

	@PutMapping("/sacar")
	public ResponseEntity sacar(@RequestParam String numeroConta,
								@RequestParam Double valorSaque) throws Exception{
		try {
			transacaoService.sacar(numeroConta, valorSaque);
			return ResponseEntity.ok().build();
		} catch (ResponseStatusException ex) {
			throw ex;
		}
	}

	@PutMapping("/transferir")
	public ResponseEntity transferir(@RequestParam String numeroContaOrigem,
									 @RequestParam String numeroContaDestino,
									 @RequestParam Double valorTransferencia) throws Exception{
		try {
			transacaoService.transferir(numeroContaOrigem, numeroContaDestino, valorTransferencia);
			return ResponseEntity.ok().build();
		} catch (ResponseStatusException ex) {
			throw ex;
		}
	}


	@PutMapping("/depositar")
	public ResponseEntity depositar(@RequestParam String numeroContaDestino,
								@RequestParam Double valorDeposito) throws Exception{
		try {
			transacaoService.depositar(numeroContaDestino, valorDeposito);
			return ResponseEntity.ok().build();
		} catch (ResponseStatusException ex) {
			throw ex;
		}
	}


}
