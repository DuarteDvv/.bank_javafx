package controller;

import java.util.LinkedList;

import util.Tributavel;

public class ManipuladorDeTributos {

    private static LinkedList<Tributavel> contasComTributos = new LinkedList<>();

    public static void adicionarTributo(Tributavel a) {
        contasComTributos.add(a);
    }

    public double valorTotalImposto() {
        double total = 0;
        for (Tributavel n : contasComTributos) {
            total += n.getValorImposto();
        }
        return total;
    }

}
