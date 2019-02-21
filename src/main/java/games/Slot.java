package games;

import static java.lang.Math.random;
import static java.lang.Math.round;

public class Slot {

    public static void main(String... __) {

        int playerMoney = 100;
        int rateAmount = 10;
        int winnerMoney = 1000;
        int size = 7;
        int minValue = 0;
        int maxValue = 100;

        while (playerMoney >= 10) {

            int firstDrumValue;
            int secondDrumValue;
            int thirdDrumValue;

            System.out.println("У Вас " + playerMoney + " $, ставка - " + rateAmount + "$");
            System.out.println("Крутим барабаны!Розыгрыш принес следующие результаты:");
            firstDrumValue = (minValue + (int) round(random() * maxValue)) % size;
            secondDrumValue = (minValue + (int) round(random() * maxValue)) % size;
            thirdDrumValue = (minValue + (int) round(random() * maxValue)) % size;
            System.out.println("первый барабан - " + firstDrumValue + ", второй - " + secondDrumValue +
                                ", третий - " + thirdDrumValue);

            if ((firstDrumValue == secondDrumValue) && (firstDrumValue == thirdDrumValue)) {
                playerMoney += winnerMoney;
                System.out.println("Выигрыш " + winnerMoney + "$, ваш капитал теперь составляет: " +
                                playerMoney + "$");
            } else {
                playerMoney -= rateAmount;
                System.out.println("Проигрыш " + rateAmount + "$, ваш капитал теперь составляет: " +
                    playerMoney + "$");
            }
        }

    }

}
