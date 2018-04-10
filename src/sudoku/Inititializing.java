package sudoku;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Inititializing {
    int[][] init_map = new int[10][10];
    int[][] init_result = new int[10][10];
    String[][] init_grid = new String[10][10];

    void Inititializing() {
        init();
//        print_GridSt(init_grid);
        //chuyen mang String sang mang int
        for (int i = 1; i < 10 ; i++) {
            for (int j = 1; j < 10; j++) {
                for (String number:num) {
                    if (init_grid[i][j].equals(number)){
                        init_map[i][j] = Integer.parseInt(number);
                        break;
                    }
                }
            }
        }
        System.out.println("");
        Backtracking(1,1);
//        System.out.println("Grid for checking: ");
//        print_Grid(init_result);
//        System.out.println("");
//        print_Grid(init_map);
//        System.out.println("");
    }

    ////Ham khoi tao
    public void init(){
        //Khởi tạo ma trận 9x9, chừa 1 hàng + 1 cột để ghi tọa độ
        for (int i=1;i<10;i++){
            for (int j=1;j<10;j++)
                init_grid[i][j]="-";
        }
        try {
//            FileInputStream in = new FileInputStream("Test.txt");
            FileInputStream in = new FileInputStream("Easy.txt");
            Scanner input = new Scanner(in,"UTF-8");
            int k = 0;
            String[] line = new String[100];
            while(input.hasNextLine()){//khi file con
                line[k] = input.nextLine();//doc 1 dong
                if(line[k].trim()!=""){
                    //mang item chua cac so tren 1 hang, cach nhau boi khoang trang
                    String[] item = line[k].split(" ");
                    for (int i = 0; i < 9; i++) {
                        init_grid[k+1][i+1] = item[i];
                    }
                }
                k++;
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    ////Ham kiem tra hop le
    public boolean check_Grid(int x, int y, int val, int[][] cells) {
        //check cột
        for (int i = 1; i < 10; i++)
            if (val==cells[i][y])
                return false;
        //check hàng
        for (int i = 1; i < 10; i++)
            if (val==cells[x][i])
                return false;
        //Check vùng 3x3. Chia 9x9 thành 3x3
        // Lấy tọa độ chia 3 để xác định vị trí của ô trên ma trận 3x3, ta có X và Y.
        int X = ((x-1) / 3)*3;
        int Y = ((y-1) / 3)*3;
        for (int i = X+1; i <= X+3; i++)
            for (int j = Y+1; j <= Y+3; j++)
                if (val==cells[i][j])
                    return false;
        return true;
    }

    ////ham copy grid
    int[][] copy_Grid(int[][] init){
        for (int i = 0; i < init.length ; i++) {
            for (int j = 0; j < init.length ; j++) {
                init_result[i][j] = init[i][j];
            }
        }
        return init_result;
    }

    ////Back tracking
    void Backtracking(int x, int y){
        //Neu y=10 -> hết hàng
        if (y==10){
            //Nếu x=9 -> đã giải xong -> copy init_map vào init_result
            if(x==9) {
                copy_Grid(init_map);
            }
            //Nếu x != 9 thì làm tiếp ở hàng tiếp theo
            else Backtracking(x+1, 1);
        }
        //Chưa hết hàng x, nếu ô = 0
        else if (init_map[x][y]==0){
            //Cho i chạy từ 1-9
            for (int i = 1; i <10 ; i++) {
                //Nếu giá trị i điền vào ô [x][y] hợp lệ thì
                if(check_Grid(x,y,i,init_map)){
                    //gán ô bằng i
                    init_map[x][y] = i;
                    //làm tiếp ở ô tiếp theo
                    Backtracking(x,y+1);
                    //Nếu không có giá trị i nào thích hợp-> quay lui
                    init_map[x][y] = 0;
                }
            }
        }
        //ô khác 0 thì làm tiếp ở ô kế bên
        else Backtracking(x,y+1);
    }

    ////Ham in grid
    String[] chars = {"A", "B", "C", "D", "E", "F", "G", "H", "I"};
    String[] num = {"1", "2","3","4","5","6","7","8","9"};
    public void print_Grid(int[][] init){
        String[][] print = new String[10][10];

        for (int i=0;i<10;i++){
            for (int j=0;j<10;j++) {
                print[i][j] = Integer.toString(init[i][j]);
            }
        }
        for (int i=0;i<10;i++){
            if((i-1)%3==0 &&i!=0) {
                System.out.println("---------------------------");
            }
            for (int j=0;j<10;j++) {
                //nếu phần tử thuộc hàng 0 thì in kí tự từ mảng chars
                if(i==0 && j!=0){
                    print[0][j] = chars[j-1];
                }
                //nếu phần tử thuộc cột 0 thì in số từ mảng num
                else if(i!=0&&j==0){
                    print[i][0] = num[i-1];
                }
                print[0][0] ="*";
                System.out.print(print[i][j] + " ");
                if(j%3==0) System.out.print("| ");
            }

            System.out.println("");
        }
    }

}
