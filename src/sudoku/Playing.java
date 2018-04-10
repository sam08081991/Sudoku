package sudoku;
//Sau khi có bàn cờ khởi tạo:
// + Copy thành map1 để lưu lại các ô khởi tạo
//      (Để khi người chơi nhập trúng ô khởi tạo sẽ báo và yêu cầu nhập lại)
//+ Check input của người chơi: gồm check tọa độ + giá trị nhập vào. Không hợp lệ thì yêu cầu nhập lại.
//+ Hàm checkGrid xem input vừa nhập có hợp với luật game hay không, nếu không thì yêu cầu nhập lại.
//+ Hàm checkResult để kiểm xem khi nào người chơi giải xong game.
import java.util.Scanner;

public class Playing {
    Inititializing Init = new Inititializing();
    String text, number;
    String value;
    int[] coordinates = new int[2];
    String[] chars = {"A", "B", "C", "D", "E", "F", "G", "H", "I"};
    String[] num = {"1","2","3","4","5","6","7","8","9"};
    String[][] init_copy = new String[10][10];
    int[][] game_Grid = new int[10][10];

    void Play(){
        Init.Inititializing();
        init_copy = copy_Grid(Init.init_grid);
        get_GameGrid(init_copy,Init.init_result);
        Init.print_Grid(game_Grid);
        while(!check_EndGame(game_Grid)){
            get_Input();
            get_Value(text, number, value);
        }
        System.out.println("Sudoku đã giải xong!!!!");
    }
    ////Lay mang int cho game
    int[][] get_GameGrid(String[][] init_copy, int[][] init_result){
        for (int i = 1; i < 10 ; i++) {
            for (int j = 1; j < 10; j++) {
                if (!init_copy[i][j].equals("0")){
                    game_Grid[i][j] = init_result[i][j];
                }
            }
        }
        return game_Grid;
    }

    ////Copy de luu map khoi tao
    String[][] copy_Grid(String[][] init){
        for (int i = 0; i < init.length ; i++) {
            for (int j = 0; j < init.length ; j++) {
                init_copy[i][j] = init[i][j];
            }
        }
        return init_copy;
    }

    ////Nhap toa do
    void get_Input(){
        Scanner sc = new Scanner(System.in);
        boolean check = true;
        while (check) {
            System.out.println("Nhập tọa độ (ex: 1a, a1, 1A, A1)");
            String input = sc.nextLine();
            if(check_InputText(input)==false){
                System.out.println("Tọa độ không hợp lệ, mời nhập lại.");
            }
            else check = false;
        }
        check = true;
        while(check){
            System.out.println("Nhập 1 giá trị từ 1-9: ");
            value = sc.nextLine();
            if(check_InputNumber(value)==false){
                System.out.println("Giá trị ô không hợp lệ, mời nhập lại.");
            }
            else check = false;
        }
        System.out.println("Column: " + text);
        System.out.println("Row: " + number);
        System.out.println("Value: " + value);
    }

    ////Check input của người chơi: gồm check tọa độ + giá trị nhập vào.
    // Không hợp lệ thì yêu cầu nhập lại.
    boolean check_InputText(String str){
        String check = str.toUpperCase();
        boolean test = false;
        //nếu chuỗi nhập có 2 kí tự
        if(check.length()==2){
            //cắt lấy kí tự đầu tiên gán cho text
            text = check.substring(0,1);
            for (String txt:chars) {
                //nếu kí tự đầu tiên là chữ
                if (text.equals(txt)){
                    //gán kí tự thứ 2 cho number
                    number = check.substring(1);
                    for (String numbr:num) {
                        //nếu kí tự thứ 2 nhập đúng, test = true và break ra khỏi for
                        if (number.equals(numbr)) {
                            test = true;
                            break;
                        }
                    }
                    if(test) break;
                }
                //nếu kí tự đầu tiên không là chữ
                else {
                    //Kiểm tra xem có phải là số không
                    for (String numbr:num) {
                        if(text.equals(numbr)){
                            //Nếu text là số thì gán kí tự thứ 2 của chuỗi cho text, kí tự đầu cho number
                            text = check.substring(1);
                            number = check.substring(0,1);
                            //Kiểm tra text xem có thuộc mảng chars không
                            for (String tx:chars) {
                                //nếu text thuộc chars thì test=true và break
                                if (text.equals(tx)){
                                    test = true;
                                    break;
                                }
                            }
                            //nếu test đúng thì break
                            if(test) break;
                        }
                    }
                }
                if (test) break;
            }
        }
        else test = false;
        return test;
    }
    boolean check_InputNumber(String str){
        String check =str.toUpperCase();
        boolean test = false;
        //nếu input có 1 kí tự thì phải từ 1-9
        if(check.length()==1){
            for (String ele:num) {
                if(check.equals(ele)) {
                    test = true;
                    break;
                }
            }
        }
        else test = false;
        return test;
    }

    ////Hàm chuyển tọa độ từ string sang index
       int[] get_Index(String text, String number){
           //Toa do doc
           for (int i = 0; i < chars.length ; i++) {
               if (chars[i].equals(text)) {
                   coordinates[1] = i + 1;
                   break;
               }
           }
           //Toa do ngang
           for (int i = 0; i < num.length ; i++) {
               if(num[i].equals(number)){
                   coordinates[0]= i+1;
                   break;
               }
           }
//           System.out.println("x = " + coordinates[0]);
//           System.out.println("y = " + coordinates[1]);
           return coordinates;
       }

    ////Hàm kiểm tra ô nhập có phải là ô có giá trị khởi tạo hay không
    boolean check_Init(String text, String number, String value){
        get_Index(text, number);
        if (!init_copy[coordinates[0]][coordinates[1]].equals("0")){
            System.out.println("Đây là vị trí khởi tạo. Mời chọn vị trí khác.");
            return false;
        }
        return true;
    }

    ////Hàm nhập giá trị vào ô
    void get_Value(String text, String number, String value){
        int val = Integer.valueOf(value);
        if(check_Init(text, number, value)){
            if(Init.check_Grid(coordinates[0],coordinates[1],val,game_Grid)){
                game_Grid[coordinates[0]][coordinates[1]] = val;
//                System.out.println("Gia tri hop le:" + game_Grid[coordinates[0]][coordinates[1]]);
//                System.out.println("Grid for checking: ");
//                Init.print_Grid(Init.init_result);
                System.out.println("");
                Init.print_Grid(game_Grid);
            }
            else {
                System.out.println("Giá trị nhập vào sai luật. Mời nhập lại");
                get_Input();
            }
        }
    }

    ////Hàm kiểm tra kết thúc game
    boolean check_EndGame(int[][] game_Grid ){
        for (int i = 1; i < 10; i++) {
            for (int j = 1; j < 10; j++) {
                if(game_Grid[i][j]!=Init.init_result[i][j]) return false;
            }
        }
        return true;
    }
}
