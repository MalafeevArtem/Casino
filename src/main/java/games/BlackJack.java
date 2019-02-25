package games;

import java.io.IOException;
import org.slf4j.*;


public class BlackJack {

    private static final Logger log = org.slf4j.LoggerFactory.getLogger(BlackJack.class);

    private static int[] cards; // Основная колода
    private static int cursor; // Счётчик карт основной колоды

    private static int[][] playersCards; // карты игроков. Первый индекс - номер игрока
    private static int[] playersCursors; // курсоры карт игроков. Индекс - номер игрока

    private static final int MAX_VALUE = 21;
    private static final int MAX_CARDS_COUNT = 8;
    private static final int MIN_VALUE_PLAYER = 11;

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
            int sumFirstPlayer;
            do {
                giveOneCard(player1);
                sumFirstPlayer = sum(player1);
            }
            while (situationCheckPlayer(sumFirstPlayer));

            // Реализуем игру компьютера
            int sumSecondPlayer;
            do {
                giveOneCard(player2);
                sumSecondPlayer = sum(player2);
            }
            while (situationCheckComp(sumSecondPlayer));


            int finalSummPlayer1 = getFinalSum(player1);
            int finalSummPlayer2 = getFinalSum(player2);

            log.info("Сумма ваших очков - {}, компьютера {} очков", finalSummPlayer1,finalSummPlayer2);

            bankCalculation(finalSummPlayer1, finalSummPlayer2);

            log.info("У вас {}$, у компьютера {}$ ", playersMoney[player1], playersMoney[player2]);
        }

        if (playersMoney[player1] > 0) {
            log.info("Вы выиграли! Поздравляем!");
        } else
            log.info("Вы проиграли. Соболезнуем...");

    }

    private static boolean situationCheckPlayer(int sum) throws IOException {

        if (sum < MIN_VALUE_PLAYER && sum < MAX_VALUE) {
            return true;
        } else if (sum < MAX_VALUE && sum > MIN_VALUE_PLAYER) {
            if (confirm("Берем еще ?")) return true;
            else return false;
        } else {
            return false;
        }
    }

    private static boolean situationCheckComp(int sum) {
        if (sum < MAX_VALUE && sum < 17) return true;
        else return false;
    }

    private static void giveOneCard(int player) {
            int card = addCard2Player(player);
            if (player == player1) {
                log.info("Вам выпала карта: {}", CardUtils.toString(card));
            } else {
                log.info("Компьютеру выпала карта: {}", CardUtils.toString(card));
        }
    }

    private static void bankCalculation(int sumPlayer1, int sumPlayer2) {

        if (sumPlayer1 > sumPlayer2) {
            log.info("Вы выиграли раунд, получаете 10$");
            playersMoney[player1] += bet;
            playersMoney[player2] -= bet;
        } else if (sumPlayer1 == sumPlayer2) {
            log.info("В это раунде ничья");
        } else {
            log.info("В этом раунде побеждает компьютер");
            playersMoney[player1] -= bet;
            playersMoney[player2] += bet;
        }
    }

    private static void initRound() {
        log.info("\nУ Вас {}$, у компьютера - {}$. Начинаем новый раунд!", playersMoney[0], playersMoney[1]);
        cards = CardUtils.getShuffledCards();
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
    private static int sum(int player) {
        int summPoints = 0;
        for (int index = 0; index < playersCursors[player]; index++) {
            summPoints += value(playersCards[player][index]);
        }
        return  summPoints;
    }

    private static int getFinalSum(int player) {
        int finalSum = sum(player);

        if (finalSum <= MAX_VALUE) {
            return finalSum;
        } else {
            return 0;
        }
    }

    private static boolean confirm(String message) throws IOException {
        log.info(message + " \"Y\" - Да, {любой другой символ} - нет (Что бы выйти из игры, нажмите Ctrl + C)");
        switch (Choice.getCharacterFromUser()) {
            case 'Y':
            case 'y': return true;
            default: return false;
        }
    }

    private static boolean moneyCheck() {
        return ((playersMoney[player1]) - bet < 0) || ((playersMoney[player2] - bet) < 0);
    }
}
