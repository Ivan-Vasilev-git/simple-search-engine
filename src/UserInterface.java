import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class UserInterface<T> {

  private Application app;
  private Scanner scanner;
  List<String> listOfFoundPeople;
  private SearchContext context;

  public UserInterface() {
    app = new Application();
    scanner = new Scanner(System.in);
    context = new SearchContext();
    listOfFoundPeople = new ArrayList<>();
  }

  public void start(String[] args) {
    if (args.length > 1 && args[0].equals("--data")) {
      addPeople(FileProcessor.readFromFile(args[1]));
      createInvertedIndexMap();
    } else {
      System.out.println("Invalid command line arguments or file name");
      return;
    }
    startMainMenu();
  }

  private void addPeople(List<String> input) {
    for (int i = 0; i < input.size(); i++) {
      String s = input.get(i);
      String[] person = s.trim().split(" ");
      switch (person.length) {
        case 1:
          app.addPersonToMap(i, new Person(person[0]));
          break;
        case 2:
          app.addPersonToMap(i, new Person(person[0], person[1]));
          break;
        default:
          app.addPersonToMap(i, new Person(person[0], person[1], person[2]));
          break;

      }
    }
  }

  private void createInvertedIndexMap() {
    app.createInvertedIndexMap();
  }

  private void startMainMenu() {
    while (true) {
      printMainMenu();
      String input = scanner.nextLine().trim();
      switch (input) {
        case "1":
          startSearchStrategiesMenu();
          findPeopleWithStrategy();
          printListOfPeople((List<T>) listOfFoundPeople);
          break;
        case "2":
          System.out.println("\n=== List of people ===");
          printMapOfPeople(app.getPersonsMap());
          break;
        case "0":
          System.out.println("\nBye!");
          scanner.close();
          return;
        default:
          System.out.println("Incorrect option! Try again.\n");
          break;
      }
    }
  }

  private void printMainMenu() {
    System.out.println("\n=== Menu ===\n"
        + "1. Find a person\n"
        + "2. Print all people\n"
        + "0. Exit");
  }

  private void startSearchStrategiesMenu() {
    while (true) {
      System.out.println("\nSelect a matching strategy: ALL, ANY, NONE");
      String option = scanner.nextLine();
      switch (option) {
        case "ALL":
          context.setStrategy(new SearchAll());
          return;
        case "ANY":
          context.setStrategy(new SearchAny());
          return;
        case "NONE":
          context.setStrategy(new SearchNone());
          return;
        default:
          System.out.println("Invalid option.");
          break;
      }
    }
  }

  private void findPeople() {
    System.out.println("\nEnter a name or email to search all suitable people.");
    String query = scanner.nextLine();
    listOfFoundPeople = app.findPeopleWithInvertedIndex(query);
  }

  private void findPeopleWithStrategy() {
    System.out.println("\nEnter a name or email to search all suitable people.");
    String query = scanner.nextLine();
    listOfFoundPeople = context.executeSearchStrategy(app, query);
  }

  private void printListOfPeople(List<T> listOfPeople) {
    if (!listOfPeople.isEmpty()) {
      System.out.println(listOfPeople.size() + " persons found:");
      for (T t : listOfPeople) {
        System.out.println(t.toString().trim());
      }
    } else {
      System.out.println("No matching people found.\n");
    }
  }

  private void printMapOfPeople(Map<Integer, Person> map) {
    for (Integer key : map.keySet()) {
      System.out.println(map.get(key).toString());
    }
  }
}
