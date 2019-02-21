package games;

public class Drunkard {

    public static void main(String... __) {

        System.out.println("Масть 36-й карты -" + getSuit(35));
        System.out.println("Размерность 36-й карты - " + getPar(35));

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
        return Suit.values()[cardNumber / 9];
    }

    private static Par getPar(int cartNumber) {
        return Par.values()[cartNumber % 9];
    }
}
