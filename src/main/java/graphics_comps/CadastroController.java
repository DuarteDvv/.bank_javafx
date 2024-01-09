package graphics_comps;

import java.net.URL;
import java.util.ResourceBundle;
import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;

public class CadastroController implements Initializable {

    @FXML
    private void voltar() throws IOException {
        App.setRoot("login");
    }

    @FXML
    private TextField entradaCpf;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        validaCpf();
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

    public void validaNome() {

    }

}
