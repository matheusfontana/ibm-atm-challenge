package br.com.ibm.challenge.repository;

import br.com.ibm.challenge.domain.Conta;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface ContaRepository extends CrudRepository<Conta, Long> {
	Optional<Conta> getContaByNumeroConta(String numeroConta);
}
