package com.company.MainGame;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Serializer {
    static public boolean serialize(String fileDirectory, String filePath, Object data){
        filePath = fileDirectory + "/" + filePath;
            try{
                Files.createDirectories(Paths.get(fileDirectory));
                FileOutputStream fileOut = new FileOutputStream(filePath);
                ObjectOutputStream out = new ObjectOutputStream(fileOut);
                out.writeObject(data);
                out.close();
                fileOut.close();
                System.out.println("Your game has been saved!");
                return true;
            }
            catch(Exception error){
                System.out.println("Your game could not be saved :(");
                return false;
            }
    }

    static public Object deserialize(String filePath){
        try{
            FileInputStream file = new FileInputStream(filePath);
            ObjectInputStream in = new ObjectInputStream(file);
            Object data = in.readObject();
            in.close();
            file.close();
            System.out.println("Finished loading game!");
            return data;
        }
        catch(Exception error){
            System.out.println("The chosen file is not compatible and couldn't be loaded. :'(");
            return false;
        }
    }


}
