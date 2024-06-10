package br.com.lojademovel.pedido;

import br.com.lojademovel.cliente.Cliente;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class Pedido implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private int id;
    private Cliente cliente;
    private List<ItemPedido> itens;
    private Date dataPedido;
    private Date dataPagamento;
    private Date dataEntrega;
    private String descricao;

    // Getters e setters existentes

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public List<ItemPedido> getItens() {
        return itens;
    }

    public void setItens(List<ItemPedido> itens) {
        this.itens = itens;
    }

    public Date getDataPedido() {
        return dataPedido;
    }

    public void setDataPedido(Date dataPedido) {
        this.dataPedido = dataPedido;
    }

    public Date getDataPagamento() {
        return dataPagamento;
    }

    public void setDataPagamento(Date dataPagamento) {
        this.dataPagamento = dataPagamento;
    }

    public Date getDataEntrega() {
        return dataEntrega;
    }

    public void setDataEntrega(Date dataEntrega) {
        this.dataEntrega = dataEntrega;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    @Override
    public String toString() {
        return "Pedido{" +
                "id=" + id +
                ", cliente=" + cliente.getNome() +
                ", itens=" + itens +
                ", dataPedido=" + dataPedido +
                ", dataPagamento=" + dataPagamento +
                ", dataEntrega=" + dataEntrega +
                ", descricao='" + descricao + '\'' +
                '}';
    }
}
