package matchers;

import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;
import utils.DataUtils;

import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class DiaDaSemanaMatcher extends TypeSafeMatcher<Date> {
    private Integer diaSemana;
    public DiaDaSemanaMatcher(Integer diaSemana) {
        this.diaSemana = diaSemana;

    }

    @Override
    protected boolean matchesSafely(Date item) {
        return DataUtils.verificarDiaSemana(item,diaSemana);
    }

    @Override
    public void describeTo(Description description) {
        Calendar data = Calendar.getInstance();
        data.set(Calendar.DAY_OF_WEEK, diaSemana);
        String dataExtenso = data.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.LONG, new Locale("pt", "BR"));
        description.appendText(dataExtenso);
    }
}
