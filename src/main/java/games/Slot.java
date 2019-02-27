package games;

import org.slf4j.Logger;

import static java.lang.Math.random;
import static java.lang.Math.round;

public class Slot {

    private static final Logger log = org.slf4j.LoggerFactory.getLogger(Slot.class);

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

            log.info("У Вас {}$, ставка - {}$", playerMoney, rateAmount);
            log.info("Крутим барабаны!Розыгрыш принес следующие результаты:");
            firstDrumValue = (minValue + (int) round(random() * maxValue)) % size;
            secondDrumValue = (minValue + (int) round(random() * maxValue)) % size;
            thirdDrumValue = (minValue + (int) round(random() * maxValue)) % size;
            log.info("первый барабан - {} второй - {}, третий - {}", firstDrumValue, secondDrumValue, thirdDrumValue);

            if ((firstDrumValue == secondDrumValue) && (firstDrumValue == thirdDrumValue)) {
                playerMoney += winnerMoney;
                log.info("Выигрыш {}$, ваш капитал теперь составляет: {}$", winnerMoney, playerMoney);
            } else {
                playerMoney -= rateAmount;
                log.info("Проигрыш {}$, ваш капитал теперь составляет: {}" +
                    " " +
                    "      и    " +
                    "" +
                    "" +
                    "" +
                    "" +
                    "$", rateAmount, playerMoney );
            }
        }

    }

}
