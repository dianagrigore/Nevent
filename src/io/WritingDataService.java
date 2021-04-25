package io;

import com.nevent.model.location.Location;
import com.nevent.model.location.Seating;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public class WritingDataService{
    private static WritingDataService instance = null;
    private WritingDataService() {}
    public static WritingDataService getInstance()
    {
        if(instance == null){
            instance = new WritingDataService();
        }
        return instance;
    }
    public void writeLocationCSV(List<Location> locations){
        try{
            File file = new File("./resource/output/location.csv");
            file.createNewFile();
        } catch (IOException e){
            System.out.println(e.getMessage());
        }
        try{
            FileWriter writer = new FileWriter("./resource/output/location.csv");
            writer.write("");
            for (Location location : locations){
                writer.write(location.getNameOfVenue() + ", " + location.getAddress() + ", " + location.getCity());
                Seating seating = location.getSeating();
                for (Map.Entry<String,Integer> entry : seating.getTicketsOfEachType().entrySet()){
                    writer.write(", " + entry.getValue());
                }
            }
            writer.close();
        } catch (IOException e){
            System.out.println(e.getMessage());
        }
    }


}
