package main;

import networking.Message;
import networking.NetworkServer;

import java.awt.geom.Point2D;

public class ServerMain extends NetworkServer implements Runnable {

    private GameState gameState;

    private ServerMain(int port) {
        super(port);

    }

    public void run() {
        initialize();
        gameState = new GameState(800, 800);
        gameState.setUp();
        gameState.setScreen(Screen.GAME);
        gameState.generatePlatforms();
        gameState.generateItems();

        while (true) {
            if (!sendMessage(new Message(gameState))) {
                System.err.println("Lost connection to client.");
                break;
            }

            handleMessages();
            gameState.updateLogic();
            gameState.updatePhysics();

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }


    @Override
    public void handleMessage(Message m) {
        Object o = m.getObject();
        if (o.getClass() == Point2D.Float.class) {
            handleMouseClick((Point2D.Float) o);
        } else if (o.getClass() == String.class) {
            handleKeyPress((String) o);
        } else {
            System.out.println("Message didn't translate properly");
        }
    }

    private void handleKeyPress(String key) {
        switch(key) {
            case "a": {
                gameState.getBall().moveLeft();
                break;
            }
            case "d": {
                gameState.getBall().moveRight();
                break;
            }
        }
    }

    private void handleMouseClick(Point2D.Float coords) {
        gameState.getBall().setX(coords.getX());

    }

    public static void main(String... args) {
        while (true) {
            ServerMain main = new ServerMain(8080);
            main.run();
        }
    }
}
