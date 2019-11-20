/**
 * It's the holiday season and you have this Christmas list from whomever.
 * You have x amount of dollars you would like to spend but don't know what to get.
 * This program will do that for you.  What this program does is parses the Christmas list
 * that is entered by the user.  Once it is parsed, it will add the item and price into a hash map.
 * From there the program will be able to print out all different combinations of items you can
 * buy based on the money amount that you enter.  This program can also be used for non Christmas events.
 *
 * @author (Kobie Arndt)
 */

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import org.apache.pdfbox.io.IOUtils;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.poi.*;
import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.extractor.WordExtractor;
import org.apache.poi.xwpf.extractor.XWPFWordExtractor;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;

import java.io.*;


public class ChristmasHelper {

    /**
     * This function will dynamically populate a hash map by parsing
     * a txt file and adding the key pair values respectively.
     * @param filename The txt file that will be parsed
     * @return  a hash map of key pair values
     * @throws IOException
     */
    public static HashMap createHashMap(String filename) throws IOException {
        String line;
        HashMap<String, Double> list = new HashMap<String, Double>();
        BufferedReader reader = new BufferedReader(new FileReader(filename));
        while ((line = reader.readLine()) != null) {
            String[] parts = line.split(",", 2);
            if (parts.length >= 2) {
                String key = parts[0];
                String value = parts[1];
                double price = Double.parseDouble(value);
                list.put(key, price);
            } else {
                System.out.println("ignoring line: " + line);
            }
        }

        for (String key : list.keySet()) {
            System.out.println("Item - " + key + " price- " + list.get(key));
        }
        reader.close();
        return list;
    }


    /**
     * This function will look at the end of the string to see what kind of file extension it is.
     * @param filename The name of the file
     * @return the file extension
     */
    public static String getFileExtension(String filename){
        if(filename.lastIndexOf(".") != -1 && filename.lastIndexOf(".") != 0){
            return filename.substring(filename.lastIndexOf(".") + 1);
        }else{
            return " ";
        }
    }


    /**
     * This is a helper function for convertPdfToTxt.  What this does is take in the input stream
     * and return it to an array of bytes.
     * @param filePath Tthe file path of the pdf file being read
     * @return the array of bytes
     * @throws IOException
     */
    private static byte[] readFileAsBytes(String filePath) throws IOException{
        FileInputStream inputStream = new FileInputStream(filePath);
        return IOUtils.toByteArray(inputStream);
    }


    /**
     * This function will convert a pdf to a txt file by parsing it using Apache's pdfbox open
     * source library.  If there are contents in the file that was already written, this
     * function will rewrite the txt file so the lists don't duplicate.
     *
     * @param filename The filename of the pdf file tot parse
     * @return The string of the txt file to be passed into createHashMap
     * @throws IOException
     */
    public static String convertPdfToTxt(String filename)throws IOException{
        byte[] thePDFFileBytes = readFileAsBytes(filename);
        PDDocument pdfDoc = PDDocument.load(thePDFFileBytes);
        PDFTextStripper reader = new PDFTextStripper();
        String txt = reader.getText(pdfDoc);
        String file = "src/pdflist.txt";
        try(FileOutputStream fos = new FileOutputStream(file, true)){
            byte[] myBytes = txt.getBytes();
            fos.write(myBytes);
        }
        BufferedReader fileReader = new BufferedReader(new FileReader(filename));
        if(fileReader.readLine() != null){
            try(FileOutputStream fnew = new FileOutputStream(file, false)){
                byte[] newBytes = txt.getBytes();
                fnew.write(newBytes);
            }
        }
        pdfDoc.close();
        return file;
    }


    /**
     * This function will convert a docx file to a txt file by parsing it using Apache's
     * POIDocument open source library.  If there are contents in the file that was
     * already written, this function will rewrite the txt file so the lists
     * don't duplicate.
     *
     * @param filename the file name of the .docx file to parse
     * @return the .txt file name to be passed into createHashMap
     * @throws IOException
     */
    public static String convertDocToTxt(String filename) throws IOException{
        File docFile = new File(filename);
        FileInputStream fis = new FileInputStream(docFile.getAbsolutePath());
        XWPFDocument document = new XWPFDocument(fis);
        XWPFWordExtractor extractor = new XWPFWordExtractor(document);
        String txt = extractor.getText();
        String file = "src/doclist.txt";
        try(FileOutputStream fos = new FileOutputStream(file, true)){
            byte[] myBytes = txt.getBytes();
            fos.write(myBytes);
        }
        BufferedReader fileReader = new BufferedReader(new FileReader(filename));
        if(fileReader.readLine() != null){
            try(FileOutputStream fnew = new FileOutputStream(file, false)){
                byte[] myBytes = txt.getBytes();
                fnew.write(myBytes);
            }
        }
        document.close();
        return file;
    }




    public static void calculateHashMap(double value,  HashMap<String, Double> list)throws  IOException{
        double price;
        String name;
        for(Map.Entry mapElement : list.entrySet()){
            name = (String)mapElement.getKey();
            price = (double)mapElement.getValue();
            if(value == 0.0){
                System.out.println("The value entered was 0 ");
                break;
            }else if(value < 0){
                System.out.println("A value was not entered");
                break;
            }else{

                }

            }
        }



    /**
     * This is the main function where it will hold the calls to the necessary functions
     * @param args the command line arguments which are the name of the file.
     * @throws IOException
     */
    public static void main(String[] args)throws IOException{
        System.out.println("Please enter your christmas list");
        Scanner in = new Scanner(System.in);
        String filename = in.nextLine();
        String result = getFileExtension(filename);
        if(result.equals("pdf")){
            String file = convertPdfToTxt(filename);
            //System.out.println(file);
            System.out.println("Christmas List items");
            HashMap<String, Double> list = createHashMap(file);
            System.out.println("Please enter the amount you would like to spend this Christmas");
            double value = in.nextDouble();
            calculateHashMap(value, list);
        }else if(result.equals("docx")){
            String file = convertDocToTxt(filename);
            System.out.println("Christmas List items");
            HashMap<String, Double> list = createHashMap(file);
            System.out.println("Please enter the amount you would like to spend this Christmas");
            double value = in.nextDouble();
            calculateHashMap(value, list);
        }else{
            System.out.println("Christmas List items");
            HashMap<String, Double> list = createHashMap(filename);
            System.out.println("Please enter the amount you would like to spend this Christmas");
            double value = in.nextDouble();
            calculateHashMap(value, list);
        }

    }
}
