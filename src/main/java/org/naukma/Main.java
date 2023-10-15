package org.naukma;

import org.naukma.ScheduleClasses.ClassSchedule;

import java.util.ArrayList;
import java.util.List;

import static org.naukma.Parser.writeToJson;

public class Main {
    public static void main(String[] args) {

        List<String> filesToParse = new ArrayList<>();
        filesToParse.add("Економіка_БП-3_Осінь_2023–2024.xlsx");
        filesToParse.add("Інженерія_програмного_забезпечення_БП-3_Осінь_2023–2024.xlsx");

        Parser p = new Parser(filesToParse);
        //write schedule to json
        writeToJson(p.getClassSchedule(), "output.json");

        //print schedule to console
        for (ClassSchedule schedule : p.getClassSchedule()) {
            System.out.println(schedule+"\n");
        }


    }
}