package graphics_comps;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import java.util.Set;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableSet;
import javafx.collections.SetChangeListener;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import model.Cliente;
import model.Conta;
import model.ContaCorrente;
import model.ContaPoupança;
import model.PlanosDeAuxilio;
import util.ContaCell;
import util.PseudoDB;

public class paginaClienteController implements Initializable {

    Cliente atualCliente = App.getCurrentClient();

    Set<Conta> contas = atualCliente.getContas();
    Set<PlanosDeAuxilio> planos = atualCliente.getPlanos();

    @FXML
    private Label dadosNome;

    @FXML
    private Label dadosCPF;

    @FXML
    private Label dadosData;

    @FXML
    private Label nContas;

    @FXML
    private Label nAuxilios;

    @FXML
    private Label dadosSaldoTotal;

    @FXML
    private TextField novaSenha;

    @FXML
    private TextField novoNome;

    @FXML
    private DatePicker novaData;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        inicializaContas();
        inicializaDados();
        validaNome();

    }

    @FXML
    private void trocarSenha() throws IOException {
        if (novaSenha.getText().length() < 8) {
            new Alert(AlertType.ERROR) {
                {
                    setTitle("Erro");
                    setHeaderText("Senha inválida");
                    setContentText("A senha deve ter no mínimo 8 caracteres");
                    showAndWait();
                }
            };

        } else {
            PseudoDB.trocarSenha(atualCliente, novaSenha.getText());
        }

    }

    @FXML
    private void trocarNome() throws IOException {
        atualCliente.setNome(novoNome.getText());
        dadosNome.setText(atualCliente.getNome());
    }

    @FXML
    private void trocarData() throws IOException {
        if (Period.between(novaData.getValue(), LocalDate.now()).getYears() < 18) {
            new Alert(AlertType.ERROR) {
                {
                    setTitle("Erro");
                    setHeaderText("Idade inválida");
                    setContentText("O usuário deve ter no mínimo 18 anos para se cadastrar");
                    showAndWait();
                }
            };
        } else {
            atualCliente.setDataNascimento(novaData.getValue());
            dadosData.setText(atualCliente.getDataNascimento().format(DateTimeFormatter.ofPattern("dd/MM/yy")));
        }

    }

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

    }

    public void inicializaDados() { // colocar observador
        dadosNome.setText(atualCliente.getNome());

        dadosCPF.setText(String.format("%s.%s.%s-%s",
                atualCliente.getCpf().substring(0, 3),
                atualCliente.getCpf().substring(3, 6),
                atualCliente.getCpf().substring(6, 9),
                atualCliente.getCpf().substring(9, 11)));

        dadosData.setText(atualCliente.getDataNascimento().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));

        nContas.setText(Integer.toString(listaContas.getItems().size()));

        nAuxilios.setText(Integer.toString(planos.size()));

        dadosSaldoTotal.setText(calcularSaldoTotal(listaContas.getItems()));


    }

    private String calcularSaldoTotal(ObservableList<Conta> observableItens) {
        double total = 0;
        for (Conta a : observableItens) {
            total += a.getSaldo();
        }
        return Double.toString(total);
    }

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

    public void inicializaContas() {
        listaContas.setCellFactory((listView) -> {
            return new ContaCell();
        });

        // Crie a ObservableList a partir do conjunto contas
        ObservableList<Conta> observableItens = FXCollections.observableArrayList(contas);
        listaContas.setItems(observableItens);

        ObservableSet<Conta> observableContas = FXCollections.observableSet(contas);
        observableContas.addListener((SetChangeListener<Conta>) change -> {
            if (change.wasAdded()) {
                // Adicione as contas diretamente na ObservableList
                observableItens.add(change.getElementAdded());
            }
        });

        // Adicione as contas fictícias (elas agora aparecerão na ListView)
        observableItens.add(new ContaCorrente("Luis"));
        observableItens.add(new ContaCorrente("Oiii"));

        listaContas.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
        if (newValue != null) {
            Conta selecionada = newValue;
            // Faça algo com a conta selecionada, como chamar o método depositar()
            }
        });

        tipoDeConta.selectedProperty().addListener((observable, oldValue, newValue) -> {
            if (tipoDeConta.isSelected()) {
                textoTipo.setText("Poupança");
            } else {
                textoTipo.setText("Corrente");
            }
        });
    }

    @FXML
    private void criarConta() throws IOException {
        if (tipoDeConta.isSelected()) {
            atualCliente
                    .criarConta(new ContaPoupança(dadosTitular.getText(), Double.parseDouble(saldoInicial.getText())));
            listaContas.getItems()
                    .add(new ContaPoupança(dadosTitular.getText(), Double.parseDouble(saldoInicial.getText())));

        } else {
            atualCliente
                    .criarConta(new ContaCorrente(dadosTitular.getText(), Double.parseDouble(saldoInicial.getText())));
            listaContas.getItems()
                    .add(new ContaCorrente(dadosTitular.getText(), Double.parseDouble(saldoInicial.getText())));
        }

        dadosTitular.clear();
        saldoInicial.clear();

    }

    @FXML
    private void depositar() throws IOException {
        Conta selecionada = listaContas.getSelectionModel().getSelectedItem();
        if (selecionada != null) {
            selecionada.depositar(Double.parseDouble(depositado.getText()));
        }

    }

    @FXML
    private void voltar() throws IOException {
        App.setRoot("login");
    }

}