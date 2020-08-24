import java.util.*;

public class Application {

  private List<Person> personList;
  private Map<Integer, Person> personsMap;
  private Map<String, List<Integer>> invertedIndexMap;

  public Application() {
    personList = new ArrayList<>();
    personsMap = new HashMap<>();
    invertedIndexMap = new TreeMap<>();
  }

  public void addPerson(String firstName, String lastName, String email) {
    personList.add(new Person(firstName, lastName, email));
  }

  public void addPerson(String firstName, String lastName) {
    personList.add(new Person(firstName, lastName));
  }

  public void addPerson(String firstName) {
    personList.add(new Person(firstName));
  }

  public void addPersonToMap(int key, Person person) {
    if (!personsMap.containsKey(key)) {
      personsMap.put(key, person);
    }
  }

  public void createInvertedIndexMap() {
    for (Integer key : personsMap.keySet()) {
      Person person = personsMap.get(key);
      addToInvertedIndexMap(person.getFirstName().toLowerCase(), key);
      if (person.getLastName() != null && !person.getLastName().isEmpty()) {
        addToInvertedIndexMap(person.getLastName().toLowerCase(), key);
      }
      if (person.getEmail() != null && !person.getEmail().isEmpty()) {
        addToInvertedIndexMap(person.getEmail().toLowerCase(), key);
      }
    }
  }

  private void addToInvertedIndexMap(String key, Integer value) {
    if (!invertedIndexMap.containsKey(key)) {
      invertedIndexMap.put(key, new ArrayList<>(value));
    }
    invertedIndexMap.get(key).add(value);
  }

  public List<String> findPeople(String query) {
    List<String> result = new ArrayList<>();
    for (Person p : personList) {
      if (p.toString().toLowerCase().contains(query.toLowerCase())) {
        result.add(p.toString());
      }
    }
    return result;
  }

  public List<String> findPeopleWithInvertedIndex(String query) {
    List<String> result = new ArrayList<>();
    List<Integer> indexes;
    if (invertedIndexMap.containsKey(query.toLowerCase())) {
      indexes = invertedIndexMap.get(query.toLowerCase());
      for (Integer key : indexes) {
        result.add(personsMap.get(key).toString());
      }
    }
    return result;
  }

  public List<String> findPeopleAny(String query) {
    List<String> result = new ArrayList<>();
    String[] queryArray = query.split(" ");
    for (String s : queryArray) {
      result.addAll(findPeopleWithInvertedIndex(s));
    }
    return result;
  }

  public List<String> findPeopleAll(String query) {
    String[] queryArray = query.split(" ");
    List<String> result = findPeopleWithInvertedIndex(queryArray[0]);
    for (int i = 1; i < queryArray.length; i++) {
      for (String s : result) {
        if (!s.contains(queryArray[i])) {
          result.remove(s);
        }
      }
    }
    return result;
  }

  public List<String> findPeopleNone(String query) {
    List<String> result = new ArrayList<>();
    for (Integer key : personsMap.keySet()) {
      result.add(personsMap.get(key).toString());
    }
    result.removeAll(findPeopleAny(query));
    return result;
  }

  public List<Person> getPersonList() {
    return List.copyOf(personList);
  }

  public Map<Integer, Person> getPersonsMap() {
    return personsMap;
  }
}
