package matchers;

import java.util.Calendar;

public class MatchersProprios {
    public static DiaDaSemanaMatcher caiEm(Integer diaSemana){
        return new DiaDaSemanaMatcher(diaSemana);
    }

    public static DiaDaSemanaMatcher caiNumaSegunda(){
        return new DiaDaSemanaMatcher(Calendar.MONDAY);
    }
}
