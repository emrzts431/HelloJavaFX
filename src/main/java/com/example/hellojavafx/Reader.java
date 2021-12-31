package com.example.hellojavafx;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class Reader {
    public static void main(String[] args) throws IOException {
        //File file = new File("C:\\Users\\steph\\Desktop\\test.pdf");
        //PDDocument document = PDDocument.load(file);
        //PDFTextStripper pdfTextStripper = new PDFTextStripper();
        //String text = pdfTextStripper.getText(document);
        //System.out.println(text);
        //System.out.println("Wanted Informations are : " + getInfo(text));
        //document.close();
        long startTime = System.currentTimeMillis();


        try {
            String temp = "";
            File f1 = new File(Controller.path2);
             System.out.println(Controller.path2);
            FileInputStream file1 = new FileInputStream(f1);

            XSSFWorkbook workbook = new XSSFWorkbook(file1);
            XSSFSheet sheet = workbook.getSheetAt(0);
            Iterator<Row> rowIterator = sheet.rowIterator();

            while (rowIterator.hasNext()){
                Row row = rowIterator.next();
                Iterator<Cell> cellIterator = row.cellIterator();
                while (cellIterator.hasNext()){

                    Cell cell = cellIterator.next();
                    temp += cell.toString() + "/";
                    //System.out.println(cell.toString());
                }
            }
            /*System.out.println(temp);
            System.out.println(Arrays.toString(setStringArray(temp)));*/
            String[] organisedInfosExcel = setStringArray(temp);
            System.out.println(Arrays.toString(organisedInfosExcel));
            file1.close();
            File pdfFolder = new File(Controller.path1);
            File[] listOfFiles = pdfFolder.listFiles();

            String st= "";
            for (File file : listOfFiles) {

                if (file.isFile()) {
                    PDDocument document = PDDocument.load(file);
                    PDFTextStripper pdfTextStripper = new PDFTextStripper();
                    String text = pdfTextStripper.getText(document);
                    //System.out.println(text);
                    String infos = getInfo(text);
                    String[] organisedInfos = organisePdfInfo(infos);
                    //System.out.println(Arrays.toString(organisedInfos));
                    st = Arrays.toString(getInfosFromName(organisedInfosExcel,organisedInfos[1]));
                    st = st.replace("[" , "");
                    st = st.replace(".0","");
                    st = st.replace(",","");
                    st = st.replace(" ","_");
                    st = st.replace("]","");

                    File newName = new File(Controller.path1+"\\"+st+".pdf");

                    System.out.println(st);
                    //System.out.println(st);
                    //System.out.println(Arrays.toString(getInfosFromName(organisedInfosExcel,organisedInfos[1])));
                    document.close();
                    if (file.renameTo(newName)){
                        System.out.println("Name geändert");
                    }else{
                        System.out.println("Name wurde nicht geändert");
                    }
                }


            }
        }catch (Exception e){
            e.printStackTrace();
            System.out.println();
        }
        long endTime = System.currentTimeMillis();
        System.out.println("Ausführungszeit : " + (endTime - startTime) + " milliseconds");

    }

    public static void umbenennung(String string){
        File f = new File(Controller.path1+"\\"+string);

    }


    public static String[] organisePdfInfo(String text){
        String number = "";
        String names = "";
        int middle = text.indexOf('-');
        for(int i = 0; i<text.length(); i++){
            if(text.charAt(i) != '-' && i<middle-1){
                number += text.charAt(i);
            }
            else if(i > middle+1){
                names+=text.charAt(i);
            }
        }
        number = setUpPdfNumber(number);
        String[] infos = {number,names};
        return infos;
    }

    public static String setUpPdfNumber(String text){
        String number = "";
        for(char a : text.toCharArray()){
            if (a != '/'){
                number += Character.toString(a);
            }
        }
        return number;
    }

    public static String[] getInfosFromName(String[] excelInfos, String name){
        System.out.println("Searched Name : " + name);
        String[] result = new String[4];
        for(int i = 2; i<excelInfos.length; i+=4){
            if((excelInfos[i+1] + " " + excelInfos[i]).equals(name)){
                System.out.println("Person exists !!");
                result[0] = excelInfos[i-2];
                result[1] = setNumber(excelInfos[i-1]);
                result[2] = excelInfos[i];
                result[3] = excelInfos[i+1];
            }
        }
        return result;
    }

    public static String setNumber(String text){
        int index = 0;
        String tmp = "";
        for(int i = 0; i < text.length(); i++){
            if(i == 1){
                continue;
            }
            else{
                if(text.charAt(i) == 'E')continue;
                else{
                    tmp+=Character.toString(text.charAt(i));
                }
            }
        }
        return tmp;
    }

    public static String[] setStringArray(String text){
        String cur = "";
        String[] infoArray = new String[getNumberOfInformations(text)];
        int count = 0;
        int index = 0;
        for(char c : text.toCharArray()){
            if (c == '/' && count > 3){
                infoArray[index] = cur;
                index++;
                count++;
                cur = "";
            }
            else{
                if(c == '/'){
                    count++;
                    cur = "";
                }
                else{
                    cur += Character.toString(c);
                }
            }
        }
        return infoArray;
    }

    public static int getNumberOfInformations(String text){
        int count = 0;
        for(char c : text.toCharArray()){
            if(c == '/'){
                count++;
            }
        }
        return count - 4;
    }

    public static String getInfo(String text) {
        String wantedInformations = "";
        for (int i = 0; i < text.length(); i++) {
            if (text.charAt(i) == 'N') {
                if (checkIfNameKey(Character.toString(text.charAt(i)) + Character.toString(text.charAt(i + 1)) + Character.toString(text.charAt(i + 2))) ) {
                    int index = i + 4;
                    String curName = "";
                    boolean flag = true;
                    while (flag) {
                        if(text.charAt(index) == '\r'){
                            wantedInformations = curName;
                            flag = false;
                            continue;
                        }else{
                            curName += Character.toString(text.charAt(index));
                            index++;

                        }

                    }
                    return wantedInformations;
                }
            }
        }
        return null;
    }

    public static boolean checkIfNameKey(String text) {
        return text.equals("Nr.");
    }
}
