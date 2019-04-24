import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class Main {
    static String path = "src/main/resources/";
    static int N;
    static int[][] array;
    static Constraint[] constraints;
    static int back = 0;
    static int iterations = 0;
    static int iterator = 0;
    static ArrayList<Solution> solutions;
    private static boolean searchingAll = true;

    public static void main(String[] args) throws IOException {
        System.out.println("BT:");
        mainBacktracking();
        back = 0;
        iterations = 0;
        iterator = 0;
        array = null;
        constraints = null;
        System.out.println("FC:");
        mainForward();

    }

    private static void mainForward() throws IOException {
        int fileIndex = 7;
        int fileIndexS = 0;

        for (; fileIndex != 8; ) {
            File file = new File("futoFC/futoFC-" + fileIndex + "-" + fileIndexS + ".txt");
            String plik = "test_futo_" + fileIndex + "_" + fileIndexS + ".txt";
            load(plik);
            back = 0;
            iterations = 0;
            iterator = 0;
            int tmp[] = findFreeCell(0, 0);
//            int tmp[] = findMostConstraintCell();
//            int tmp[] = findLessConstraintCell();
            System.out.println("File: " + plik);
            solutions = new ArrayList<Solution>();
            long time = System.nanoTime();
            forwardchecking(tmp[0], tmp[1]);
            time = System.nanoTime() - time;
            printSolutions();
            System.out.println("All backs: " + back);
            System.out.println("All iterations: " + iterations);
            System.out.println("Iterator: " + iterator);
            System.out.println("All solutions: " + solutions.size());
            System.out.println("Time: " + time / 1000000000.0 + " sec\n");
            writeToFile("Backs: " + back, file);
            writeToFile("\n\rIterations: " + iterations, file);
            writeToFile("\n\rITERATOR: " + iterator, file);
            writeToFile("\n\rTime: " + time / 1000000000.0 + " sec", file);
            writeToFile("\n\rSolutions: " + solutions.size(), file);
            writeToFile("\n\r" + writeSolutions(), file);

            fileIndexS += 1;
            if (fileIndexS == 3) {
                fileIndexS = 0;
                fileIndex++;
            }

        }

    }

    private static void mainBacktracking() throws IOException {
        int fileIndex = 7;
        int fileIndexS = 0;

        for (; fileIndex != 8; ) {
            File file = new File("H2futoBT/futoBT-" + fileIndex + "-" + fileIndexS + ".txt");
            String plik = "test_futo_" + fileIndex + "_" + fileIndexS + ".txt";
            load(plik);
            back = 0;
            iterations = 0;
            iterator = 0;
            int tmp[] = findFreeCell(0, 0);
//            int tmp[] = findMostConstraintCell();
//            int tmp[] = findLessConstraintCell();
            System.out.println("File: " + plik);
            solutions = new ArrayList<Solution>();

            long time = System.nanoTime();
            backtracking(tmp[0], tmp[1]);
//            backtracking(0,0);
            time = System.nanoTime() - time;
            //printSolutions();
            System.out.println("All backs: " + back);
            System.out.println("All iterations: " + iterations);
            System.out.println("Iterator: " + iterator);
            System.out.println("All solutions: " + solutions.size());
            System.out.println("Time: " + time / 1000000000.0 + " sec\n");
            writeToFile("Backs: " + back, file);
            writeToFile("\n\rIterations: " + iterations, file);
            writeToFile("\n\rITERATOR: " + iterator, file);
            writeToFile("\n\rTime: " + time / 1000000000.0 + " sec", file);
            writeToFile("\n\rSolutions: " + solutions.size(), file);
            writeToFile("\n\r" + writeSolutions(), file);

            fileIndexS += 1;
            if (fileIndexS == 3) {
                fileIndexS = 0;
                fileIndex++;
            }

        }
    }

    private static int[] findMostConstraintCell() {
        int mostX = 0, mostY = 0;
        int mostK = 0;

        for (int i = 0; i < N; i++)
            for (int j = 0; j < N; j++) {
                int tmp = countConstraints(i, j);
                if (tmp > mostK) {
                    mostX = i;
                    mostY = j;
                    mostK = tmp;
                }
            }
        return new int[]{mostX, mostY};
    }

    private static int[] findLessConstraintCell() {
        int mostX = 0, mostY = 0;
        int mostK = 9999999;

        for (int i = 0; i < N; i++)
            for (int j = 0; j < N; j++) {
                int tmp = countConstraints(i, j);
                if (tmp < mostK) {
                    mostX = i;
                    mostY = j;
                    mostK = tmp;
                }
            }
        return new int[]{mostX, mostY};
    }

    private static int countConstraints(int x, int y) {
        int k = 0;
        for (Constraint c : constraints) {
            if (c.containsCell(x, y))
                k++;
        }
        return k;
    }


    private static boolean forwardchecking(int x, int y) {
        iterations += 1;
        if (y >= N) {
            y = 0;
            x++;
        }

        if (x == N && y == 0 && findFreeCell(0, 0) == null) {
            solutions.add(new Solution(cloneArray(array), back, iterations));
            return true;
        } else if (findFreeCell(0, 0) != null && x == N && y == 0) {
            int[] tmp = findFreeCell(0, 0);
            x = tmp[0];
            y = tmp[1];
        }

        boolean isBack = true;
        if (array[x][y] != 0)
            return forwardchecking(x, y + 1);
        for (int i = 1; i <= N; i++) {
            // System.out.println();
            // printArray(array);
            // System.out.println("chce wpisać:" + i + " w:" + x + "-" + y);

            if (isOkFC(i, x, y)) {
                writeFirstPossible(i, x, y);
                //   System.out.println("Jest ok.");
                //  printArray(array);
                isBack = false;
                if (forwardchecking(x, y + 1) && !searchingAll)
                    return true;
            }

            if (isBack) {
                back += 1;
                //   System.out.println("cofa");
            }
        }


        array[x][y] = 0;
        return false;
    }

    private static boolean backtracking(int x, int y) {
        iterations += 1;
        if (y >= N) {
            y = 0;
            x++;
        }
        if (x == N && y == 0 && findFreeCell(0, 0) == null) {
            solutions.add(new Solution(cloneArray(array), back, iterations));
            return true;
        } else if (findFreeCell(0, 0) != null && x == N && y == 0) {
            int[] tmp = findFreeCell(0, 0);
            x = tmp[0];
            y = tmp[1];
        }
        boolean isBack = true;
        if (array[x][y] != 0)
            return backtracking(x, y + 1);
        for (int i = 1; i <= N; i++) {
            //System.out.println("wpisuje:"+i+" w:"+x+"-"+y);
            if (isOk(i, x, y)) {
                //  System.out.println("Jest ok.");
                writeFirstPossible(i, x, y);
                isBack = false;
                if (backtracking(x, y + 1) && !searchingAll)
                    return true;
            }
            if (isBack) {
                back += 1;
                //  System.out.println("cofa");
            }
        }


        array[x][y] = 0;
        return false;
    }


    private static boolean isOkFC(int k, int x, int y) {
        if (!isOk(k, x, y))
            return false;
        for (int j = y + 1; j < N; j++) {
            if (!doesVariableHasRealm(k, x, j, x, y))
                return false;
        }
        for (int j = x + 1; j < N; j++)
            if (!doesVariableHasRealm(k, j, y, x, y))
                return false;
        return true;

    }

    private static boolean doesVariableHasRealm(int k, int x, int y, int xx, int yy) {
        int realm = N - 1;
        for (int i = 1; i <= N; i++) {
            if (i != k && array[xx][yy] != i && array[xx][yy] != 0)
                if (!isOk(i, x, y))
                    realm--;
        }
        return realm != 0;

    }


    private static void printSolutions() {

        for (Solution s : solutions) {
            printArray(s.solution);
            System.out.println("Backs: " + s.backs + " Iterations:" + s.iterations);
        }
    }

    private static String writeSolutions() {
        String text = "";
        for (Solution solution : solutions) {
            text += arrayToString(solution.solution);
        }

        return text;
    }

    private static String arrayToString(int[][] arr) {
        String text = "";
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                text += arr[i][j] + "|";
            }
            text += "\n\r";
        }
        return text;
    }

    private static void writeToFile(String text, File file) throws IOException {
        FileWriter fw = new FileWriter(file, true);
        fw.append(text);        // Writing to the file
        fw.close();
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

    private static boolean isOk(int k, int x, int y) {
        boolean isOk = !czyJestWWierszuLubKolumnie(k, x, y);
        if (!isOk)
            return false;
        return isOkWithConstraints(k, x, y);
    }

    public static boolean isOkWithConstraints(int k, int x, int y) {
        for (Constraint constraint : constraints) {
            if (constraint.containsCell(x, y)) {
                if (!constraint.isValid(x, y, k, array))
                    return false;
            }
        }
        return true;
    }

    private static void writeFirstPossible(int k, int i, int i1) {
        array[i][i1] = k;
        iterator++;
    }

    private static int[] findFreeCell(int startX, int startY) {
        for (int i = startX; i < N; i++)
            for (int j = startY; j < N; j++) {
                if (array[i][j] == 0)
                    return new int[]{i, j};
            }
        return null;
    }


    private static void printArray(int[][] array) {
        for (int[] n : array) {
            for (int m : n)
                System.out.print(m + " ");
            System.out.println();
        }
    }

    private static void printConstraints() {
        for (Constraint c : constraints)
            System.out.println(c.xL + "" + c.yL + "<" + c.xG + "" + c.yG);
    }

    public static boolean czyJestWWierszuLubKolumnie(int value, int wiersz, int kolumna) {
        return czyJestWWierszu(value, wiersz) || czyJestWKolumnie(value, kolumna);
    }

    public static boolean czyJestWWierszuLubKolumnieBezTejSamej(int value, int wiersz, int kolumna, int x, int y) {
        return czyJestWWierszuBezTejSamej(value, wiersz, x, y) || czyJestWKolumnieBezTejSamej(value, kolumna, x, y);
    }

    public static boolean czyJestWKolumnie(int value, int n) {
        for (int i = 0; i < N; i++) {
            if (array[i][n] == value)
                return true;
        }
        return false;
    }

    public static boolean czyJestWWierszu(int value, int n) {
        for (int i = 0; i < N; i++) {
            if (array[n][i] == value)
                return true;
        }
        return false;
    }

    public static boolean czyJestWKolumnieBezTejSamej(int value, int n, int x, int y) {
        for (int i = 0; i < N; i++) {
            if (array[i][n] == value)
                if (i != x && n != y)
                    return true;
        }
        return false;
    }

    public static boolean czyJestWWierszuBezTejSamej(int value, int n, int x, int y) {
        for (int i = 0; i < N; i++) {
            if (array[n][i] == value)
                if (n != x && i != y)
                    return true;
        }
        return false;
    }

    public static void load(String fileName) throws FileNotFoundException {
        Scanner in = new Scanner(new File(path + fileName));
        String line = in.nextLine();
        N = Integer.valueOf(line);
        array = null;
        array = new int[N][N];
        line = in.nextLine(); //START:
        for (int i = 0; i < N; i++) {
            line = in.nextLine();
            String[] splitedLine = line.split("\\;");
            for (int j = 0; j < N; j++) {
                array[i][j] = Integer.valueOf(splitedLine[j]);
                // System.out.print(array[i][j]);
            }
            //    System.out.println();
        }

        ArrayList<Constraint> c = new ArrayList<Constraint>();
        line = in.nextLine();//REL:
        while (in.hasNext()) {
            line = in.nextLine();
            String[] tmp = line.split("\\;");
            int[] firstCell = changeRelToInts(tmp[0]);
            int[] secondCell = changeRelToInts(tmp[1]);
            c.add(new Constraint(firstCell[0], firstCell[1], secondCell[0], secondCell[1]));
        }
        constraints = new Constraint[c.size()];
        int i = 0;
        for (Constraint tempC : c) {
            constraints[i] = new Constraint(tempC);
            i++;
        }
        c = null;
    }

    private static int[] changeRelToInts(String s) {
        char c = s.charAt(0);
        int y = s.charAt(1) - '0';
        return new int[]{c - 65, y - 1};
    }
}
