package org.example.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Carrinho {

    private int idItem;
    private int quantidade;
    private double preco;
    private double total;
    private Pedido pedido;
    private Produto produto;

    public double setTotal(double preco){
        return total += preco;
    }
}
