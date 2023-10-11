package com.example;

public class ItemPedido {
    
    public String name;
    
    public double value;

    public ItemPedido(String name, double value)
    {
        this.name = name;
        this.value = value;
    }

    public double getSubtotal()
    {
        return this.value;
    }
}