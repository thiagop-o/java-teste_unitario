package dao;

import entidades.Locacao;

import java.util.List;

public interface LocacaoDAO {
    public void salvar(Locacao locacao);

    List<Locacao> obterLocacoesPendentes();
}
