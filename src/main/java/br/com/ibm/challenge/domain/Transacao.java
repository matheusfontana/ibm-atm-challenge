package br.com.ibm.challenge.domain;

import br.com.ibm.challenge.enumerator.TransacaoEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
public class Transacao {

	@Id
	@GeneratedValue
	private Long id;

	@OneToOne
	@JoinColumn(name = "id")
	private Conta contaOrigem;

	@OneToOne
	@JoinColumn(name = "id")
	private Conta contaDestino;

	private TransacaoEnum tipoTransacao;

	private double valorTransacao;

	private Date dataTransacao;
}
