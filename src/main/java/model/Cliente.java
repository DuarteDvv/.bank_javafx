package model;

import java.util.Collections;
import java.util.Date;
import java.util.Set;
import java.util.HashSet;
import java.time.LocalDate;

public class Cliente implements Comparable<Cliente> {

    private String cpf;
    private String nome;
    private LocalDate dataNascimento;
    private Set<Conta> contas = new HashSet<>();
    private Set<PlanosDeAuxilio> planos = new HashSet<>();

    public Cliente(String cpf, String nome, LocalDate nascimento) {
        this.cpf = cpf;
        this.nome = nome;
        this.dataNascimento = nascimento;
    }

    @Override
    public boolean equals(Object object) {
        if (object == null || !(object instanceof Cliente)) {
            return false;
        }

        Cliente client = (Cliente) object;
        return client.cpf.equals(this.cpf);
    }

    public String getCpf() {
        return this.cpf;
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