package games;

import org.apache.commons.math3.util.MathArrays;

import static org.apache.commons.math3.util.MathArrays.shuffle;

public class Drunkard {

    private static final int PARS_TOTAL_COUNT = Par.values().length;
    private static final int CARDS_TOTAL_COUNT = PARS_TOTAL_COUNT * Suit.values().length;
    private static int[] firstPlayersCards = new int[CARDS_TOTAL_COUNT];
    private static int[] secondPlayersCards = new int[CARDS_TOTAL_COUNT];
    private static int[] deckOfCards = new int[CARDS_TOTAL_COUNT];

    public static void main(String... __) {

        for (int i = 0; i < deckOfCards.length; i++) {
            deckOfCards[i] = i;
        }

        MathArrays.shuffle(deckOfCards);

        for (int i = 0; i < deckOfCards.length / 2; i++) {
            firstPlayersCards[i] = deckOfCards[i];
            secondPlayersCards[i] = deckOfCards[18 + i];
        }

    }

    enum Suit {
        SPADES, // пики
        HEARTS, // червы
        CLUBS, // трефы
        DIAMONDS // бубны
    }

    enum Par {
        SIX,
        SEVEN,
        EIGHT,
        NINE,
        TEN,
        JACK,
        QUEEN,
        KING,
        AVE
    }

    private static Suit getSuit(int cardNumber) {
        return Suit.values()[cardNumber / PARS_TOTAL_COUNT];
    }

    private static Par getPar(int cartNumber) {
        return Par.values()[cartNumber % PARS_TOTAL_COUNT];
    }

}
