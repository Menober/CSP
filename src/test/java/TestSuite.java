import org.junit.Test;

import static junit.framework.TestCase.assertFalse;
import static junit.framework.TestCase.assertTrue;

public class TestSuite {

    @Test
    public void constraintTest() {
        Constraint c = new Constraint(1, 1, 3, 3);
        assertTrue(c.containsCell(1, 1));
        assertTrue(c.containsCell(3, 3));
        assertFalse(c.containsCell(1, 2));
        assertFalse(c.containsCell(2, 1));
    }

    @Test
    public void isLessTest() {
        int[][] array = {{2, 0, 0},
                {0, 0, 0},
                {3, 3, 3}};

        Constraint c1 = new Constraint(0, 1, 0, 0);
        Constraint c2 = new Constraint(1, 0, 1, 1);
        Main.array = array;
        Main.constraints = new Constraint[]{c1, c2};
        System.out.println(Main.isOkWithConstraints(1, 0, 1));
        System.out.println(Main.isOkWithConstraints(3, 0, 1));
        System.out.println(Main.isOkWithConstraints(1, 1, 0));

    }

    @Test
    public void isOk() {
        int[][] array = {{1, 2, 3},
                          {2, 1, 1},
                          {3, 1, 1}};
        Main2.array = array;
        int[][] con = {{1, 0, 0},
                        {0, 0, 0},
                        {0, 0, 0},
                        {0, 0, 0}};
        Main2.conArray = con;
        System.out.println(Main2.isOkG(0));


    }


}
