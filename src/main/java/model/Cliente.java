package model;

import util.Autenticavel;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Date;
import java.util.Map;
import java.util.HashMap;

public class Cliente implements Autenticavel, Comparable<Cliente> {

    private String cpf;
    private String nome;
    private Date dataNascimento;
    private String senha;
    private List<Conta> contas = new ArrayList<>();
    private List<PlanosDeAuxilio> planos = new ArrayList<>();

    @Override
    public boolean autentica(String senha) {
        // ...
        return true;

    }

    @Override
    public boolean equals(Object object) {
        if (object == null || !(object instanceof Cliente)) {
            return false;
        }

        Cliente client = (Cliente) object;
        return client.cpf.equals(this.cpf);

    }

    @Override
    public int compareTo(Cliente outroCliente) {
        return this.nome.compareToIgnoreCase(outroCliente.nome);

    }

    public void criarConta(Conta novaConta) {

        contas.add(novaConta);
    }

    public void apagarConta(Conta contaParaApagar) {

        contas.remove(contaParaApagar);
    }

    public List<Conta> getContas() {
        return Collections.unmodifiableList(contas);
    }

    public List<PlanosDeAuxilio> getPlanos() {
        return Collections.unmodifiableList(planos);
    }

}
