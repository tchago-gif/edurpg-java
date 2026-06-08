import java.util.HashSet;
import java.util.Random;
import java.util.Scanner;
import java.util.Set;

public class EduRPG {

    static Scanner scanner = new Scanner(System.in);
    static Random random = new Random();

    // Vetor que guarda os nomes dos alunos/personagens.
    static String[] alunos;

    // Matriz que guarda o desempenho dos alunos nas missões.
    // Cada linha é um aluno, cada coluna é uma missão.
    static int[][] desempenho;

    // Vetor que guarda o XP total de cada aluno.
    static int[] xp;

    static boolean dadosCadastrados = false;

    static final String[] MISSOES = {
            "Matematica",
            "Logica",
            "Programacao"
    };

    // =========================================================
    // 1. MÉTODO PRINCIPAL E MENU
    // =========================================================

    public static void main(String[] args) {
        int opcao;

        do {
            mostrarMenu();
            opcao = lerInteiro("Escolha uma opcao: ");

            switch (opcao) {
                case 1:
                    menuGerenciarAlunos();
                    break;

                case 2:
                    registrarDesempenho();
                    break;

                case 3:
                    mostrarMatrizDesempenho();
                    break;

                case 4:
                    mostrarXpNivelEClassificacao();
                    break;

                case 5:
                    operacoesComConjuntos();
                    break;

                case 6:
                    calcularCombinacoesDeEquipe();
                    break;

                case 7:
                    simularRecompensa();
                    break;

                case 0:
                    System.out.println("\nEncerrando o EduRPG...");
                    break;

                default:
                    System.out.println("\nOpcao invalida. Tente novamente.");
            }

        } while (opcao != 0);

        scanner.close();
    }

    public static void mostrarMenu() {
        System.out.println("\n==================================");
        System.out.println("          EDU RPG - JAVA");
        System.out.println("==================================");
        System.out.println("1 - Gerenciar alunos");
        System.out.println("2 - Registrar desempenho nas missoes");
        System.out.println("3 - Mostrar matriz de desempenho");
        System.out.println("4 - Calcular XP, nivel e classificacao");
        System.out.println("5 - Operacoes com conjuntos de missoes");
        System.out.println("6 - Calcular combinacoes de equipes");
        System.out.println("7 - Simular recompensa por desempenho");
        System.out.println("0 - Sair");
        System.out.println("==================================");
    }

    // =========================================================
    // 2. SUBMENU DE GERENCIAMENTO DE ALUNOS
    // =========================================================

    public static void menuGerenciarAlunos() {
        int opcao;

        do {
            System.out.println("\n========== GERENCIAR ALUNOS ==========");
            System.out.println("1 - Criar lista inicial de alunos");
            System.out.println("2 - Adicionar novo aluno");
            System.out.println("3 - Remover aluno");
            System.out.println("4 - Editar aluno");
            System.out.println("5 - Listar alunos");
            System.out.println("0 - Voltar");
            System.out.println("======================================");

            opcao = lerInteiro("Escolha uma opcao: ");

            switch (opcao) {
                case 1:
                    cadastrarAlunos();
                    break;

                case 2:
                    adicionarAluno();
                    break;

                case 3:
                    removerAluno();
                    break;

                case 4:
                    editarAluno();
                    break;

                case 5:
                    listarAlunos();
                    break;

                case 0:
                    System.out.println("\nVoltando ao menu principal...");
                    break;

                default:
                    System.out.println("\nOpcao invalida. Tente novamente.");
            }

        } while (opcao != 0);
    }

    // =========================================================
    // 3. CADASTRO, ADIÇÃO, EDIÇÃO, REMOÇÃO E LISTAGEM
    // =========================================================

    public static void cadastrarAlunos() {
        int quantidade;

        do {
            quantidade = lerInteiro("\nInforme a quantidade de alunos/aventureiros: ");

            if (quantidade <= 0) {
                System.out.println("A quantidade deve ser maior que zero.");
            }

        } while (quantidade <= 0);

        // Aqui o sistema cria as estruturas principais.
        alunos = new String[quantidade];
        desempenho = new int[quantidade][MISSOES.length];
        xp = new int[quantidade];

        scanner.nextLine();

        // Lê o nome de cada aluno e armazena no vetor.
        for (int i = 0; i < alunos.length; i++) {
            System.out.print("Nome do aluno " + (i + 1) + ": ");
            alunos[i] = scanner.nextLine();

            while (alunos[i].trim().isEmpty()) {
                System.out.print("Nome invalido. Digite novamente: ");
                alunos[i] = scanner.nextLine();
            }
        }

        dadosCadastrados = true;

        System.out.println("\nAlunos cadastrados com sucesso!");
    }

    public static void adicionarAluno() {
        if (!verificarCadastro()) {
            return;
        }

        scanner.nextLine();

        System.out.print("\nDigite o nome do novo aluno: ");
        String novoAluno = scanner.nextLine();

        while (novoAluno.trim().isEmpty()) {
            System.out.print("Nome invalido. Digite novamente: ");
            novoAluno = scanner.nextLine();
        }

        if (validarAluno(novoAluno)) {
            System.out.println("\nEsse aluno ja esta cadastrado.");
            return;
        }

        int novoTamanho = alunos.length + 1;

        String[] novosAlunos = new String[novoTamanho];
        int[][] novoDesempenho = new int[novoTamanho][MISSOES.length];
        int[] novoXp = new int[novoTamanho];

        // Como vetor tem tamanho fixo, criamos novos vetores maiores e copiamos os dados antigos.
        for (int i = 0; i < alunos.length; i++) {
            novosAlunos[i] = alunos[i];
            novoXp[i] = xp[i];

            for (int j = 0; j < MISSOES.length; j++) {
                novoDesempenho[i][j] = desempenho[i][j];
            }
        }

        // O novo aluno entra na última posição.
        novosAlunos[novoTamanho - 1] = novoAluno;

        alunos = novosAlunos;
        desempenho = novoDesempenho;
        xp = novoXp;

        System.out.println("\nAluno adicionado com sucesso!");
    }

    public static void editarAluno() {
        if (!verificarCadastro()) {
            return;
        }

        scanner.nextLine();

        System.out.print("\nDigite o nome do aluno que deseja editar: ");
        String nomeAtual = scanner.nextLine();

        int indiceEditar = -1;

        // Procura a posição do aluno no vetor.
        for (int i = 0; i < alunos.length; i++) {
            if (alunos[i].equalsIgnoreCase(nomeAtual)) {
                indiceEditar = i;
                break;
            }
        }

        if (indiceEditar == -1) {
            System.out.println("\nAluno nao encontrado.");
            return;
        }

        System.out.print("Digite o novo nome do aluno: ");
        String novoNome = scanner.nextLine();

        while (novoNome.trim().isEmpty()) {
            System.out.print("Nome invalido. Digite novamente: ");
            novoNome = scanner.nextLine();
        }

        // Impede trocar para um nome que já existe.
        for (int i = 0; i < alunos.length; i++) {
            if (i != indiceEditar && alunos[i].equalsIgnoreCase(novoNome)) {
                System.out.println("\nJa existe outro aluno com esse nome.");
                return;
            }
        }

        alunos[indiceEditar] = novoNome;

        System.out.println("\nAluno editado com sucesso!");
    }

    public static void removerAluno() {
        if (!verificarCadastro()) {
            return;
        }

        scanner.nextLine();

        System.out.print("\nDigite o nome do aluno que deseja remover: ");
        String nomeRemover = scanner.nextLine();

        int indiceRemover = -1;

        // Procura a posição do aluno que será removido.
        for (int i = 0; i < alunos.length; i++) {
            if (alunos[i].equalsIgnoreCase(nomeRemover)) {
                indiceRemover = i;
                break;
            }
        }

        if (indiceRemover == -1) {
            System.out.println("\nAluno nao encontrado.");
            return;
        }

        if (alunos.length == 1) {
            alunos = null;
            desempenho = null;
            xp = null;
            dadosCadastrados = false;

            System.out.println("\nAluno removido. Nao ha mais alunos cadastrados.");
            return;
        }

        int novoTamanho = alunos.length - 1;

        String[] novosAlunos = new String[novoTamanho];
        int[][] novoDesempenho = new int[novoTamanho][MISSOES.length];
        int[] novoXp = new int[novoTamanho];

        int novoIndice = 0;

        // Copia todos os alunos, pulando o aluno removido.
        for (int i = 0; i < alunos.length; i++) {
            if (i != indiceRemover) {
                novosAlunos[novoIndice] = alunos[i];
                novoXp[novoIndice] = xp[i];

                for (int j = 0; j < MISSOES.length; j++) {
                    novoDesempenho[novoIndice][j] = desempenho[i][j];
                }

                novoIndice++;
            }
        }

        alunos = novosAlunos;
        desempenho = novoDesempenho;
        xp = novoXp;

        System.out.println("\nAluno removido com sucesso!");
    }

    public static void listarAlunos() {
        if (!verificarCadastro()) {
            return;
        }

        System.out.println("\n========== ALUNOS CADASTRADOS ==========");

        for (int i = 0; i < alunos.length; i++) {
            System.out.println((i + 1) + " - " + alunos[i]);
        }
    }

    // =========================================================
    // 4. REGISTRO E EXIBIÇÃO DA MATRIZ DE DESEMPENHO
    // =========================================================

    public static void registrarDesempenho() {
        if (!verificarCadastro()) {
            return;
        }

        System.out.println("\nCada missao possui nota de 0 a 10 acertos.");

        // Primeiro for percorre alunos, segundo for percorre missões.
        for (int i = 0; i < alunos.length; i++) {
            System.out.println("\nAluno: " + alunos[i]);

            for (int j = 0; j < MISSOES.length; j++) {
                int nota;

                do {
                    nota = lerInteiro("Acertos na missao " + MISSOES[j] + ": ");

                    if (nota < 0 || nota > 10) {
                        System.out.println("Valor invalido. Digite um numero entre 0 e 10.");
                    }

                } while (nota < 0 || nota > 10);

                // Salva a nota na matriz.
                desempenho[i][j] = nota;
            }
        }

        calcularXpDeTodos();

        System.out.println("\nDesempenho registrado com sucesso!");
    }

    public static void mostrarMatrizDesempenho() {
        if (!verificarCadastro()) {
            return;
        }

        System.out.println("\n========== MATRIZ DE DESEMPENHO ==========");
        System.out.printf("%-15s", "Aluno");

        for (String missao : MISSOES) {
            System.out.printf("%-15s", missao);
        }

        System.out.println();

        // Exibe a matriz em formato de tabela.
        for (int i = 0; i < alunos.length; i++) {
            System.out.printf("%-15s", alunos[i]);

            for (int j = 0; j < MISSOES.length; j++) {
                System.out.printf("%-15d", desempenho[i][j]);
            }

            System.out.println();
        }
    }

    // =========================================================
    // 5. CÁLCULO DE XP, NÍVEL, APROVEITAMENTO E CLASSIFICAÇÃO
    // =========================================================

    public static int calcularXp(int[] notasAluno) {
        int totalXp = 0;

        // Cada acerto vale 10 pontos de XP.
        for (int nota : notasAluno) {
            totalXp += nota * 10;
        }

        return totalXp;
    }

    public static void calcularXpDeTodos() {
        // Calcula o XP usando as notas da matriz.
        for (int i = 0; i < alunos.length; i++) {
            xp[i] = calcularXp(desempenho[i]);
        }
    }

    public static int calcularNivel(int xpAluno) {
        // A cada 100 pontos de XP, o aluno sobe um nível.
        return xpAluno / 100 + 1;
    }

    public static double calcularAproveitamento(int[] notasAluno) {
        int soma = 0;

        for (int nota : notasAluno) {
            soma += nota;
        }

        int totalPossivel = notasAluno.length * 10;

        // Calcula o aproveitamento em porcentagem.
        return (soma * 100.0) / totalPossivel;
    }

    public static String classificarAluno(int xpAluno, double aproveitamento) {
        // Classificação baseada em condições lógicas.
        if (xpAluno >= 240 && aproveitamento >= 80) {
            return "Heroi do Conhecimento";
        } else if (aproveitamento >= 60) {
            return "Aprendiz em Evolucao";
        } else {
            return "Precisa Treinar Mais";
        }
    }

    public static void mostrarXpNivelEClassificacao() {
        if (!verificarCadastro()) {
            return;
        }

        calcularXpDeTodos();

        System.out.println("\n========== XP, NIVEL E CLASSIFICACAO ==========");

        for (int i = 0; i < alunos.length; i++) {
            double aproveitamento = calcularAproveitamento(desempenho[i]);
            int nivel = calcularNivel(xp[i]);
            String classificacao = classificarAluno(xp[i], aproveitamento);

            System.out.println("\nAluno: " + alunos[i]);
            System.out.println("XP: " + xp[i]);
            System.out.println("Nivel: " + nivel);
            System.out.printf("Aproveitamento: %.2f%%\n", aproveitamento);
            System.out.println("Classificacao: " + classificacao);
        }
    }

    // =========================================================
    // 6. OPERAÇÕES COM CONJUNTOS
    // =========================================================

    public static void operacoesComConjuntos() {
        if (!verificarCadastro()) {
            return;
        }

        scanner.nextLine();

        System.out.println("\nAlunos disponiveis para os conjuntos:");
        listarAlunos();

        // HashSet representa conjuntos e remove duplicidades automaticamente.
        Set<String> missaoMatematica = new HashSet<>();
        Set<String> missaoLogica = new HashSet<>();

        System.out.println("\nVamos montar dois conjuntos de alunos:");
        System.out.println("Conjunto A - alunos que participaram da missao de Matematica");
        System.out.println("Conjunto B - alunos que participaram da missao de Logica");

        System.out.println("\nDigite os nomes para o Conjunto A.");
        System.out.println("Digite FIM para encerrar.");

        while (true) {
            System.out.print("Aluno do Conjunto A: ");
            String nome = scanner.nextLine();

            if (nome.equalsIgnoreCase("FIM")) {
                break;
            }

            if (validarAluno(nome)) {
                missaoMatematica.add(nome);
            } else {
                System.out.println("Aluno nao cadastrado. Nome ignorado.");
            }
        }

        System.out.println("\nDigite os nomes para o Conjunto B.");
        System.out.println("Digite FIM para encerrar.");

        while (true) {
            System.out.print("Aluno do Conjunto B: ");
            String nome = scanner.nextLine();

            if (nome.equalsIgnoreCase("FIM")) {
                break;
            }

            if (validarAluno(nome)) {
                missaoLogica.add(nome);
            } else {
                System.out.println("Aluno nao cadastrado. Nome ignorado.");
            }
        }

        // União: junta os alunos dos dois conjuntos.
        Set<String> uniao = new HashSet<>(missaoMatematica);
        uniao.addAll(missaoLogica);

        // Interseção: mantém apenas quem está nos dois conjuntos.
        Set<String> intersecao = new HashSet<>(missaoMatematica);
        intersecao.retainAll(missaoLogica);

        // Diferença: mostra quem está em Matemática, mas não em Lógica.
        Set<String> diferenca = new HashSet<>(missaoMatematica);
        diferenca.removeAll(missaoLogica);

        System.out.println("\n========== RESULTADO DOS CONJUNTOS ==========");
        System.out.println("Conjunto A - Matematica: " + missaoMatematica);
        System.out.println("Conjunto B - Logica: " + missaoLogica);
        System.out.println("Uniao A U B: " + uniao);
        System.out.println("Intersecao A ∩ B: " + intersecao);
        System.out.println("Diferenca A - B: " + diferenca);
    }

    // =========================================================
    // 7. ANÁLISE COMBINATÓRIA
    // =========================================================

    public static void calcularCombinacoesDeEquipe() {
        if (!verificarCadastro()) {
            return;
        }

        int totalAlunos = alunos.length;
        int tamanhoEquipe;

        do {
            tamanhoEquipe = lerInteiro("\nInforme o tamanho da equipe: ");

            if (tamanhoEquipe <= 0 || tamanhoEquipe > totalAlunos) {
                System.out.println("Tamanho invalido. Deve ser maior que 0 e menor ou igual ao total de alunos.");
            }

        } while (tamanhoEquipe <= 0 || tamanhoEquipe > totalAlunos);

        // Calcula quantas equipes diferentes podem ser formadas.
        long combinacoes = calcularCombinacao(totalAlunos, tamanhoEquipe);

        System.out.println("\n========== COMBINACOES DE EQUIPES ==========");
        System.out.println("Total de alunos: " + totalAlunos);
        System.out.println("Tamanho da equipe: " + tamanhoEquipe);
        System.out.println("Quantidade de equipes possiveis: " + combinacoes);
    }

    public static long calcularCombinacao(int n, int k) {
        // Fórmula: C(n,k) = n! / (k! * (n-k)!).
        return calcularFatorial(n) / (calcularFatorial(k) * calcularFatorial(n - k));
    }

    public static long calcularFatorial(int numero) {
        long resultado = 1;

        // Multiplica os números de 2 até o valor informado.
        for (int i = 2; i <= numero; i++) {
            resultado *= i;
        }

        return resultado;
    }

    // =========================================================
    // 8. PROBABILIDADE E RECOMPENSAS
    // =========================================================

    public static void simularRecompensa() {
        if (!verificarCadastro()) {
            return;
        }

        calcularXpDeTodos();

        System.out.println("\n========== SIMULACAO DE RECOMPENSA ==========");

        for (int i = 0; i < alunos.length; i++) {
            double aproveitamento = calcularAproveitamento(desempenho[i]);
            String recompensa = sortearRecompensa(aproveitamento);

            System.out.println("\nAluno: " + alunos[i]);
            System.out.printf("Aproveitamento: %.2f%%\n", aproveitamento);
            System.out.println("Recompensa recebida: " + recompensa);
        }
    }

    public static String sortearRecompensa(double aproveitamento) {
        // Sorteia um número de 1 a 100 para simular probabilidade.
        int sorteio = random.nextInt(100) + 1;

        // Quanto maior o aproveitamento, melhor a chance de recompensa.
        if (aproveitamento >= 80) {
            if (sorteio <= 30) {
                return "Item Lendario";
            } else if (sorteio <= 70) {
                return "Item Raro";
            } else {
                return "Item Comum";
            }
        } else if (aproveitamento >= 60) {
            if (sorteio <= 15) {
                return "Item Raro";
            } else {
                return "Item Comum";
            }
        } else {
            if (sorteio <= 10) {
                return "Item Raro";
            } else {
                return "Item Comum";
            }
        }
    }

    // =========================================================
    // 9. MÉTODOS AUXILIARES
    // =========================================================

    public static boolean validarAluno(String nome) {
        for (String aluno : alunos) {
            if (aluno.equalsIgnoreCase(nome)) {
                return true;
            }
        }

        return false;
    }

    public static boolean verificarCadastro() {
        if (!dadosCadastrados) {
            System.out.println("\nPrimeiro cadastre os alunos na opcao 1.");
            return false;
        }

        return true;
    }

    public static int lerInteiro(String mensagem) {
        while (true) {
            System.out.print(mensagem);

            if (scanner.hasNextInt()) {
                return scanner.nextInt();
            } else {
                System.out.println("Entrada invalida. Digite um numero inteiro.");
                scanner.next();
            }
        }
    }
}