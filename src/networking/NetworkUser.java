package networking;

import java.util.Optional;

/**
 * A NetworkUser is a handy way of reusing message-routing code between
 * both NetworkServer and NetworkClient.
 */
public abstract class NetworkUser implements MessageHandler {
    public NetworkEngine engine;

    /**
     * Wrapper method around the NetworkEngine's sendMessage method.
     * @param m The message to send to our connected device.
     */
    public boolean sendMessage(Message m) {
        return engine.sendMessage(m);
    }

    /**
     * Call handleMessage for each message returned by the NetworkEngine.
     */
    public boolean handleMessages() {
        if (!engine.isRunning()) return false;
        Optional<Message> maybeMessage;
        while ((maybeMessage = engine.nextMessage()).isPresent()) {
            Message m = maybeMessage.get();
            this.handleMessage(m);
        }
        return true;
    }

    public Message waitForMessage() { return engine.waitForMessage(); }
}
