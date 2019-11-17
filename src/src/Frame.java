import  javax.swing.*;
import java.awt.*;
import java.util.Scanner;

public class Frame extends JFrame {
    public static String title = "Tower Defense Game - by Khánh  "; // tiêu đề


    public static Dimension size = new Dimension(1280,700);
    public Frame(){
        setTitle(title);
        setSize(size);
        setResizable(false);// khả năng thay đổi kích thước
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);// đóng jframe

        init();
    }
    /*public void setSize(Dimension dim){
        System.out.println("Hello");
    }*/

    private void init() {
        setLayout(new GridLayout(1,1,0,0));// bố trí screen toàn bộ màm bên trong
        Screen screen = new Screen(this);
        add(screen);
        setVisible(true); // hiện cửa sổ frame lên
    }


    public static void main(String args[]){
        Frame frame = new Frame();
    }
}
