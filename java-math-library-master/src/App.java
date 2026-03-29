import de.tilman_neumann.util.Multiset;
import de.tilman_neumann.util.Multiset_HashMapImpl;
import de.tilman_neumann.util.Pair;
import de.tilman_neumann.util.StringUtil;

public class App {
    public static void main(String[] args) {
        Pair<String, Integer> p = new Pair<String, Integer>("hassan", 10);
        Multiset<String> m = new Multiset_HashMapImpl<String>();
        String s = StringUtil.repeat("hassan", 2);
        String s2 = StringUtil.formatLeft("hassan", "1234567");
        String s3 = StringUtil.formatRight("hassan", "1234567");

        m.add("hassan");
        m.add("hassan", -3);
        System.out.println(m.get("hassann"));
        System.out.println(p.getFirst());
        System.out.println(s);
        System.out.println(s2);
        System.out.println(s3);
    }
}