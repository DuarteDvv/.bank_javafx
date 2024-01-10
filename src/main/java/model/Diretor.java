package model;

import util.Autenticavel;

public class Diretor extends Funcionario implements Autenticavel {

    @Override
    public boolean autentica(String senha) {
        //
        return false;
    }

}
