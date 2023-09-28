package Task1;

public class Elevator {
    private int currentFloor;
    private int minFloor;
    private int maxFloor;

    public Elevator(int minFloor, int maxFloor) {
        this.minFloor = minFloor;
        this.maxFloor = maxFloor;
        this.currentFloor = 1;
    }

    public int getCurrentFloor() {
        return currentFloor;
    }

    public void moveDown() {
        if (currentFloor > minFloor) {
            currentFloor--;
        }
    }

    public void moveUp() {
        if (currentFloor < maxFloor) {
            currentFloor++;
        }
    }

    public void move(int floor) {
        if (floor >= minFloor && floor <= maxFloor) {
            while (currentFloor < floor) {
                moveUp();
                System.out.println("Текущий этаж: " + currentFloor);
            }

            while (currentFloor > floor) {
                moveDown();
                System.out.println("Текущий этаж: " + currentFloor);
            }
        } else {
            System.out.println("Ошибка: введенный этаж находится за пределами допустимого диапазона.");
        }
    }
}
