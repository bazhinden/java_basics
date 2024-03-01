public class Main {
    public static void main(String[] args) {
        Container container = new Container();
        container.addCount(5672);
        System.out.println(container.getCount());

        for (int i = 0; i <= 65535; i++) {
            char ch = (char) i;

            if ((ch >= 'А' && ch <= 'Я') || (ch >= 'а' && ch <= 'я') || ch == 'Ё' || ch == 'ё') {
                System.out.println("Буква: " + ch + ", Код: " + i);
            }
        }
    }
}
