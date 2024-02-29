public class Main {
    public static void main(String[] args) {
        Container container = new Container();
        container.addCount(5672);
        System.out.println(container.getCount());

        System.out.println("Числовые коды букв русского алфавита.");
        System.out.println("Заглавные буквы:");
        for (char ch = 'А'; ch <= 'Я'; ch++) {
            int code = (int) ch;
            System.out.println(ch + ": " + code);
        }

        System.out.println("Строчные буквы:");
        for (char ch = 'а'; ch <= 'я'; ch++) {
            int code = (int) ch;
            System.out.println(ch + ": " + code);
        }

        char letterYo = 'Ё';
        int codeYo = (int) letterYo;
        System.out.println("Буква " + letterYo + ": " + codeYo);

    }
}
