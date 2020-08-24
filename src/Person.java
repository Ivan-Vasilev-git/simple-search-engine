public class Person {

  private String firstName;
  private String lastName;
  private String email;

  public Person(String firstName) {
    this(firstName, "", "");
  }

  public Person(String firstName, String lastName) {
    this(firstName, lastName, "");
  }

  public Person(String firstName, String lastName, String email) {
    this.firstName = firstName;
    this.lastName = lastName;
    this.email = email;
  }

  public String getFirstName() {
    return firstName.trim();
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public String getLastName() {
    return lastName.trim();
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public String getEmail() {
    return email.trim();
  }

  public void setEmail(String email) {
    this.email = email;
  }

  @Override
  public String toString() {
    return (firstName + " " + lastName + " " + email).trim();
  }
}
