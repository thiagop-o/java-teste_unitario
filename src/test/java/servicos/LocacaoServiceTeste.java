package servicos;

import entidades.Filme;
import entidades.Locacao;
import entidades.Usuario;
import exceptions.LocadoraException;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ErrorCollector;
import org.junit.rules.ExpectedException;
import utils.DataUtils;

import java.util.Date;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;
import static utils.DataUtils.obterDataComDiferencaDias;

public class LocacaoServiceTeste {
    //cenario
    LocacaoService locacaoService = new LocacaoService();
    Usuario usuario = new Usuario("Usuario 1");
    Filme filme = new Filme("filme 1", 1, 5.0);


    //açao
    Locacao locacao = locacaoService.alugarFilme(usuario,filme);

    @Rule
    public ErrorCollector errorCollector = new ErrorCollector();

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    public LocacaoServiceTeste() throws Exception {
    }

    @Test(expected = Exception.class)
    public  void testeEstoqueVazio() throws Exception {
        Filme filme2 = new Filme("filme 1", 0, 5.0);
        //verificacao
        locacaoService.alugarFilme(usuario,filme2);


    }

    @Test
    public  void testeEstoqueVazio2() {
        Filme filme2 = new Filme("filme 1", 0, 5.0);
        //verificacao

        try{
            locacaoService.alugarFilme(usuario,filme2);
            fail("Não deveria ter passado");
        }catch (Exception ex){
            assertThat(ex.getMessage(), is("Filme sem estoque"));
        }
    }

    @Test
    public  void testeEstoqueVazio3() throws Exception {
        Filme filme2 = new Filme("filme 1", 0, 5.0);
        expectedException.expect(Exception.class);
        expectedException.expectMessage("Filme sem estoque");

        //verificacao
        locacaoService.alugarFilme(usuario,filme2);


    }


    @Test
    public  void testeLocacaoValor() {
        //verificacao
        errorCollector.checkThat(locacao.getValor(), is(equalTo(5.0)));

    }
    @Test
    public  void testeDataLocacao() {
        //verificacao
        errorCollector.checkThat(DataUtils.isMesmaData(locacao.getDataLocacao(), new Date()), is(true));

    }
    @Test
    public  void testeDataRetorno() {
        //verificacao

        errorCollector.checkThat(DataUtils.isMesmaData(locacao.getDataRetorno(), obterDataComDiferencaDias(1)), is(true));
    }

    @Test
    public void testeLocacaoUsuarioVazio() throws NullPointerException {


        try {
            locacaoService.alugarFilme(null , filme);
        } catch (LocadoraException e) {
            assertThat(e.getMessage(), is("Usuario Vazio"));
        }


    }
}
