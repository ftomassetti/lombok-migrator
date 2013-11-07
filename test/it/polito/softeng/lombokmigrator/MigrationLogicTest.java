package it.polito.softeng.lombokmigrator;

import it.polito.softeng.LombokMigrator;
import it.polito.softeng.MigrationLogic;
import japa.parser.ParseException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * User: federico
 * Date: 10/4/13
 * Time: 11:51 AM
 * To change this template use File | Settings | File Templates.
 */
public class MigrationLogicTest {
    @Before
    public void setUp() throws Exception {

    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void testGetterBooleanRemoval() throws IOException, ParseException {
        String code = "class A { private boolean a;\nboolean isA(){return this.a;}\n}";
        LombokMigrator lm = new LombokMigrator();
        assertEquals("class A {\n\n    @Getter()\n    private boolean a;\n}\n",lm.migrateCodeToString(code));
    }

    @Test
    public void testGetterRemoval() throws IOException, ParseException {
        String code = "class A { private String a;\nString getA(){return this.a;}\n}";
        LombokMigrator lm = new LombokMigrator();
        assertEquals("class A {\n\n    @Getter()\n    private String a;\n}\n",lm.migrateCodeToString(code));
    }

    @Test
    public void testSetterRemoval() throws IOException, ParseException {
        String code = "class A { private String a;\nvoid setA(String a){this.a=a;}\n}";
        LombokMigrator lm = new LombokMigrator();
        assertEquals("class A {\n\n    @Setter()\n    private String a;\n}\n",lm.migrateCodeToString(code));
    }
}