import java.util.Scanner;

public class Main {

    static Scanner sc = new Scanner(System.in);

    // Classe Pessoa
    static class Pessoa {
        String nome;
        int idade;
        double peso, altura;
    }

    // QUESTÃO 1 - Buscar pessoa pelo nome
    public static int buscarPessoa(Pessoa[] v, int qtd, String nome) {

        for (int i = 0; i < qtd; i++) {
            if (v[i].nome.equalsIgnoreCase(nome)) {
                return i;
            }
        }

        return -1;
    }

    // QUESTÃO 1 - Cadastrar pessoa
    public static int cadastrarPessoa(Pessoa[] v, int qtd) {

        // verificar se o vetor está cheio
        if (qtd >= v.length) {
            System.out.println("Vetor cheio!");
            return qtd;
        }

        Pessoa p = new Pessoa();
        String nome;

        // garantir nome único
        do {
            System.out.print("Digite o nome: ");
            nome = sc.nextLine();

            if (buscarPessoa(v, qtd, nome) != -1) {
                System.out.println("Nome já cadastrado. Digite outro.");
            }

        } while (buscarPessoa(v, qtd, nome) != -1);

        p.nome = nome;

        System.out.print("Digite a idade: ");
        p.idade = sc.nextInt();

        System.out.print("Digite o peso: ");
        p.peso = sc.nextDouble();

        System.out.print("Digite a altura: ");
        p.altura = sc.nextDouble();
        sc.nextLine();

        // adicionar no final do vetor
        v[qtd] = p;

        return qtd + 1;
    }

    // QUESTÃO 2 - Calcular IMC
    public static double calcularIMC(double peso, double altura) {
        return peso / (altura * altura);
    }

    // QUESTÃO 2 - Imprimir pessoas
    public static void imprimirPessoas(Pessoa[] v, int qtd) {

        if (qtd == 0) {
            System.out.println("Nenhuma pessoa cadastrada.");
            return;
        }

        for (int i = 0; i < qtd; i++) {

            double imc = calcularIMC(v[i].peso, v[i].altura);

            System.out.println("\nPessoa " + (i + 1));
            System.out.println("Nome: " + v[i].nome);
            System.out.println("Idade: " + v[i].idade);
            System.out.println("Peso: " + v[i].peso);
            System.out.println("Altura: " + v[i].altura);
            System.out.printf("IMC: %.2f\n", imc);
        }
    }

    // QUESTÃO 3 - Pessoa mais velha com IMC Magreza
    public static int maisVelhaIMCMagreza(Pessoa[] v, int qtd) {

        int indice = -1;
        int maiorIdade = -1;

        for (int i = 0; i < qtd; i++) {

            double imc = calcularIMC(v[i].peso, v[i].altura);

            if (imc < 18.5) {

                if (v[i].idade > maiorIdade) {
                    maiorIdade = v[i].idade;
                    indice = i;
                }
            }
        }

        return indice;
    }

    // QUESTÃO 4 - Insertion Sort por nome
    public static void insertionSortPorNome(Pessoa[] v, int qtd) {

        for (int i = 1; i < qtd; i++) {

            Pessoa chave = v[i];
            int j = i - 1;

            while (j >= 0 &&
                    v[j].nome.compareToIgnoreCase(chave.nome) > 0) {

                v[j + 1] = v[j];
                j--;
            }

            v[j + 1] = chave;
        }
    }

    // QUESTÃO 5 - Contar pessoas por faixa de IMC
    public static int contarPessoasPorIMC(Pessoa[] v, int qtd,
                                           double imcMin, double imcMax) {

        int contador = 0;

        for (int i = 0; i < qtd; i++) {

            double imc = calcularIMC(v[i].peso, v[i].altura);

            if (imc >= imcMin && imc <= imcMax) {
                contador++;
            }
        }

        return contador;
    }

    // MAIN
    public static void main(String[] args) {

        Pessoa[] pessoas = new Pessoa[100];
        int qtd = 0;
        int opcao;

        do {

            System.out.println("\n===== MENU =====");
            System.out.println("1 - Cadastrar Pessoa");
            System.out.println("2 - Imprimir Pessoas");
            System.out.println("3 - Pessoa mais velha com IMC Magreza");
            System.out.println("4 - Ordenar por Nome");
            System.out.println("5 - Contar pessoas por faixa de IMC");
            System.out.println("0 - Sair");
            System.out.print("Escolha: ");

            opcao = sc.nextInt();
            sc.nextLine();

            switch (opcao) {

                case 1:
                    qtd = cadastrarPessoa(pessoas, qtd);
                    break;

                case 2:
                    imprimirPessoas(pessoas, qtd);
                    break;

                case 3:
                    int indice = maisVelhaIMCMagreza(pessoas, qtd);

                    if (indice == -1) {
                        System.out.println("Nenhuma pessoa com IMC Magreza.");
                    } else {
                        System.out.println("Pessoa encontrada:");
                        System.out.println("Nome: " +
                                pessoas[indice].nome);
                    }
                    break;

                case 4:
                    insertionSortPorNome(pessoas, qtd);
                    System.out.println("Pessoas ordenadas!");
                    break;

                case 5:

                    System.out.print("Digite IMC minimo: ");
                    double min = sc.nextDouble();

                    System.out.print("Digite IMC maximo: ");
                    double max = sc.nextDouble();

                    int total =
                            contarPessoasPorIMC(pessoas, qtd, min, max);

                    System.out.println(
                            "Quantidade encontrada: " + total);
                    break;

                case 0:
                    System.out.println("Programa encerrado.");
                    break;

                default:
                    System.out.println("Opcao invalida.");
            }

        } while (opcao != 0);
    }
}