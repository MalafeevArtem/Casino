package games;

import org.slf4j.Logger;


public class Drunkard {

    private static final Logger log = org.slf4j.LoggerFactory.getLogger(Drunkard.class);

    private static int[][] playersCards = new int[2][CardUtils.CARDS_TOTAL_COUNT + 1];

    private static int[] playersCardTails = new int[2];
    private static int[] playersCardHeads = new int[2];

    private static int player1 = 0;
    private static int player2 = 1;

    private static int[] deckCards = CardUtils.getShuffledCards();

    public static void main(String... __) {

        distributionCards();

        playersCardTails[player1] = 0;
        playersCardTails[player2] = 0;
        playersCardHeads[player1] = deckCards.length / 2;
        playersCardHeads[player2] = deckCards.length / 2;

        int counter = 1;

        while (true) {
            log.info("Итерация {}! Игрок №1 карта: {}; Игрок №2 карта: {}", counter,
                CardUtils.toString(playersCards[player1][playersCardTails[player1]]),
                CardUtils.toString(playersCards[player2][playersCardTails[player2]]));

            int result = moveWinner(playersCardTails[player1], playersCardTails[player2]);

            switch (result) {

                case 0: {
                    log.info("Выиграл игрок №1\n");
                    someoneWon(player1, player2);
                    showCountOfCards(player1, result);
                    showCountOfCards(player2, result);
                    break;
                }
                case 1: {
                    log.info("Выиграл игрок №2\n");
                    someoneWon(player2, player1);
                    showCountOfCards(player1, result);
                    showCountOfCards(player2, result);
                    break;
                }

                case 2: {
                    log.info("Спор - каждый остается при своих\n");
                    friendlyWon();
                    showCountOfCards(player1, result);
                    showCountOfCards(player2, result);
                    break;
                }
            }

            if (playerCardsIsEmpty(player1) && result == 0) {
                log.info("Выиграл первый игрок. Количество произведенных итераций: {}", counter);
                break;
            } else if (playerCardsIsEmpty(player2) && result == 1) {
                log.info("Выиграл второй игрок. Количество произведенных итераций: {}", counter);
                break;
            } else {
                counter++;
            }

        }
    }

    // Метод проверки победиля хода
    private static int moveWinner(int cardFirstPlayer, int cardSecondPlayer) {

        int valueFirstCard = getValueCard(playersCards[player1][cardFirstPlayer]);
        int valueSecondCard = getValueCard(playersCards[player2][cardSecondPlayer]);

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

        playersCards[player1][playersCardHeads[player1]] = playersCardTails[player1];
        playersCards[player2][playersCardHeads[player2]] = playersCardTails[player2];

        playersCardTails[player1] = incrementIndex(playersCardTails[player1]);
        playersCardHeads[player1] = incrementIndex(playersCardHeads[player1]);
        playersCardTails[player2] = incrementIndex(playersCardTails[player2]);
        playersCardHeads[player2] = incrementIndex(playersCardHeads[player2]);

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

    // Метод, который проверяет на пустоту колоды карт 
    private static boolean playerCardsIsEmpty(int playerIndex) {
        int tail = playersCardTails[playerIndex];
        int head = playersCardHeads[playerIndex];

        return tail == head;
    }

    // Методы, который раздает карты игрокам
    private static void distributionCards() {

        for (int index = 0; index < deckCards.length / 2; index++) {
            playersCards[player1][index] = deckCards[index];
            playersCards[player2][index] = deckCards[deckCards.length / 2 + index];
        }

    }

    // Метод возвращает размер карты
    private static int getValueCard(int cardNumber) {
        return cardNumber % CardUtils.PARS_TOTAL_COUNT;
    }

    // Индекс карты
    private static int incrementIndex(int i) {
        return (i + 1) % CardUtils.CARDS_TOTAL_COUNT;
    }

}
