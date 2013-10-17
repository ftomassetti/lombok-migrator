package it.polito.softeng.lombokmigrator;

import it.polito.softeng.LombokMigrator;
import japa.parser.ParseException;
import lombok.Getter;
import lombok.Setter;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertEquals;

/**
 * Created with IntelliJ IDEA.
 * User: federico
 * Date: 10/4/13
 * Time: 11:51 AM
 * To change this template use File | Settings | File Templates.
 */
public class LombokTest {

    class MyClass {
        @Getter @Setter private String myField;
    }


    @Before
    public void setUp() throws Exception {

    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void testGetterAndSetter() throws IOException, ParseException {
        MyClass instance = new MyClass();
        instance.setMyField("foo");
        assertEquals("foo",instance.getMyField());
    }
}