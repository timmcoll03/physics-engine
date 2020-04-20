//testing

import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferStrategy;
import java.awt.event.KeyListener;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Image;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class Main implements Runnable, KeyListener {

    final int WIDTH = 700; //800
    final int HEIGHT = 700; //700
    public BufferStrategy bufferStrategy;
    public JFrame frame;
    public JPanel panel;
    public Canvas canvas;

    public Robot robot, floor;

    public static void main(String[] args) {

        Main ex = new Main();
        new Thread(ex).start();

    }

    public Main() {

        setUpGraphics();

        robot = new Robot(200, 200);
        robot.width = 20;
        robot.height = 20;

        floor = new Robot(20, HEIGHT-100);
        floor.width = WIDTH-40;
        floor.height = 25;
        floor.isFreeFalling=false;




    }

    public void run() {



        while (true) {
            renderGraphics();
            moveThings();
            CollisionCheck();
            pause(20);
        }
    }

    public void moveThings() {

        if (robot.isFreeFalling){
            robot.ypos+=10;

        }

        if (robot.isJumping){
            robot.ypos-=20;
        }

        if (robot.isfThrusting) {
            robot.forwardthrust();
        }

        if (robot.isbThrusting){
            robot.backwardthrust();
        }

        if (robot.isRight) {
            robot.angle=0;
        }

        if (robot.isLeft) {
            robot.angle=180;
        }
    }

    public void CollisionCheck() {

        while (robot.ypos+20==floor.ypos){
            robot.isFreeFalling=false;
        }

    }

    public void renderGraphics() {
        Graphics2D g = (Graphics2D) bufferStrategy.getDrawGraphics();
        g.fillRect(0, 0, frame.getWidth(), frame.getHeight());

        Rectangle bot = new Rectangle(robot.xpos, robot.ypos, robot.width, robot.height);
        Rectangle flr = new Rectangle(floor.xpos, floor.ypos, floor.width, floor.height);

        if (robot.isAlive) {

            //g2.rotate(Math.toRadians(robot.angle), robot.xpos + robot.width / 2, robot.ypos + robot.height / 2);
            g.setColor(Color.gray);
            g.draw(bot);
            g.fill(bot);


            g.setColor(Color.white);
            g.draw(flr);
            g.fill(flr);

            g.setColor(Color.white);
            g.drawString(robot.angle + "°", 20, 20);

        }

        g.dispose();

        bufferStrategy.show();

    }

    public void setUpGraphics() {
        frame = new JFrame("Robotics Simulator");   //Create the program window or frame.  Names it.

        panel = (JPanel) frame.getContentPane();  //sets up a JPanel which is what goes in the frame
        panel.setPreferredSize(new Dimension(WIDTH, HEIGHT));  //sizes the JPanel
        panel.setLayout(null);   //set the layout

        // creates a canvas which is a blank rectangular area of the screen onto which the application can draw
        // and trap input events
        canvas = new Canvas();
        canvas.setBounds(0, 0, WIDTH, HEIGHT);
        canvas.setIgnoreRepaint(true);


        panel.add(canvas);  // adds the canvas to the panel.

        // frame operations
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  //makes the frame close and exit nicely
        frame.pack();  //adjusts the frame and its contents so the sizes are at their default or larger
        frame.setResizable(false);   //makes it so the frame cannot be resized
        frame.setVisible(true);      //IMPORTANT!!!  if the frame is not set to visible it will not appear on the screen!

        // sets up things so the screen displays images nicely.

        canvas.createBufferStrategy(2);
        bufferStrategy = canvas.getBufferStrategy();
        canvas.requestFocus();
        canvas.addKeyListener(this);

        frame.addComponentListener(new ComponentAdapter() {
            public void componentResized(ComponentEvent evt) {
//                Component c = (Component)evt.getSource();
                canvas.setBounds(0, 0, frame.getWidth(), frame.getHeight());
            }
        });

        System.out.println("Graphic Setup Finished");

    }

    //Pauses or sleeps the computer for the amount specified in milliseconds
    public void pause(int time) {
        //sleep
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
        }
    }

    @Override
    public void keyTyped(KeyEvent keyEvent) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        if (key == 39) {
            robot.isRight = true;
        }
        if (key == 37) {
            robot.isLeft = true;
        }
        if (key == 38) {
            robot.isfThrusting = true;
        }
        if (key == 40) {
            robot.isbThrusting = true;
        }

        if (key == 32) {
            robot.isJumping = true;
            pause(20);
            robot.isJumping = false;
            System.out.println("Jump");
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int key = e.getKeyCode();
        if (key == 39) {
            robot.isRight = false;
        }

        if (key == 37) {
            robot.isLeft = false;
        }
        if (key == 38) {
            robot.isfThrusting = false;
        }

        if (key == 40) {
            robot.isbThrusting = false;
        }

    }
}
