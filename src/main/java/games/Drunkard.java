package games;

import org.slf4j.Logger;


public class Drunkard {

    private static final Logger log = org.slf4j.LoggerFactory.getLogger(Drunkard.class);

    private static int[][] playersCards = new int[2][CardUtils.CARDS_TOTAL_COUNT];

    private static int[] playersCardTails = new int[2];
    private static int[] playersCardHeads = new int[2];

    private static final int PLAYER_1 = 0;
    private static final int PLAYER_2 = 1;


    public static void main(String... __) {

        init();

        int counter = 1;
        int winner;


       do {
            int cardTailPlayer1 = playersCards[PLAYER_1][playersCardTails[PLAYER_1]];
            int cardTailPlayer2 = playersCards[PLAYER_2][playersCardTails[PLAYER_2]];

            log.info("Итерация {}! Игрок №1 карта: {}; Игрок №2 карта: {}", counter,
                CardUtils.toString(cardTailPlayer1),
                CardUtils.toString(cardTailPlayer2));

            showCountsOfCards(PLAYER_1);
            showCountsOfCards(PLAYER_2);

            winner = moveWinner(getValueCard(cardTailPlayer1), getValueCard(cardTailPlayer2));

            if (winner > 0) {
                log.info("Выиграл игрок №1\n");
                playerCardsAdd(PLAYER_1, cardTailPlayer1);
                playerCardsAdd(PLAYER_1, cardTailPlayer2);
            } else if (winner == 0) {
                log.info("Спор - каждый остается при своих\n");
                playerCardsAdd(PLAYER_1, cardTailPlayer1);
                playerCardsAdd(PLAYER_2, cardTailPlayer2);
            } else {
                log.info("Выиграл игрок №2\n");
                playerCardsAdd(PLAYER_2, cardTailPlayer2);
                playerCardsAdd(PLAYER_2, cardTailPlayer1);
            }

            counter++;

            playersCardTails[PLAYER_1] = incrementIndex(playersCardTails[PLAYER_1]);
            playersCardTails[PLAYER_2] = incrementIndex(playersCardTails[PLAYER_2]);
        }
        while (!(playerCardsIsEmpty(PLAYER_1) || playerCardsIsEmpty(PLAYER_2)));

        if (winner == 1)
              log.info("Выиграл первый игрок. Количество произведенных итераций: {}", counter);
        else
             log.info("Выиграл второй игрок. Количество произведенных итераций: {}", counter);
    }

    private static void init() {
        int[] deckCards = CardUtils.getShuffledCards();

        for (int i = 0; i < deckCards.length; i++)
            playersCards[i % 2][i / 2] = deckCards[i];

        playersCardHeads[PLAYER_1] = playersCardHeads[PLAYER_2] = deckCards.length / 2;
    }

    private static void showCountsOfCards(int player) {
        int countCards = playersCardHeads[player] - playersCardTails[player];

        if (countCards >= 0)
            log.info("У игрока №{} - {} карт;", player + 1, countCards);
        else
            log.info("У игрока №{} - {} карт;", player + 1, (playersCardHeads[player] +
                CardUtils.CARDS_TOTAL_COUNT - playersCardTails[player]));

    }

    private static int moveWinner(int cardPlayer1, int cardPlayer2) {
        int differenceOfValue = cardPlayer1 - cardPlayer2;
        return Math.abs(differenceOfValue) == 8 ? -differenceOfValue : differenceOfValue;
    }

    private static int getValueCard(int cardNumber) {
        return cardNumber % CardUtils.PARS_TOTAL_COUNT;
    }

    private static int incrementIndex(int i) {
        return (i + 1) % CardUtils.CARDS_TOTAL_COUNT;
    }

    private static void playerCardsAdd(int playerIndex, int... cards) {
        int[] playerCards = playersCards[playerIndex];
        int endCursor = playersCardHeads[playerIndex] % CardUtils.CARDS_TOTAL_COUNT;

        for (int card : cards) {
            playerCards[endCursor] = card;
            playersCardHeads[playerIndex] = incrementIndex(endCursor);
        }
    }

    private static boolean playerCardsIsEmpty(int playerIndex) {
        int tail = playersCardTails[playerIndex];
        int head = playersCardHeads[playerIndex];

        return tail == head;
    }

}
