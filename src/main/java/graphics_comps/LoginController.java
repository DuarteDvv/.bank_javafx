package graphics_comps;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;

import model.Cliente;
import util.PseudoDB;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class LoginController implements Initializable {

    @FXML
    private TextField entradaCpf;

    @FXML
    private PasswordField entradaSenha;

    @FXML
    private TextField senhaVisivel;

    @FXML
    private CheckBox checkVisivel;

    @FXML
    private CheckBox checkColaborador;

    @FXML
    private TextField loginID;

    @FXML
    private Button botaoEntrar;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        validaCpf();
        tornarSenhaVisivel();
        ativarColab();
        // apagar
        PseudoDB.registrarCliente(new Cliente("99999999999", "Luis", LocalDate.now()), "99999999");

    }

    public void validaCpf() {
        entradaCpf.textProperty().addListener((observable, oldValue, newValue) -> {
            String cpfValidado = newValue.replaceAll("[^\\d]", "");
            if (cpfValidado.length() > 11) {
                cpfValidado = cpfValidado.substring(0, 11);
            }

            if (cpfValidado.length() < 11) {
                botaoEntrar.setDisable(true);

            } else {
                botaoEntrar.setDisable(false);

            }

            entradaCpf.setText(cpfValidado);
        });
    }

    public void tornarSenhaVisivel() {
        checkVisivel.selectedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                entradaSenha.setVisible(false);
                senhaVisivel.setVisible(true);
                senhaVisivel.setText(entradaSenha.getText());
            } else {
                entradaSenha.setVisible(true);
                senhaVisivel.setVisible(false);
                entradaSenha.setText(senhaVisivel.getText());
            }
        });
    }

    public void ativarColab() {
        checkColaborador.selectedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                loginID.setVisible(true);
            } else {
                loginID.setVisible(false);
            }
        });
    }

    @FXML
    private void entrar() throws IOException {
        String cpf = entradaCpf.getText();
        String senha = entradaSenha.getText();

        if (!checkColaborador.isSelected()) {
            Cliente atualClient = PseudoDB.buscarClientePorCpf(cpf);
            if (atualClient != null) {
                if (!PseudoDB.verificarSenha(atualClient, senha)) {
                    new Alert(AlertType.ERROR) {
                        {
                            setTitle("Erro");
                            setHeaderText("Senha incorreta");
                            setContentText("A senha fornecida não corresponde ao CPF cadastrado.");
                            showAndWait();
                        }
                    };

                } else {
                    // tranfere o objeto cliente para a proxima pagina
                    App.setDimension(350, 440);
                    App.setCurrentClient(atualClient);
                    App.setRoot("paginaCliente");
                }
            } else {
                new Alert(AlertType.ERROR) {
                    {
                        setTitle("Erro");
                        setHeaderText("CPF não cadastrado");
                        setContentText("O CPF fornecido não está cadastrado no sistema.");
                        showAndWait();

                    }
                };

            }
        } else { // funcionario

        }
    }

    @FXML
    private void cadastrar() throws IOException {
        App.setRoot("cadastro");
    }

}