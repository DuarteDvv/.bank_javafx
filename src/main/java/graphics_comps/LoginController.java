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
    private CheckBox checkVisivel;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        validaCpf();
        tornarSenhaVisivel();
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
