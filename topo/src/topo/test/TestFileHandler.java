package topo.test;

import java.io.File;
import java.io.IOException;

import org.apache.log4j.Logger;

import topo.utils.FileHandler;

public class TestFileHandler{
	final static Logger logger = Logger.getLogger(TestFileHandler.class);
	
    public static void main(String[] args){
        String csvPath = "C:\\Users\\lukemao\\workspace\\topo\\resources\\";
        String relativePath = "resources";
        String csvFilename = "line.csv";
        
        FileHandler handler = FileHandler.FileHandlerFactory();
        File f = FileHandler.getFile(relativePath, csvFilename);
        handler.initialiseLineIterator(f);
        logger.info("Created csv file: "+f.exists());
        logger.info("handler has next line: "+handler.hasNextLine());
        while(handler.hasNextLine()){
            logger.info(handler.readNextLine());
        }
        
        File newFile = FileHandler.createFile(csvPath, "testfile.csv");
        try {
			logger.info("newly Created csv file: "+newFile.createNewFile());
			logger.info("newly Created csv file exist?: "+newFile.exists());
			FileHandler.deleteFile(newFile);
			logger.info("deleted csv file exist?: "+newFile.exists());
			
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
		}
        
    }
}