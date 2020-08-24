import java.util.List;

public interface SearchStrategy {
  List<String> search(Application app, String query);
}
