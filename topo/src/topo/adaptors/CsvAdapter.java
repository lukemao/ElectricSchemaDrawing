package topo.adaptors;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

import topo.utils.FileHandler;

public class CsvAdapter{
	
	private File file;
	private FileHandler handler;
	private String currentLine;
	private ArrayList<String> fields; 
	private Iterator<String> fieldIterator;
	private int lineIndex;
	private int fieldIndex;
	
	public CsvAdapter(){
		this(null, null);
	}
	
	public CsvAdapter(String pathname, String filename){
		this.file = FileHandler.createFile(pathname, filename);
		this.handler = FileHandler.FileHandlerFactory(file);
		this.lineIndex = 0;
	}
	
	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
	}

	public boolean hasNext(){
		return handler==null?false:handler.hasNextLine();
	}
	
	public String readNextLine(){
		if(!hasNext()||handler==null) return null;
		this.currentLine = handler.readNextLine();
		if(currentLine != null){
			this.fields = new ArrayList<String>(
					Arrays.asList(currentLine.split(",")));
			fieldIterator = fields.iterator();
			this.fieldIndex = 0;
			lineIndex++;
		}
		return currentLine;
	}
	
	public boolean hasNextField(){
		return fields==null?false:fieldIterator.hasNext();
	}
	
	public String readNextField(){
		this.fieldIndex++;
		return fields==null?null:fieldIterator.next();
	}
	
	public int fieldSize(){
		return fields == null?0:fields.size();
	}
	
	public int getLineIndex(){
		return this.lineIndex;
	}
	public int getFieldIndex(){
		return this.fieldIndex;
	}
	
	public void reset(){
		this.lineIndex = 0; 
		this.currentLine= null;
		this.handler = FileHandler.FileHandlerFactory(this.file);
		this.fieldIterator = null;
	}
	
}