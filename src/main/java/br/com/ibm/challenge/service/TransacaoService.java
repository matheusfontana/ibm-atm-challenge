package br.com.ibm.challenge.service;

import br.com.ibm.challenge.domain.Conta;
import br.com.ibm.challenge.domain.Transacao;
import br.com.ibm.challenge.enumerator.TransacaoEnum;
import br.com.ibm.challenge.repository.TransacaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
				throw new Exception ("Saldo insuficiente");
			}

			contaAssociado = contaService.saveOrUpdate(contaAssociado);

			if (contaAssociado.getSaldo().equals(valorAposSaque)) {
				if (this.gerarTransacao(contaAssociado, TransacaoEnum.SAQUE).getId() != null) {
					return true;
				}
			}

			return false;
		} else {
			throw new Exception("Conta informada não existe");
		}

	}

	public Transacao gerarTransacao(Conta conta, TransacaoEnum transacaoEnum){
		Transacao transacao = new Transacao();
		transacao.setTipoTransacao(transacaoEnum);
		transacao.setDataTransacao(new Date());
		transacao.setValorTransacao(conta.getSaldo());
		transacao.setContaOrigem(conta);

		return transacaoRepository.save(transacao);
	}

}
