/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package elgamal;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author zulvafachrina
 */
public class IOFile {
    private String filename;
    private String data;
    
    public IOFile(String filename) {
        this.filename = filename;
    } 
    
    public String readFile(){
        Path path = Paths.get(filename);
        String result = new String();
        try {
            byte[] bytes = Files.readAllBytes(path);
            result = new String(bytes);
            this.data = result;
        } catch (IOException ex) {
            Logger.getLogger(IOFile.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }
    
    public List<String> getDataSegment() {
        readFile();
        List<String> result = new ArrayList<String>();
        for (int i=0; i<data.length(); i+=3) {
            if (data.length() < i+3) {
                result.add(data.substring(i));
            } else {
                result.add(data.substring(i, i+3));
            }
        }
        return result;
    }
    
    public void writeFile(String output) throws IOException{
        byte data[] = output.getBytes();
        File someFile = new File(filename);
        try {
            FileOutputStream fos = new FileOutputStream(someFile);
            fos.write(data);
            fos.flush();
            fos.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(IOFile.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public String getData() {
        return data;
    }
}
