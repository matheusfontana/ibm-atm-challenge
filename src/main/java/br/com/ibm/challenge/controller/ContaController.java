package br.com.ibm.challenge.controller;

import br.com.ibm.challenge.domain.Conta;
import br.com.ibm.challenge.service.ContaService;
import com.google.common.base.Preconditions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RequestMapping("/contas")
@RestController
public class ContaController {
	@Autowired
	private ContaService contaService;

	@GetMapping("/")
	private List<Conta> retornaTodasContas(){
		return contaService.getAllContas();
	}

	@GetMapping("/{numeroConta}")
	private ResponseEntity<Conta> retornaContaPorNumeroConta(@RequestParam String numeroConta) {
		try {
			Optional<Conta> conta = Preconditions.checkNotNull(contaService.getContaByNumeroConta(numeroConta));

			if (conta.isPresent()){
				return ResponseEntity.ok(conta.get());
			}

			return ResponseEntity.notFound().build();
		} catch (Exception e) {
			throw e;
		}
	}

	@PostMapping("/")
	private ResponseEntity<Long> novaConta(@RequestBody Conta conta){
		try{
			contaService.saveOrUpdate(conta);
			return new ResponseEntity<>(conta.getId(), HttpStatus.CREATED);
		} catch (Exception e) {
			throw e;
		}
	}
}
