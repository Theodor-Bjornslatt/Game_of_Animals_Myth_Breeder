package com.company;

import java.io.*;

public class Serializer {
    static public boolean serialize(String filePath, Object data){
        try{
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
