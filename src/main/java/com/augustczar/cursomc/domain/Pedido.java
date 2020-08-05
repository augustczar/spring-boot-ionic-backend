package com.augustczar.cursomc.domain;

import java.io.Serializable;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
public class Pedido implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@JsonFormat(pattern = "dd/MM/yyy HH:mm")
	private Date instante;

	//peculiaridade do jpa para nao dar erro ao salvar um pedido e o pagamento mapeamento um pra um
	@OneToOne(cascade = CascadeType.ALL, mappedBy = "pedido") 
	private Pagamento pagamento;
	
	@ManyToOne
	@JoinColumn(name = "cliente_id")
	private Cliente cliente;
	
	@ManyToOne
	@JoinColumn(name = "endereco_de_entrega_id")
	private Endereco enderecoDeEntega;
	
	//Set garante que eu tenha uma colecao de itens sem repeticao
	@OneToMany(mappedBy = "id.pedido")
	private Set<ItemPedido> itens = new HashSet<ItemPedido>();
	
	public Pedido() {
	}

	public Pedido(Integer id, Date instante, Cliente cliente, Endereco enderecoDeEntega) {
		super();
		this.id = id;
		this.instante = instante;
		this.cliente = cliente;
		this.enderecoDeEntega = enderecoDeEntega;
	}
	
	public double getValorTotal() {
		double soma = 0.0;
		for(ItemPedido ip : itens) {
			soma = soma + ip.getSubTotal();
		}
		return soma;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Date getInstante() {
		return instante;
	}

	public void setInstante(Date instante) {
		this.instante = instante;
	}

	public Pagamento getPagamento() {
		return pagamento;
	}

	public void setPagamento(Pagamento pagamento) {
		this.pagamento = pagamento;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public Endereco getEnderecoDeEntega() {
		return enderecoDeEntega;
	}

	public void setEnderecoDeEntega(Endereco enderecoDeEntega) {
		this.enderecoDeEntega = enderecoDeEntega;
	}

	public Set<ItemPedido> getItens() {
		return itens;
	}

	public void setItens(Set<ItemPedido> itens) {
		this.itens = itens;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Pedido other = (Pedido) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		NumberFormat nf = NumberFormat.getCurrencyInstance(new Locale("pt", "BR"));
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
		
		StringBuilder builder = new StringBuilder();
		
		builder.append("Pedido numero: ");
		builder.append(getId());
		builder.append(", Instante:");
		builder.append(sdf.format(getInstante()));
		builder.append(", Cliente: ");
		builder.append(getCliente().getNome());
		builder.append(", Situacao do pagamento: ");
		builder.append(getPagamento().getEstadoPagamento().getDescricao());
		builder.append("\nDetalhes:\n");

		for(ItemPedido ip : getItens()) {
			builder.append(ip.toString());
		}
		
		builder.append("Valor Total: ");
		builder.append(nf.format(getValorTotal()));
		
		return builder.toString();
	}
	
	
}

