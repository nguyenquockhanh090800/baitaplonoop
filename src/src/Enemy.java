import java.awt.*;

public class Enemy extends Rectangle {
    public int xC,yC; // chỉ số ô vuông
    public int health ; // máu thằng địch
    public int healthSpace = 3, healthHeight = 6;// healthSpace là khoảng cách thanh máu với quái
    public int mobSize = 52;
    public int mobWalk = 0;
    public int upward = 0, downward = 1, right = 2,left = 3;
    public int direction = right;
    public int mobID = Value.mobAir;
    public boolean inGame = false;
    public boolean hasUpwad = false;
    public boolean hasDownward = false;
    public boolean hasLeft = false;
    public boolean hasRight = false;


    public Enemy() {
    }

    public void spawnMob(int mobID) { // sinh quái
        if (Value.mobspeed > 0) {
            for (int y = 0; y < Screen.room.block.length; y++) {
                if (Screen.room.block[y][0].groundID == Value.groundRoad) {
                    setBounds(Screen.room.block[y][0].x, Screen.room.block[y][0].y, mobSize, mobSize);
                    xC = 0;
                    yC = y;
                }
            }

            this.mobID = mobID;
            this.health = mobSize;
            inGame = true;
        }
    }

    public void deleteMob(){ //xóa quái
        inGame = false;
        direction = right;
        mobWalk = 0;
        Screen.room.block[0][0].getMoney(mobID);

    }

    public void looseHealth(){
        Screen.health -= 1;
    }// khi địch vào nhà máu nhà -1



    public int walkFrame = 0, walkSpeed = 16;

    public void physic() {//
        if (Value.mobspeed > 0) {
            if(Value.mobspeed == 1){
                walkSpeed = 16;
            }
            if(Value.mobspeed == 2){
                walkSpeed = 8;
            }
            if (walkFrame >= walkSpeed) {
                if (direction == right) {
                    x += 1;
                } else if (direction == upward) {
                    y -= 1;
                } else if (direction == downward) {
                    y += 1;
                } else if (direction == left) {
                    x -= 1;
                }
                mobWalk += 1;
                if (mobWalk == Screen.room.blockSize) { // đi hết 1 ô vuông rồi kiểm tra xem đi tiếp như thế nào
                    if (direction == right) {
                        xC += 1;
                        hasRight = true;

                    } else if (direction == upward) {
                        yC -= 1;
                        hasUpwad = true;
                    } else if (direction == downward) {
                        yC += 1;
                        hasDownward = true;
                    } else if (direction == left) {
                        xC -= 1;
                        hasLeft = true;
                    }
                    if (!hasUpwad) {
                        try {
                            if (Screen.room.block[yC + 1][xC].groundID == Value.groundRoad) {
                                direction = downward;
                            }
                        } catch (Exception e) {
                        }
                    }

                    if (!hasDownward) {
                        try {
                            if (Screen.room.block[yC - 1][xC].groundID == Value.groundRoad) {
                                direction = upward;
                            }

                        } catch (Exception e) {
                        }
                    }
                    if (!hasLeft) {
                        try {
                            if (Screen.room.block[yC][xC + 1].groundID == Value.groundRoad) {
                                direction = right;
                            }

                        } catch (Exception e) {
                        }
                    }

                    if (!hasRight) {
                        try {
                            if (Screen.room.block[yC][xC - 1].groundID == Value.groundRoad) {// kiểm tra ô vuông bên trái nếu là đường thì đi
                                direction = left;
                            }

                        } catch (Exception e) {
                        }
                    }

                    if (Screen.room.block[yC][xC].ariID == Value.airCave) {
                        deleteMob();
                        looseHealth();
                    }
                    hasUpwad = false;
                    hasDownward = false;
                    hasLeft = false;
                    hasRight = false;
                    mobWalk = 0;
                }
                walkFrame = 0;
            } else {
                walkFrame += 1;
            }

        }
    }
    public void loseHealth(int amo){
        health -= amo;
        checkDeath();
    }
    public void checkDeath(){
        if(health < 0){
            deleteMob();
        }
    }
    public boolean inDead(){ // kiểm tra xem còn trong game k
        if(inGame){
            return false;
        }else {
            return true;
        }
    }
    public void draw(Graphics g){

        g.drawImage(Screen.tileset_mob[mobID], x, y, width,height,null);
        //Health bar
        g.setColor(new Color(180,50,50));
        g.fillRect(x,y - (healthSpace + healthHeight),width,healthHeight);

        g.setColor(new Color(50,180,50));
        g.fillRect(x,y - (healthSpace + healthHeight),health,healthHeight);

        g.setColor(new Color(0,0,0));
        g.drawRect(x,y - (healthSpace + healthHeight),health - 1,healthHeight -1);
    }
}

