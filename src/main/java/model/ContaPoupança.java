package model;

public class ContaPoupança extends Conta {

  public ContaPoupança(String titu) {
    super(titu);
  }

  public ContaPoupança(String titu, double sald) {
    super(titu, sald);
  }

  @Override
  public String getTipoDeConta() {
    return "Poupança";
  }

}