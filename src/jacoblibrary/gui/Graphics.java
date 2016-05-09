package jacoblibrary.gui;

import java.awt.*;
import java.awt.image.BufferedImage;
import javax.swing.JPanel;

public abstract class Graphics extends JPanel implements Runnable, Pauseable {
    
    private Application parent;
    
    private Thread thread;
    private boolean running;
    private boolean paused = false;
	
    private int FPS = 30;
    private double averageFPS;

    public Graphics(Application parent) {
        
        super();
        this.parent = parent;

        this.setBackground(Color.WHITE);
        
    }
    
    public void start() {
        
        if (thread == null) {
            thread = new Thread(this);
            thread.start();
	}
        
    }

    @Override
    public void run() {

        running = true;

        long startTime;
        long URDTimeMillis;
        long targetTime;
        long waitTime;
        long totalTime = 0;

        long frameCount = 0;
        int maxFrameCount = 30;

        while (running) {

            startTime = System.nanoTime();

            if (!paused && getWidth() != 0 && getHeight() != 0) {

                BufferedImage image = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_RGB);
                Graphics2D g = (Graphics2D) image.getGraphics();
                g.setColor(getBackground());
                g.fillRect(0, 0, getWidth(), getHeight());

                update();
                render(g);
                draw(image);

            }

            URDTimeMillis = (System.nanoTime() - startTime) / 1000000;
            targetTime = 1000 / FPS;
            waitTime = targetTime - URDTimeMillis;

            try {
                Thread.sleep(waitTime);
            } catch (Exception e) {
            }

            totalTime += System.nanoTime() - startTime;
            frameCount++;

            if (frameCount == maxFrameCount) {
                averageFPS = 1000.0 / ((totalTime / frameCount) / 1000000.0);
                totalTime = 0;
                frameCount = 0;
            }

        }

    }
    
    public abstract void update();
    
    public abstract void render(Graphics2D g);
    
    private void draw(BufferedImage image) {

        if (parent.isDisplayable()) {
            try {
                java.awt.Graphics g2 = this.getGraphics();
                g2.drawImage(image, 0, 0, null);
                g2.dispose();
            } catch (Exception e) {
                //e.printStackTrace();
            }
        }
        
    }

    @Override
    public void pause() {
        paused = true;
    }

    @Override
    public void resume() {
        paused = false;
    }

    public int getFPS() { return this.FPS; }
    public void setFPS(int FPS) {
        this.FPS = FPS;
    }

    public int getWidth() { return this.getPreferredSize().width; }
    public int getHeight() { return this.getPreferredSize().height; }

}
