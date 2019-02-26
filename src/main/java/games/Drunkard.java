package games;

import org.slf4j.Logger;


public class Drunkard {

    private static final Logger log = org.slf4j.LoggerFactory.getLogger(Drunkard.class);

    private static int[][] playersCards = new int[2][CardUtils.CARDS_TOTAL_COUNT + 1];

    private static int[] playersCardTails = new int[2];
    private static int[] playersCardHeads = new int[2];

    private final static int PLAYER_1 = 0;
    private final static int PLAYER_2 = 1;

    public static void main(String... __) {

        init();

        int counter = 1;
        int result = 0;

        while (!(playerCardsIsEmpty(PLAYER_1) || playerCardsIsEmpty(PLAYER_2))) {

            log.info("Итерация {}! Игрок №1 карта: {}; Игрок №2 карта: {}", counter,
                CardUtils.toString(playersCards[PLAYER_1][playersCardTails[PLAYER_1]]),
                CardUtils.toString(playersCards[PLAYER_2][playersCardTails[PLAYER_2]]));

            result = moveWinner(playersCardTails[PLAYER_1], playersCardTails[PLAYER_2]);

            switch (result) {

                case 0:
                    log.info("Выиграл игрок №1\n");
                    someoneWon(PLAYER_1, PLAYER_2);
                    showCountOfCards(PLAYER_1, result);
                    showCountOfCards(PLAYER_2, result);
                    counter++;
                    break;

                case 1:
                    log.info("Выиграл игрок №2\n");
                    someoneWon(PLAYER_2, PLAYER_1);
                    showCountOfCards(PLAYER_1, result);
                    showCountOfCards(PLAYER_2, result);
                    counter++;
                    break;

                case 2:
                    log.info("Спор - каждый остается при своих\n");
                    friendlyWon();
                    showCountOfCards(PLAYER_1, result);
                    showCountOfCards(PLAYER_2, result);
                    counter++;
                    break;
            }
        }

        if (result == 0)
            log.info("Выиграл первый игрок. Количество произведенных итераций: {}", counter);
        else
            log.info("Выиграл второй игрок. Количество произведенных итераций: {}", counter);
    }
    
    private static int moveWinner(int cardFirstPlayer, int cardSecondPlayer) {

        int valueFirstCard = getValueCard(playersCards[PLAYER_1][cardFirstPlayer]);
        int valueSecondCard = getValueCard(playersCards[PLAYER_2][cardSecondPlayer]);

        if (valueFirstCard == 0 && valueSecondCard == 8) {
            return 0;
        } else if (valueSecondCard == 0 && valueFirstCard == 8) {
            return 1;
        } else if (valueFirstCard > valueSecondCard) {
            return 0;
        } else if (valueFirstCard == valueSecondCard) {
            return 2;
        } else {
            return 1;
        }
    }
    
    private static void someoneWon(int winPlayer, int loserPlayer) {
        playersCards[winPlayer][playersCardHeads[winPlayer]] = playersCardTails[winPlayer];
        playersCards[winPlayer][playersCardHeads[winPlayer] + 1] = playersCardTails[loserPlayer];
        playersCardTails[winPlayer] = incrementIndex(playersCardTails[winPlayer]);

        for (int index = 0; index < 2; index++) {
            playersCardHeads[winPlayer] = incrementIndex(playersCardHeads[winPlayer]);
        }

        playersCardTails[loserPlayer] = incrementIndex(playersCardTails[loserPlayer]);
    }

    private static void friendlyWon() {
        playersCards[PLAYER_1][playersCardHeads[PLAYER_1]] = playersCardTails[PLAYER_1];
        playersCards[PLAYER_2][playersCardHeads[PLAYER_2]] = playersCardTails[PLAYER_2];

        playersCardTails[PLAYER_1] = incrementIndex(playersCardTails[PLAYER_1]);
        playersCardHeads[PLAYER_1] = incrementIndex(playersCardHeads[PLAYER_1]);
        playersCardTails[PLAYER_2] = incrementIndex(playersCardTails[PLAYER_2]);
        playersCardHeads[PLAYER_2] = incrementIndex(playersCardHeads[PLAYER_2]);
    }


    private static void showCountOfCards(int player, int result) {
        int countCards = playersCardHeads[player] - playersCardTails[player];

        if (playerCardsIsEmpty(player) && result == player) {
            log.info("У игрока №{} - {} карт;", player + 1, CardUtils.CARDS_TOTAL_COUNT);
        } else if (countCards >= 0) {
            log.info("У игрока №{} - {} карт;", player + 1, countCards);
        } else {
            log.info("У игрока №{} - {} карт;", player + 1, CardUtils.CARDS_TOTAL_COUNT - playersCardTails[player]);
        }
    }

    private static boolean playerCardsIsEmpty(int playerIndex) {
        int tail = playersCardTails[playerIndex];
        int head = playersCardHeads[playerIndex];

        return tail == head;
    }

    private static void init() {
        int[] deckCards = CardUtils.getShuffledCards();

        for (int i = 0; i < deckCards.length; i++)
            playersCards[i % 2][i / 2] = deckCards[i];

        playersCardHeads[PLAYER_1] = playersCardHeads[PLAYER_2] = deckCards.length / 2;
    }

    private static int getValueCard(int cardNumber) {
        return cardNumber % CardUtils.PARS_TOTAL_COUNT;
    }

    private static int incrementIndex(int i) {
        return (i + 1) % CardUtils.CARDS_TOTAL_COUNT;
    }

}
