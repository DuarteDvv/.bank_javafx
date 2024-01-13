package graphics_comps;

import java.net.URL;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.ResourceBundle;
import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import model.Cliente;
import util.PseudoDB;

public class CadastroController implements Initializable {

    @FXML
    private void voltar() throws IOException {
        App.setRoot("login");
    }

    @FXML
    private TextField entradaCpf;

    @FXML
    private TextField entradaNome;

    @FXML
    private PasswordField entradaSenha;

    @FXML
    private PasswordField entradaVerificaSenha;

    @FXML
    private Button botaoCadastrar;

    @FXML
    private DatePicker entradaData;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        validaCpf();
        validaNome();
    }

    public void validaCpf() {
        entradaCpf.textProperty().addListener((observable, oldValue, newValue) -> {
            String cpfValidado = newValue.replaceAll("[^\\d]", "");
            if (cpfValidado.length() > 11) {
                cpfValidado = cpfValidado.substring(0, 11);
            }

            if (cpfValidado.length() < 11 || entradaNome.getText().length() == 0) {
                botaoCadastrar.setDisable(true);

            } else {
                botaoCadastrar.setDisable(false);

            }

            entradaCpf.setText(cpfValidado);
        });
    }

    public void validaNome() {
        entradaNome.textProperty().addListener((observable, oldValue, newValue) -> {
            String nomeValidado = newValue.replaceAll("[^a-zA-Z ]", "");
            entradaNome.setText(nomeValidado);
        });

    }

    @FXML
    private void cadastrar() throws IOException {
        String cpfAtual = entradaCpf.getText();
        String nomeAtual = entradaNome.getText();
        String senhaAtual = entradaSenha.getText();
        LocalDate dataNascimentoAtual = entradaData.getValue().atStartOfDay(ZoneId.systemDefault()).toLocalDate();

        if (PseudoDB.buscarClientePorCpf(cpfAtual) != null) {
            new Alert(AlertType.ERROR) {
                {
                    setTitle("Erro");
                    setHeaderText("Clienta Já cadastrado");
                    setContentText("O CPF fornecido já esta cadastrado");
                    showAndWait();
                }
            };

        } else {

            if (senhaAtual.length() < 8) {
                new Alert(AlertType.ERROR) {
                    {
                        setTitle("Erro");
                        setHeaderText("Senha inválida");
                        setContentText("A senha deve ter no mínimo 8 caracteres");
                        showAndWait();
                    }
                };

            } else if (!senhaAtual.equals(entradaVerificaSenha.getText())) {
                new Alert(AlertType.ERROR) {
                    {
                        setTitle("Erro");
                        setHeaderText("Senha errada");
                        setContentText("As senhas estao divergindo");
                        showAndWait();

                    }
                };
            }

            else if (Period.between(dataNascimentoAtual, LocalDate.now()).getYears() < 18) {
                new Alert(AlertType.ERROR) {
                    {
                        setTitle("Erro");
                        setHeaderText("Idade inválida");
                        setContentText("O usuário deve ter no mínimo 18 anos para se cadastrar");
                        showAndWait();
                    }
                };
            }

            else {
                PseudoDB.registrarCliente(new Cliente(cpfAtual, nomeAtual, dataNascimentoAtual), senhaAtual);
                App.setRoot("login");
                new Alert(AlertType.INFORMATION) {
                    {
                        setTitle("Sucesso");
                        setHeaderText("Cadastrado com sucesso");
                        setContentText("O usuário foi cadastrado com sucesso");
                        showAndWait();
                    }
                };

            }

        }

    }

}
