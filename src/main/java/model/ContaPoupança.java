package model;

public class ContaPoupança extends Conta {

  ContaPoupança(String titu) {
    super(titu);
  }

  ContaPoupança(String titu, double sald) {
    super(titu, sald);
  }

  @Override
  public String getTipoDeConta() {
    return "Conta Poupança";
  }

}