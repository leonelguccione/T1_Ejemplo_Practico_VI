package sinonimos;
/*====================================================================================
 * La prueba de persistencia debe demostrar :
 * Se puede crear un Diccionario Vacio
 * Se pueden agregar Sinonimos y que estos persistan al momento de ser agregados
 * Se puedan eliminar Sinonimos y que estos persistan al momento de ser eliminados
 * ======================================================================================
 */

import java.io.File;
import java.io.FileNotFoundException;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class PersistenciaTest
{
    Diccionario dic;

    public PersistenciaTest()
    {
    }

    //borra el archivo
    @Before
    public void setUp() 
    {
        dic = new Diccionario();
        File archivo = new File("sinonimos.xml");
        if (archivo.exists())
            archivo.delete();
    }

    @After
    public void tearDown()
    {

    }

    //verifica que el método persistir crea un archivo
    @Test
    public void testCrearArchivo()
    {
        try
        {
            Persistencia.persistir(dic);
            File archivo = new File("sinonimos.xml");
            Assert.assertTrue("Debería existir el archivo sinonimos.xml", archivo.exists());

        }
        catch (FileNotFoundException e)
        {
            Assert.fail("No debería lanzar excepcion: " + e.getMessage());
        }
    }

    //verifica la lectoescritura de un diccionario vacío. 
    //la clase Diccionario debe tener sobreescritos los métodos equals y haschode. De otra forma
    //no puedo comparar dos instancias distintas de la misma clase aunque tengan el mismo estado.
    @Test
    public void testDiccionarioVacioArchivo()
    {
        try
        {
            Persistencia.persistir(dic);
            Diccionario diccionario2 = Persistencia.despersitir();
            Assert.assertEquals("Los diccionarios deberían ser vacíos", this.dic, diccionario2); 
        }
        catch (FileNotFoundException e)
        {
            Assert.fail("No debería lanzar excepcion: " + e.getMessage());
        }
    }

    //verifica la lectoescritura de un diccionario vacío.
    @Test
    public void testDiccionarioConSinonimos()
    {
        try
        {
            this.llenaDiccionario(dic);
            Persistencia.persistir(dic);
            Diccionario diccionario2 = Persistencia.despersitir();
            Assert.assertEquals("Los diccionarios deberían tener cuatro sinónimos", this.dic, diccionario2);
        }
        catch (FileNotFoundException e)
        {
            Assert.fail("No debería lanzar excepción: " + e.getMessage());
        }
    }

    //verifica si se lanza una excepción al intentar leer un archivo inexistente.
    @Test
    public void testDespersitirSinArchivo()
    {
        try
        {
            dic = Persistencia.despersitir();
            Assert.fail("debería haber lanzado una excepcion de tipo FilaNotFound");
        }
        catch (FileNotFoundException e)
        {

        }
    }

    private void llenaDiccionario(Diccionario diccionario)
    {
        try
        {
            diccionario.agregar_sinonimo(new Sinonimo("casa", "hogar"));
            diccionario.agregar_sinonimo(new Sinonimo("casa", "vivienda"));
            diccionario.agregar_sinonimo(new Sinonimo("automovil", "coche"));
            diccionario.agregar_sinonimo(new Sinonimo("automovil", "carro"));

        }
        catch (Exception e)
        {
            Assert.fail("no debería lanzar excepcion");
        }
    }
}
