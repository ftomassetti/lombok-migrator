package it.polito.softeng;

import japa.parser.JavaParser;
import japa.parser.ParseException;
import japa.parser.ast.CompilationUnit;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

public class LombokMigrator {

    private String encoding = "UTF-8";

    private void error(String msg){
        System.err.println("Error: "+msg)     ;
    }

    public String migrateCodeToString(String code) throws  ParseException, IOException {
        InputStream stream = new ByteArrayInputStream(code.getBytes(encoding));
        CompilationUnit compilationUnit = JavaParser.parse(stream, encoding);
        code = new MigrationLogic().migrateCompilationUnitToLombok(compilationUnit, code);
        return code;
    }

    public String migrateFileToString(File originalFile) throws  ParseException, IOException {
        String code = FileUtils.readFile(originalFile);
        return migrateCodeToString(code);
    }

    public void migrateFile(File originalFile, File destinationFile){
        try {
            String code = migrateFileToString(originalFile);
            FileUtils.writeFile(destinationFile,code);
        } catch (ParseException ex){
            error("parse exception processing "+originalFile+", "+ex.getMessage());
        } catch (IOException ex){
            error("IO exception processing "+originalFile+", "+ex.getMessage());
        }
    }

    public void migrateDir(File dir,File destDir){
        for (File f : dir.listFiles()){
            File destFile = new File(destDir.getPath()+"/"+f.getName());
            migrateFileOrDir(f,destFile);
        }
    }

    public void migrateFileOrDir(File f,File dest){
        if (f.isFile()){
            migrateFile(f,dest);
        } else  if (f.isDirectory()){
            migrateDir(f,dest);
        } else {
            throw new RuntimeException("Unexpected: "+f);
        }
    }

	public static void main(String[] args){

	}
	
}