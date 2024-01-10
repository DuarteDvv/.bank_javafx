package model;

import util.Autenticavel;

public abstract class Funcionario implements Autenticavel {
    private String nome;
    private String cpf;
    private double salario;
    private String id;

    private static int numeroDeFuncionarios;

    public boolean autentica(String cpf, String senha) {
        return true;
    }

    public static int getNFuncionarios() {
        return numeroDeFuncionarios;
    }

    @Override
    public boolean equals(Object object) {
        if (object == null) {
            return false;
        }

        if (!(object instanceof Funcionario)) {
            return false;
        }

        Funcionario func = (Funcionario) object;
        return (this.cpf == func.cpf);

    }

}
