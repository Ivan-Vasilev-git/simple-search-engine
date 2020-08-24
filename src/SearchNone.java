import java.util.List;

public class SearchNone implements SearchStrategy {
  @Override
  public List<String> search(Application app, String query) {
    return app.findPeopleNone(query);
  }
}
