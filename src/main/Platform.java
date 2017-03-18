package main;

import com.sun.xml.internal.bind.v2.runtime.reflect.opt.Const;

import java.io.Serializable;

import static org.lwjgl.opengl.GL11.glColor4f;


class Platform extends CollidablePlatform implements Serializable {

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
        checkForCollision(ball, timeStepPixels, x, y, width);

        // Update platform position.
        y += dy * timeStepPixels;
        game.score += dy * timeStepPixels;
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
