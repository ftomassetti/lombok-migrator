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

            private boolean isAGetter(MethodDeclaration methodDeclaration){
                if (!methodDeclaration.getName().startsWith("get")){
                     return false;
                }
                return true;
            }

            private boolean isASetter(MethodDeclaration methodDeclaration){
                if (!methodDeclaration.getName().startsWith("set")){
                    return false;
                }
                return true;
            }

            @Override
            public Object visit(MethodDeclaration methodDeclaration, Object o) {
                System.out.println("Visiting "+methodDeclaration);
                System.out.println("\tname: "+methodDeclaration.getName());
                if (isAGetter(methodDeclaration)){

                } else if (isASetter(methodDeclaration)){

                }
               return null;  //To change body of implemented methods use File | Settings | File Templates.
            }

        };
        compilationUnit.accept(visitor,"");

        return "";
    }


}
