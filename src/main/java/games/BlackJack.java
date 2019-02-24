package games;

import java.io.IOException;


public class BlackJack {

    private static int[] cards; // Основная колода
    private static int cursor; // Счётчик карт основной колоды

    private static int[][] playersCards; // карты игроков. Первый индекс - номер игрока
    private static int[] playersCursors; // курсоры карт игроков. Индекс - номер игрока

    private static final int MAX_VALUE = 21;
    private static final int MAX_CARDS_COUNT = 8;

    private static int player1 = 0;
    private static int player2 = 1;

    private static int bet = 10;

    private static int[] playersMoney = {100, 100};

    private static int value(int card) {
        switch (CardUtils.getPar(card)) {
            case JACK: return 2;
            case QUEEN: return 3;
            case KING: return 4;
            case SIX: return 6;
            case SEVEN: return 7;
            case EIGHT: return 8;
            case NINE: return 9;
            case TEN: return 10;
            case AVE:
            default: return 11;
        }
    }

    public static void main(String... __) throws IOException {

        while (!moneyCheck()) {

            initRound();


            // Реализуем игру с игроком
            for (int index = 1; index < 3; index++) {
                int card = addCard2Player(player1);
                System.out.println("Вам выпала карта " + CardUtils.toString(card));
            }

            int sumFirstPlayer = sum(player1);

            for (int index = 0; sumFirstPlayer < MAX_VALUE && index < 5; index++) {

                if (confirm("Берем еще ?")) {
                    int card = addCard2Player(player1);
                    sumFirstPlayer = sum(player1);
                    System.out.println("Вам выпала карта:" + CardUtils.toString(card));
                } else {
                    break;
                }
            }

            // Реализуем игру компьютера
            for (int index = 1; index < 3; index++) {
                int card = addCard2Player(player2);
                System.out.println("Компьютеру выпала карта " + CardUtils.toString(card));
            }

            int sumSecondPlayer = sum(player2);

            for (int index = 0; index < 5 && sumSecondPlayer < 17; index++) {
                    int card = addCard2Player(player2);
                    sumSecondPlayer = sum(player2);
                    System.out.println("Компьютеру выпала карта:" + CardUtils.toString(card));
            }

            int finalSummPlayer1 = getFinalSum(player1);
            int finalSummPlayer2 = getFinalSum(player2);

            System.out.println("Сумма ваших очков - " + finalSummPlayer1 + ", компьютера " +
                        finalSummPlayer2 + " очков");

            bankCalculation(finalSummPlayer1, finalSummPlayer2);

            System.out.println("У вас " + playersMoney[player1] + "$ " +
                        ", у компьютера " + playersMoney[player2] + "$ ");
        }

        if (playersMoney[player1] > 0)
            System.out.println("Вы выиграли! Поздравляем!");
        else
            System.out.println("Вы проиграли. Соболезнуем...");

    }

    private static void bankCalculation(int sumPlayer1, int sumPlayer2) {

        if (sumPlayer1 > sumPlayer2) {
            System.out.println("Вы выиграли раунд, получаете 10$");
            playersMoney[player1] += bet;
            playersMoney[player2] -= bet;
        } else if (sumPlayer1 == sumPlayer2) {
            System.out.println("В это раунде ничья");
        } else {
            System.out.println("В этом раунде побеждает компьютер");
            playersMoney[player1] -= bet;
            playersMoney[player2] += bet;
        }
    }

    private static void initRound() {
        System.out.println("\nУ Вас " + playersMoney[0] + "$, у компьютера - " + playersMoney[1] + "$. Начинаем новый раунд!");
        cards = CardUtils.getShaffledCards();
        playersCards = new int[2][MAX_CARDS_COUNT];
        playersCursors = new int[]{0, 0};
        cursor = 0;
    }

    private static int addCard2Player(int player) {

        int cardIndex = cards[cursor];
        playersCards[player][playersCursors[player]] = cardIndex;
        cursor++;
        playersCursors[player]++;

        return cardIndex;
    }

    // Метод, который считает сумму очков игрока.
    static int sum(int player) {
        int summPoints = 0;
        for (int index = 0; index < playersCursors[player]; index++) {
            summPoints += value(playersCards[player][index]);
        }
        return  summPoints;
    }

    static int getFinalSum(int player) {
        int finalSum = sum(player);

        if (finalSum <= MAX_VALUE) {
            return finalSum;
        } else {
            return 0;
        }
    }

    static boolean confirm(String message) throws IOException {
        System.out.println(message + " \"Y\" - Да, {любой другой символ} - нет (Что бы выйти из игры, нажмите Ctrl + C)");
        switch (Choice.getCharacterFromUser()) {
            case 'Y':
            case 'y': return true;
            default: return false;
        }
    }

    static boolean moneyCheck() {
        return ((playersMoney[player1]) - bet < 0) || ((playersMoney[player2] - bet) < 0);
    }
}
