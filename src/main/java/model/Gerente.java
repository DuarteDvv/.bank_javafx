package model;

import util.Autenticavel;

public class Gerente extends Funcionario implements Autenticavel {

    @Override
    public boolean autentica(int senha) {
        //
        return false;
    }

}
