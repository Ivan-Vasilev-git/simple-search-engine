import java.util.List;

public class SearchAll implements SearchStrategy {
  @Override
  public List<String> search(Application app, String query) {
    return app.findPeopleAll(query);
  }
}
