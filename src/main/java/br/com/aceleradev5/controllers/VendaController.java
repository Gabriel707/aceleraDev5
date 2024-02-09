package br.com.aceleradev5.controllers;

import br.com.aceleradev5.dtos.ImportacaoPlanilhaDTO;
import br.com.aceleradev5.dtos.RelatorioVendasPorClienteDTO;
import br.com.aceleradev5.dtos.RelatorioVendasPorMesDTO;
import br.com.aceleradev5.exceptions.cliente.CepNotFoundException;
import br.com.aceleradev5.exceptions.cliente.ClienteNotFoundException;
import br.com.aceleradev5.exceptions.produto.ProdutoNotFoundException;
import br.com.aceleradev5.exceptions.venda.VendaNotFoundException;
import br.com.aceleradev5.services.VendaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/vendas")
public class VendaController {

    @Autowired
    private VendaService vendaService;

    @PostMapping("/importacao-planilha")
    public ResponseEntity<?> uploadPlanilhaVendas(@RequestParam("planilhaVendas") MultipartFile planilhaVendas) {
        try {
            ImportacaoPlanilhaDTO importacaoPlanilhaDTO = vendaService.importarPlanilhaVendas(planilhaVendas);
            return ResponseEntity.ok(importacaoPlanilhaDTO);
        } catch (ProdutoNotFoundException | ClienteNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    @GetMapping("/relatorio-venda-mes")
    public ResponseEntity<?> gerarRelatorioVendasPorMes(@RequestParam("mes") Integer mes) {
        try {
            RelatorioVendasPorMesDTO relatorioVendasPorMesDTO = vendaService.gerarRelatorioVendasPorMes(mes);
            return ResponseEntity.ok(relatorioVendasPorMesDTO);
        } catch (VendaNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    @GetMapping("/relatorio-venda-clientes")
    public ResponseEntity<?> gerarRelatorioVendasPorCliente(@RequestParam("cpf") String cpf) {
        try {
            RelatorioVendasPorClienteDTO relatorioVendasPorClienteDTO = vendaService.gerarRelatorioPorCpfCliente(cpf);
            return ResponseEntity.ok(relatorioVendasPorClienteDTO);
        } catch (ClienteNotFoundException | CepNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }
}
