import java.util.List;

public class SearchContext {
  private SearchStrategy strategy;

  public void setStrategy(SearchStrategy strategy) {
    this.strategy = strategy;
  }

  public List<String> executeSearchStrategy(Application app, String query) {
    return strategy.search(app, query);
  }
}
