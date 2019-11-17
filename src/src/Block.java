import java.awt.*;

public class Block extends Rectangle { // ô vuông in room
    public Rectangle towerSquare1;
    public Rectangle towerSquare2;
    public Rectangle towerSquare3;
    public int towerSquareSize1 = 130;
    public int towerSquareSize2 = 150;
    public int towerSquareSize3 = 200;

    public int groundID;
    public int ariID;
    public int loseTime1 = 200, loseFrame1 = 0;// tốc độ trừ máu
    public int loseTime2 = 300, loseFrame2 = 0;
    public int loseTime3 = 500, loseFrame3 = 0;
    public int shotEnemy = -1;
    //public int shotEnemy2 = -1;
    //public int shotEnemy3 = -1;
    public boolean shoting1 = false;
    public boolean shoting2 = false;
    public boolean shoting3 = false;

    public Block(int x,int y,int width, int height, int groundID,int airID ){
        setBounds(x,y,width,height);
        towerSquare1 = new Rectangle(x -(towerSquareSize1/2),y-(towerSquareSize1/2),width +(towerSquareSize1),height +(towerSquareSize1));
        towerSquare2 = new Rectangle(x -(towerSquareSize2/2),y-(towerSquareSize2/2),width +(towerSquareSize2),height +(towerSquareSize2));
        towerSquare3 = new Rectangle(x -(towerSquareSize3/2),y-(towerSquareSize3/2),width +(towerSquareSize3),height +(towerSquareSize3));
        this.groundID = groundID;
        this.ariID = airID;
    }
    public void draw(Graphics g){
        g.drawImage(Screen.tileset_ground[groundID],x,y,width,height,null);
        if(ariID != Value.airAir){// nếu có vật trên không khí
            g.drawImage(Screen.tileset_air[ariID],x,y,width,height,null);
        }
    }
    public void physic(){

        if (shotEnemy!= -1 && towerSquare1.intersects(Screen.enemies[shotEnemy])) { // phát hiện va chạm(quái vào tầm bắn)
            shoting1 = true;
        }else {
            shoting1 = false;
        }
        if(!shoting1) {

            shoting1 = false;
            if (ariID == Value.airTowerLaser ) {// xác định để tạo laze;
                for (int i = 0; i < Screen.enemies.length; i++) {
                    if (Screen.enemies[i].inGame) {
                        if (towerSquare1.intersects(Screen.enemies[i])) {
                            shoting1 = true;
                            shotEnemy = i;// bắn quái thứ i;
                        }
                    }
                }
            }
        }
        if(Value.mobspeed != 0) {
            if (shoting1) {
                if(Value.mobspeed == 2){
                    loseTime1 = 150;
                }
                else if(Value.mobspeed ==1){
                    loseTime1 = 300;
                }

                if (loseFrame1 >= loseTime1) {
                    if (Value.airTowerLaser == ariID)
                        Screen.enemies[shotEnemy].loseHealth(2);// amo tương đương sát thương
                    loseFrame1 = 0;
                } else {
                    loseFrame1 += 1;
                }

                if (Screen.enemies[shotEnemy].inDead()) {

                    shoting1 = false;
                    shotEnemy = -1;
                    Screen.killed += 1;
                    Screen.hasWon();
                }

            }
        }
        // truj 2
        // xác định để tạo laze;
        if (shotEnemy!= -1 && towerSquare2.intersects(Screen.enemies[shotEnemy])) { // phát hiện va chạm(quái vào tầm bắn)
            shoting2 = true;
        }else {
            shoting2 = false;
        }
        if(!shoting2) {

            shoting2 = false;
            if (ariID == Value.airTowerLaser2 ) {
                for (int i = 0; i < Screen.enemies.length; i++) {
                    if (Screen.enemies[i].inGame) {
                        if (towerSquare2.intersects(Screen.enemies[i])) {
                            shoting2 = true;
                            shotEnemy = i;// bắn quái thứ i;
                        }
                    }
                }
            }
        }
        if(Value.mobspeed != 0) {
            if (shoting2) {
                if(Value.mobspeed == 2){
                    loseTime2 = 150;
                }
                else if(Value.mobspeed ==1){
                    loseTime2 = 300;
                }

                if (loseFrame2 >= loseTime2) {
                    if (Value.airTowerLaser2 == ariID)
                        Screen.enemies[shotEnemy].loseHealth(2);// amo tương đương sát thương
                    loseFrame2 = 0;
                } else {
                    loseFrame2 += 1;
                }

                if (Screen.enemies[shotEnemy].inDead()) {

                    shoting2 = false;
                    shotEnemy= -1;
                    Screen.killed += 1;
                    Screen.hasWon();
                }

            }
        }

        // truj3

        if (shotEnemy!= -1 && towerSquare3.intersects(Screen.enemies[shotEnemy])) { // phát hiện va chạm(quái vào tầm bắn)
            shoting3 = true;
        }else {
            shoting3= false;
        }
        if(!shoting3) {

            shoting3 = false;
            if (ariID == Value.airTowerLaser3) {// xác định để tạo laze;
                for (int i = 0; i < Screen.enemies.length; i++) {
                    if (Screen.enemies[i].inGame) {
                        if (towerSquare3.intersects(Screen.enemies[i])) {
                            shoting3 = true;
                            shotEnemy = i;// bắn quái thứ i;
                        }
                    }
                }
            }
        }
        if(Value.mobspeed != 0) {
            if (shoting3) {
                if(Value.mobspeed == 2){
                    loseTime3 = 250;
                }
                else if(Value.mobspeed == 1){
                    loseTime3 = 500;
                }

                if (loseFrame3 >= loseTime3) {
                    if (Value.airTowerLaser3 == ariID)
                        Screen.enemies[shotEnemy].loseHealth(5);// amo tương đương sát thương
                    loseFrame3 = 0;
                } else {
                    loseFrame3 += 1;
                }

                if (Screen.enemies[shotEnemy].inDead()) {

                    shoting3 = false;
                    shotEnemy = -1;
                    Screen.killed += 1;
                    Screen.hasWon();
                }
            }
        }


    }



    public void getMoney(int mobID){
        Screen.coinage += Value.deathReward[mobID];

    }
    public void fight1(Graphics g){
        if(Screen.isDebug1) {
            if (ariID == Value.airTowerLaser) {
                g.drawRect(towerSquare1.x, towerSquare1.y, towerSquare1.width, towerSquare1.height);

            }
        }
        if(shoting1){
            g.setColor(new Color(0,100,150));
            if(shotEnemy!= -1) {
                g.drawLine(x + (width / 2), y + (height / 2), Screen.enemies[shotEnemy].x + (Screen.enemies[shotEnemy].width / 2), Screen.enemies[shotEnemy].y + (Screen.enemies[shotEnemy].height / 2));
            }
        }
    }
    public void fight2(Graphics g){
        if(Screen.isDebug2) {
            if (ariID == Value.airTowerLaser2) {
                g.drawRect(towerSquare2.x, towerSquare2.y, towerSquare2.width, towerSquare2.height);

            }
        }
        if(shoting2){
            g.setColor(new Color(0,0,150));
            if(shotEnemy!= -1) {
                g.drawLine(x + (width / 2), y + (height / 2), Screen.enemies[shotEnemy].x + (Screen.enemies[shotEnemy].width / 2), Screen.enemies[shotEnemy].y + (Screen.enemies[shotEnemy].height / 2));
            }

        }
    }
    public void fight3(Graphics g){
        if(Screen.isDebug3) {
            if (ariID == Value.airTowerLaser3) {
                g.drawRect(towerSquare3.x, towerSquare3.y, towerSquare3.width, towerSquare3.height);

            }
        }
        if(shoting3){
            g.setColor(new Color(100,0,150));
            if(shotEnemy!= -1) {
                g.drawLine(x + (width / 2), y + (height / 2), Screen.enemies[shotEnemy].x + (Screen.enemies[shotEnemy].width / 2), Screen.enemies[shotEnemy].y + (Screen.enemies[shotEnemy].height / 2));
            }
        }
    }

}



