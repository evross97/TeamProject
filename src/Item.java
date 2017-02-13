import static org.lwjgl.opengl.GL11.glColor4f;

import java.util.Random;


public class Item {

    private int x, y, dy, radius;
    private Graphics game;

    /*
     * Constructor for item class(PowerUps)
     * @param y the y position of the powerUp
     */
    public Item(int y) {
        this.y = y;
        Random r = new Random();
        x = r.nextInt(700) + radius + 100;
        radius = 10;
        dy = 2;
    }

    /*
     * Get method for Y
     */
    public int getY() {
        return y;
    }
    /*
     * Set method for Y
     * @param y
     */
    public void setY(int y) {
        this.y = y;
    }
    /*
     * Get method for X
     */
    public int getX() {
        return x;
    }
    /*
     * Set method for x
     * @param x
     */
    public void setX(int x) {
        this.x = x;
    }

    /*
     * Updates the position of the PowerUp
     */
    public void update(Graphics game, Ball ball) {
        y += dy;
        this.game = game;
         checkForCollision(ball);
         if(y > game.getHeight() - radius ){
             Random r = new Random();
             y = - game.getHeight()-100 - r.nextInt(300);
             x = r.nextInt(game.getWidth()-100)+ radius+ 100;

         }

    }
    /*
     * Checks for collision between the ball and the powerUp
     * @param ball
     */
    private void checkForCollision(Ball ball) {
        int ballX = ball.getX();
        int ballY = ball.getY();
        int ballR = ball.getRadius();

        int a = x - ballX;
        int b = y - ballY;
        int collide = radius + ballR;
        //distance between object centers
        double c = Math.sqrt((double) (a*a) + (double) (b*b));
        if(c < collide){
            performAction(ball);
            x = 0;
            y = game.getHeight() + 100;
        }

    }
    /*
     * Changes the behaviour of the ball depending on the powerUp
     * @param ball
     */
    private void performAction(Ball ball) {
    }

    /*
     * Paints the powerUps
     */
    public void paint(){
        float[] vertices = createCircle(game.changexCoord(x),game.changeyCoord(y),0.2f,0.02f);
        Model circle1 = new Model(vertices);

        circle1.render(vertices);
    }

    /**
     * Calculates all the points of the circumference of the circle
     * @param posx the current x position of the centre of the powerUp
     * @param posy the current y position of the centre of the powerUp
     * @param posz the current z position of the centre of the powerUp
     * @return all the points of the circle
     */
    private static float[] createCircle(float posx, float posy, float posz, double radius)
    {
        int noSides = 360;
        int noVertices = noSides + 2;
        float doublePI = (float) Math.PI * 2;

        int i = 1;
        float[] vertices = new float[noVertices*3];
        float x = posx;
        float y = posy;
        float z = posz;
        vertices[0] = x;
        vertices[1] = y;
        vertices[2] = z;
        for(int j= 3; j < (noVertices * 3); j = j + 3)
        {

            glColor4f(0,0,1,0);

            vertices[j] = (float) ( x + (radius * Math.cos(i * doublePI / noSides)));
            vertices[j+1] = (float) ( y + (radius * Math.sin(i * doublePI / noSides)));
            vertices[j+2] = z;
            i++;
        }

        return vertices;
    }
}
