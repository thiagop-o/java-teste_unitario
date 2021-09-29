package servicos;

import entidades.Filme;
import entidades.Locacao;
import entidades.Usuario;
import exceptions.LocadoraException;
import org.junit.Assert;
import org.junit.Test;
import utils.DataUtils;

import java.util.Date;

import static utils.DataUtils.adicionarDias;
import static utils.DataUtils.obterDataComDiferencaDias;

public class LocacaoService {
	
	public Locacao alugarFilme(Usuario usuario, Filme filme) throws LocadoraException {


		if(usuario == null){
			throw new LocadoraException("Usuario Vazio");
		}

		if (filme.getEstoque() == 0){
			throw new LocadoraException("Filme sem estoque");
		}
		Locacao locacao = new Locacao();
		locacao.setFilme(filme);
		locacao.setUsuario(usuario);
		locacao.setDataLocacao(new Date());
		locacao.setValor(filme.getPrecoLocacao());

		//Entrega no dia seguinte
		Date dataEntrega = new Date();
		dataEntrega = adicionarDias(dataEntrega, 1);
		locacao.setDataRetorno(dataEntrega);
		
		//Salvando a locacao...	
		//TODO adicionar método para salvar
		
		return locacao;
	}


}