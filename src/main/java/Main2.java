import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Main2 {
    static String path = "src/main/resources/";
    static int N;
    static int[][] array;
    static int[][] conArray;
    static int back = 0;
    static int iterations = 0;
    static ArrayList<Solution> solutions;
    private static boolean searchingAll = true;

    public static void main(String[] args) throws IOException {
        mainBacktracking();
    }

    private static void mainBacktracking() throws IOException {
        String plik = "test_sky_" + 2 + "_" + 0 + ".txt";
        load(plik);
        backtracking(0, 0);
        System.out.println("ROZWIAZANIA:");
        for (Solution s : solutions) {
            printArray(s.solution);
            System.out.println();
        }
        System.out.println("Rozw: "+solutions.size());
      //  test();
    }
    private static void test(){
        array=new int[3][3];
        array[0][0]=1;
        array[0][1]=2;
        array[0][2]=3;

        array[1][0]=2;
        array[1][1]=3;
        array[1][2]=4;

        array[2][0]=0;
        array[2][1]=0;
        array[2][2]=0;
        printArray(array);
        System.out.println(isOkG(2));
        System.out.println(isOkD(0));
        System.out.println(isOkL(0));
        System.out.println(isOkP(2));
    }

    private static boolean backtracking(int x, int y) {
        iterations += 1;
        if (y >= N) {
            y = 0;
            x++;
        }

        if (x == N && y == 0) {
            solutions.add(new Solution(cloneArray(array), back, iterations));
            return true;
        }

        boolean isBack = true;
        for (int i = 1; i <= N; i++) {
            System.out.println("wpisuje:"+i+" w:"+x+"-"+y);
            array[x][y]=i;
            // System.out.println();
            if (isOk(i,x, y)) {
                System.out.println("Jest ok.");

                isBack = false;
                if (backtracking(x, y + 1) && !searchingAll)
                    return true;
            }
            if (isBack) {
                back += 1;
               // array[x][y]=0;
            //      System.out.println("cofa");
            }
        }


        array[x][y] = 0;
        return false;
    }

    public static boolean isOk(int k,int x, int y) {
        return isOkG(y) && isOkD(y) && isOkL(x) && isOkP(x) && !isInRowOrColumn(k,x, y);
    }

    public static boolean isInRowOrColumn(int k, int x, int y) {
        return isInRow(k,x, y) || isInColumn(k,x, y);
    }

    public static boolean isInRow(int k,int x, int y) {
        for (int i = 0; i < N; i++) {
            if (array[x][i] == k&&i!=y)
                return true;
        }
        return false;
    }

    public static boolean isInColumn(int k,int x, int y) {
        for (int i = 0; i < N; i++) {
            if (array[i][y] == k&&i!=x)
                return true;
        }
        return false;
    }

    public static boolean isOkG(int y) {
        int index = conArray[0][y];
        if (index == 0)
            return true;
        int counter = 1;
        int max = array[0][y];
        for (int i = 1; i < N; i++) {
            if (array[i][y] > max) {
                max = array[i][y];
                counter++;
            }
        }
        return counter == index;
    }

    public static boolean isOkD(int y) {
        int index = conArray[1][y];
        if (index == 0)
            return true;
        int counter = 1;
        int s=N-1;
        int max;
        for(max=array[s][y];max==0&&s>0;s--)
            max = array[s][y];

        for (int i = N - 2; i >= 0; i--) {
            if (array[i][y] > max) {
                max = array[i][y];
                counter++;
            }
        }
        return counter == index;
    }

    public static boolean isOkL(int x) {
        int index = conArray[2][x];
        if (index == 0)
            return true;
        int counter = 1;
        int max = array[x][0];
        for (int i = 1; i < N; i++) {
            if (array[x][i] > max) {
                max = array[x][i];
                counter++;
            }
        }
        return counter == index;
    }

    public static boolean isOkP(int x) {
        int index = conArray[3][x];
        if (index == 0)
            return true;
        int counter = 1;

        int s=N-1;
        int max;
        for(max=array[x][s];max==0&&s>0;s--)
            max = array[x][s];

        for (int i = N - 2; i >= 0; i--) {
            if (array[x][i] > max) {
                max = array[x][i];
                counter++;
            }
        }
        return counter == index;
    }

    private static int[][] cloneArray(int[][] array) {
        int[][] newArray = new int[N][N];
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                newArray[i][j] = array[i][j];
            }
        }
        return newArray;
    }

    public static void load(String fileName) throws FileNotFoundException {
        Scanner in = new Scanner(new File(path + fileName));
        String line = in.nextLine();
        N = Integer.valueOf(line);
        array = null;
        array = new int[N][N];
        conArray = new int[4][N];
        solutions = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            line = in.nextLine();
            String[] splitedLine = line.split("\\;");
            for (int j = 0; j < N; j++)
                conArray[i][j] = Integer.valueOf(splitedLine[j + 1]);

        }

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                array[i][j] = 0;
                //  System.out.print(conArray[i][j]);
            }
            //    System.out.println();
        }

    }

    private static void printArray(int[][] array) {
        for (int[] n : array) {
            for (int m : n)
                System.out.print(m + " ");
            System.out.println();
        }
    }

}
