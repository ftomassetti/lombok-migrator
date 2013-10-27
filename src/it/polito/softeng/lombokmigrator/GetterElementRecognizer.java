package it.polito.softeng.lombokmigrator;

import japa.parser.ast.body.MethodDeclaration;

/**
 * Created with IntelliJ IDEA.
 * User: federico
 * Date: 10/17/13
 * Time: 12:11 PM                                          leme
 * To change this template use File | Settings | File Templates.
 */
public class GetterElementRecognizer extends CombinedAstElementRecognizer<MethodDeclaration>{

    public GetterElementRecognizer(){
        addElement(new NamePrefixMethodDeclarationRecognizer("get"));
    }

}
