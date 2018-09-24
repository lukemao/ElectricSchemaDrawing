package topo.test;

import java.io.File;

import org.apache.log4j.Logger;

import topo.adaptors.CsvAdapter;
import topo.utils.FileHandler;

public class TestAdapter{
	final static Logger logger = Logger.getLogger(TestAdapter.class);
	
    public static void main(String[] args){
        String csvPath = "C:\\Users\\lukemao\\workspace\\topo\\resources";
        String relativePath = "resources";
        String csvFilename = "node.csv";
        
        CsvAdapter ca = new CsvAdapter(csvPath, csvFilename);
        FileHandler handler = FileHandler.FileHandlerFactory();
        File f = FileHandler.getFile(relativePath, csvFilename);
        handler.initialiseLineIterator(f);
        logger.info("Created csv file: "+f.exists());
        logger.info("csv has next line: "+ca.hasNext());
        
        //String s = ca.readNextLine();
        while(ca.hasNext()){
            ca.readNextLine();
            logger.info("line<"+ca.getLineIndex()+"> ");
            while(ca.hasNextField()){
                logger.info("\t |-field<"+ca.getFieldIndex()+"> "+ca.readNextField());
            }
            logger.info("--------------------");
        }
    }
}