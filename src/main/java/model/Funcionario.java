package model;

public abstract class Funcionario {
    private String nome;
    private String cpf;
    private double salario;

    private static int numeroDeFuncionarios;

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
