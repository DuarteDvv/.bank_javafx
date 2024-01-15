package model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.Random;

public abstract class Conta implements Comparable<Conta> {
    private static int numeroDeContas = 0;
    private static final Random rand = new Random();

    private String titular;
    private double saldo;
    private int agencia;
    private long numero;
    private LocalDateTime dataEntrada;

    Conta(String titu) {
        setSaldo(0);
        setTitular(titu);
        setConstAtributos();

        numeroDeContas++;
    }

    Conta(String titu, double sald) {
        setSaldo(sald);
        setTitular(titu);
        setConstAtributos();

        numeroDeContas++;
    }

    public static int getNContas() {
        return numeroDeContas;
    }

    protected void setSaldo(double newSaldo) {
        saldo = newSaldo;
    }

    public double getSaldo() {
        return saldo;
    }

    protected void setTitular(String titular) {
        this.titular = titular;
    }

    public String getTitular() {
        return titular;
    }

    protected void setConstAtributos() {
        agencia = rand.nextInt(9000) + 1000;
        numero = rand.nextInt(90000000) + 10000000;
        dataEntrada = LocalDateTime.now();
    }

    public int getAgencia() {
        return agencia;
    }

    public LocalDateTime getDataEntrada() {
        return dataEntrada;
    }

    public long getNumero() {
        return numero;
    }

    public void transfere(Conta destino, double valor) {
        sacar(valor);
        destino.depositar(valor);
    }

    public void sacar(double sacado) {
        if (sacado <= 0) {
            throw new IllegalArgumentException("Saque inválido");
        } else if (getSaldo() - sacado < 0) {
            throw new IllegalArgumentException("Saldo insuficiente");
        } else {
            setSaldo(getSaldo() - sacado);
        }
    }

    public void depositar(double depositado) {
        if (depositado <= 0) {
            throw new IllegalArgumentException("Depósito inválido");
        }

        setSaldo(getSaldo() + depositado);
    }

    public double rendimentoMensal() {
        return saldo * 0.1;
    }

    @Override
    public String toString() {
        DateTimeFormatter formata = DateTimeFormatter.ofPattern("dd/MM/yyyy -- HH:mm");
        return String.format("%s %d %.2f %s",
                titular + " || " + numero + " || " + saldo + " || " + formata.format(dataEntrada));
    }

    @Override
    public boolean equals(Object object) {
        if (object == null || !(object instanceof Conta)) {
            return false;
        }

        Conta conta = (Conta) object;
        return agencia == conta.agencia && numero == conta.numero;
    }

    @Override
    public int compareTo(Conta outraConta) {
        return titular.compareTo(outraConta.titular);
    }

    public Comparator<Conta> comparadorPorNumero = Comparator.comparingLong(c -> {
        return c.numero;
    });

    public Comparator<Conta> comparadorPorData = Comparator.comparing(c -> {
        return c.dataEntrada;
    });

    public abstract String getTipoDeConta();
}
