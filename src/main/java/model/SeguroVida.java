package model;

public class SeguroVida extends PlanosDeAuxilio {

    private double valor;
    private int numeroApolice;
    private String titular;

    @Override
    public double getValorImposto() {
        return 0.02 * valor;
    }

}
