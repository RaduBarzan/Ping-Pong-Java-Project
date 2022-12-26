import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;

public class Game extends Canvas implements Runnable, KeyListener{

    boolean isRunning = false;

    private BufferStrategy bs;
    private BufferedImage image;

    public static Player player;
    public static Player player2;
    public static Ball ball;

    public static final int width = 240;
    public static final int heigth = 160;
    private final int scale = 5;

    private final int speed = 3;
    private final int fps = 60;
    private final long ns = 1000000000/ fps;
    private long lastTime = System.nanoTime();

    private boolean rightPressedPlayer1 = false;
    private boolean leftPressedPlayer1 = false;
    private boolean rightPressedPlayer2 = false;
    private boolean leftPressedPlayer2 = false;


    public static void main(String[] args) {
        Game game = new Game();
        Thread thread = new Thread(game);
        JFrame frame = new JFrame();
        game.start();
        game.startFrame(frame);
        thread.start();
    }

    Game(){
        this.setPreferredSize(new Dimension(width * scale, heigth * scale));
        this.addKeyListener(this);
        image = new BufferedImage(width, heigth, BufferedImage.TYPE_INT_RGB);
    }

    public synchronized void start() {
        player = new Player(100, 155);
        player2 = new Player(100, 0);
        ball = new Ball();
        isRunning = true;
    }

    public void startFrame(JFrame frame) {
        frame.add(this);
        frame.pack();
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    public void tick() {
        if(rightPressedPlayer1) {
            player.rightPressed();
        }
        if(rightPressedPlayer2) {
            player2.rightPressed();
        }
        if (leftPressedPlayer1) {
            player.leftPressed();
        }
        if (leftPressedPlayer2) {
            player2.leftPressed();
        }
    }

    public void render() {
        bs = this.getBufferStrategy();

        if(bs == null) {
            this.createBufferStrategy(3);
            return;
        }

        Graphics g = image.getGraphics();

        // Background
        g.setColor(new Color(0, 0, 0));
        g.fillRect(0, 0, width, heigth);

        player.render(g);
        ball.render(g);
        player2.render(g);

        g = bs.getDrawGraphics();

        g.drawImage(image, 0, 0, width * scale, heigth * scale,null);
        bs.show();
    }

    public void run() {
        while(isRunning) {
            this.requestFocus();
            long now = System.nanoTime();

            if(now - lastTime >= ns/ speed) {
                tick();
                render();
                lastTime = now;
            }
        }

    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_RIGHT)
            rightPressedPlayer1 = true;
        else if (e.getKeyCode() == KeyEvent.VK_LEFT)
            leftPressedPlayer1 = true;
        if(e.getKeyCode() == KeyEvent.VK_D)
            rightPressedPlayer2 = true;
        else if (e.getKeyCode() == KeyEvent.VK_A)
            leftPressedPlayer2 = true;

    }

    @Override
    public void keyReleased(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_RIGHT)
            rightPressedPlayer1 = false;
        else if (e.getKeyCode() == KeyEvent.VK_LEFT)
            leftPressedPlayer1 = false;
        if(e.getKeyCode() == KeyEvent.VK_D)
            rightPressedPlayer2 = false;
        else if (e.getKeyCode() == KeyEvent.VK_A)
            leftPressedPlayer2 = false;

    }

}