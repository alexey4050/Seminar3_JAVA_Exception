import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;


    public class UserInputConsoleReader implements UserInputReader {
        private Scanner scanner;

        public UserInputConsoleReader(Scanner scanner) {
            this.scanner = scanner;
        }

        @Override
        public UserData readUserData() throws UserDataException {
            while (true) {
                System.out.println("Введите данные в следующем порядке, разделяя пробелом: Фамилия Имя Отчество Дата рождения (dd.mm.yyyy) Номер телефона Пол (f и m)");

                try {
                    String input = scanner.nextLine();
                    String[] data = input.split(" ");

                    if (data.length != 6) {
                        throw new UserDataException("Введено неверное количество данных");
                    }
                    LocalDate dateOfBirthday = parseDataBirthday(data[3]);
                    long phoneNumber = parsePhoneNumber(data[4]);
                    Gender gender = parseGender(data[5]);

                    return new UserData(data[0], data[1], data[2], dateOfBirthday, phoneNumber, gender);
            }catch (ParseException | NumberFormatException e) {
                System.out.println("Ошибка" + e.getMessage());
            }
        }
    }
    private LocalDate parseDataBirthday(String dateOfBirthday) throws ParseException {
        try {
            return LocalDate.parse(dateOfBirthday, DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        }catch (Exception e) {
            throw new ParseException("Неверный формат даты рожждения");
        }
    }

    private long parsePhoneNumber(String phoneNumberString) throws  ParseException {
        try {
            return Long.parseLong(phoneNumberString);
        }catch (NumberFormatException e) {
            throw  new ParseException("Неверный формат номера телефона");
        }
    }

    private Gender parseGender(String genderString) throws ParseException {
        if(genderString.equalsIgnoreCase("m")) {
            return Gender.MALE;
        }else if (genderString.equalsIgnoreCase("f")) {
            return  Gender.FEMALE;
        }else {
            throw  new ParseException("Неверный формат ввода пола");
        }
    }
}
