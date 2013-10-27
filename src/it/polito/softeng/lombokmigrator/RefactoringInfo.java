package it.polito.softeng.lombokmigrator;

import java.util.LinkedList;
import java.util.List;
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
 * Date: 10/17/13
 * Time: 2:05 PM
 * To change this template use File | Settings | File Templates.
 */
public class RefactoringInfo {

    private List<Node> toRemove;

    public RefactoringInfo(){
        toRemove = new LinkedList<Node>();
    }

    public void addToRemove(Node node) {
        toRemove.add(node);
    }



}
