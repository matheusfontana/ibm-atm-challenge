package br.com.ibm.challenge.service;

import br.com.ibm.challenge.domain.Conta;
import br.com.ibm.challenge.repository.ContaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ContaService {
	@Autowired
	private ContaRepository contaRepository;

	public List<Conta> getAllContas() {
		List<Conta> contas = new ArrayList<>();
		contaRepository.findAll().forEach(contas::add);
		return contas;
	}

	public Optional<Conta> getContaById(Long id) {
		return contaRepository.findById(id);
	}

	public Optional<Conta> getContaByNumeroConta(String numeroConta) {
		return contaRepository.getContaByNumeroConta(numeroConta);
	}

	public Conta saveOrUpdate(Conta conta) {
		return contaRepository.save(conta);
	}

	public void delete(Long id) {
		contaRepository.deleteById(id);
	}

	public static Double valorAposSaque(Double saldoAtual, Double valorSaque) {
		return (saldoAtual - valorSaque);
	}
}
