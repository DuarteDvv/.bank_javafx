package graphics_comps;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;

import model.Cliente;

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

    private Map<Cliente, String> clientes = new HashMap<>();

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
        String cpf = entradaCpf.getText();
        String senha = entradaSenha.getText();
        
        if(!checkColaborador.isSelected()){
            if (clientes.containsKey(cpf)) {
                if (!clientes.get(cpf).equals(hash(senha))) {
                    Alert alert = new Alert(AlertType.ERROR);
                    alert.setTitle("Erro");
                    alert.setHeaderText("Senha incorreta");
                    alert.setContentText("A senha fornecida não corresponde ao CPF cadastrado.");
                    alert.showAndWait();
                } else {
                    App.setRoot("secondary");
                }
            } else {
                Alert alert = new Alert(AlertType.ERROR);
                alert.setTitle("Erro");
                alert.setHeaderText("CPF não cadastrado");
                alert.setContentText("O CPF fornecido não está cadastrado no sistema.");
                alert.showAndWait();
            }
        }
        else{

        }
    }

    @FXML
    private void cadastrar() throws IOException {
        App.setRoot("cadastro");
    }

    // Função para criar hash de senha
    private String hash(String senha) {
        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        byte[] hash = md.digest(senha.getBytes(StandardCharsets.UTF_8));
        return new String(hash, StandardCharsets.UTF_8);
    }
}