package model;

import util.Autenticavel;
import java.util.Date;
import java.util.Set;
import java.util.HashSet;

public class Cliente implements Comparable<Cliente> {

    private String cpf;
    private String nome;
    private Date dataNascimento;
    private Set<Conta> contas = new HashSet<>();
    private Set<PlanosDeAuxilio> planos = new HashSet<>();

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
        if (this.nome == null || outroCliente.nome == null) {
            return 0;
        }
        return this.nome.compareToIgnoreCase(outroCliente.nome);
    }

    public void criarConta(Conta novaConta) {
        contas.add(novaConta);
    }

    public void apagarConta(Conta contaParaApagar) {
        contas.remove(contaParaApagar);
    }

    public Set<Conta> getContas() {
        return Collections.unmodifiableSet(contas);
    }

    public Set<PlanosDeAuxilio> getPlanos() {
        return Collections.unmodifiableSet(planos);
    }

}