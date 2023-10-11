import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.*;

@ExtendWith(MockitoExtension.class)
public class PedidoTest{
    
    // lista de exercícios:
    // https://gist.github.com/rodrigoprestesmachado/bec3f61f7b8332a3b70d06393ab3c26a

    @Mock
    DescontoService descontoService;

    // 1
    @Test
    public void verificarDesconto() {
        when(descontoService.calcularDesconto(100.0)).thenReturn(10.0);

        List<ItemPedido> itens = new ArrayList<>();

        itens.add(new ItemPedido("teste", 100.0));

        Pedido pedido = new Pedido(itens, descontoService);

        double valorTotal = pedido.calcularValorTotal();

        Assertions.assertEquals(90.0, valorTotal);
    }

    // 2
    @Test
    public void verificarDescontoSemValor() {
        when(descontoService.calcularDesconto(100.0)).thenReturn(0.0);

        List<ItemPedido> itens = new ArrayList<>();

        itens.add(new ItemPedido("teste", 100.0));

        Pedido pedido = new Pedido(itens, descontoService);

        double valorTotal = pedido.calcularValorTotal();

        Assertions.assertEquals(100.0, valorTotal);
    }

    // 3
    @Test
    public void descontoMaiorQueTotal() {
        when(descontoService.calcularDesconto(100.0)).thenReturn(120.0);

        List<ItemPedido> itens = new ArrayList<>();

        itens.add(new ItemPedido("teste", 100.0));

        Pedido pedido = new Pedido(itens, descontoService);

        double valorTotal = pedido.calcularValorTotal();

        Assertions.assertEquals(0.0, valorTotal);
    }

    // 4
    @Test
    public void descontoMaiorQueTotalLancaExcecao() {
        when(descontoService.calcularDesconto(100.0)).thenThrow(IllegalArgumentException.class);

        List<ItemPedido> itens = new ArrayList<>();

        itens.add(new ItemPedido("teste", 100.0));

        Pedido pedido = new Pedido(itens, descontoService);

        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            pedido.calcularValorTotal();
        });
    }

    // 5
    @Test
    public void listaVaziaRetornaZero() {
        when(descontoService.calcularDesconto(0.0)).thenReturn(0.0);

        Pedido pedido = new Pedido(descontoService);

        double valorTotal = pedido.calcularValorTotal();

        Assertions.assertEquals(0.0, valorTotal);
    } 

    // 6
    @Test
    public void descontosDiferentes() {
        when(descontoService.calcularDesconto(100.0)).thenReturn(10.0); // 10% de desconto
        when(descontoService.calcularDesconto(10.0)).thenReturn(0.0); // 0 de desconto

        List<ItemPedido> itens = new ArrayList<>();

        itens.add(new ItemPedido("item1", 100.0));
        itens.add(new ItemPedido("item2", 10.0));

        Pedido pedido = new Pedido(itens, descontoService);

        double valorTotal = pedido.calcularValorTotalPorItem();

        Assertions.assertEquals(100.0, valorTotal);
    }

    // 7
    @Test
    public void quantidadeDeChamadasNoService() {
        when(descontoService.calcularDesconto(100.0)).thenReturn(10.0); // 10% de desconto

        List<ItemPedido> itens = new ArrayList<>();

        itens.add(new ItemPedido("item1", 100.0));

        Pedido pedido = new Pedido(itens, descontoService);

        pedido.calcularValorTotal();

        Assertions.assertEquals(pedido.chamadas, 1);
    }

}
