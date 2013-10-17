package it.polito.softeng.it.polito.softeng.lombokmigrator;

import japa.parser.ast.*;
import japa.parser.ast.body.*;
import japa.parser.ast.expr.*;
import japa.parser.ast.stmt.*;
import japa.parser.ast.type.*;

/**
 * Created with IntelliJ IDEA.
 * User: federico
 * Date: 10/17/13
 * Time: 12:03 PM
 * To change this template use File | Settings | File Templates.
 */
public class NamePrefixMethodDeclarationRecognizer implements AstElementRecognizer<MethodDeclaration> {

    private String prefix;

    public NamePrefixMethodDeclarationRecognizer(String prefix){
        this.prefix = prefix;
    }

    @Override
    public boolean recognize(MethodDeclaration element) {
        return element.getName().startsWith(prefix) && element.getName().length()>prefix.length();
    }
}
