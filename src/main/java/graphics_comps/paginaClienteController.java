package graphics_comps;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import java.util.Set;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.collections.ObservableSet;
import javafx.collections.SetChangeListener;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.VBox;
import javafx.util.Duration;
import model.Cliente;
import model.Conta;
import model.ContaCorrente;
import model.ContaPoupança;
import util.ContaCell;
import util.PseudoDB;

public class paginaClienteController implements Initializable {

    private Cliente atualCliente = App.getCurrentClient();
    private Conta atualConta;

    private Set<Conta> contas = atualCliente.getContas();

    // Componentes da interface gráfica
    @FXML
    private Label dadosNome;
    @FXML
    private Label dadosCPF;
    @FXML
    private Label dadosData;
    @FXML
    private Label nContas;
    @FXML
    private Label dadosSaldoTotal;
    @FXML
    private TextField novaSenha;
    @FXML
    private TextField novoNome;
    @FXML
    private DatePicker novaData;
    @FXML
    private ListView<Conta> listaContas;
    @FXML
    private TextField dadosTitular;
    @FXML
    private TextField saldoInicial;
    @FXML
    private ToggleButton tipoDeConta;
    @FXML
    private Label textoTipo;
    @FXML
    private TextField depositado;
    @FXML
    private TextField sacado;
    @FXML
    private Label contaSelecionadaDep;
    @FXML
    private Label contaSelecionadaSaq;
    @FXML
    private Label contaSelecionadaTrans;
    @FXML
    private TextField tranfereAgencia;
    @FXML
    private TextField tranfereNumero;
    @FXML
    private TextField transfereSaldo;
    @FXML
    private VBox seguroVida;
    @FXML
    private VBox transporte;
    @FXML
    private VBox alimentacao;
    @FXML
    private VBox refeicao;
    @FXML
    private VBox aposentadoria;
    @FXML
    private VBox familia;
    @FXML
    private Label sitSeguro;
    @FXML
    private Label sitTransporte;
    @FXML
    private Label sitAlimentacao;
    @FXML
    private Label sitRefeicao;
    @FXML
    private Label sitAposentadoria;
    @FXML
    private Label sitFamilia;
    @FXML
    private ToggleButton tgbSeguro;
    @FXML
    private ToggleButton tgbTrans;
    @FXML
    private ToggleButton tgbAliment;
    @FXML
    private ToggleButton tgbRefeicao;
    @FXML
    private ToggleButton tgbAposentadoria;
    @FXML
    private ToggleButton tgbFamilia;
    @FXML
    private ProgressIndicator progressIndicator;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        inicializaContas();
        inicializaDados();
        validaNome();
        tooltips();
    }

    // Métodos de ação

    // Troca a senha do cliente
    @FXML
    private void trocarSenha() throws IOException {
        if (novaSenha.getText().length() < 8) {
            exibirAlerta("Erro", "Senha inválida", "A senha deve ter no mínimo 8 caracteres");
        } else {
            PseudoDB.trocarSenha(atualCliente, novaSenha.getText());
        }
    }

    // Troca o nome do cliente
    @FXML
    private void trocarNome() throws IOException {
        atualCliente.setNome(novoNome.getText());
        dadosNome.setText(atualCliente.getNome());
    }

    // Troca a data de nascimento do cliente
    @FXML
    private void trocarData() throws IOException {
        if (Period.between(novaData.getValue(), LocalDate.now()).getYears() < 18) {
            exibirAlerta("Erro", "Idade inválida",
                    "O usuário deve ter no mínimo 18 anos para se cadastrar");
        } else {
            atualCliente.setDataNascimento(novaData.getValue());
            dadosData.setText(atualCliente.getDataNascimento().format(DateTimeFormatter.ofPattern("dd/MM/yy")));
        }
    }

    // Cria uma nova conta para o cliente
    @FXML
    private void criarConta() throws IOException {
        if (tipoDeConta.isSelected()) {
            atualCliente.criarConta(new ContaPoupança(dadosTitular.getText(),
                    Double.parseDouble(saldoInicial.getText())));
            listaContas.getItems().add(new ContaPoupança(dadosTitular.getText(),
                    Double.parseDouble(saldoInicial.getText())));
        } else {
            atualCliente.criarConta(new ContaCorrente(dadosTitular.getText(),
                    Double.parseDouble(saldoInicial.getText())));
            listaContas.getItems().add(new ContaCorrente(dadosTitular.getText(),
                    Double.parseDouble(saldoInicial.getText())));
        }

        dadosTitular.clear();
        saldoInicial.clear();
    }

    // Deposita dinheiro na conta selecionada
    @FXML
    private void depositar() throws IOException {
        if (atualConta != null) {
            atualConta.depositar(Double.parseDouble(depositado.getText()));
        }

        dadosSaldoTotal.setText(calcularSaldoTotal(listaContas.getItems()));
    }

    // Saca dinheiro da conta selecionada
    @FXML
    private void sacar() throws IOException {
        if (atualConta != null) {
            atualConta.sacar(Double.parseDouble(sacado.getText()));
        }

        dadosSaldoTotal.setText(calcularSaldoTotal(listaContas.getItems()));
    }

    // Transfere dinheiro entre contas
    @FXML
    private void transfere() {
        Conta contaAlvo = existeContaInterna();
        if (atualConta != null) {
            if (contaAlvo != null) {
                atualConta.transfere(contaAlvo, Double.parseDouble(transfereSaldo.getText()));
            } else {
                exibirAlerta("Erro", "Conta não encontrada", "A conta não está registrada internamente");
            }
        }

        dadosSaldoTotal.setText(calcularSaldoTotal(listaContas.getItems()));
    }

    // Sai da conta do cliente
    @FXML
    private void sairDaConta() throws IOException {
        // Configuração do ProgressIndicator
        progressIndicator.setProgress(0.0);
        progressIndicator.setVisible(true);

        // Configuração da animação
        Timeline timeline = new Timeline(
                new KeyFrame(Duration.ZERO, new KeyValue(progressIndicator.progressProperty(), 0)),
                new KeyFrame(Duration.seconds(2), new KeyValue(progressIndicator.progressProperty(), 1)));

        // Configuração do evento a ser executado após a animação
        timeline.setOnFinished(event -> {
            try {
                App.setDimension(300, 350);
                App.setRoot("login");
            } catch (IOException e) {
                e.printStackTrace(); // Lida com exceções de IO
            }
        });

        // Inicia a animação
        timeline.play();
    }

    // Métodos auxiliares

    // Valida o nome para aceitar apenas caracteres alfabéticos e espaços
    public void validaNome() {
        novoNome.textProperty().addListener((observable, oldValue, newValue) -> {
            String nomeValidado = newValue.replaceAll("[^a-zA-Z ]", "");
            novoNome.setText(nomeValidado);
        });

        dadosTitular.textProperty().addListener((observable, oldValue, newValue) -> {
            String nomeValidado = newValue.replaceAll("[^a-zA-Z ]", "");
            dadosTitular.setText(nomeValidado);
        });

        saldoInicial.textProperty().addListener((observable, oldValue, newValue) -> {
            String nomeValidado = newValue.replaceAll("[^\\d]", "");
            saldoInicial.setText(nomeValidado);
        });

        depositado.textProperty().addListener((observable, oldValue, newValue) -> {
            String nomeValidado = newValue.replaceAll("[^\\d]", "");
            depositado.setText(nomeValidado);
        });

        sacado.textProperty().addListener((observable, oldValue, newValue) -> {
            String nomeValidado = newValue.replaceAll("[^\\d]", "");
            sacado.setText(nomeValidado);
        });

        transfereSaldo.textProperty().addListener((observable, oldValue, newValue) -> {
            String nomeValidado = newValue.replaceAll("[^\\d]", "");
            transfereSaldo.setText(nomeValidado);
        });

        tranfereAgencia.textProperty().addListener((observable, oldValue, newValue) -> {
            String nomeValidado = newValue.replaceAll("[^\\d]", "");
            tranfereAgencia.setText(nomeValidado);
        });

        tranfereNumero.textProperty().addListener((observable, oldValue, newValue) -> {
            String nomeValidado = newValue.replaceAll("[^\\d]", "");
            tranfereNumero.setText(nomeValidado);
        });

    }

    // Inicializa os dados do cliente na interface gráfica
    private void inicializaDados() {
        dadosNome.setText(atualCliente.getNome());

        dadosCPF.setText(String.format("%s.%s.%s-%s",
                atualCliente.getCpf().substring(0, 3),
                atualCliente.getCpf().substring(3, 6),
                atualCliente.getCpf().substring(6, 9),
                atualCliente.getCpf().substring(9, 11)));

        dadosData.setText(atualCliente.getDataNascimento().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));

        nContas.setText(Integer.toString(listaContas.getItems().size()));

        dadosSaldoTotal.setText(calcularSaldoTotal(listaContas.getItems()));
    }

    // Calcula o saldo total das contas
    private String calcularSaldoTotal(ObservableList<Conta> observableItens) {
        double total = 0;
        for (Conta a : observableItens) {
            total += a.getSaldo();
        }
        return Double.toString(total);
    }

    // Inicializa as contas do cliente na interface gráfica
    private void inicializaContas() {
        listaContas.setCellFactory((listView) -> {
            return new ContaCell();
        });

        ObservableList<Conta> observableItens = FXCollections.observableArrayList(contas);
        listaContas.setItems(observableItens);

        ObservableSet<Conta> observableContas = FXCollections.observableSet(contas);
        observableContas.addListener((SetChangeListener<Conta>) change -> {
            if (change.wasAdded()) {
                observableItens.add(change.getElementAdded());
            }
        });

        listaContas.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                atualConta = newValue;
                contaSelecionadaDep.setText(newValue.getTitular() + " Nº: " + newValue.getNumero());
                contaSelecionadaSaq.setText(newValue.getTitular() + " Nº: " + newValue.getNumero());
                contaSelecionadaTrans.setText(newValue.getTitular() + " Nº: " + newValue.getNumero());
            }
        });

        tipoDeConta.selectedProperty().addListener((observable, oldValue, newValue) -> {
            if (tipoDeConta.isSelected()) {
                textoTipo.setText("Poupança");
            } else {
                textoTipo.setText("Corrente");
            }
        });

        listaContas.getItems().addListener((ListChangeListener.Change<? extends Conta> c) -> {
            nContas.setText(Integer.toString(listaContas.getItems().size()));
            dadosSaldoTotal.setText(calcularSaldoTotal(listaContas.getItems()));
        });
    }

    // Verifica se a conta interna existe
    private Conta existeContaInterna() {
        for (Conta a : listaContas.getItems()) {
            if (String.valueOf(a.getNumero()).equals(tranfereNumero.getText())
                    && String.valueOf(a.getAgencia()).equals(tranfereAgencia.getText())) {
                return a;
            }
        }

        return null;
    }

    // Configura os tooltips para os elementos da interface gráfica
    private void tooltips() {
        Tooltip detalhesSeguro = new Tooltip(
                "Este auxílio oferece planos de saúde e remédios de no valor até 1000 reais");
        Tooltip.install(seguroVida, detalhesSeguro);

        Tooltip detalhesTransporte = new Tooltip(
                "Este auxílio oferece cartões de transporte ou passagens, de até 500 reais");
        Tooltip.install(transporte, detalhesTransporte);

        Tooltip detalhesAlimentacao = new Tooltip("Este auxílio oferece vale alimentação de até 700 reais");
        Tooltip.install(alimentacao, detalhesAlimentacao);

        Tooltip detalhesRefeicao = new Tooltip("Este auxílio oferece vale refeição de até 300 reais");
        Tooltip.install(refeicao, detalhesRefeicao);

        Tooltip detalhesAposentadoria = new Tooltip("Este auxílio oferece até 1300 reais para idosos ");
        Tooltip.install(aposentadoria, detalhesAposentadoria);

        Tooltip detalhesFamilia = new Tooltip("Este plano oferece auxílio família de 600 reais");
        Tooltip.install(familia, detalhesFamilia);

        tgbSeguro.selectedProperty().addListener((observable, oldValue, newValue) -> {
            if (tgbSeguro.isSelected()) {
                sitSeguro.setText("Cadastrado");
            } else {
                sitSeguro.setText("Não cadastrado");
            }
        });

        tgbTrans.selectedProperty().addListener((observable, oldValue, newValue) -> {
            if (tgbTrans.isSelected()) {
                sitTransporte.setText("Cadastrado");
            } else {
                sitTransporte.setText("Não cadastrado");
            }
        });

        tgbAliment.selectedProperty().addListener((observable, oldValue, newValue) -> {
            if (tgbAliment.isSelected()) {
                sitAlimentacao.setText("Cadastrado");
            } else {
                sitAlimentacao.setText("Não cadastrado");
            }
        });

        tgbRefeicao.selectedProperty().addListener((observable, oldValue, newValue) -> {
            if (tgbRefeicao.isSelected()) {
                sitRefeicao.setText("Cadastrado");
            } else {
                sitRefeicao.setText("Não cadastrado");
            }
        });

        tgbAposentadoria.selectedProperty().addListener((observable, oldValue, newValue) -> {
            if (tgbAposentadoria.isSelected()) {
                sitAposentadoria.setText("Cadastrado");
            } else {
                sitAposentadoria.setText("Não cadastrado");
            }
        });

        tgbFamilia.selectedProperty().addListener((observable, oldValue, newValue) -> {
            if (tgbFamilia.isSelected()) {
                sitFamilia.setText("Cadastrado");
            } else {
                sitFamilia.setText("Não cadastrado");
            }
        });
    }

    // Exibe um alerta na interface gráfica
    private void exibirAlerta(String title, String header, String content) {
        new Alert(Alert.AlertType.ERROR) {
            {
                setTitle(title);
                setHeaderText(header);
                setContentText(content);
                showAndWait();
            }
        };
    }
}
