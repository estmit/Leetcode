import java.util.List;
import model.NestedInteger;

public class NestedListWeightSum {
    public int depthSum(List<NestedInteger> nestedList) {
        if (nestedList == null || nestedList.isEmpty()) {
            return 0;
        }

        int position = 0;
        int depth = 1;
        return sum(nestedList, position, depth);
    }

    protected int sum(List<NestedInteger> list, int pos, int depth) {
        if (pos >= list.size()) {
            return 0;
        }

        NestedInteger i = list.get(pos);
        if (i.isInteger()) {
            return i.getInteger() * depth + sum(list, pos + 1, depth);
        } else {
            List<NestedInteger> l = i.getList();
            return sum(l, 0, depth + 1) + sum(list, pos + 1, depth);
        }
    }
}
