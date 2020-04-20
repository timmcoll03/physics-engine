import java.awt.*;

public class Robot {

    public int xpos, ypos, width, height;
    public double velocity = 0, terminalvel = 5, facceleration = .2, g = 9.81, drag = .15, ythrust, xthrust, angle;
    public boolean isfThrusting, isbThrusting, isRight, isLeft, isJumping, isFreeFalling=true;
    public boolean isAlive;
    public Rectangle rec;

    public Robot(int pXpos, int pYpos) {
        xpos = pXpos;
        ypos = pYpos;
        isfThrusting = false;
        isbThrusting = false;
        isAlive = true;
        angle = 0;
        rec = new Rectangle(xpos, ypos, width, height);
    }

    public void forwardthrust() {

        if (velocity < terminalvel) {
            velocity += (facceleration - drag);
        }
        ythrust = Math.sin(Math.toRadians(angle)) * velocity;
        xthrust = Math.cos(Math.toRadians(angle)) * velocity;
        ypos += ythrust;
        xpos += xthrust;
        rec = new Rectangle(xpos, ypos, width, height);
    }

    public void backwardthrust() {

        if (velocity < terminalvel) {
            velocity -= (facceleration - drag);
        }
        ythrust = Math.sin(Math.toRadians(angle)) * velocity;
        xthrust = Math.cos(Math.toRadians(angle)) * velocity;
        ypos -= ythrust;
        xpos -= xthrust;
        rec = new Rectangle(xpos, ypos, width, height);
    }
}
