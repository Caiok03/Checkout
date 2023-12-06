package org.example.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "Pedido")
public class Pedido {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "IDPedido")
        private int idPedido;

        @Column(name = "TotalCompra")
        private double totalCompra;

        @ManyToOne
        @JoinColumn(name = "IDUsuario")
        private Usuario usuario;

        @OneToMany(cascade = CascadeType.ALL)
        private List<Produto> produtos;


}
