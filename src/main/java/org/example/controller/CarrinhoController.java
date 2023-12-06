package org.example.controller;

import org.example.models.Carrinho;
import org.example.models.Pedido;
import org.example.models.Produto;
import org.example.models.Usuario;
import org.example.repository.PedidoRepository;
import org.example.repository.ProdutoRepository;
import org.example.repository.UsuarioRepository;

import java.text.DecimalFormat;
import java.util.*;

public class CarrinhoController {
    private List<Carrinho> listaCarrinho ;
    private double totalCompra;
    private ProdutoRepository produtoRepository;
    private PedidoRepository pedidoRepository;

    private UsuarioRepository usuarioRepository;

    public CarrinhoController() {
        this.listaCarrinho = new ArrayList<>();
        this.totalCompra = 0.0;
        this.produtoRepository = new ProdutoRepository();
        this.pedidoRepository = new PedidoRepository();
    }

    public void adicionarItem(int idProduto, int quantidade) {
        Optional<Produto> optionalProduto = produtoRepository.buscarProdutoPorId(idProduto);
        Produto produto = optionalProduto.get();
        boolean produtoJaNoCarrinho = listaCarrinho.stream().anyMatch(item -> item.getProduto().getIdProduto() == idProduto);

        if (!produtoJaNoCarrinho) {
            Carrinho item = new Carrinho();
            item.setProduto(produto);
            item.setQuantidade(quantidade);
            item.setPreco(produto.getPreco() * quantidade);

            listaCarrinho.add(item);

            totalCompra += item.getPreco();
        }
        }




    public double getTotalCompra() {
        return totalCompra;
    }

    public String gerarRecibo() {
        StringBuilder recibo = new StringBuilder();
        recibo.append("Recibo da Compra:\n");
        recibo.append("--------------------------\n");

        for (Carrinho item : listaCarrinho) {
            recibo.append(item.getProduto().getNomeProduto())
                    .append(" - Quantidade: ").append(item.getQuantidade())
                    .append(" - Preço: ").append(item.getPreco()).append("\n");
        }

        DecimalFormat df = new DecimalFormat("#.##");
        recibo.append("--------------------------\n");
        recibo.append("Total da Compra: ").append(df.format(totalCompra));

        return recibo.toString();
    }

    public void removerItem(int index) {
        if (index >= 0 && index < listaCarrinho.size()) {
            Carrinho itemRemovido = listaCarrinho.remove(index);
            totalCompra -= itemRemovido.getPreco();
        }
    }

    public List<Carrinho> getCarrinho() {
        return listaCarrinho;
    }

    public boolean finalizarCompra(String nomeUsuario) {
        boolean pagamentoBemSucedido = simularPagamento();


        if (pagamentoBemSucedido) {
            if (!listaCarrinho.isEmpty()) {
                Pedido novoPedido = new Pedido();
                novoPedido.setTotalCompra(this.totalCompra);

                //Usuario usuario = usuarioRepository.buscarUsuarioPorNome(nomeUsuario);

                List<Produto> produtos = new ArrayList<>();
                for (Carrinho itemCarrinho : listaCarrinho) {
                    produtos.add(itemCarrinho.getProduto());
                }

                novoPedido.setProdutos(produtos);

                pedidoRepository.atualizarPedido(novoPedido);

                String recibo = gerarRecibo();
                System.out.println(recibo);
                return true;
            } else {
                System.out.println("Falhou: Carrinho vazio");
            }
        } else {
            System.out.println("Pagamento falhou");
        }
        return false;
    }

    private boolean simularPagamento() {
        Random random = new Random();
        return random.nextDouble() < 0.9; // 90% de chance de sucesso
    }
}

