package games;

import org.apache.commons.math3.util.MathArrays;


public class Drunkard {


    private static int[][] playersCards = new int[2][CardUtils.CARDS_TOTAL_COUNT + 1];

    private static int[] playersCardTails = new int[2];
    private static int[] playersCardHeads = new int[2];

    private static int player1 = 0;
    private static int player2 = 1;

    private static int[] deckCards = CardUtils.getShaffledCards();

    public static void main(String... __) {

        distributionCards();

        playersCardTails[player1] = 0;
        playersCardTails[player2] = 0;
        playersCardHeads[player1] = deckCards.length / 2;
        playersCardHeads[player2] = deckCards.length / 2;

        int counter = 1;

        while (true) {
            System.out.println("Итерация " + counter + "! Игрок №1 карта: " + CardUtils.toString(playersCards[player1][playersCardTails[player1]]) +
                "; Игрок №2 карта: " + CardUtils.toString(playersCards[player2][playersCardTails[player2]]));

            int result = moveWinner(playersCardTails[player1], playersCardTails[player2]);

            switch (result) {

                case 0: {
                    System.out.println("Выиграл игрок №1\n");
                    winFirstPlayer();
                    showCountOfCards(result);
                    break;
                }
                case 1: {
                    System.out.println("Выиграл игрок №2\n");
                    winSecondPlayer();
                    showCountOfCards(result);
                    break;
                }

                case 2: {
                    System.out.println("Спор - каждый остается при своих\n");
                    friendlyWon();
                    showCountOfCards(result);
                    break;
                }
            }


            if (playerCardsIsEmpty(player1) && result == 0) {
                System.out.println("Выиграл первый игрок. Количество произведенных итераций: " + counter);
                break;
            } else if (playerCardsIsEmpty(player2) && result == 1) {
                System.out.println("Выиграл второй игрок. Количество произведенных итераций:" + counter);
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

    private static void winFirstPlayer() {

        playersCards[player1][playersCardHeads[player1]] = playersCardTails[player1];
        playersCards[player1][playersCardHeads[player1] + 1] = playersCardTails[player2];

        playersCardTails[player1] = incrementIndex(playersCardTails[player1]);
        playersCardHeads[player1] = incrementIndex(playersCardHeads[player1]);
        playersCardHeads[player1] = incrementIndex(playersCardHeads[player1]);
        playersCardTails[player2] = incrementIndex(playersCardTails[player2]);
    }

    private static void winSecondPlayer() {

        playersCards[player2][playersCardHeads[player2]] = playersCardTails[player2];
        playersCards[player2][playersCardHeads[player2] + 1] = playersCardTails[player1];

        playersCardTails[player2] = incrementIndex(playersCardTails[player2]);
        playersCardHeads[player2] = incrementIndex(playersCardHeads[player2]);
        playersCardHeads[player2] = incrementIndex(playersCardHeads[player2]);
        playersCardTails[player1] = incrementIndex(playersCardTails[player1]);

    }

    private static void friendlyWon() {

        playersCards[player1][playersCardHeads[player1]] = playersCardTails[player1];
        playersCards[player2][playersCardHeads[player2]] = playersCardTails[player2];

        playersCardTails[player1] = incrementIndex(playersCardTails[player1]);
        playersCardHeads[player1] = incrementIndex(playersCardHeads[player1]);
        playersCardTails[player2] = incrementIndex(playersCardTails[player2]);
        playersCardHeads[player2] = incrementIndex(playersCardHeads[player2]);
    }

    private static void showCountOfCards(int result) {

        int countCardsFirst = playersCardHeads[player1] - playersCardTails[player1];
        int countCardsSecond = playersCardHeads[player2] - playersCardTails[player2];

        if (playerCardsIsEmpty(player1) && result == 0) {
            System.out.print("У игрока №1 " + 36 + "карт; ");
        } else if (countCardsFirst >= 0) {
            System.out.print("У игрока №1 " + countCardsFirst + "карт; ");
        } else {
            System.out.print("У игрока №1 " + (playersCardHeads[player1] + CardUtils.CARDS_TOTAL_COUNT - playersCardTails[player1]) + "карт; ");
        }

        if (playerCardsIsEmpty(player2) && result == 1) {
            System.out.println("У игрока №1 " + 36 + "карт: ");
        } else if (countCardsSecond >= 0) {
            System.out.println(" У игрока №2 " + countCardsSecond + "карт;");
        } else {
            System.out.println(" У игрока №2 " + (playersCardHeads[player2] + CardUtils.CARDS_TOTAL_COUNT - playersCardTails[player2]) + "карт;");
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

    // Метод, который мешает колоду
    private static void shufflingCards() {

        for (int index = 0; index < deckCards.length; index++) {
            deckCards[index] = index;
        }

        MathArrays.shuffle(deckCards);

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
