package console.classes;

import console.interfaces.Powered;
import console.exceptions.NoActivityException;

public class GameConsole implements Powered {
    public enum Brand {
        SONY, MICROSOFT
    }

    private final Brand brand;
    private final String model;
    private final String serial;
    private Gamepad firstGamepad;
    private Gamepad secondGamepad;
    private boolean isOn = false;
    private Game activeGame;
    private int waitingCounter = 0;

    public GameConsole(Brand brand, String model, String serial) {
        this.brand = brand;
        this.model = model;
        this.serial = serial;
        this.firstGamepad = new Gamepad(brand, 1);
        this.secondGamepad = new Gamepad(brand, 2);
    }

    public Gamepad getFirstGamepad() {
        return firstGamepad;
    }

    public Gamepad getSecondGamepad() {
        return secondGamepad;
    }

    public String getModel() {
        return model;
    }

    public Brand getBrand() {
        return brand;
    }

    public String getSerial() {
        return serial;
    }

    public boolean isOn() {
        return isOn;
    }

    public void setOn(boolean isOn) {
        this.isOn = isOn;
    }

    @Override
    public void powerOn() {
        this.isOn = true;
        System.out.println("Консоль включена");
    }

    @Override
    public void powerOff() {
        this.isOn = false;
        System.out.println("Консоль выключена");
    }

    public void loadGame(Game game) {
        this.activeGame = game;
        System.out.println("Игра " + game.getName() + " загружается.");
    }

    public void playGame() throws NoActivityException {
        if (activeGame == null) {
            System.out.println("Нет загруженной игры.");
            return;
        }

        System.out.println("Играем в " + activeGame.getName());

        // Уменьшение заряда активных джойстиков
        if (firstGamepad.isOn()) {
            firstGamepad.decreaseCharge();
        } else if (secondGamepad.isOn()) {
            secondGamepad.decreaseCharge();
        }

        checkStatus();
    }

    private void checkStatus() throws NoActivityException {
        if (!firstGamepad.isOn() && !secondGamepad.isOn()) {
            waitingCounter++;
            System.out.println("Подключите джойстик. Счетчик ожидания: " + waitingCounter);
            if (waitingCounter > 5) {
                this.powerOff();
                throw new NoActivityException("Приставка завершает работу из-за отсутствия активности.");
            }
        } else {
            waitingCounter = 0;
        }
    }

    public class Gamepad implements Powered {
        private final Brand brand;
        private final String consoleSerial;
        private final int connectedNumber;
        private String color;
        private double chargeLevel = 100.0;
        private boolean isOn = false;

        public Gamepad(Brand brand, int connectedNumber) {
            this.brand = brand;
            this.consoleSerial = GameConsole.this.serial;
            this.connectedNumber = connectedNumber;
        }

        public Brand getBrand() {
            return brand;
        }

        public String getConsoleSerial() {
            return consoleSerial;
        }

        public int getConnectedNumber() {
            return connectedNumber;
        }

        public String getColor() {
            return color;
        }

        public void setColor(String color) {
            this.color = color;
        }

        public double getChargeLevel() {
            return chargeLevel;
        }

        public void setChargeLevel(double chargeLevel) {
            this.chargeLevel = chargeLevel;
        }

        public boolean isOn() {
            return isOn;
        }

        public void setOn(boolean isOn) {
            this.isOn = isOn;
        }

        @Override
        public void powerOn() {
            this.isOn = true;
            GameConsole.this.setOn(true);
            System.out.println("Геймпад " + connectedNumber + " включен");
        }

        @Override
        public void powerOff() {
            this.isOn = false;
            System.out.println("Геймпад " + connectedNumber + " выключен");

            if (connectedNumber == 1 && !isOn()) {
                System.out.println("Первый геймпад выключен. Второй становится первым.");
                GameConsole.this.firstGamepad = GameConsole.this.secondGamepad;
            }
        }

        public void decreaseCharge() {
            if (chargeLevel > 0) {
                chargeLevel -= 10;
                System.out.println("Заряд геймпада " + connectedNumber + ": " + chargeLevel + "%");
                if (chargeLevel <= 0) {
                    this.powerOff();
                }
            }
        }
    }
}
