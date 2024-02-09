package br.com.aceleradev5.services;

import br.com.aceleradev5.dtos.*;
import br.com.aceleradev5.entities.Cliente;
import br.com.aceleradev5.entities.Produto;
import br.com.aceleradev5.entities.Venda;
import br.com.aceleradev5.exceptions.cliente.CepNotFoundException;
import br.com.aceleradev5.exceptions.cliente.ClienteNotFoundException;
import br.com.aceleradev5.exceptions.produto.ProdutoNotFoundException;
import br.com.aceleradev5.exceptions.venda.VendaNotFoundException;
import br.com.aceleradev5.repositories.ClienteRepository;
import br.com.aceleradev5.repositories.EstoqueRepository;
import br.com.aceleradev5.repositories.ProdutoRepository;
import br.com.aceleradev5.repositories.VendaRepository;
import jakarta.transaction.Transactional;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class VendaService {

    @Autowired
    private EstoqueRepository estoqueRepository;

    @Autowired
    private VendaRepository vendaRepository;

    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private CepService cepService;

    @Transactional
    public ImportacaoPlanilhaDTO importarPlanilhaVendas(MultipartFile planilhaVendas) throws ProdutoNotFoundException, IOException,
            ClienteNotFoundException {

        List<Venda> vendasASeremInseridas = new ArrayList<>();

        try (InputStream planinhaStream = planilhaVendas.getInputStream();
             Workbook workbookVendas = new XSSFWorkbook(planinhaStream)) {

            Sheet sheetVendas = workbookVendas.getSheetAt(0);

            for (Row rowVenda : sheetVendas) {

                if (rowVenda.getRowNum() == 0) {
                    continue;
                }

                Long codigoProduto = (long) rowVenda.getCell(0).getNumericCellValue();
                String cpfCliente = rowVenda.getCell(1).getStringCellValue();
                Integer quantidadeProdutosComprados = (int) rowVenda.getCell(2).getNumericCellValue();
                LocalDate dataCompra = rowVenda.getCell(3).getLocalDateTimeCellValue().toLocalDate();

                Produto produto = produtoRepository.searchProdutoByCodigo(codigoProduto);

                if (produto == null) {
                    throw new ProdutoNotFoundException(String.format("O codigo do produto %s não foi encontrado na base", codigoProduto));
                }

                Cliente cliente = clienteRepository.searchClienteByCpf(cpfCliente);

                if (cliente == null) {
                    throw new ClienteNotFoundException(String.format("O cpf %s não foi encontrado na base", cpfCliente));
                }

                Venda venda = construirEntidadeVendas(produto, cliente, quantidadeProdutosComprados, dataCompra);

                vendasASeremInseridas.add(venda);

            }

            vendaRepository.saveAll(vendasASeremInseridas);
        }

        return construirRetornoImportacaoVendas("Planilha de vendas importada com sucesso");
    }
    
    public RelatorioVendasPorMesDTO gerarRelatorioVendasPorMes(Integer mes) throws VendaNotFoundException {

        List<Venda> vendasMes = vendaRepository.searchVendasByMes(mes);

        if (vendasMes.isEmpty()) {
            throw new VendaNotFoundException(String.format("Não foram encontradas vendas para o mês: %s", mes));
        }

        Map<Long, List<Venda>> vendasPorProduto = agrupaVendasPorProduto(vendasMes);

        List<ProdutoVendidoMesDTO> listaProdutosVendidos = agrupaProdutosVendidos(vendasPorProduto);

        int quantidadeTotalVendida = calculaQuantidadeTotalVendida(vendasMes);

        return constroiRetornoRelatorioVendasPorMes(mes, listaProdutosVendidos, quantidadeTotalVendida);
    }

    public RelatorioVendasPorClienteDTO gerarRelatorioPorCpfCliente(String cpf) throws ClienteNotFoundException, CepNotFoundException {

        Cliente cliente = clienteRepository.searchClienteByCpf(cpf);

        if (cliente == null) {
            throw new ClienteNotFoundException("O Cliente não foi encontrado");
        }

        EnderecoDTO enderecoDTO = cepService.consultaCepCliente(cliente.getCep());

        List<Venda> vendasRealizadasPeloCliente = vendaRepository.searchVendasByCpf(cpf);

        List<ProdutoVendidoClienteDTO> listaProdutosCompradosCliente = construirProdutoVendidoClienteDTO(vendasRealizadasPeloCliente);

        return construirRetornoRelatorioVendaClienteDTO(cliente, enderecoDTO, listaProdutosCompradosCliente);
    }

    private RelatorioVendasPorClienteDTO construirRetornoRelatorioVendaClienteDTO(Cliente cliente, EnderecoDTO enderecoDTO, List<ProdutoVendidoClienteDTO> listaProdutosCompradosUsuario) {
        RelatorioVendasPorClienteDTO relatorioVendasPorCliente = new RelatorioVendasPorClienteDTO();
        relatorioVendasPorCliente.setNome(cliente.getNome());
        relatorioVendasPorCliente.setCpf(cliente.getCpf());
        relatorioVendasPorCliente.setDataNascimento(cliente.getDataNascimento());
        relatorioVendasPorCliente.setEndereco(enderecoDTO);
        relatorioVendasPorCliente.setListaProdutosCompradosUsuario(listaProdutosCompradosUsuario);
        return relatorioVendasPorCliente;
    }

    private List<ProdutoVendidoClienteDTO> construirProdutoVendidoClienteDTO(List<Venda> vendasRealizadasPeloCliente) {
        List<ProdutoVendidoClienteDTO> listaProdutosCompradosUsuario = new ArrayList<>();

        for (Venda venda : vendasRealizadasPeloCliente) {
            ProdutoVendidoClienteDTO produtoVendidoUsuario = new ProdutoVendidoClienteDTO();
            produtoVendidoUsuario.setCodigoProduto(venda.getProduto().getCodigo());
            produtoVendidoUsuario.setNome(venda.getProduto().getNome());
            produtoVendidoUsuario.setQuantidadeProdutoComprado(venda.getQtdProdutosComprados());
            produtoVendidoUsuario.setDataCompra(venda.getDataDaVenda());

            listaProdutosCompradosUsuario.add(produtoVendidoUsuario);
        }
        return listaProdutosCompradosUsuario;
    }

    private RelatorioVendasPorMesDTO constroiRetornoRelatorioVendasPorMes(Integer mes, List<ProdutoVendidoMesDTO> listaProdutosVendidos,
                                                                          int quantidadeTotalVendida) {
        RelatorioVendasPorMesDTO relatorioVendasPorMes = new RelatorioVendasPorMesDTO();
        relatorioVendasPorMes.setListaProdutosVendidos(listaProdutosVendidos);
        relatorioVendasPorMes.setQuantidadeTotalVendida(quantidadeTotalVendida);
        relatorioVendasPorMes.setStatusVenda(verificaStatusVendas(quantidadeTotalVendida, mes));
        return relatorioVendasPorMes;
    }

    private Map<Long, List<Venda>> agrupaVendasPorProduto(List<Venda> vendasMes) {
        Map<Long, List<Venda>> vendasPorProduto = new HashMap<>();
        for (Venda vendaMes : vendasMes) {
            Long codigoProduto = vendaMes.getProduto().getCodigo();

            if (!vendasPorProduto.containsKey(codigoProduto)) {
                vendasPorProduto.put(codigoProduto, new ArrayList<>());
            }
            vendasPorProduto.get(codigoProduto).add(vendaMes);
        }
        return vendasPorProduto;
    }

    private List<ProdutoVendidoMesDTO> agrupaProdutosVendidos(Map<Long, List<Venda>> vendasPorProduto) {
        List<ProdutoVendidoMesDTO> listaProdutosVendidos = new ArrayList<>();
        for (Long produto : vendasPorProduto.keySet()) {
            List<Venda> vendasDoProduto = vendasPorProduto.get(produto);
            List<DetalheVendaDTO> detalhesVendas = new ArrayList<>();

            for (Venda venda : vendasDoProduto) {
                DetalheVendaDTO detalheVenda = new DetalheVendaDTO();
                detalheVenda.setCpf(venda.getCliente().getCpf());
                detalheVenda.setNome(venda.getCliente().getNome());
                detalheVenda.setQuantidadeComprada(venda.getQtdProdutosComprados());
                detalheVenda.setDataCompra(venda.getDataDaVenda());

                detalhesVendas.add(detalheVenda);
            }

            ProdutoVendidoMesDTO produtoVendido = new ProdutoVendidoMesDTO();
            produtoVendido.setCodigoProduto(produto);
            produtoVendido.setListaDetalheVenda(detalhesVendas);
            listaProdutosVendidos.add(produtoVendido);
        }
        return listaProdutosVendidos;
    }

    private int calculaQuantidadeTotalVendida(List<Venda> vendasMes) {
        int quantidadeTotalVendida = 0;
        for (Venda venda : vendasMes) {
            quantidadeTotalVendida = quantidadeTotalVendida + venda.getQtdProdutosComprados();
        }
        return quantidadeTotalVendida;
    }

    private String verificaStatusVendas(Integer quantidadeTotalDeProdutosVendidos, Integer mes) {

        Integer quantidadeTotalProdutosDisponiveis = estoqueRepository.calculaTotalDeProdutosDisponiveisNoEstoqueParaMes(mes);

        if (quantidadeTotalDeProdutosVendidos > quantidadeTotalProdutosDisponiveis) {
            return "QTD_DIVERGENTE";
        } else if (quantidadeTotalDeProdutosVendidos < (quantidadeTotalProdutosDisponiveis * 0.25)) {
            return "BAIXA_DEMANDA";
        } else {
            return "OK";
        }
    }


    private Venda construirEntidadeVendas(Produto produto, Cliente cliente, Integer quantidadeProdutosComprados,
                                          LocalDate dataCompra) {
        Venda venda = new Venda();
        venda.setProduto(produto);
        venda.setCliente(cliente);
        venda.setQtdProdutosComprados(quantidadeProdutosComprados);
        venda.setDataDaVenda(dataCompra);
        return venda;
    }

    private ImportacaoPlanilhaDTO construirRetornoImportacaoVendas(String resultadoImportacao) {
        ImportacaoPlanilhaDTO importacaoPlanilhaDTO = new ImportacaoPlanilhaDTO();
        importacaoPlanilhaDTO.setMensagemProcessamento(resultadoImportacao);
        return importacaoPlanilhaDTO;
    }
}
