import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class SubdomainVisitCount {
    public List<String> subdomainVisits(String[] cpdomains) {
        Map<String, Integer> domainCounter = new HashMap<>();

        for (String cpdomain : cpdomains) {
            int splitIdx = cpdomain.indexOf(" "); // more efficient than cpdomain.split since we know there is only 1
                                                  // space in the string
            Integer count = Integer.valueOf(cpdomain.substring(0, splitIdx));
            String domain = cpdomain.substring(splitIdx + 1);
            for (int i = domain.length() - 1; i >= 0; --i) {
                if (domain.charAt(i) == '.') {
                    String subdomain = domain.substring(i + 1);
                    domainCounter.merge(subdomain, count, Integer::sum);
                }
            }
            domainCounter.merge(domain, count, Integer::sum);
        }

        return domainCounter.entrySet().stream()
                .map(domainCountKvPair -> domainCountKvPair.getValue().toString() + " " + domainCountKvPair.getKey())
                .collect(Collectors.toList());
    }
}
