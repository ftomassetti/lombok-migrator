package it.polito.softeng.lombokmigrator;

/**
 * Created with IntelliJ IDEA.
 * User: federico
 * Date: 10/17/13
 * Time: 12:02 PM
 * To change this template use File | Settings | File Templates.
 */
public interface AstElementRecognizer<T> {

    boolean recognize(T element);

}
