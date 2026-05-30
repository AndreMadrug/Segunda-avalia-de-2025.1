import java.util.Scanner;

public class Banco {

    public static final int TAM_CONTAS = 50;
    public static final int TAM_OP = 1000;

    // ─── Auxiliares ──────────────────────────────────────────────────────────────

    
    private static Conta lerConta(Scanner sc) {
        Conta c = new Conta();
        System.out.print("ID: ");
        c.id = sc.nextInt(); sc.nextLine();
        System.out.print("Cliente: ");
        c.cliente = sc.nextLine();
        System.out.print("Saldo: ");
        c.saldo = sc.nextDouble();
        System.out.print("Limite: ");
        c.limite = sc.nextDouble(); sc.nextLine();
        return c;
    }

    /** Já fornecida pelo enunciado. */
    public static int buscaId(Conta[] v, int tam, int x) {
        for (int i = 0; i < tam; i++)
            if (v[i].id == x) return i;
        return -1;
    }

    // ─── Questão 1 ───────────────────────────────────────────────────────────────

    
    public static int cadastrarConta(Conta[] v, int tam) {
        if (tam >= TAM_CONTAS) {
            System.out.println("Vetor de contas está cheio.");
            return tam;
        }

        Scanner sc = new Scanner(System.in);
        Conta nova = lerConta(sc);

        if (buscaId(v, tam, nova.id) != -1) {
            System.out.println("Erro: ID já cadastrado.");
            return tam;
        }
        if (nova.saldo < 0) {
            System.out.println("Erro: saldo não pode ser negativo.");
            return tam;
        }
        if (nova.limite < 0) {
            System.out.println("Erro: limite não pode ser negativo.");
            return tam;
        }

        v[tam] = nova;
        return tam + 1;
    }

    // ─── Questão 2 ───────────────────────────────────────────────────────────────

   
    public static int buscaBinariaCliente(Conta[] v, int tam, String x) {
        int inicio = 0, fim = tam - 1;

        while (inicio <= fim) {
            int meio = (inicio + fim) / 2;
            int cmp = v[meio].cliente.compareToIgnoreCase(x);

            if (cmp == 0)  return meio;
            if (cmp < 0)   inicio = meio + 1;
            else           fim    = meio - 1;
        }
        return -1;
    }

    // ─── Questão 3 ───────────────────────────────────────────────────────────────

  
    public static int insertionSortCliente(Conta[] v, int tam) {
        for (int i = 1; i < tam; i++) {
            Conta chave = v[i];
            int j = i - 1;

            while (j >= 0 && v[j].cliente.compareToIgnoreCase(chave.cliente) > 0) {
                v[j + 1] = v[j];
                j--;
            }
            v[j + 1] = chave;
        }
        return tam;
    }

    // ─── Questão 4 ───────────────────────────────────────────────────────────────

    
    public static int filtraOperacoes(Operacao[] vOp, int tamOp,
                                      Operacao[] vOpFiltrado, int x) {
        int tamFiltrado = 0;

        for (int i = 0; i < tamOp; i++) {
            if (vOp[i].idConta == x) {
                vOpFiltrado[tamFiltrado] = vOp[i];
                tamFiltrado++;
            }
        }
        return tamFiltrado;
    }

    // ─── Questão 5 ───────────────────────────────────────────────────────────────

    /** Formata um valor double como "NNNN.NNC" ou "NNNN.NND". */
    private static String formatarValor(double v) {
        char sinal = v >= 0 ? 'C' : 'D';
        return String.format("%.2f%c", Math.abs(v), sinal);
    }

    /** Descrição textual do tipo de operação. */
    private static String descricaoTipo(char tipo) {
        switch (tipo) {
            case 'D': return "Depósito";
            case 'S': return "Saque";
            case 'T': return "Transferência";
            default:  return "Desconhecido";
        }
    }

    public static void extrato(Conta[] vContas, int tamConta,
                               Operacao[] vOp,    int tamOp) {

        Scanner sc = new Scanner(System.in);
        System.out.print("Digite o ID da conta: ");
        int idBuscado = sc.nextInt();

        int posConta = buscaId(vContas, tamConta, idBuscado);
        if (posConta == -1) {
            System.out.println("Conta não encontrada.");
            return;
        }

        Conta conta = vContas[posConta];

        // Filtra apenas as operações desta conta
        Operacao[] filtradas = new Operacao[TAM_OP];
        int tamFiltrado = filtraOperacoes(vOp, tamOp, filtradas, idBuscado);

        // Determina janela das últimas 5
        int inicio = Math.max(0, tamFiltrado - 5);

        // Calcula saldo anterior às 5 últimas operações (partindo de saldo 0)
        double saldoAnterior = 0;
        for (int i = 0; i < inicio; i++) {
            saldoAnterior += filtradas[i].valor;
        }

        // Impressão do extrato
        System.out.println("+----------------------------------------+");
        System.out.printf( "| CONTA N.%03d%29s%n", conta.id, "|");
        System.out.println("+----------------------------------------+");
        System.out.printf( "|CLIENTE: %-31s|%n", conta.cliente);
        System.out.println("+----------------------------------------+");
        System.out.println("| ID | OPERAÇÃO        | VALOR  | SALDO  |");
        System.out.println("+----+-----------------+--------+--------+");
        System.out.printf( "|    |Saldo Anterior   |        |%8s|%n",
                           formatarValor(saldoAnterior));

        double saldoCorrente = saldoAnterior;
        for (int i = inicio; i < tamFiltrado; i++) {
            Operacao op = filtradas[i];
            saldoCorrente += op.valor;

            System.out.printf("|%04d|%-17s|%8s|%8s|%n",
                op.id,
                descricaoTipo(op.tipo),
                formatarValor(op.valor),
                formatarValor(saldoCorrente));
        }

        System.out.println("+----+-----------------+--------+--------+");
        System.out.printf( "| Saldo Atual             |        |%8s|%n",
                           formatarValor(saldoCorrente));
        System.out.println("+--------+");
    }
}
