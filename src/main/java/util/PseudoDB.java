package util;

import java.util.HashMap;
import java.util.Map;
import model.Cliente;

public class PseudoDB {

    private static Map<Cliente, String> container = new HashMap<>();

    // Método para registrar um cliente
    public static void registrarCliente(Cliente cliente, String senha) {
        container.put(cliente, senha);
    }

    // Método para excluir um cliente
    public static void excluirCliente(Cliente cliente) {
        container.remove(cliente);
    }

    // Método para buscar um cliente pelo CPF
    public static Cliente buscarClientePorCpf(String cpf) {
        for (Cliente cliente : container.keySet()) {
            if (cliente.getCpf().equals(cpf)) {
                return cliente;
            }
        }
        return null;
    }

    public static void trocarSenha(Cliente a, String novaSenha) {
        container.replace(a, novaSenha);

    }

    // Método para verificar se a senha corresponde à digitada
    public static boolean verificarSenha(Cliente cliente, String senhaDigitada) {
        return container.get(cliente).equals(senhaDigitada);
    }
}