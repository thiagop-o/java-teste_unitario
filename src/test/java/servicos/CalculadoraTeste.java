package servicos;

import exceptions.NaoPodeDividirPorZeroException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class CalculadoraTeste {
    private Calculadora calc;

    @Before
    public void setup(){
        calc = new Calculadora();
    }

    @Test
    public void c1_somarValores(){
        //cenario
        int a = 5, b = 3;


        //acao
        int resultado = calc.somar(a,b);

        //verificacao
        Assert.assertEquals(8,resultado);

    }

    @Test
    public void c2_subtrairValores(){
        int a = 3, b = 2;


        int resultado = calc.subtrair(a,b);

        Assert.assertEquals(1,resultado);
    }

    @Test
    public void c3_dividirValores() throws NaoPodeDividirPorZeroException {
        int a = 6, b = 3;


        int resultado = calc.dividir(a,b);
        Assert.assertEquals(2,resultado);

    }

    @Test(expected = NaoPodeDividirPorZeroException.class)
    public void c4_lancarExceptionSeZero() throws NaoPodeDividirPorZeroException {
        int a = 10, b = 0;


        calc.dividir(a,b);
    }
}
