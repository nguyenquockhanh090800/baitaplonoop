import java.awt.*;

public class Room { // phòng game
    public int worlWidth = 12;// sl ô vuông ngang
    public  int worlHeight = 9; // sl ô vuông dọc
    public int blockSize = 52;
    public Block[][] block;
    public Room(){
        define();
    }
    public void define(){ // khởi tạo thành phần trong phòng Game
        block = new Block[worlHeight][worlWidth];
        for(int y = 0; y<block.length;y++){
            for (int x = 0; x< block[0].length; x++){
                block[y][x] = new Block((Screen.myWidth/2) - ((worlWidth*blockSize)/2) + (x*blockSize),y*blockSize,blockSize,blockSize,Value.groundGraaa ,Value.airAir);
            }
        }// căn lưới ô vuông ở giữa
    }

    public void physic(){
        for(int y = 0; y<block.length;y++){
            for(int x =0 ; x< block[0].length;x++){
                block[y][x].physic();
            }
        }

    }

    public void draw(Graphics g){
        for(int y = 0; y<block.length;y++){
            for (int x = 0; x< block[0].length; x++){
                block[y][x].draw(g);
                //block[y][x].fight(g);
            }
        }
        for(int y = 0; y<block.length;y++){
            for (int x = 0; x< block[0].length; x++){
                block[y][x].fight1(g);
                block[y][x].fight2(g);
                block[y][x].fight3(g);
            }
        }

    }

}

