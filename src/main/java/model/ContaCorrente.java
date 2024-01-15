package model;

import util.Tributavel;

public class ContaCorrente extends Conta implements Tributavel {

  public ContaCorrente(String titu) {
    super(titu);
  }

  public ContaCorrente(String titu, double sald) {
    super(titu, sald);
  }

  @Override
  public double getValorImposto() {
    return 0.01 * getSaldo();
  }

  @Override
  public void sacar(double sacado) {

    if (sacado <= 0) {
      throw new IllegalArgumentException("Saque invalido");

    } else if ((getSaldo() - sacado) < 0) {
      throw new IllegalArgumentException("Saldo insuficiente");

    } else {
      setSaldo(getSaldo() - sacado - 0.10);
    }
  }

  @Override
  public String getTipoDeConta() {
    return "Corrente";
  }
}