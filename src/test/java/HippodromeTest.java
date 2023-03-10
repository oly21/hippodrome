import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class HippodromeTest {
    private static Hippodrome hippodrome;
    private static Hippodrome hippodromeMock;
    static List<Horse> horsesEmpty = new ArrayList<>();
    static List<Horse> horses = new ArrayList<>();
    static List<Horse> horsesMock = new ArrayList<>();


    @BeforeAll
    static void beforeAll() {
        for (int i = 0; i < 30; i++) {
            horses.add(new Horse("n", 0.1, 1.0 + i));
        }
        hippodrome = new Hippodrome(horses);

        for (int i = 0; i < 50; i++) {
            horsesMock.add(mock(Horse.class));
        }
        hippodromeMock = new Hippodrome(horsesMock);

    }

    @Test
    public void testHippodromeConstructorWithNullList() {
        assertThrows(IllegalArgumentException.class, () -> new Hippodrome(null));
    }

    @Test
    public void testConstructorWithNullListMessage() {
        Throwable exception = assertThrows(IllegalArgumentException.class, () -> new Hippodrome(null));
        assertEquals("Horses cannot be null.", exception.getMessage());
    }

    @Test
    public void testHippodromeConstructorWithEmptyList() {
        assertThrows(IllegalArgumentException.class, () -> new Hippodrome(horsesEmpty));
    }

    @Test
    public void testConstructorWithEmptyListMessage() {
        Throwable exception = assertThrows(IllegalArgumentException.class, () -> new Hippodrome(horsesEmpty));
        assertEquals("Horses cannot be empty.", exception.getMessage());
    }

    @Test
    void getHorses() {
        assertIterableEquals(horses, hippodrome.getHorses());
    }

    @Test
    void move() {
        hippodromeMock.move();
        for (Horse horse : horsesMock) {
            verify(horse).move();
        }
    }

    @Test
    void getWinner() {
        assertEquals(30.0, hippodrome.getWinner().getDistance());
    }
}