import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.MockedStatic;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mockStatic;


class HorseTest {
    private static Horse horse;
    private static Horse horseWithoutDistance;

    @BeforeAll
    static void beforeAll() {
        horse = new Horse("h", 1.0, 2.0);
        horseWithoutDistance = new Horse("h", 1.0);
    }

    @Test
    public void testConstructorWithNullName() {
        assertThrows(IllegalArgumentException.class, () -> new Horse(null, 1));
    }

    @Test
    public void testConstructorWithNullNameMessage() {
        Throwable exception = assertThrows(IllegalArgumentException.class, () -> new Horse(null, 0.0));
        assertEquals("Name cannot be null.", exception.getMessage());
    }

    @ParameterizedTest
    @ValueSource(strings = {"", "   ", "\t", "\n", "\r\n"})
    public void testConstructorWithSpaceName(String name) {
        assertThrows(IllegalArgumentException.class, () -> new Horse(name, 0.0));
    }

    @ParameterizedTest
    @ValueSource(strings = {"", "   ", "\t", "\n", "\r\n"})
    public void testConstructorWithSpaceNameMessage(String name) {
        Throwable exception = assertThrows(IllegalArgumentException.class, () -> new Horse(name, 0.0));
        assertEquals("Name cannot be blank.", exception.getMessage());
    }

    @Test
    public void testConstructorWithNegativeSpeedMessage() {
        Throwable exception = assertThrows(IllegalArgumentException.class, () -> new Horse("h", -1));
        assertEquals("Speed cannot be negative.", exception.getMessage());
    }

    @Test
    public void testConstructorWithNegativeSpeed() {
        assertThrows(IllegalArgumentException.class, () -> new Horse("h", -1));
    }

    @Test
    public void testConstructorWithNegativeDistance() {
        assertThrows(IllegalArgumentException.class, () -> new Horse("h", 0.0, -1));

    }

    @Test
    public void testConstructorWithNegativeDistanceMessage() {
        Throwable exception = assertThrows(IllegalArgumentException.class, () -> new Horse("h", 0.0, -1));
        assertEquals("Distance cannot be negative.", exception.getMessage());
    }

    @Test
    void getName() {
        assertEquals("h", horse.getName());
    }

    @Test
    void getSpeed() {
        assertEquals(1.0, horse.getSpeed());
    }

    @Test
    void getDistance() {
        assertEquals(2.0, horse.getDistance());
    }

    @Test
    void getDistanceNull() {
        assertEquals(0, horseWithoutDistance.getDistance());
    }


    @Test
    void moveGetRandomDoubleCalled() {
        try (MockedStatic<Horse> horseMockedStatic = mockStatic(Horse.class)) {
            horse.move();
            horseMockedStatic.verify(() -> Horse.getRandomDouble(0.2, 0.9));
        }
    }

    @Test
    void moveGetRandomDouble() {
        try (MockedStatic<Horse> horseMockedStatic = mockStatic(Horse.class)) {
            horseMockedStatic.when(() -> Horse.getRandomDouble(0.2, 0.9)).thenReturn(0.5);
            assertEquals(0.5, Horse.getRandomDouble(0.2, 0.9), 0.0001);
        }
    }

    @Test
    void moveDistance() {
        double distance = 2.0;
        double speed = 1.0;

        try (MockedStatic<Horse> horseMockedStatic = mockStatic(Horse.class)) {
            horseMockedStatic.when(() -> Horse.getRandomDouble(0.2, 0.9)).thenReturn(0.5);
            double expectedDistance = distance + speed * 0.5;
            horse.move();
            assertEquals(expectedDistance, horse.getDistance(), 0.0001);
        }
    }
}