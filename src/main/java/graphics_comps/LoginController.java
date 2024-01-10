package graphics_comps;

import java.io.IOException;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;

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

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        validaCpf();
        tornarSenhaVisivel();
        ativarColab();
    }

    public void validaCpf() {
        entradaCpf.textProperty().addListener((observable, oldValue, newValue) -> {
            String cpfValidado = newValue.replaceAll("[^\\d]", "");
            if (cpfValidado.length() > 11) {
                cpfValidado = cpfValidado.substring(0, 11);
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
        App.setRoot("secondary");
    }

    @FXML
    private void cadastrar() throws IOException {
        App.setRoot("cadastro");

    }

}
