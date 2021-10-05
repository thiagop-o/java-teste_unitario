package servicos;

import dao.LocacaoDAO;
import entidades.Filme;
import entidades.Locacao;
import entidades.Usuario;
import exceptions.FilmeSemEstoqueException;
import exceptions.LocadoraException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.runners.Parameterized.Parameter;
import static org.junit.runners.Parameterized.Parameters;

@RunWith(Parameterized.class)
public class CalculoValorLocacaoTeste {
    @InjectMocks
    private LocacaoService service;
    @Mock
    private LocacaoDAO dao;
    @Mock
    private SPCService spc;


    @Parameter
    public List<Filme> filmes;
    @Parameter(value = 1)
    public double valorLocacao;
    @Parameter(value = 2)
    public String cenario;



    private final static Filme filme1 = new Filme("Filme 1", 2, 4.0);
    private final static Filme filme2 = new Filme("Filme 2", 2, 4.0);
    private final static Filme filme3 = new Filme("Filme 3", 2, 4.0);
    private final static Filme filme4 = new Filme("Filme 4", 2, 4.0);
    private final static Filme filme5 = new Filme("Filme 5", 2, 4.0);
    private final static Filme filme6 = new Filme("Filme 6", 2, 4.0);

    @Before
    public void setup(){
        MockitoAnnotations.initMocks(this);
    }

    @Parameters(name = "{2}")
    public static Collection<Object> getParametros(){
        return Arrays.asList(new Object[][]{
                {Arrays.asList(filme1,filme2,filme3),11.0, "Desconto 25% no 3º Filme"},
                {Arrays.asList(filme1,filme2,filme3,filme4),13.0 , "Desconto 50% no 4º Filme"},
                {Arrays.asList(filme1,filme2,filme3,filme4,filme5),14.0 , "Desconto 75% no 5º Filme"},
                {Arrays.asList(filme1,filme2,filme3,filme4,filme5,filme6),14.0 , "Desconto 100% no 6º Filme"}
        });
    }

    @Test
    public void deveCalcularValorLocacaoConsiderandoDescontos() throws Exception {
        //cenario
        Usuario usuario = new Usuario("Usuario 1");


        //acao
        Locacao resultado = service.alugarFilme(usuario, filmes);

        //verificacao
        assertThat(resultado.getValor(), is(valorLocacao));
    }
}
