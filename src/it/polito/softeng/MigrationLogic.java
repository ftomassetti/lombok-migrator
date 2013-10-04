package it.polito.softeng;

import japa.parser.ast.*;
import japa.parser.ast.body.*;
import japa.parser.ast.expr.*;
import japa.parser.ast.stmt.*;
import japa.parser.ast.type.*;
import japa.parser.ast.visitor.GenericVisitor;
import japa.parser.ast.visitor.GenericVisitorAdapter;

/**
 * Created with IntelliJ IDEA.
 * User: federico
 * Date: 10/4/13
 * Time: 11:27 AM
 * To change this template use File | Settings | File Templates.
 */
public class MigrationLogic {


    public String migrateCompilationUnitToLombok(CompilationUnit compilationUnit, String code){
        GenericVisitor<Object,Object> visitor = new GenericVisitorAdapter<Object, Object>() {

            @Override
            public Object visit(MethodDeclaration methodDeclaration, Object o) {
                System.out.println("Visiting "+methodDeclaration);
                return null;  //To change body of implemented methods use File | Settings | File Templates.
            }

        };
        compilationUnit.accept(visitor,"");

        return "";
    }


}
