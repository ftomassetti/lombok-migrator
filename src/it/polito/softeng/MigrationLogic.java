package it.polito.softeng;

import it.polito.softeng.it.polito.softeng.lombokmigrator.RefactoringInfo;
import japa.parser.ast.*;
import japa.parser.ast.body.*;
import japa.parser.ast.expr.*;
import japa.parser.ast.stmt.*;
import japa.parser.ast.type.*;
import japa.parser.ast.visitor.GenericVisitor;
import japa.parser.ast.visitor.GenericVisitorAdapter;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: federico
 * Date: 10/4/13
 * Time: 11:27 AM
 * To change this template use File | Settings | File Templates.
 */
public class MigrationLogic {

    private class ClassInfo {

        private class FieldInfo {
            public String name;
            public FieldDeclaration fieldDeclaration;
            private VariableDeclarator variableDeclarator;
        }

        private Map<String,FieldInfo> declaredFields = new HashMap<String,FieldInfo>();
        private ClassOrInterfaceDeclaration decl;
        private List<BodyDeclaration> membersToDelete = new LinkedList<BodyDeclaration>();

        public ClassInfo(ClassOrInterfaceDeclaration decl){
            this.decl = decl;
            for (BodyDeclaration bd : decl.getMembers()){
                if (bd instanceof FieldDeclaration){
                    FieldDeclaration fd = (FieldDeclaration)bd;
                    for (VariableDeclarator vd : fd.getVariables()){
                        FieldInfo fieldInfo = new FieldInfo();
                        fieldInfo.name = vd.getId().getName();
                        fieldInfo.fieldDeclaration = fd;
                        fieldInfo.variableDeclarator = vd;
                        declaredFields.put(fieldInfo.name,fieldInfo);
                    }

                }
            }
        }

        public boolean hasField(String name){
            return declaredFields.containsKey(name);
        }

        public boolean hasField(String name, Type type){
            if (!hasField(name)) return false;
            FieldInfo fi = declaredFields.get(name);
            return fi.fieldDeclaration.getType().equals(type);
        }

        public void addGetterAnnotation(String name){
            FieldInfo fi = declaredFields.get(name);
            NormalAnnotationExpr getterAnnotation = new NormalAnnotationExpr();
            NameExpr getterName = new NameExpr();
            getterName.setName("Getter");
            getterAnnotation.setName(getterName);
            if (fi.fieldDeclaration.getAnnotations()==null){
                fi.fieldDeclaration.setAnnotations(new LinkedList<AnnotationExpr>());
            }
            fi.fieldDeclaration.getAnnotations().add(getterAnnotation);
        }

        public void recordDeleteMember(BodyDeclaration bodyDeclaration){
            this.membersToDelete.add(bodyDeclaration);
        }

        public void performRecordedModifications(){
            for (BodyDeclaration bd : membersToDelete){
                deleteMember(bd);
            }
            membersToDelete.clear();
        }

        public void deleteMember(BodyDeclaration bodyDeclaration){
            decl.getMembers().remove(bodyDeclaration);
        }
    }

    private class MyVisitor extends GenericVisitorAdapter<Object, Object> {
        private ClassInfo currentClass;

        private boolean isAGetter(MethodDeclaration methodDeclaration){
            if (methodDeclaration.getParameters()!=null && methodDeclaration.getParameters().size()>0){
                return false;
            }
            if (methodDeclaration.getThrows()!=null && methodDeclaration.getThrows().size()!=0){
                return false;
            }
            String expectedFieldName = fieldNameFromGetter(methodDeclaration);
            if (expectedFieldName==null){
                return false;
            }
            if (!currentClass.hasField(expectedFieldName)){
                return false;
            }
            if (!currentClass.hasField(expectedFieldName,methodDeclaration.getType())){
                return false;
            }
            if (methodDeclaration.getBody().getStmts().size()!=1){
                return false;
            }
            if (!(methodDeclaration.getBody().getStmts().get(0) instanceof  ReturnStmt)){
                return false;
            }
            ReturnStmt returnStmt = (ReturnStmt)methodDeclaration.getBody().getStmts().get(0);
            if (returnStmt.getExpr() instanceof  NameExpr) {
                NameExpr nameExpr = (NameExpr)returnStmt.getExpr();
                if (!nameExpr.getName().equals(expectedFieldName)){
                    return false;
                }
            } else if (returnStmt.getExpr() instanceof  FieldAccessExpr){
                FieldAccessExpr fieldAccessExpr = (FieldAccessExpr)returnStmt.getExpr();
                if (!(fieldAccessExpr.getScope() instanceof  ThisExpr)){
                    return false;
                }
                if (!fieldAccessExpr.getField().equals(expectedFieldName)){
                    return false;
                }
            } else {
                return false;
            }
            return true;
        }

        public String fieldNameFromGetter(MethodDeclaration methodDeclaration){
            if (methodDeclaration.getName().startsWith("get")){
                return methodDeclaration.getName().substring(3,4).toLowerCase()+methodDeclaration.getName().substring(4);
            } else {
                return null;
            }
        }

        private boolean isASetter(MethodDeclaration methodDeclaration){
            if (!methodDeclaration.getName().startsWith("set")){
                return false;
            }
            return true;
        }

        @Override
        public Object visit(ClassOrInterfaceDeclaration n, Object arg) {
            currentClass = new ClassInfo(n);
            return super.visit(n, arg);
        }

        @Override
        public Object visit(MethodDeclaration methodDeclaration, Object o) {
            System.out.println("Visiting "+methodDeclaration);
            System.out.println("\tname: "+methodDeclaration.getName());
            if (isAGetter(methodDeclaration)){
                currentClass.recordDeleteMember(methodDeclaration);
                currentClass.addGetterAnnotation(fieldNameFromGetter(methodDeclaration));
            } else if (isASetter(methodDeclaration)){

            }
            return null;  //To change body of implemented methods use File | Settings | File Templates.
        }

        public ClassInfo getCurrentClass(){
            return currentClass;
        }
    }

    public String migrateCompilationUnitToLombok(CompilationUnit compilationUnit, String code){
        final RefactoringInfo refactoringInfo = new RefactoringInfo();
        MyVisitor visitor = new MyVisitor();
        compilationUnit.accept(visitor,"");
        visitor.getCurrentClass().performRecordedModifications();

        return compilationUnit.toString();
    }


}
