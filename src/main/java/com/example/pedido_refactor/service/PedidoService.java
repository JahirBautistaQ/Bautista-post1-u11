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

        // Validación cliente
        if (clienteId == null
                || datosCliente == null
                || datosCliente.getNombre() == null
                || datosCliente.getNombre().isBlank()
                || datosCliente.getEmail() == null
                || !datosCliente.getEmail().contains("@")) {

            return "ERROR_CLIENTE";
        }

        // Calculo total
        double total = 0;

        for (int i = 0; i < productosIds.size(); i++) {

            Producto p = repo.findProductoById(productosIds.get(i));

            if (p == null) {
                return "ERROR_PRODUCTO";
            }

            total += p.getPrecio() * cantidades.get(i);
        }

        // Descuentos
        if (codigoDescuento != null
                && codigoDescuento.equals("VIP10")) {

            total = total * 0.90;

        } else if (codigoDescuento != null
                && codigoDescuento.equals("NEW20")) {

            total = total * 0.80;
        }

        // Notificación
        System.out.println("Enviando email a: " + datosCliente.getEmail());
        System.out.println("Pedido urgente: " + esUrgente);

        Pedido pedido = new Pedido(
                clienteId,
                datosCliente.getNombre(),
                total
        );

        return "OK_" + repo.save(pedido).getId();
    }
}