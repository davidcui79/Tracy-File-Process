package logic;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.Vector;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class FileBroker {
	File refFile = null;
	File srcFile = null;
	File tgtFile = null;
	Vector <String[]>refDesc = null;

	public FileBroker(File ref, File src, File tgt) {
		refFile = ref;
		srcFile = src;
		tgtFile = tgt;
		refDesc = new Vector<String[]>();
	}

	public boolean open(File file) {
		return false;
	}
	
	public void readRefFile(){
		//reset refDesc
		refDesc = new Vector<String[]>();
		
		try {
			BufferedReader br = null;
			FileReader fr;
			fr = new FileReader(refFile);
			br = new BufferedReader(fr);
			String line = null;
			
			//read ref files
			while((line=br.readLine()) != null){
				//process line
				if(line.length() > 1){
				    String[] fields = line.split(RefToken.FIELD_SEP);
				    if(fields.length != RefToken.NUM_OF_FIELDS){
				    	System.out.println("Error(num of fields violation) in reference file at line: " + line);
				    	continue;
				    }
				    refDesc.add(fields);
				}
			}
			
			/*
			 * debug code to print content of reference file to console
			 */
			
			System.out.println("Reference file stored:\n");
			for(int i = 0; i < refDesc.size(); i++){
				String[] fields = refDesc.elementAt(i);
				//product field
				String[] atts = fields[RefToken.PRODUCT_FIELD_NUM].split(RefToken.ATTR_SEP);
				String startPoint = atts[RefToken.START_ATTR_NUM];
				String value = atts[RefToken.VALUE_ATTR_NUM];
				System.out.print("If at " + startPoint + " value is " + value);
				//rstate field
				atts = fields[RefToken.RSTATE_FIELD_NUM].split(RefToken.ATTR_SEP);
				startPoint = atts[RefToken.START_ATTR_NUM];
				value = atts[RefToken.VALUE_ATTR_NUM];
				System.out.print(" and at " + startPoint + " value is " + value);
				//new rstate field
				atts = fields[RefToken.NEW_RSTATE_FIELD_NUM].split(RefToken.ATTR_SEP);
				startPoint = atts[RefToken.START_ATTR_NUM];
				value = atts[RefToken.VALUE_ATTR_NUM];
				System.out.println(", update at " + startPoint + " to value " + value);
			}
			
			
			br.close();
			fr.close();
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		

	}
	
	public void convert(){
		try {
			readRefFile();
			
			BufferedReader br = null;
			FileReader fr = new FileReader(srcFile);
			br = new BufferedReader(fr);
			File tempFile = new File(tgtFile.getParent(), tgtFile.getName()+".tmp");
			
			//create temp file
			FileOutputStream tfos = new FileOutputStream(tempFile);
			char _unixCR = 10;
			char[] unixCR = new char[1];
			unixCR[0] = _unixCR;
			String cr = new String(unixCR);
			String line = null;

			//write to the temp file
			while((line =  br.readLine()) != null){
                //we have left out the end of line char from calling readLine(), need to add it back 
				line += cr;
				boolean updatePerformed = false;
				for(int i = 0; i < refDesc.size(); i++){
					/*
					 * PRODUCT
					 */
					String[] fields = refDesc.elementAt(i);
					String[] atts = fields[RefToken.PRODUCT_FIELD_NUM].split(RefToken.ATTR_SEP);
					String startPointStr = atts[RefToken.START_ATTR_NUM];
					int startPoint = -1;
					try{
					    startPoint = Integer.parseInt(startPointStr);
					}catch (NumberFormatException e){
						e.printStackTrace();
						continue;
					}
					String value = atts[RefToken.VALUE_ATTR_NUM];
					
					if(line.length() < startPoint + value.length()){
						continue;
					}
					
					String product = line.substring(startPoint, startPoint + value.length());
					if(!product.equals(value)){
						continue;
					}
					
					/*
					 * RSTATE
					 */
					atts = fields[RefToken.RSTATE_FIELD_NUM].split(RefToken.ATTR_SEP);
					startPointStr = atts[RefToken.START_ATTR_NUM];
					startPoint = -1;
					try{
					    startPoint = Integer.parseInt(startPointStr);
					}catch (NumberFormatException e){
						e.printStackTrace();
						continue;
					}
					value = atts[RefToken.VALUE_ATTR_NUM];
					String rstate = line.substring(startPoint, startPoint + value.length());
					
					if(line.length() < startPoint + value.length()){
						continue;
					}
					
					if(!rstate.equals(value)){
						continue;
					}
					
					/*
					 * update RSTATE
					 */
					atts = fields[RefToken.NEW_RSTATE_FIELD_NUM].split(RefToken.ATTR_SEP);
					startPointStr = atts[RefToken.START_ATTR_NUM];
					startPoint = -1;
					try{
					    startPoint = Integer.parseInt(startPointStr);
					}catch (NumberFormatException e){
						e.printStackTrace();
						tfos.write(line.getBytes());
						continue;
					}
					value = atts[RefToken.VALUE_ATTR_NUM];
					
					String newLine = line.substring(0, startPoint);
					newLine += value;
					if(line.length() > startPoint + value.length()){
					    newLine += line.substring(startPoint + value.length(), line.length());
					} 
					 
					newLine += cr;
					tfos.write(newLine.getBytes());
					updatePerformed = true;
					break;
				}
				if(!updatePerformed){
					tfos.write(line.getBytes());
				}
			}
			/*
			while((line =  br.readLine()) != null){
				String lineNoCR = line.substring(0, line.length() - 1);
				char _unixCR = 10;
				char[] unixCR = new char[1];
				unixCR[0] = _unixCR;
				lineNoCR += new String(unixCR);
				tfos.write(lineNoCR.getBytes());
			}
			*/

			br.close();
			tfos.close();
			
			//create zip file
			FileOutputStream fos = new FileOutputStream(tgtFile);
			ZipOutputStream zout = new ZipOutputStream(new 
			           BufferedOutputStream(fos));
			ZipEntry entry = new ZipEntry(srcFile.getName());
			zout.putNextEntry(entry);
            FileInputStream fi = new FileInputStream(tempFile);
            final int BUFFER = 2048;
            byte data[] = new byte[BUFFER];
            BufferedInputStream origin = new BufferedInputStream(fi, BUFFER);
            int count = -1;
            while((count = origin.read(data, 0, BUFFER)) != -1) {
               zout.write(data, 0, count);
            }
            origin.close();
            zout.close();

            //delete temp file
            tempFile.delete();
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


}
