package main;

import java.io.Serializable;

import static org.lwjgl.opengl.GL11.glColor4f;


class Platform implements Serializable {

    private int dy;
    private int width, height;
    private double x, y;
    private double highestPoint;
    public boolean isNull;

    /*
     *Constructor for platform object
     *@param x the current x position of the platform(the top left corner of the platform)
     *@param y the current y position of the platform(the top left corner of the platform)
     *@param width the width of the platform
     *@param height the height of the platform
     */
    Platform(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        dy = Constants.PLATFORM_START_DY;
        highestPoint = 200;
        this.isNull = false;
    }

    /*
     *Updates the position of the platform
     *@param game the game class object
     *@param ball the ball class object
     */
    void update(GameState game, double timeStep) {
        double timeStepPixels = timeStep * Constants.TIME_STEP_COEFFICIENT;
        Ball ball = game.getBall();

        // If platform is offscreen, move it back on!
        if (y > game.getWindowHeight()) {
            y = -300;
            x = game.random.nextInt(game.getWindowWidth() - 140);
            return;
        }

        // If we've got the flying powerup, don't bother with collision.
        if (ball.getCountFlyPower() > 0) {
            y += Constants.FLY_POWERUP_SPEED;
            game.score += Constants.FLY_POWERUP_SPEED;
            return;
        }

        // Otherwise, first check for permission.
        checkForCollision(ball);

        // Update platform position.

        y += dy * timeStepPixels;
        game.score += dy * timeStepPixels;
    }


    /*
     * Checks if any ball has collided with the platform
     * @param ball the ball object
     */
    private void checkForCollision(Ball ball) {
        double ballX = ball.getX();
        double ballY = ball.getY();
        int radius = ball.getRadius();

        double ballBottom = ballY + radius;
        double rectTop = y;
        double rectLeft = x;
        double rectRight = x + width;

        // Check the ball's height is colliding with the top of the platform.
        double tolerance = Math.abs(ball.getMaxSpeed());  // pixels
        if (ballBottom < rectTop - tolerance) return;
        if (ballBottom > rectTop + tolerance) return;

        // Check the ball is aligned with the top of the platform.
        if (ballX < rectLeft) return;
        if (ballX > rectRight) return;

        // Check that the ball is moving downwards.
        if (ball.getDy() < 0) return;


        // If the ball has collided with the top of the platform ~Tom
        // AudioEngine.getInstance().playTrack(AudioEngine.BOING); // Play the boing sound
        System.out.println("[INFO] Platform.checkForCollision : Collision! Setting ball's new dY.");
        ball.setY(rectTop - radius);
        ball.setDy(-ball.getMaxSpeed());
    }

    /*
     * Draws the platform
     */
    void paint(Window game, boolean opponent) {
        if(isNull) return;
        double scaledX = game.glScaleX(x, opponent, Screen.GAME);
        double scaledY = game.glScaleY(y);
        double widthGl = game.glScaleDistance(width);
        double heightGl = game.glScaleDistance(height);

        double[] verticesb = {scaledX, scaledY, 0.3f, scaledX, (scaledY - heightGl), 0.3f, (scaledX + widthGl), (scaledY - heightGl), 0.3f, (scaledX + widthGl), scaledY, 0.3f};
        glColor4f(1, 0, 0, 0);
        Rectangle.drawrectangle(verticesb);
    }
    
    public void setDx(int dx){
    	this.dy = dx;
    }
}
