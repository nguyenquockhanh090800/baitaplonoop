import javax.swing.*;
import java.awt.*;
import java.awt.image.CropImageFilter;
import java.awt.image.FilteredImageSource;
import java.awt.Image.*;
import java.awt.image.ImageObserver;
import java.awt.image.ImageProducer;
import java.io.File;
import java.util.regex.Matcher;

public class Screen extends JPanel implements Runnable {
    public Thread thread = new Thread(this);

    public static Image[] tileset_ground = new Image[100];
    public static Image[] tileset_air = new Image[100];
    public static Image[] tileset_res = new Image[100];
    public static Image[] tileset_mob = new Image[100];
    public static Image start;
    //public static Image screenn;


    public static int myWidth, myHeight; // rộng Screen, dài Screen
    public static int coinage = 10, health = 100;
    public static int killed = 0,killsTowin = 0,level = 1,maxLevel = 3;//killstowin lấy dữ liệu từ file text
    public static int winTime = 4000, winFrame = 0; // thời gian qua màn(thắng)

    public static boolean isFirst = true;
    public static boolean isDebug1 = false;
    public static boolean isDebug2 = false;
    public static boolean isDebug3 = false;
    public static boolean isWin = false;

    public static Point mse = new Point(0,0);
    public static  Room room;
    public static Save save;
    public static Store store;
    public static Enemy[] enemies = new Enemy[100];

    public Screen(Frame frame){
        frame.addMouseListener(new KeyHandel());
        frame.addMouseMotionListener(new KeyHandel());
        thread.start(); // chạy chủ đề

    }

    public static void hasWon(){
        if(killed == killsTowin){
            isWin = true;
            killed = 0;
            coinage = 0;
        }
    }

    public void define(){
        room = new Room();// khởi tạo với groundID = 0; airID =-1;
        save = new Save();
        store = new Store();
        coinage = 50;
        health = 100;


        /*for(int i = 0; i< tileset_ground.length;i++){
            tileset_ground[i] = new ImageIcon("res/tileset_ground1.png").getImage();
            tileset_ground[i] = createImage(new FilteredImageSource(tileset_ground[i].getSource(),new CropImageFilter(0,26*i,26,26)));// cắt ảnh
        }*/
        tileset_ground[0] = new ImageIcon("res/cor.jpg").getImage();
        tileset_ground[1] = new ImageIcon("res/road1.jpg").getImage();
        for(int i = 0; i< tileset_air.length;i++){
            tileset_air[i] = new ImageIcon("res/tileset_air1.png").getImage();
            tileset_air[i] = createImage(new FilteredImageSource(tileset_air[i].getSource(),new CropImageFilter(0,26*i,26,26)));
        }
        tileset_res[0] = new ImageIcon("res/cel1.png").getImage();
        tileset_res[1] = new ImageIcon("res/heart1.png").getImage();
        tileset_res[2] = new ImageIcon("res/coin1.png").getImage();
        tileset_res[3] = new ImageIcon("res/pause.png").getImage();
        tileset_res[4] = new ImageIcon("res/x2.png").getImage();
        //tileset_res[5] = new ImageIcon("res/upgrade.png").getImage();
        tileset_res[6] = new ImageIcon("res/sell.png").getImage();
        tileset_res[7] = new ImageIcon("res/help.png").getImage();
        tileset_mob[0] = new ImageIcon("res/mob1.png").getImage();
        tileset_res[8] = new ImageIcon("res/helpclose.png").getImage();
        tileset_res[9] = new ImageIcon("res/x1.png").getImage();
        tileset_res[10] = new ImageIcon("res/tieptuc.png").getImage();

        start = new ImageIcon("res/start.png").getImage();
        //screenn = new ImageIcon("res/screen.jpg").getImage();

        save.loadSave(new File("save/mission" + level ));//gán groundTD, airID cho block từ file text;
        for(int i = 0; i<enemies.length;i++){
            enemies[i] = new Enemy();
        }
    }

    public void paintComponent(Graphics g){ // vẽ mọi thứ lên màn hình

        if(isFirst){
            myWidth = getWidth();
            myHeight = getHeight();
            define();
            isFirst = false;
        }
        g.setColor(new Color(128,128,128)); // màu screen;
        g.fillRect(0,0,getWidth(),getHeight());
        //g.drawImage(screenn,0,0,1280,700,null);
        g.setColor(new Color(0,0,0));// màu viền screen;
        g.drawLine(room.block[0][0].x-1,0,room.block[0][0].x-1, room.block[room.worlHeight - 1][0].y + room.blockSize);//Draw the Left line
        g.drawLine(room.block[0][room.worlWidth - 1].x +room.blockSize,0,room.block[0][room.worlWidth - 1].x +room.blockSize, room.block[room.worlHeight - 1][0].y + room.blockSize);//         Right
        g.drawLine(room.block[0][0].x,room.block[room.worlHeight-1][0].y + room.blockSize,room.block[0][room.worlWidth-1].x + room.blockSize,room.block[room.worlHeight-1][0].y + room.blockSize);//    bottom

        room.draw(g); //Draw room = block.drwa

        for(int i = 0 ; i< enemies.length;i++){
            if(enemies[i].inGame){
                enemies[i].draw(g);
            }
        }

        store.draw(g); //Draw store
        if(health < 1){// draw sau game
            g.setColor(new Color(240,20,20));
            g.fillRect(0,0,myWidth,myHeight);
            g.setColor(new Color(225,225,225));
            g.setFont(new Font("Courier New",Font.BOLD,14));
            g.drawString("Game Over, Unlucky!...",10,20);
        }
        if(isWin){
            g.setColor(new Color(255,255,255));
            g.fillRect(0,0,getWidth(),getHeight());
            g.setColor(new Color(0,0,0));
            g.setFont(new Font("Courier New",Font.BOLD,14));
            if(level == maxLevel){
                g.drawString("You Win..." ,10,20);
            }else {
                g.drawString("You have passed 1 stage..." ,10,20);
            }

        }
        //g.drawImage(start,50,50,140,90,null);


    }
    public int spawnTime = 2400, spawnFrame =0;// spawnFrame thời điểm ra quái nếu khác không vòng đầu ít thời gian hơn
    public void enemySpawner(){
        if(spawnFrame >= spawnTime){
            for(int i = 0; i< enemies.length;i++){
                if(!enemies[i].inGame){
                    enemies[i].spawnMob(Value.mobGreeny);
                    break;
                }
            }
            spawnFrame = 0;
        }else {
            spawnFrame +=1;
        }

    }
    // public static int fpsFrame = 0, fgs = 1000000;
    public void run(){ //chạy
        while (true){
            if(!isFirst && health > 0 && !isWin){
                room.physic();
                enemySpawner();
                for(int i =0; i<enemies.length;i++){
                    if(enemies[i].inGame){
                        enemies[i].physic();
                    }
                }
            }else {
                if(isWin){
                    if(winFrame >= winTime){
                        if(level > maxLevel){
                            System.exit(0);
                        }else {
                            level += 1;
                            define();
                            isWin = false;
                        }
                        winFrame = 0;
                    }else {
                        winFrame +=1;
                    }
                }
            }
            repaint();

            try {
                thread.sleep(1);
            }catch (Exception e){

            }

        }
    }
}

