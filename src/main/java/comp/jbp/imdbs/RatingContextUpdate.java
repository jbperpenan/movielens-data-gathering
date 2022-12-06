package comp.jbp.imdbs;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;

import java.io.FileWriter;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class RatingContextUpdate {

    public static void main(String[] args) throws Exception {

        List<String[]> lines = new ArrayList<>();
        lines.add(new String[] { "userId", "movieId", "score","timestamp","title","genres","rating","awards" });

        for (String[] awards: readAllLines("awards.csv")) {
            if (awards[0].equals("movieId")) {
                continue;
            }

            //System.out.println("Awards MovieID: "+ awards[0]);
            //System.out.println("Awards Awards: "+ awards[1]);

            for (String[] rate: readAllLines("rating_context.csv")) {
                if (rate[0].equals("userId")) {
                    continue;
                }

                //System.out.println("Ratings MovieID: "+ rate[1]);
                //System.out.println("Ratings Awards: "+ rate[7]);

                if(awards[0].equals(rate[1])){
                  /*System.out.println("Ratings MovieID: "+ rate[1]);
                    System.out.println("Awards MovieID: "+ awards[0]);
                    System.out.println("Ratings Awards: "+ rate[7]);
                    System.out.println("Awards Awards: "+ awards[1]);*/

                    rate[7] = awards[1];
                    //System.out.println("Title : "+ rate[4]);
                    if(rate[4].contains(",")) {
                        //System.out.println("Title with comma: "+ rate[4]);
                        //rate[4] = rate[4].replace(',','%');
                        rate[4] = "\""+rate[4]+"\"";
                        //System.out.println("Title with comma: "+ rate[4]);
                    }
                    //System.out.println("UPDATED Awards: "+ rate[7]);

                    //return;
                    lines.add(rate);
                }
            }
        }

/*        for(String[] str: lines) {
            System.out.println("LIST UPDATED Awards: "+ str[7]);
        }*/

        try{
            writeLineByLine(lines);
        }catch (Exception e) {
            e.printStackTrace();
        }
/*
        for (String[] newCsv: readAllLines("rating_awards_context.csv")) {
            System.out.println("NEW CSV UPDATED: "+ newCsv[7]);
        }*/
    }

    public static void writeLineByLine(List<String[]> lines) throws Exception {
        Path path = Paths.get
                (ClassLoader.getSystemResource("rating_awards_context.csv").toURI());
        CSVWriter writer = new CSVWriter(new FileWriter(path.toString()),
                CSVWriter.DEFAULT_SEPARATOR , CSVWriter.NO_QUOTE_CHARACTER, CSVWriter.NO_ESCAPE_CHARACTER, CSVWriter.DEFAULT_LINE_END);
        try {
            writer.writeAll(lines);
            /*for (String[] line : lines) {
                writer.writeNext(line);
            }*/
        }catch (Exception e) {
            e.printStackTrace();
        }finally {
            writer.close();
        }
    }

    public static List<String[]> readAllLines(String fileName) throws Exception {
        Path path = Paths.get
                (ClassLoader.getSystemResource(fileName).toURI());
        try (Reader reader = Files.newBufferedReader(path)) {
            try (CSVReader csvReader = new CSVReader(reader)) {
                return csvReader.readAll();
            }
        }
    }
}
