package console.classes;
import console.exceptions.NoActivityException;

public class PlayRoom {
    public static void main(String[] args) {
        try {
            GameConsole console = new GameConsole(GameConsole.Brand.SONY, "PS4 PRO", "SN12345");

            console.powerOn();
            System.out.println("Консоль включена: " + console.isOn());

            console.getFirstGamepad().powerOn();
            System.out.println("Первый геймпад включен: " + console.getFirstGamepad().isOn());

            Game.VirtualGame game = Game.getVirtualGame("Horizon Zero Dawn", Game.Genre.ACTION);
            console.loadGame(game.getData());

            console.playGame();
            console.playGame();

            console.getFirstGamepad().powerOff();

            console.getSecondGamepad().powerOn();
            System.out.println("Второй геймпад включен: " + console.getSecondGamepad().isOn());

            console.playGame();

            console.getFirstGamepad().powerOff();
            console.getSecondGamepad().powerOff();

            for (int i = 0; i < 6; i++) {
                console.playGame();
            }

        } catch (NoActivityException e) {
            System.out.println("Ошибка: " + e.getMessage());
        }
    }
}
