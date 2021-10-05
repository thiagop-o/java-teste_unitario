package servicos;

import dao.LocacaoDAO;
import entidades.Filme;
import entidades.Locacao;
import entidades.Usuario;
import exceptions.FilmeSemEstoqueException;
import exceptions.LocadoraException;
import matchers.DiaDaSemanaMatcher;
import org.junit.*;
import org.junit.rules.ErrorCollector;
import org.junit.rules.ExpectedException;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import utils.DataUtils;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static builders.FilmeBuilder.umFilme;
import static builders.LocacaoBuilder.umLocacao;
import static builders.UsuarioBuilder.umUsuario;
import static matchers.MatchersProprios.caiEm;
import static matchers.MatchersProprios.caiNumaSegunda;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.*;
import static utils.DataUtils.isMesmaData;
import static utils.DataUtils.obterDataComDiferencaDias;

public class LocacaoServiceTeste {
    @InjectMocks
    private LocacaoService service;
    @Mock
    private LocacaoDAO dao;
    @Mock
    private SPCService spc;
    @Mock
    private EmailService emailService;

    @Rule
    public ErrorCollector error = new ErrorCollector();

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Before
    public void setup(){
        MockitoAnnotations.initMocks(this);
        /*service = new LocacaoService();
        dao = mock(LocacaoDAO.class);
        spc = mock(SPCService.class);
        service.setLocacaoDAO(dao);
        service.setSpcService(spc);
        emailService = mock(EmailService.class);
        service.setEmailService(emailService);*/
    }

    @Test
    public void deveAlugarFilme() throws Exception {
        Assume.assumeFalse(DataUtils.verificarDiaSemana(new Date(),Calendar.SATURDAY));
        //cenario
        Usuario usuario = umUsuario().agora();
        List<Filme> filmes = Arrays.asList(umFilme().comValor(5.0).agora());

        //acao
        Locacao locacao = service.alugarFilme(usuario, filmes);

        //verificacao
        error.checkThat(locacao.getValor(), is(equalTo(5.0)));
        error.checkThat(isMesmaData(locacao.getDataLocacao(), new Date()), is(true));
        error.checkThat(isMesmaData(locacao.getDataRetorno(), obterDataComDiferencaDias(1)), is(true));
    }

    @Test(expected = FilmeSemEstoqueException.class)
    public void naoDeveAlugarFilmeSemEstoque() throws Exception{
        //cenario
        Usuario usuario = umUsuario().agora();
        List<Filme> filmes = Arrays.asList(umFilme().semEstoque().agora());

        //acao
        service.alugarFilme(usuario, filmes);
    }

    @Test
    public void naoDeveAlugarFilmeSemUsuario() throws Exception{
        //cenario
        List<Filme> filmes = Arrays.asList(umFilme().agora());

        //acao
        try {
            service.alugarFilme(null, filmes);
            Assert.fail();
        } catch (LocadoraException e) {
            assertThat(e.getMessage(), is("Usuario vazio"));
        }
    }

    @Test
    public void naoDeveAlugarFilmeSemFilme() throws Exception {
        //cenario
        Usuario usuario = umUsuario().agora();

        exception.expect(LocadoraException.class);
        exception.expectMessage("Filme vazio");

        //acao
        service.alugarFilme(usuario, null);
    }

    @Test
    public void deveDevolverNaSegundaAoAlugarNoSabado() throws Exception {
        Assume.assumeTrue(DataUtils.verificarDiaSemana(new Date(), Calendar.SATURDAY));

        Usuario usuario = umUsuario().agora();
        List<Filme> filmes = Arrays.asList(umFilme().agora());

        Locacao retorno = service.alugarFilme(usuario, filmes);

        //boolean ehSegunda = DataUtils.verificarDiaSemana(retorno.getDataRetorno(), Calendar.MONDAY);
        //Assert.assertTrue(ehSegunda);
        assertThat(retorno.getDataRetorno(),new DiaDaSemanaMatcher(Calendar.MONDAY));
        assertThat(retorno.getDataRetorno(), caiEm(Calendar.MONDAY));
        assertThat(retorno.getDataRetorno(), caiNumaSegunda());
    }

    @Test
    public void naoDeveAlugarFilmeParaNegativado() throws Exception {
        Usuario usuario = umUsuario().agora();;
        List<Filme> filmes = Arrays.asList(umFilme().agora());

        when(spc.possuiNegaticação(usuario)).thenReturn(true);


        try {
            service.alugarFilme(usuario,filmes);
            Assert.fail();
        } catch (LocadoraException e) {
            Assert.assertThat(e.getMessage(), is("Usuario Negativado"));
        }


        verify(spc).possuiNegaticação(usuario);
    }

    @Test
    public void deveEnviarEmailParaLocacoesAtrasadas(){
        Usuario usuario = umUsuario().agora();
        Usuario usuario2 = umUsuario().comNome("Usuario em dia").agora();
        Usuario usuario3 = umUsuario().comNome("Outro Atrasado").agora();
        List<Locacao> locacoes = Arrays.asList(
                umLocacao().comUsuario(usuario).atrasado().agora(),
                umLocacao().comUsuario(usuario2).agora(),
                umLocacao().comUsuario(usuario3).atrasado().agora());

        when(dao.obterLocacoesPendentes()).thenReturn(locacoes);

        service.notificarAtrasos();

        verify(emailService).notificarAtraso(usuario);
        verify(emailService, never()).notificarAtraso(usuario2);
        verify(emailService).notificarAtraso(usuario3);
    }

    @Test
    public void deveTratarErroSPC() throws Exception {
        //cenario
        Usuario usuario = umUsuario().agora();
        List<Filme> filmes = Arrays.asList(umFilme().agora());

        when(spc.possuiNegaticação(usuario)).thenThrow(new Exception("Sistema Instavel no SPC"));
        exception.expect(LocadoraException.class);
        exception.expectMessage("SPC fora do ar, tente novamente");

        //acao
        service.alugarFilme(usuario,filmes);

        //verificacao


    }
}
