package divingcalculations;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DiveFormulasTest {

    @Test
    void calculateMOD() {
        DiveFormulas diveFormulas = new DiveFormulas();
        assertEquals(33, diveFormulas.calculateMOD(1.4, 32));
    }

    @Test
    void calculateSMOD() {
        DiveFormulas diveFormulas = new DiveFormulas();
        assertEquals(33, diveFormulas.calculateSMOD(32));
    }

    @Test
    void calculateBM() {
        DiveFormulas diveFormulas = new DiveFormulas();
        assertEquals(22, diveFormulas.calculateBM(1.5, 56));
    }

    @Test
    void calculatePP() {
        DiveFormulas diveFormulas = new DiveFormulas();
        assertEquals(1.15, diveFormulas.calculatePP(40, 23));
    }

    @Test
    void calculateEAD() {
        DiveFormulas diveFormulas = new DiveFormulas();
        assertEquals(57, diveFormulas.calculateEAD(58, 22));
    }

    @Test
    void print_PPTable(){
        DiveFormulas diveFormulas = new DiveFormulas();
        diveFormulas.print_PPTable(33,40,15,40);
    }

    @Test
    void print_EadtTable(){
        DiveFormulas diveFormulas = new DiveFormulas();
        diveFormulas.print_EadtTable(21,25,3,31);
    }
}