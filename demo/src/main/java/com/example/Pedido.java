package com.example;

import java.util.ArrayList;
import java.util.List;

public class Pedido {

    private List<ItemPedido> itens;
    private DescontoService descontoService;
    public int chamadas;

    public Pedido(DescontoService descontoService) {
        this.itens = new ArrayList<>();
        this.descontoService = descontoService;
        this.chamadas = 0;
    }

    public Pedido(List<ItemPedido> itens, DescontoService descontoService) {
        this.itens = itens;
        this.descontoService = descontoService;
        this.chamadas = 0;
    }

    public double calcularValorTotal() {
        double valorTotal = 0.0;

        for (ItemPedido item : itens) {
            valorTotal += item.getSubtotal();
        }

        double result = valorTotal - descontoService.calcularDesconto(valorTotal);
        this.chamadas += 1;

        if(result < 0){
            return 0.0;
        }
        
        return result;
    }

    public double calcularValorTotalPorItem() {
        double valorTotal = 0.0;

        for (ItemPedido item : itens) {
            this.chamadas += 1;
            valorTotal += item.getSubtotal() - descontoService.calcularDesconto(item.getSubtotal());
        }

        if(valorTotal < 0){
            return 0.0;
        }
        
        return valorTotal;
    }
}