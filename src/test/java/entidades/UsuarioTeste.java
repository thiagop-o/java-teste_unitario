package entidades;


import org.junit.Assert;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class UsuarioTeste {
    @Test(expected = Exception.class)
    public void nomeVazio() throws Exception{
        Usuario usuario = new Usuario();

        Assert.assertNull(usuario.getNome());


    }
}
