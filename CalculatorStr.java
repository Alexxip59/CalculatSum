import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Введите строку и подстроку каждую в двойных кавычках и операнд между ними -, /, *, + ");
        String strSum = scanner.nextLine();

        String regex = "^ *\"([^\"]*?)\" *(?:([-+]) *\"([^\"]*?)\"|([/*]) *(\\d+?)) *$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(strSum);
        boolean found = matcher.find();

        if (!found) {
            throw new IllegalArgumentException(" Ошибка Выражения !");
        }

        String[] members = parseStr(matcher);
        String result = "";

        switch (members[1]) {
            case "+":
                result = sumStr(members);
                break;
            case "-":
                result = diffStr(members);
                break;
            case "/":
                result = divStr(members);
                break;
            case "*":
                result = multipleStr(members);
                break;
            default:
                break;
        }

        System.out.println("\"" + result + "\"");
    }

    private static String multipleStr(String[] members) {
        testStr(members[0]);
        int counter = Integer.parseInt(members[2]);
        testNumber(counter);
        String result = "";

        for (int i = 0; i < counter; i++) {
            result = result.concat(members[0]);
        }

        int lengthResult = result.length();

        if (lengthResult > 40) {
            result = result.substring(0, 40) + "...";
        }

        return result;
    }

    private static String diffStr(String[] members) {
        testStr(members[0]);
        testStr(members[2]);
        return members[0].replaceAll(members[2], "");
    }

    private static String sumStr(String[] members) {
        testStr(members[0]);
        testStr(members[2]);
        return members[0].concat(members[2]);
    }

    private static String divStr(String[] members) {
        testStr(members[0]);
        String result = "";
        int lengthStr = (members[0].length());
        int number = Integer.parseInt(members[2]);
        testNumber(number);
        int r = lengthStr / number;
        result = members[0].substring(0,r);
        return result;
    }

    private static void testNumber(int number) {
        if ((number < 1) || (number > 10)) {
            throw new IllegalArgumentException(" Число должно быть от 1 до 10 включительно !");
        }
    }

    private static void testStr(String member) {
        if (member.length() > 10) {
            throw new IllegalArgumentException(" Длинна вводимой строки не должна превышать 10 символов ! ");
        }
    }

    private static String[] parseStr(Matcher matcher) {
        int counter = matcher.groupCount();
        String[] members = new String[3];
        int offset = 0;
        for (int i = 1; i <= counter; i++) {
            String local = matcher.group(i);
            if (local != null) {
                members[offset] = local;
                offset++;
            }

        }

        return members;
    }

}
