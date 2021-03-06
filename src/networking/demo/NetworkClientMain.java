package networking.demo;

import networking.Message;
import networking.NetworkClient;

import java.util.Random;


/**
 * This is an example use of the tools in the Networking package.
 * It's intended to showcase how a game loop might use the sendMessage
 * and handleMessage methods to implement synchronisation between server
 * and client-side physics.
 */
class Client extends NetworkClient implements Runnable {

    // A random number generator and an integer can simulate input and game state respectively.
    private final Random random = new Random();
    private int gameState = 0;

    /**
     * Client is an example of what a main GameClient might look like.
     * In order to use the features of the NetworkClient, we need to call the constructor.
     *
     * @param host The hostname of the server we're connecting to.
     * @param port The port on which the server is listening for connections.
     */
    Client(String host, int port) {
        super(host, port);
    }

    @Override
    public void run() {
        initialize();

        while (true) {
            try {

                Thread.sleep(1000);
                System.out.println("Client tick");

                // We must implement handleMessage (singular) below.
                handleMessages();

                /*
                 * Here, we handle player input. The client is
                 * permitted to simulate the next game state given the input,
                 * but every tick it should check to see if the server has
                 * "pinged" it with an authoritative game-state update.
                 */

                System.out.println("Current game state: " + gameState);
                int input = random.nextInt(6);
                System.out.println("Got player input: " + input);
                simulateUpdate(input);
                System.out.printf("Current game state (after this tick's simulation): %d.\n", gameState);
                sendInput(input);

            } catch (InterruptedException e) {
                System.err.println("Thread was interrupted:");
                e.printStackTrace();
                break;
            }
        }
    }

    /**
     * Update the game state, introducing a slight error.
     *
     * @param input The player input to use in our update simulation.
     */
    private void simulateUpdate(int input) {
        gameState += input * 1.25;
        gameState %= 20;
    }

    /**
     * Utility method to send a player's input to the server, wrapped in a
     * networking.Message object.
     *
     * @param input The player's input.
     */
    private void sendInput(int input) {
        sendMessage(new Message(String.valueOf(input)));
    }


    /**
     * This method allows us to handle incoming messages from the server. In
     * this case, we're using the message as a new authoritative game state.
     * N.B. that this method is called by the internal mechanisms and won't
     * need to be called manually by anyone implementing a NetworkClient.
     *
     * @param m The message we've received from the server.
     */
    @Override
    public void handleMessage(Message m) {
        int gameState = Integer.valueOf(m.getText());
        System.out.println("Got authoritative game state " + gameState + " from server. Updating local state now...");
        this.gameState = gameState;
    }
}


/**
 * Spin up a server to demonstrate the client-server synchronisation.
 */
public class NetworkClientMain {
    public static void main(String... args) {
        Client client = new Client("localhost", 8080);
        new Thread(client).start();
    }
}
