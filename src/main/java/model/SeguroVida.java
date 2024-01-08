package model;

import util.Tributavel;

public class SeguroVida implements Tributavel {

    private double valor;
    private int numeroApolice;
    private String titular;

    @Override
    public double getValorImposto() {
        return 0.02 * valor;
    }

}
