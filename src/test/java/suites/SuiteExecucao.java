package suites;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import servicos.CalculadoraTeste;
import servicos.CalculoValorLocacaoTeste;
import servicos.LocacaoServiceTeste;

import static org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({
        CalculadoraTeste.class,
        CalculoValorLocacaoTeste.class,
        LocacaoServiceTeste.class
})
public class SuiteExecucao {

}
