class Circle {

    /**
     * Draws the pinball
     *
     * @param x the current x position of the centre of the pinball
     * @param y the current y position of the centre of the pinball
     */
    static void paintPinball(Window window, int x, int y, int radius) {
        float radius2 = window.glScaleDistance(radius);
        float x2 = window.glScaleX(x);
        float y2 = window.glScaleY(y);
        float[] vertices = createCircle(x2, y2, 0.5f, radius2);
        Model circle1 = new Model(vertices);
        circle1.render(vertices);
    }

    /**
     * Draws the powerUp(item class)
     *
     * @param x the current x position of the centre of the powerUp
     * @param y the current y position of the centre of the powerUp
     */
    static public void paintItem(Window window, int x, int y, int radius) {
        float radius2 = window.glScaleDistance(radius);
        float x2 = window.glScaleX(x);
        float y2 = window.glScaleY(y);
        float[] vertices = createCircle(x2, y2, 0.3f, radius2);
        Model circle1 = new Model(vertices);
        circle1.render(vertices);
    }

    /**
     * Calculates all the points of the circumference of the circle
     *
     * @param posx the current x position of the centre of the pinball
     * @param posy the current y position of the centre of the pinball
     * @param posz the current z position of the centre of the pinball
     * @return all the points of the circle
     */
    private static float[] createCircle(float posx, float posy, float posz, double radius) {
        int noSides = 360;
        int noVertices = noSides + 2;
        float doublePI = (float) Math.PI * 2;

        int i = 1;
        float[] vertices = new float[noVertices * 3];
        float x = posx;
        float y = posy;
        float z = posz;
        vertices[0] = x;
        vertices[1] = y;
        vertices[2] = z;
        for (int j = 3; j < (noVertices * 3); j = j + 3) {
            vertices[j] = (float) (x + (radius * Math.cos(i * doublePI / noSides)));
            vertices[j + 1] = (float) (y + (radius * Math.sin(i * doublePI / noSides)));
            vertices[j + 2] = z;
            i++;
        }

        return vertices;
    }

}
