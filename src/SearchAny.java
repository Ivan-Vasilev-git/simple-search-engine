import java.util.List;

public class SearchAny implements SearchStrategy {
  @Override
  public List<String> search(Application app, String query) {
    return app.findPeopleAny(query);
  }
}
