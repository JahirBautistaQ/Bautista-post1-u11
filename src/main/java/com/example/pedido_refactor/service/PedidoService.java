package com.example.pedido_refactor.service;

import com.example.pedido_refactor.model.DatosCliente;
import com.example.pedido_refactor.model.Pedido;
import com.example.pedido_refactor.model.Producto;
import com.example.pedido_refactor.repository.PedidoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PedidoService {

    @Autowired
    private PedidoRepository repo;

    public String procesarPedido(Long clienteId,
                                 DatosCliente datosCliente,
                                 List<Long> productosIds,
                                 List<Integer> cantidades,
                                 String metodoPago,
                                 boolean esUrgente,
                                 String codigoDescuento) {

        if (!clienteValido(clienteId, datosCliente)) {
            return "ERROR_CLIENTE";
        }

        double total = calcularTotal(productosIds, cantidades);

        if (total == -1) {
            return "ERROR_PRODUCTO";
        }

        double totalConDescuento =
                aplicarDescuento(total, codigoDescuento);

        notificarCliente(datosCliente, esUrgente);

        return persistirPedido(
                clienteId,
                datosCliente,
                totalConDescuento
        );
    }

    // =========================
    // EXTRACT METHOD
    // =========================

    private boolean clienteValido(Long clienteId,
                                  DatosCliente datosCliente) {

        return clienteId != null
                && datosCliente != null
                && datosCliente.getNombre() != null
                && !datosCliente.getNombre().isBlank()
                && datosCliente.getEmail() != null
                && datosCliente.getEmail().contains("@");
    }

    private double calcularTotal(List<Long> productosIds,
                                 List<Integer> cantidades) {

        double total = 0;

        for (int i = 0; i < productosIds.size(); i++) {

            Producto producto =
                    repo.findProductoById(productosIds.get(i));

            if (producto == null) {
                return -1;
            }

            total += producto.getPrecio() * cantidades.get(i);
        }

        return total;
    }

    private double aplicarDescuento(double total,
                                    String codigoDescuento) {

        if (codigoDescuento == null) {
            return total;
        }

        if (codigoDescuento.equals("VIP10")) {
            return total * 0.90;
        }

        if (codigoDescuento.equals("NEW20")) {
            return total * 0.80;
        }

        return total;
    }

    private void notificarCliente(DatosCliente datosCliente,
                                  boolean esUrgente) {

        System.out.println(
                "Enviando email a: "
                        + datosCliente.getEmail()
        );

        System.out.println(
                "Pedido urgente: "
                        + esUrgente
        );
    }

    private String persistirPedido(Long clienteId,
                                   DatosCliente datosCliente,
                                   double total) {

        Pedido pedido = new Pedido(
                clienteId,
                datosCliente.getNombre(),
                total
        );

        return "OK_" + repo.save(pedido).getId();
    }
}