import service.LeitorApi;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        var leitorApi = new LeitorApi();
        String moedasConvertidas = "";
        boolean runtime = true;

        // loop responsável pelo programa continuar funcionando
        while (runtime) {
            // responsável por exibir o menu de escolhas
            System.out.println(
                    "Conversor de moedas: \n" +
                            "\n" +
                            "Escolha uma opção válida:\n" +
                            "1) Dólar => Peso argentino\n" +
                            "2) Peso argentino => Dólar\n" +
                            "3) Dólar => Real brasileiro\n" +
                            "4) Real brasileiro => Dólar\n" +
                            "5) Dólar => Peso colombiano\n" +
                            "6) Peso colombiano => Dólar\n" +
                            "7) Sair\n" +
                            "\n" +
                            "***********************************************");

            // lê o número informado pelo usuário, verifica erros e define o caso de conversão de moeda
            try {
                int opcao = Integer.valueOf(scanner.nextLine());
                switch (opcao) {
                    case 1:
                        moedasConvertidas = "USD/ARS/";
                        System.out.println("Você escolheu Dólar => Peso argentino");
                        break;
                    case 2:
                        moedasConvertidas = "ARS/USD/";
                        System.out.println("Você escolheu Peso argentino => Dólar");
                        break;
                    case 3:
                        moedasConvertidas = "USD/BRL/";
                        System.out.println("Você escolheu Dólar => Real brasileiro");
                        break;
                    case 4:
                        moedasConvertidas = "BRL/USD/";
                        System.out.println("Você escolheu Real brasileiro => Dólar");
                        break;
                    case 5:
                        moedasConvertidas = "USD/COP/";
                        System.out.println("Você escolheu Dólar => Peso colombiano");
                        break;
                    case 6:
                        moedasConvertidas = "COP/USD/";
                        System.out.println("Você escolheu Peso colombiano => Dólar");
                        break;
                    case 7:
                        System.out.println("Saindo...");
                        runtime = false;
                        break;
                    default:
                        System.out.println("Opção inválida! Tente novamente.\n");
                        continue;
                }
                // Caso a flag runtime esteja ativa, pede o valor para converter, trata caso ocorra erros, e devolve o valor convertido
                if (runtime) {
                    System.out.println("Por favor, informe o valor que deseja converter:");
                    double valor = Double.valueOf(scanner.nextLine());
                    System.out.println("O valor convertido é: " + leitorApi.callLeitorApi(moedasConvertidas, valor));
                }
            } catch (InputMismatchException e) {
                System.out.println("Entrada inválida! Por favor, insira um número.");
                scanner.next(); // Limpa o scanner
            } catch (Exception e) {
                System.out.println("Ocorreu um erro: " + e.getMessage());
            }
        }
        scanner.close();
    }
}
