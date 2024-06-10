package br.com.lojademovel.pedido;

import br.com.lojademovel.movel.Movel;

import java.io.Serial;
import java.io.Serializable;

public class ItemPedido implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private Movel movel;
    private int quantidade;

    public Movel getMovel() {
        return movel;
    }

    public void setMovel(Movel movel) {
        this.movel = movel;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    @Override
    public String toString() {
        return movel.getNome() + " - Quantidade: " + quantidade;
    }
}
