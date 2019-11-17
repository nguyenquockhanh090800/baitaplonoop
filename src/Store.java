import com.sun.org.apache.xerces.internal.impl.dv.xs.ExtendedSchemaDVFactoryImpl;
import javafx.scene.Scene;
import sun.security.krb5.SCDynamicStoreConfig;

import java.awt.*;

public class Store {
    public static int shopWidth = 4;// độ rộng cửa hàng
    public static int buttonSize = 52; // kích thước nút
    public static int cellSpace = 10;// khoảng trống hai nút
    public static int awayFromRoom = 20; // khoảng cách với room
    public static int iconSize = 20;
    public static int iconSpace = 6; // khoảng cách của chỉ số với icon coin + health
    public static int iconTextY = 15; // độ cao chỉ số
    public static int itemIn = 4;
    public static int heldID = -1;// id các ô store
    public static int realID = -1; // chỉ số các ô store
    public static int[] buttonID = {Value.airTowerLaser,Value.airTowerLaser2,Value.airTowerLaser3,Value.airTrashCan};
    public static int[] buttonPrice = {10,20,30,0};
    public static Rectangle buttonstart1;
    public static boolean pause = false;
    public static boolean x2 = false;
    public static boolean start1 = false;
    public static boolean sell = false;




    public Rectangle[] button = new Rectangle[shopWidth];
    public Rectangle buttonHealth;
    public Rectangle buttonCoins;
    public Rectangle buttonpause;
    public Rectangle buttonx2;
    public Rectangle information;
    //public Rectangle upgrade;
    public Rectangle buttonsell;
    public Rectangle help;
    public Rectangle helpclose;


    public boolean holdsItem = false;
    public boolean holdsItem2 = false;
    public int xm1,xm2,xm3;
    public int ym1,ym2,ym3;
    public boolean map1,map2,map3;

    public Store(){
        define();
    }

    public void click(int mouseButton) {
        if(buttonsell.contains(Screen.mse)){
            if(mouseButton == 1){
                sell = true;
                if(holdsItem2 && map1){
                    Screen.room.block[ym1][xm1].ariID = -1;
                    Screen.room.block[ym1][xm1].shotEnemy= -1;
                    //sell = false;
                    Value.sell = false;
                    Screen.coinage+= 5;
                    holdsItem2 = false;
                }
                if(holdsItem2 && map2){
                    Screen.room.block[ym2][xm2].ariID = -1;
                    Screen.room.block[ym2][xm2].shotEnemy = -1;
                    //sell = false;
                    Value.sell = false;
                    Screen.coinage +=10;
                    holdsItem2 = false;
                }
                if(holdsItem2 && map3){
                    Screen.room.block[ym3][xm3].ariID = -1;
                    Screen.room.block[ym3][xm3].shotEnemy= -1;
                    //sell = false;
                    Value.sell = false;
                    Screen.coinage +=20;
                    holdsItem2 = false;
                }
            }
        }
        if(helpclose.contains(Screen.mse)){
            if(mouseButton == 1){
             Value.inhelp = false;
            }
        }
        if(help.contains(Screen.mse)){
            if(mouseButton == 1){
                Value.inhelp = true;
            }
        }
        if(buttonstart1.contains((Screen.mse))){
            if(mouseButton == 1){
                start1 = true;
                Value.mobspeed = 1;
            }
        }

        if (buttonpause.contains(Screen.mse)) {
            if(start1 == true) {
                if (mouseButton == 1) {
                    if (pause == false) {
                        pause = true;
                        Value.mobspeed = 0;
                    } else if (pause == true) {
                        pause = false;
                        if (x2) Value.mobspeed = 2;
                        if (!x2) Value.mobspeed = 1;
                    }
                }
            }
        }
        if(buttonx2.contains(Screen.mse)){
            if(start1 == true) {
                if (mouseButton == 1) {
                    if (x2 == false) {
                        x2 = true;
                        Value.mobspeed = 2;
                    } else if (x2 == true) {
                        x2 = false;
                        Value.mobspeed = 1;
                    }
                }
            }
        }
        if(start1 == true) {
            if (pause == false) {
                for (int y = 0; y < Screen.room.block.length; y++) {
                    for (int x = 0; x < Screen.room.block[0].length; x++) {
                        if (Screen.room.block[y][x].contains(Screen.mse)) {
                            if (mouseButton == 1) {
                                if (Screen.room.block[y][x].ariID == Value.airTowerLaser) {
                                    holdsItem2 = true;
                                    xm1 = x;
                                    ym1 = y;
                                    map1 = true;
                                    map2 = false;
                                    map3 = false;
                                }
                                if (Screen.room.block[y][x].ariID == Value.airTowerLaser2) {
                                    holdsItem2 = true;
                                    xm2 = x;
                                    ym2 = y;
                                    map2 = true;
                                    map1 = false;
                                    map3 = false;
                                }
                                if (Screen.room.block[y][x].ariID == Value.airTowerLaser3) {
                                    holdsItem2 = true;
                                    xm3 = x;
                                    ym3 = y;
                                    map3 = true;
                                    map1 = false;
                                    map2 = false;
                                }
                            }
                        }
                    }
                }


                if (mouseButton == 1) {
                    for (int i = 0; i < button.length; i++) {
                        if (button[i].contains(Screen.mse)) {
                            if (buttonID[i] != Value.airAir) {
                                if (buttonID[i] == Value.airTrashCan) {//Delete item nếu item != thùng rác giữ
                                    holdsItem = false;
                                    heldID = Value.airAir;

                                } else {
                                    heldID = buttonID[i];
                                    realID = i;
                                    holdsItem = true;
                                    holdsItem2 = false;
                                }
                            }
                        }
                    }
                    if (holdsItem) { //nếu giữ
                        if (Screen.coinage >= buttonPrice[realID]) {
                            for (int y = 0; y < Screen.room.block.length; y++) {
                                for (int x = 0; x < Screen.room.block[0].length; x++) {
                                    if (Screen.room.block[y][x].contains(Screen.mse)) {
                                        if (Screen.room.block[y][x].groundID != Value.groundRoad && Screen.room.block[y][x].ariID == Value.airAir) {
                                            Screen.room.block[y][x].ariID = heldID;
                                            Screen.coinage -= buttonPrice[realID];
                                        }

                                    }
                                }
                            }
                        }


                    }
                }
                }
            }


    }


    public void define(){
        for(int i = 0; i<button.length;i++){
            button[i] = new Rectangle((Screen.myWidth/2) - ((shopWidth*(buttonSize + cellSpace))/2) + ((buttonSize+cellSpace)*i) - 52*2,(Screen.room.block[Screen.room.worlHeight - 1][0].y) + Screen.room.blockSize + awayFromRoom,buttonSize,buttonSize);//khung
        }
        buttonHealth = new Rectangle(Screen.room.block[0][0].x - 1, button[0].y, iconSize, iconSize);
        buttonCoins = new Rectangle(Screen.room.block[0][0].x - 1, button[0].y + button[0].height - iconSize, iconSize, iconSize);
        buttonstart1 = new Rectangle(50,50,140,90);
        buttonpause = new Rectangle(1280 - 40, 0, 20,20);
        buttonx2 = new Rectangle(1280 - 40 - 30, 0,20,20);
        information = new Rectangle(1280 - 250, 60,220,150);
        //upgrade = new Rectangle(1280 - 250 + 120, 60 + 150 + 20,100,40);
        buttonsell = new Rectangle(1280 -250, 60 + 150 + 20, 100,40);
        help = new Rectangle(50,50 + 90 +30,140,50);
        helpclose = new Rectangle(140/2 - 10,50 + 90 +30 +80 +30 + 40,20,20);


    }
    public void draw(Graphics g) {
        for (int i = 0; i < button.length; i++) {
            if (button[i].contains(Screen.mse)) {// khi trỏ chuột vào
                g.setColor(new Color(255, 255, 255, 150));
                g.fillRect(button[i].x, button[i].y, button[i].width, button[i].height);
            }

            g.drawImage(Screen.tileset_res[0], button[i].x, button[i].y, button[i].width, button[i].height, null);
            if (buttonID[i] != Value.airAir)
                g.drawImage(Screen.tileset_air[buttonID[i]], button[i].x + itemIn, button[i].y + itemIn, button[i].width - (itemIn * 2), button[i].height - (itemIn * 2), null);
            if (buttonPrice[i] > 0) {
                g.setColor(new Color(255, 255, 255));
                g.setFont(new Font("Courier New", Font.BOLD, 14));
                g.drawString("$" + buttonPrice[i], button[i].x + itemIn, button[i].y + itemIn + 10);
            }
        }
        g.drawImage(Screen.tileset_res[1], buttonHealth.x, buttonHealth.y, buttonHealth.width, buttonHealth.height, null);
        g.drawImage(Screen.tileset_res[2], buttonCoins.x, buttonCoins.y, buttonCoins.width, buttonCoins.height, null);
        g.setFont(new Font("Courier New", Font.BOLD, 14));
        g.setColor(new Color(255, 255, 255));
        g.drawString("" + Screen.health, buttonHealth.x + buttonHealth.width + iconSpace, buttonHealth.y +iconSpace);
        g.drawString("$" + Screen.coinage, buttonCoins.x + buttonCoins.width + iconSpace, buttonCoins.y + iconTextY);
        //g.setColor(new Color(0,0,0));

        /*g.setFont(new Font("Courier New", Font.BOLD, 14));
        g.drawRect(1280 - 250 + 120,60 + 150 + 20,100,40);


        g.setFont(new Font("Courier New", Font.BOLD, 14));
        g.drawRect(1280 -250 ,  60 +150 +20,100,40);*/


        g.setColor(new Color(255, 255, 255));

        g.drawRect(1280 - 250, 60, 220, 150);// ve o information
        g.drawRect(50, 50, 140, 90);// ve start;
        g.drawRect(50, 50 + 90 + 30, 140, 50);// help
        g.drawRect(1280 - 40, 0, 20, 20);
        g.drawRect(1280 - 40 - 30, 0, 20, 20);
        g.drawImage(Screen.start, 50, 50, 140, 90, null);

        g.drawImage(Screen.tileset_res[7], 50, 50 + 90 + 30, 140, 50, null);
        g.drawImage(Screen.tileset_res[3], 1280 - 40, 0, 20, 20, null);
        g.drawImage(Screen.tileset_res[4], 1280 - 40 - 30, 0, 20, 20, null);
        g.drawImage(Screen.tileset_res[5], 1280 - 250 + 120, 60 + 150 + 20, 100, 40, null);
        g.drawImage(Screen.tileset_res[6], 1280 - 250, 60 + 150 + 20, 100, 40, null);
        g.setColor(new Color(255, 70, 50, 40));


        // tô màu khi trỏ chuột vào
        if (buttonpause.contains(Screen.mse)) {

            g.fillRect(1280 - 40, 0, 20, 20);
        }
        if (buttonx2.contains(Screen.mse)) {
            g.fillRect(1280 - 40 - 30, 0, 20, 20);
        }
        if (buttonstart1.contains(Screen.mse)) {
            g.fillRect(50, 50, 140, 90);
        }
        if (help.contains(Screen.mse)) {
            g.fillRect(50, 50 + 90 + 30, 140, 50);
        }
        /*if(helpclose.contains(Screen.mse)){
            g.fillRect(140/2 - 10,50 + 90 +30 +80 +30,20,20);
        }*/

        if (holdsItem) {// nếu giữ thì in cùng chuột và in hình nhỏ hơn và thoong tin
            g.drawImage(Screen.tileset_air[heldID], Screen.mse.x - ((button[0].width - (itemIn * 2)) / 2) + itemIn, Screen.mse.y - ((button[0].width - (itemIn * 2)) / 2) + itemIn, button[0].width - (itemIn * 2), button[0].height - (itemIn * 2), null);
            g.drawImage(Screen.tileset_air[heldID], 1280 - 250 + 10, 70, 52, 52, null);


            if (Screen.coinage >= buttonPrice[realID]) {// vẽ tầm bắn, thong tin
                //System.out.println(holdID);
                g.setColor(Color.RED);
                if (realID == 1) {
                    g.drawRect(Screen.mse.x - ((button[0].width - (itemIn * 2)) / 2) + itemIn - 75, Screen.mse.y - ((button[0].width - (itemIn * 2)) / 2) + itemIn - 75, 150 + 52, 150 + 52);
                    g.setFont(new Font("Courier New", Font.BOLD, 14));
                    g.drawString("Name: se" , 1280 - 250 + 10 + 52 + 10, 70 + 14);
                    g.drawString("Dame :" + 2, 1280 - 250 + 10 + 52 + 10, 70 + 14 + 30);
                    g.drawString("Tam ban : " + 150, 1280 - 250 + 10 + 52 + 10, 70 + 14 + 30 + 30);
                    g.drawString("Toc danh" + 100, 1280 - 250 + 10 + 52 + 10, 70 + 14 + 30 + 60);
                }
                if (realID == 0) {
                    g.drawRect(Screen.mse.x - ((button[0].width - (itemIn * 2)) / 2) + itemIn - 65, Screen.mse.y - ((button[0].width - (itemIn * 2)) / 2) + itemIn - 65, 130 + 52, 130 + 52);
                    g.setFont(new Font("Courier New", Font.BOLD, 14));
                    g.drawString("Name: ez - Level" + 1, 1280 - 250 + 10 + 52 + 10, 70 + 14);
                    g.drawString("Dame :" + 1, 1280 - 250 + 10 + 52 + 10, 70 + 14 + 30);
                    g.drawString("Tam ban :" + 130, 1280 - 250 + 10 + 52 + 10, 70 + 14 + 30 + 30);
                    g.drawString("Toc danh" + 200, 1280 - 250 + 10 + 52 + 10, 70 + 14 + 30 + 60);
                    //g.drawString("upgrade -"+5 ,1280 - 250 + 120 + 25, 60+ 150+ 20 + 20 + 40);
                    g.drawString("sell +" + 5, 1280 - 250 + 25, 60 + 150 + 40 + 40);

                }
                if (realID == 2) {
                    g.drawRect(Screen.mse.x - ((button[0].width - (itemIn * 2)) / 2) + itemIn - 100, Screen.mse.y - ((button[0].width - (itemIn * 2)) / 2) + itemIn - 100, 200 + 52, 200 + 52);
                    g.setFont(new Font("Courier New", Font.BOLD, 14));
                    g.drawString("Name: ez - Level" + 1, 1280 - 250 + 10 + 52 + 10, 70 + 14);
                    g.drawString("Dame :" + 5, 1280 - 250 + 10 + 52 + 10, 70 + 14 + 30);
                    g.drawString("Tam ban :" + 200, 1280 - 250 + 10 + 52 + 10, 70 + 14 + 30 + 30);
                    g.drawString("Toc danh" + 250, 1280 - 250 + 10 + 52 + 10, 70 + 14 + 30 + 60);
                }

            } else if (Screen.coinage < buttonPrice[realID]) {
                g.setColor(Color.RED);
                g.setFont(new Font("Courier New", Font.BOLD, 14));
                g.drawString("Không Đủ Vàng! ", 1280 - 250 + 80, 70 + 14 + 30 + 30 + 50);

            }


        }
        if (holdsItem2) {
            if (map1) {
                g.drawImage(Screen.tileset_air[Value.airTowerLaser], 1280 - 250 + 10, 70, 52, 52, null);
                g.setColor(Color.RED);
                g.setFont(new Font("Courier New", Font.BOLD, 14));
                g.drawString("Name: ez - Level" + 1, 1280 - 250 + 10 + 52 + 10, 70 + 14);
                g.drawString("Dame :" + 5, 1280 - 250 + 10 + 52 + 10, 70 + 14 + 30);
                g.drawString("Tam ban :" + 130, 1280 - 250 + 10 + 52 + 10, 70 + 14 + 30 + 30);
                g.drawString("Toc danh" + 200, 1280 - 250 + 10 + 52 + 10, 70 + 14 + 30 + 60);
                //g.drawString("upgrade -"+5 ,1280 - 250 + 120 + 25, 60+ 150+ 20 + 20 + 40);
                g.drawString("sell +" + 5, 1280 - 250 + 25, 60 + 150 + 40 + 40);

                if (map2) {
                    g.drawImage(Screen.tileset_air[Value.airTowerLaser2], 1280 - 250 + 10, 70, 52, 52, null);
                    g.setColor(Color.RED);
                    g.setFont(new Font("Courier New", Font.BOLD, 14));
                    g.drawString("Name: ez - Level" + 1, 1280 - 250 + 10 + 52 + 10, 70 + 14);
                    g.drawString("Dame :" + 1, 1280 - 250 + 10 + 52 + 10, 70 + 14 + 30);
                    g.drawString("Tam ban :" + 150, 1280 - 250 + 10 + 52 + 10, 70 + 14 + 30 + 30);
                    g.drawString("Toc danh" + 200, 1280 - 250 + 10 + 52 + 10, 70 + 14 + 30 + 60);
                    //g.drawString("upgrade -"+5 ,1280 - 250 + 120 + 25, 60+ 150+ 20 + 20 + 40);
                    g.drawString("sell +" + 5, 1280 - 250 + 25, 60 + 150 + 40 + 40);

                }
                if (map3) {
                    g.drawImage(Screen.tileset_air[Value.airTowerLaser3], 1280 - 250 + 10, 70, 52, 52, null);
                    g.setColor(Color.RED);
                    g.setFont(new Font("Courier New", Font.BOLD, 14));
                    g.drawString("Name: ez - Level" + 1, 1280 - 250 + 10 + 52 + 10, 70 + 14);
                    g.drawString("Dame :" + 1, 1280 - 250 + 10 + 52 + 10, 70 + 14 + 30);
                    g.drawString("Tam ban :" + 200, 1280 - 250 + 10 + 52 + 10, 70 + 14 + 30 + 30);
                    g.drawString("Toc danh" + 200, 1280 - 250 + 10 + 52 + 10, 70 + 14 + 30 + 60);
                    //g.drawString("upgrade -"+5 ,1280 - 250 + 120 + 25, 60+ 150+ 20 + 20 + 40);
                    g.drawString("sell +" + 5, 1280 - 250 + 25, 60 + 150 + 40 + 40);
                }
            }
            if (Value.inhelp == true) {
                g.setColor(Color.RED);
                g.setFont(new Font("Courier New", Font.BOLD, 14));
                g.drawString("-Bấm Start để bắt đầu", 50, 50 + 90 + 30 + 80);
                g.drawString("-Bấm vào các tháp để xây dựng tháp", 50, 50 + 90 + 30 + 80 + 20);
                g.drawString("-.......", 50, 50 + 90 + 30 + 80 + 40);
                g.drawImage(Screen.tileset_res[8], 140 / 2 - 10, 50 + 90 + 30 + 80 + 30 + 40, 20, 20, null);
            }
            if (x2 == true) {
                g.drawImage(Screen.tileset_res[9], 1280 - 40 - 30, 0, 20, 20, null);
            }
            if (pause == true) {
                g.drawImage(Screen.tileset_res[10], 1280 - 40, 0, 20, 20, null);
            }

        }
    }
}



