import entidades.Usuario;
import org.junit.Assert;
import org.junit.Test;

public class AssertTeste {

    @Test
    public void teste(){
        Assert.assertTrue(true);
        Assert.assertFalse(false);

        Assert.assertEquals(1,1);
        Usuario u1 = new Usuario("Usuario 1");
        Usuario u2 = new Usuario("Usuario 1");
        Usuario u3 = null;

        Assert.assertEquals(u1.getClass().getName(),u2.getClass().getName() );
        Assert.assertNull(u3);


    }

}
