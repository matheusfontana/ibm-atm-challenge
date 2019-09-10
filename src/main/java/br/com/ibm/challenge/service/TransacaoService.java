package br.com.ibm.challenge.service;

import br.com.ibm.challenge.domain.Conta;
import br.com.ibm.challenge.domain.Transacao;
import br.com.ibm.challenge.enumerator.TransacaoEnum;
import br.com.ibm.challenge.repository.TransacaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Date;
import java.util.Optional;

@Service
public class TransacaoService {

	@Autowired
	private TransacaoRepository transacaoRepository;

	@Autowired
	private ContaService contaService;

	public boolean sacar(String numeroConta, Double valorSaque) throws Exception{
		Optional<Conta> conta = contaService.getContaByNumeroConta(numeroConta);

		if(conta.isPresent()){
			Conta contaAssociado = conta.get();
			Double saldoAtual = contaAssociado.getSaldo();

			Double valorAposSaque = ContaService.valorAposSaque(saldoAtual, valorSaque);
			if (valorAposSaque > 0) {
				contaAssociado.setSaldo(valorAposSaque);
			} else {
				throw new ResponseStatusException(HttpStatus.ACCEPTED,"Saldo insuficiente para realizar a operação");
			}

			contaAssociado = contaService.saveOrUpdate(contaAssociado);

			if (contaAssociado.getSaldo().equals(valorAposSaque)) {
				if (this.gerarTransacao(contaAssociado, null, TransacaoEnum.SAQUE, valorSaque).getId() != null) {
					return true;
				}
			}

			return false;
		} else {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Conta informada não existe");
		}
	}

	public boolean transferir(String numeroContaOrigem, String numeroContaDestino, Double valorTransferido) throws Exception {
		Optional<Conta> contaOrigem = contaService.getContaByNumeroConta(numeroContaOrigem);
		Optional<Conta> contaDestino = contaService.getContaByNumeroConta(numeroContaDestino);

		if(contaOrigem.isPresent() && contaDestino.isPresent()){
			Conta contaAssociadoOrigem = contaOrigem.get();
			Conta contaAssociadoDestino = contaDestino.get();
			Double saldoAtualOrigem = contaAssociadoOrigem.getSaldo();
			Double saldoAtualDestino = contaAssociadoDestino.getSaldo();

			Double valorAposSaque = ContaService.valorAposSaque(saldoAtualOrigem, valorTransferido);
			if (valorAposSaque > 0) {
				contaAssociadoOrigem.setSaldo(valorAposSaque);
				contaAssociadoDestino.setSaldo((saldoAtualDestino + valorTransferido));
			} else {
				throw new ResponseStatusException(HttpStatus.ACCEPTED,"Saldo insuficiente para realizar a operação");
			}

			contaAssociadoOrigem = contaService.saveOrUpdate(contaAssociadoOrigem);
			contaAssociadoDestino = contaService.saveOrUpdate(contaAssociadoDestino);

			if (contaAssociadoOrigem.getSaldo().equals(valorAposSaque)) {
				if (this.gerarTransacao(contaAssociadoOrigem, contaAssociadoDestino, TransacaoEnum.TRANSFERENCIA, valorTransferido).getId() != null) {
					return true;
				}
			}

			return false;
		} else {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Conta informada não existe");
		}
	}

	public boolean depositar(String numeroContaDestino, Double valorDeposito) throws Exception{
		Optional<Conta> conta = contaService.getContaByNumeroConta(numeroContaDestino);

		if(conta.isPresent()){
			Conta contaDestino = conta.get();
			Double saldoAtual = contaDestino.getSaldo();

			contaDestino.setSaldo((saldoAtual + valorDeposito));
			contaDestino = contaService.saveOrUpdate(contaDestino);
			if (this.gerarTransacao(null, contaDestino, TransacaoEnum.DEPOSITO, valorDeposito).getId() != null) {
				return true;
			}

			return false;
		} else {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Conta informada não existe");
		}
	}

	public Transacao gerarTransacao(Conta contaOrigem, Conta contaDestino, TransacaoEnum transacaoEnum, Double valorTransacao){
		Transacao transacao = new Transacao();
		transacao.setTipoTransacao(transacaoEnum);
		transacao.setDataTransacao(new Date());
		transacao.setValorTransacao(valorTransacao);
		transacao.setContaOrigem(contaOrigem);
		transacao.setContaDestino(contaDestino);

		return transacaoRepository.save(transacao);
	}

}
