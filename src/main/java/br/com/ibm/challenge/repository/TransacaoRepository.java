package br.com.ibm.challenge.repository;

import br.com.ibm.challenge.domain.Transacao;
import org.springframework.data.repository.CrudRepository;

public interface TransacaoRepository extends CrudRepository<Transacao, Long> {
}
