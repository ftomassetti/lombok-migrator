package it.polito.softeng.it.polito.softeng.lombokmigrator;

import java.util.LinkedList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: federico
 * Date: 10/17/13
 * Time: 12:05 PM
 * To change this template use File | Settings | File Templates.
 */
public class CombinedAstElementRecognizer<T> implements AstElementRecognizer<T> {

    private List<AstElementRecognizer<T>> elements;

    public CombinedAstElementRecognizer(){
        elements = new LinkedList<AstElementRecognizer<T>>();
    }

    public void addElement(AstElementRecognizer<T> element){
        elements.add(element);
    }

    @Override
    public boolean recognize(T element) {
        for (AstElementRecognizer<T> el :elements){
            if (!el.recognize(element)){
                return false;
            }
        }
        return true;
    }
}
