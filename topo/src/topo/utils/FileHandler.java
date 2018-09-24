package topo.utils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.LineIterator;
import org.apache.commons.io.filefilter.SuffixFileFilter;

public class FileHandler{
    
    private LineIterator iter;
    
    private FileHandler(){}

    private FileHandler(File f){
        initialiseLineIterator(f);
    }
    
    public static FileHandler FileHandlerFactory(){
        return new FileHandler();
    }
    
    public static FileHandler FileHandlerFactory(File f){
        return new FileHandler(f);
    }
    
    public static File getFile(String path, String filename){
        return FileUtils.getFile(path +"\\"+ filename);
    }
    
    public static File createFile(String filename){
        File f = new File(filename);
        try {
            f.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return f;
    }
    
    public static File createFile(String path, String filename){
        File f = new File(path, filename);
        try {
            f.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return f;
    }
    
    public static String getAbsolutePath(File f){
        return FileUtils.getTempDirectory().getAbsolutePath();
    }
    
    public void initialiseLineIterator(File f){
        try{
            this.iter = FileUtils.lineIterator(f);
        }catch(IOException io){
            io.printStackTrace();
        }
    }
    
    public LineIterator getLineIterator(){
        return this.iter;
    }
    
    public boolean hasNextLine(){
        return iter.hasNext();
    }
    
    public String readNextLine(){
        return iter.next();
    }
    
    public void writeLines(File f, Collection<String> lines){
        try {
            FileUtils.writeLines(f, lines);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public static void deleteFile(File f){
        FileUtils.deleteQuietly(f);
    }
}