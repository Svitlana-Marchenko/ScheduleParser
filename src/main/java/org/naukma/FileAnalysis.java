package org.naukma;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.naukma.Enums.ClassTime;
import org.naukma.Enums.Faculty;
import org.naukma.Enums.Specialization;
import org.naukma.Enums.WeekDay;
import org.naukma.ScheduleClasses.ClassSchedule;
import org.naukma.ScheduleClasses.GroupSchedule;
import org.naukma.ScheduleClasses.TimeSlot;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class FileAnalysis {

    // Data structure to store the lesson schedule
    private Map<String, ClassSchedule> schedule;
    // Flag to indicate the end of the file
    private boolean endOfFile = false;
    // Number of empty lines to signify the end of a file
    private int emptyLineToEnd;

    // Constructor that initializes the schedule map and empty line count
    public FileAnalysis(int numOfEmptyLineToEnd) {
        schedule = new HashMap<>();
        emptyLineToEnd = numOfEmptyLineToEnd;
    }
    // Getter method to access the class schedule
    public Map<String, ClassSchedule> getSchedule() {
        return schedule;
    }
    // Parse a list of file names
    public void parseListOfFiles(List<String> files) {
        for (String file : files) {
            parseFile(file);
            endOfFile = false;
        }
    }
    // Parse a single file
    private void parseFile(String fileName) {
        ClassLoader classLoader = FileAnalysis.class.getClassLoader();
        InputStream inputStream = classLoader.getResourceAsStream(fileName);


        if (inputStream != null) {
            try {
                Workbook workbook = new XSSFWorkbook(inputStream);
                Sheet sheet = workbook.getSheetAt(0);
                Iterator<Row> rowIterator = sheet.iterator();

                Faculty faculty = Faculty.fromString(findFacultyString(rowIterator));

                Row rowWithSpeciAndYear = rowIterator.next();
                int year = findYear(rowWithSpeciAndYear);
                Specialization specialization = Specialization.fromString(findSpecialization(rowWithSpeciAndYear));

                rowIterator = findStartOfSchedule(rowIterator, "День");
                getWeekSchedule(faculty, specialization, year, rowIterator);
            } catch (IllegalArgumentException e) {
                System.err.println("IllegalArgumentException: " + e.getMessage() + " in file " + fileName);
            } catch (IOException e) {
                System.err.println("IOException: " + e.getMessage() + " in file " + fileName);
            }
        }
    }

    // Find the year of studying from the given row
    private static int findYear(Row currentRow) {
        String currentCellContent = currentRow.getCell(0).toString();

        Pattern pattern = Pattern.compile("\\d{1}");
        Matcher matcher = pattern.matcher(currentCellContent);

        if (matcher.find()) {
            String year = matcher.group();

            pattern = Pattern.compile("МП");
            matcher = pattern.matcher(currentCellContent);

            if (matcher.find())
                return Integer.parseInt(year) + 4;
            return Integer.parseInt(year);
        }
        return 0;
    }

    // Find and return the faculty name
    private static String findFacultyString(Iterator<Row> iterator) {
        while (iterator.hasNext()) {
            Row currentRow = iterator.next();
            String currentCellContent = currentRow.getCell(0).toString();

            if (currentCellContent.startsWith("Факультет")) {
                return currentCellContent;
            }
        }
        return "Факультет не знайдено";
    }

    // Find and return the specialization from the given row
    private static String findSpecialization(Row currentRow) {
        String currentCellContent = currentRow.getCell(0).toString();

        Pattern pattern = Pattern.compile("((«|\")(.*?)(»|\"))");
        Matcher matcher = pattern.matcher(currentCellContent);

        List<String> matches = new ArrayList<>();

        while (matcher.find()) {
            matches.add(matcher.group(1).trim());
        }
        String answ = "";
        for (String m : matches) {
            answ += m.replaceAll("\"|«|»", "") + " ";
        }
        return answ;
    }
    // Find the start of lines with lesson
    private static Iterator<Row> findStartOfSchedule(Iterator<Row> rowIterator, String searchText) {
        while (rowIterator.hasNext()) {
            Row row = rowIterator.next();
            Iterator<Cell> cellIterator = row.cellIterator();
            while (cellIterator.hasNext()) {
                Cell cell = cellIterator.next();
                if (cell.getCellType().equals(CellType.STRING) && cell.getStringCellValue().equals(searchText)) {
                    return rowIterator;
                }
            }
        }
        return null;
    }

    //add week schedule to global variable schedule
    private void getWeekSchedule(Faculty faculty, Specialization specialization, int year, Iterator<Row> rowIterator) {
        Row row = rowIterator.next();
        while (!row.getCell(0).toString().isEmpty() && !endOfFile) {
            row = getDaySchedule(row, faculty, specialization, year, rowIterator);
        }
    }

    //add day schedule to global variable schedule
    private Row getDaySchedule(Row rows, Faculty faculty, Specialization specialization, int year, Iterator<Row> rowIterator) {
        Row row = rows;
        WeekDay day = WeekDay.fromString(String.valueOf(row.getCell(0)));
        do {
            row = getTimeSlotSchedule(row, faculty, specialization, day, year, rowIterator);
        }
        while ((row.getCell(0)).toString().isEmpty() && !endOfFile);
        return row;
    }

   // add timeslot schedule to global variable schedule
    private Row getTimeSlotSchedule(Row rows, Faculty faculty, Specialization specialization, WeekDay day, int year, Iterator<Row> rowIterator) {
        Row row = rows;
        ClassTime time = ClassTime.fromString(String.valueOf(row.getCell(1)));
        int emptyCounter = 0;
        do {
            String subject = String.valueOf(row.getCell(2)).replaceAll("\\s+", " ");
            if (!subject.isEmpty() && !subject.equals("null")) {
                emptyCounter = 0;
                String group = validateFeLecture(String.valueOf(row.getCell(3)));

                TimeSlot slot = new TimeSlot(day, time, String.valueOf(row.getCell(5)), String.valueOf(row.getCell(4)));


                if (schedule.containsKey(subject)) {
                    if (schedule.get(subject).getGroups().containsKey(group)) {
                        schedule.get(subject).getGroups().get(group).add(slot);
                    } else {
                        //todo change ab a
                        List<TimeSlot> ab = new ArrayList<>();
                        ab.add(slot);
                        schedule.get(subject).getGroups().put(group, ab);
                    }
                } else {

                    HashMap<String, List<TimeSlot>> a = new HashMap<>();
                    List<TimeSlot> ab = new ArrayList<>();
                    ab.add(slot);
                    a.put(group, ab);
                    schedule.put(subject, new ClassSchedule(faculty, specialization, subject, year, a));

                }
            } else {
                emptyCounter++;
                if (emptyCounter > emptyLineToEnd)
                    endOfFile = true;
            }
            if (!rowIterator.hasNext()) {
                endOfFile = true;
                return row;
            }
            row = rowIterator.next();
        }
        while (String.valueOf(row.getCell(1)).isEmpty() && !endOfFile);
        return row;
    }
    //validate лекція .п
    private static String validateFeLecture(String group) {
        if (group.matches("Лекція\\s*\\d{1}п"))
            return "Лекція";
        return group;
    }
}
