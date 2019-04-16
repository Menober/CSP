public class Constraint {
    int xL;
    int yL;
    int xG;
    int yG;

    public Constraint(int xL, int yL, int xG, int yG) {
        this.xL = xL;
        this.yL = yL;
        this.xG = xG;
        this.yG = yG;
    }

    public Constraint(Constraint tempC) {
        this.xL = tempC.xL;
        this.yL = tempC.yL;
        this.xG = tempC.xG;
        this.yG = tempC.yG;
    }

    public boolean containsCell(int x, int y) {
        return (xL == x && yL == y) || (xG == x && yG == y);
    }

    public boolean isValid(int x1, int y1, int v1, int v2) {
        boolean isValid = false;
        if (v1 == 0 || v2 == 0)
            return true;
        //pierwsza jest mniejsza
        if (x1 == xL && y1 == yL) {
            if (v1 < v2)
                isValid = true;
        } else { //pierwsza większa
            if (v1 > v2)
                isValid = true;
        }
        return isValid;
    }

    public boolean isValid(int x1, int y1, int v1, int[][] array) {
        boolean isValid = false;
        //pierwsza jest mniejsza
        if (x1 == xL && y1 == yL) {
            if (v1 < array[xG][yG] || array[xG][yG] == 0)
                isValid = true;
        } else { //pierwsza większa
            if (v1 > array[xL][yL] || array[xL][yL] == 0)
                isValid = true;
        }
        return isValid;
    }
}
