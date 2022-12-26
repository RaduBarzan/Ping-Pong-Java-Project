import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.Random;

public class Ball {
    public double x;
    public double y;
    public double dx;
    public double dy;

    private double angle;

    private final double speed = 0.8;
    private final int width = 5;
    private final int heigth = 5;

    Ball(){
        this.x = Game.width / 2;
        this.y = 40;

        angle = new Random().nextInt(120 - 60) + 60 + 1;
        while(angle<110 && angle>70) {
            angle = new Random().nextInt(120 - 60) + 61;
        }

        this.dx = Math.cos(Math.toRadians(angle));
        this.dy = Math.sin(Math.toRadians(angle));
    }
    public void render(Graphics g){
        tick();
        g.setColor(new Color(0, 255, 0));
        g.fillRect((int)x, (int)y, width, heigth);
    }

    public void tick(){

        x +=  dx * speed;
        y += dy * speed;

        // Ball Outside
        if(y >= Game.heigth)
        {
            System.out.println("Player's 2 point!");
            new Game().start();
            return;
        }else if(y <= 0) {
            System.out.println("Player's 1 point");
            new Game().start();
            return;
        }

        if(x<=0 || x>=Game.width - width)
            dx*= -1;
        Rectangle bounds = new Rectangle((int)x,(int)y, width, heigth);
        Rectangle boundsPlayer = new Rectangle(Game.player.x, Game.player.y, Game.player.WIDTH, Game.player.HEIGHT);
        Rectangle boundsPlayer2 = new Rectangle((int)Game.player2.x, Game.player2.y, Game.player2.WIDTH, Game.player2.HEIGHT);

        if(bounds.intersects(boundsPlayer)) {

            angle = new Random().nextInt(120 - 60) + 61;

            while(angle<120 && angle>70) {
                angle = new Random().nextInt(120 - 60) + 61;
            }

            this.dx = Math.sin(Math.toRadians(angle));
            this.dy = Math.cos(Math.toRadians(angle));

            if(dy>0)
                dy *= -1;
        }else if(bounds.intersects(boundsPlayer2)) {
            angle = new Random().nextInt(120 - 60) + 61;

            while(angle<120 && angle>70) {
                angle = new Random().nextInt(120 - 60) + 61;
            }

            this.dx = Math.sin(Math.toRadians(angle));
            this.dy = Math.cos(Math.toRadians(angle));

            if(dy<0)
                dy *= -1;
        }

    }
}