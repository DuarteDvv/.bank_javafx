package util;

import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.VBox;
import model.Conta;
import graphics_comps.App;

import java.time.format.DateTimeFormatter;

public class ContaCell extends ListCell<Conta> {

    private static final String COR1 = "-fx-background-color: linear-gradient(to bottom, #87CEEB, #FFFFFF);";
    private static final String COR2 = "-fx-background-color: linear-gradient(to bottom, #98FB98, #FFFFFF);";

    @Override
    protected void updateItem(Conta conta, boolean empty) {
        super.updateItem(conta, empty);

        if (empty || conta == null) {
            setText(null);
            setGraphic(null);
            setBackground(null);
        } else {
            VBox vbox = new VBox();
            vbox.setAlignment(Pos.CENTER_LEFT);

            Label tituLabel = new Label(conta.getTitular());
            Label numeroETipoContaLabel = new Label(
                    "Número: " + conta.getNumero() + "  Agencia: " + conta.getAgencia());
            Label agenciaEntradaLabel = new Label(
                    "Entrada: " + DateTimeFormatter.ofPattern("dd/MM/yyyy -- HH:mm").format(conta.getDataEntrada()));
            Label tipoLabel = new Label("Tipo " + conta.getTipoDeConta());

            // Adiciona um Label específico para o saldo
            Label saldoContaLabel = new Label();
            vbox.getChildren().add(saldoContaLabel);

            Button btnExcluir = new Button("Excluir");
            btnExcluir.setOnAction(event -> {
                App.getCurrentClient().apagarConta(conta);
                ObservableList<Conta> items = getListView().getItems();
                items.remove(conta);
            });

            vbox.getChildren().addAll(tituLabel, numeroETipoContaLabel, agenciaEntradaLabel, tipoLabel, btnExcluir);

            // Configura a célula com o VBox
            setGraphic(vbox);
            setText(null);

            // Personaliza o estilo da célula com cores intercaladas
            int index = getIndex();
            if (index % 2 == 0) {
                setStyle(COR1 + "-fx-border-radius: 10px; -fx-border-color: #000;");
            } else {
                setStyle(COR2 + "-fx-border-radius: 10px; -fx-border-color: #000;");
            }

            // Adiciona um listener para atualizar o saldo na célula quando ele for alterado
            conta.saldoProperty().addListener((observable, oldValue, newValue) -> {
                saldoContaLabel.setText("Saldo : " + newValue);
            });

            // Atualiza o saldo inicialmente
            saldoContaLabel.setText("Saldo : " + conta.getSaldo());

            // Criar Tooltip com informações da conta
            Tooltip tooltip = new Tooltip("Titular: " + conta.getTitular() + "\nNúmero: " + conta.getNumero() +
                    "\nAgência: " + conta.getAgencia() + "\nTipo: " + conta.getTipoDeConta());

            // Associar Tooltip ao elemento
            Tooltip.install(vbox, tooltip);

        }
    }
}
