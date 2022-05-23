import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;

public class GameField extends JPanel implements ActionListener {
    private final int SIZE = 320;
    private final int DOT_SIZE = 16;
    private final int ALL_DOTS = 400;
    private Image dot;
    private Image APPLE;
    private int AppleX;
    private int AppleY;
    private int[] x = new int[ALL_DOTS];
    private int[] y = new int[ALL_DOTS];
    private int dots;
    private Timer timer;
    private boolean right = true;
    private boolean left = false;
    private boolean up = false;
    private boolean down = false;
    private boolean InGame = true;


    public GameField() {
        setBackground(Color.BLACK);
        initGame();
        LoadImages();
        addKeyListener(new FieldKeyListener());
        setFocusable(true);
    }
    public void initGame() {
        dots = 3;
        for (int i = 0; i < dots; i++) {
            x[i] = 48 - i*DOT_SIZE;
            y[i] = 48;
        }
        timer = new Timer(250,this);
        timer.start();
        createApple();
    }
    public void createApple(){
        AppleX = new Random().nextInt(20)*DOT_SIZE;
        AppleY = new Random().nextInt(20)*DOT_SIZE;
    }
    public void LoadImages() {
        ImageIcon iia = new ImageIcon("src/APPLE.png");
        APPLE = iia.getImage();
        ImageIcon iid = new ImageIcon("src/dot.png");
        dot = iid.getImage();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if(InGame){
            g.drawImage(APPLE,AppleX,AppleY,this);
            for(int i = 0; i < dots; i++){
                g.drawImage(dot,x[i],y[i],this);
            }
        } else{
            String str = "Game Over";
            //Font f = new Font("Arial",14,Font.BOLD);
            g.setColor(Color.yellow);
            //g.setFont(f);
            g.drawString(str,125,SIZE/2);
        }
    }

    public void move(){
        for(int i = dots; i > 0; i--){
            x[i] = x[i-1];
            y[i] = y[i-1];
        }
        if(left){
            x[0] -= DOT_SIZE;}
        if(right){
            x[0] += DOT_SIZE;}
        if(up){
            y[0] -= DOT_SIZE;}
        if(down){
            y[0] += DOT_SIZE;}

    }
    public void checkApple(){
        if(x[0] == AppleX && y[0] == AppleY){
            dots++;
            createApple();
        }
    }
    public void checkCollisions(){
        for (int i = dots; i > 0; i--) {
            if(i>4 && x[0] == x[i] && y[0] == y[i]){
                InGame = false;
            }
        }
        if(x[0]>SIZE){
            InGame = false;
        }
        if(x[0]<0){
            InGame = false;
        }
        if(y[0]>SIZE){
            InGame = false;
        }
        if(y[0]<0){
            InGame = false;
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(InGame){
            checkApple();
            checkCollisions();
            move();
        }
        repaint();

    }
    class FieldKeyListener extends KeyAdapter{
        @Override
        public void keyPressed(KeyEvent e) {
            super.keyPressed(e);
            int key = e.getKeyCode();
            if(key == KeyEvent.VK_LEFT && !right){
                left = true;
                up = false;
                down = false;
            }
            if(key == KeyEvent.VK_RIGHT && !left){
                right = true;
                up = false;
                down = false;
            }
            if(key == KeyEvent.VK_UP && !down){
                up = true;
                right = false;
                left = false;
            }
            if(key == KeyEvent.VK_DOWN && !up){
                down = true;
                right = false;
                left = false;
            }
        }
    }
}
