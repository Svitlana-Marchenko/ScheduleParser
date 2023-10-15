package org.naukma;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.naukma.Enums.Faculty;
import org.naukma.Enums.Specialization;
import org.naukma.ScheduleClasses.ClassSchedule;
import org.naukma.ScheduleClasses.GroupSchedule;
import org.naukma.ScheduleClasses.TimeSlot;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Parser {

    private Set<ClassSchedule> classSchedule;

    // Constructor that takes a list of file names for parsing
    public Parser(List<String> fileToParse) {
        FileAnalysis analysis = new FileAnalysis(5);
        analysis.parseListOfFiles(fileToParse);
        this.classSchedule = validateSchedule(analysis.getSchedule().values());
    }

    // Getter for classSchedule
    public Set<ClassSchedule> getClassSchedule() {
        return classSchedule;
    }

    // Setter for classSchedule
    public void setClassSchedule(Set<ClassSchedule> classSchedule) {
        this.classSchedule = classSchedule;
    }

    // Validate the class schedule and transform it to set
    private Set<ClassSchedule> validateSchedule(Collection<ClassSchedule> classSchedules) {
        List<ClassSchedule> classToProcess = new ArrayList<>();

        for (ClassSchedule classSchedule : classSchedules) {
            if (classSchedule.getSpecialization().equals(Specialization.FE)) {
                classToProcess.add(classSchedule);
            }
        }
        classSchedules.removeAll(classToProcess);

        Set<ClassSchedule> result = new HashSet<>(classSchedules);
        result.addAll(validateFeSchedule(classToProcess));
        result.addAll(classSchedules);

        return result;
    }

    // Validate the FE lessons
    private static List<ClassSchedule> validateFeSchedule(List<ClassSchedule> classToProcess) {
        List<ClassSchedule> validatedList = new ArrayList<>();
        for (ClassSchedule classS : classToProcess) {
            validateFeClassSchedule(classS, validatedList);
        }
        return validatedList;
    }

    // Validate ClassSchedule object
    private static void validateFeClassSchedule(ClassSchedule cl, List<ClassSchedule> list) {
        String subject = cl.getSubjectName();
        Map<String, GroupSchedule> groupMap = cl.getGroups();

        for (String group : groupMap.keySet()) {
            boolean economic = fromEconomic(subject, group);
            boolean finance = fromFinance(subject, group);
            boolean marketing = fromMarketing(subject, group);
            boolean management = fromManagement(subject, group);

            String vSubject = cleanSubjectFromFE(subject);
            String vGroup = cleanGroupFromFE(group);

            List<TimeSlot> gr = groupMap.get(group).getTime();

            if (economic) {
                if (list.contains(new ClassSchedule(Faculty.FACULTY_OF_ECONOMICS, Specialization.ECONOMICS, vSubject, cl.getYearOfStudying(), null))) {
                    list.get(list.indexOf(new ClassSchedule(Faculty.FACULTY_OF_ECONOMICS, Specialization.ECONOMICS, vSubject, cl.getYearOfStudying(), null))).getGroups().put(vGroup, new GroupSchedule(vGroup, gr));
                } else {
                    HashMap<String, GroupSchedule> a = new HashMap<>();
                    a.put(vGroup, new GroupSchedule(vGroup, gr));
                    list.add(new ClassSchedule(Faculty.FACULTY_OF_ECONOMICS, Specialization.ECONOMICS, vSubject, cl.getYearOfStudying(), a));
                }
            }
            if (finance) {
                if (list.contains(new ClassSchedule(Faculty.FACULTY_OF_ECONOMICS, Specialization.FINANCE_BANKING_AND_INSURANCE, vSubject, cl.getYearOfStudying(), null))) {
                    list.get(list.indexOf(new ClassSchedule(Faculty.FACULTY_OF_ECONOMICS, Specialization.FINANCE_BANKING_AND_INSURANCE, vSubject, cl.getYearOfStudying(), null))).getGroups().put(vGroup, new GroupSchedule(vGroup, gr));

                } else {
                    HashMap<String, GroupSchedule> a = new HashMap<>();
                     a.put(vGroup, new GroupSchedule(vGroup, gr));
                    list.add(new ClassSchedule(Faculty.FACULTY_OF_ECONOMICS, Specialization.FINANCE_BANKING_AND_INSURANCE, vSubject, cl.getYearOfStudying(), a));
                }
            }
            if (management) {
                if (list.contains(new ClassSchedule(Faculty.FACULTY_OF_ECONOMICS, Specialization.MANAGEMENT, vSubject, cl.getYearOfStudying(), null))) {
                    list.get(list.indexOf(new ClassSchedule(Faculty.FACULTY_OF_ECONOMICS, Specialization.MANAGEMENT, vSubject, cl.getYearOfStudying(), null))).getGroups().put(vGroup, new GroupSchedule(vGroup, gr));

                } else {
                    HashMap<String, GroupSchedule> a = new HashMap<>();
                    a.put(vGroup, new GroupSchedule(vGroup, gr));
                    list.add(new ClassSchedule(Faculty.FACULTY_OF_ECONOMICS, Specialization.MANAGEMENT, vSubject, cl.getYearOfStudying(), a));
                }
            }
            if (marketing) {
                if (list.contains(new ClassSchedule(Faculty.FACULTY_OF_ECONOMICS, Specialization.MARKETING, vSubject, cl.getYearOfStudying(), null))) {
                     list.get(list.indexOf(new ClassSchedule(Faculty.FACULTY_OF_ECONOMICS, Specialization.MARKETING, vSubject, cl.getYearOfStudying(), null))).getGroups().put(vGroup, new GroupSchedule(vGroup, gr));

                } else {
                    HashMap<String, GroupSchedule> a = new HashMap<>();
                     a.put(vGroup, new GroupSchedule(vGroup, gr));
                    list.add(new ClassSchedule(Faculty.FACULTY_OF_ECONOMICS, Specialization.MARKETING, vSubject, cl.getYearOfStudying(), a));
                }
            }
        }
    }


    // Check if the subject or group is related to economics
    private static boolean fromEconomic(String subject, String group) {
        String subjectR = "екон|ек|економ";
        String groupR = "\\d+\\s*(ек+мен|е)";

        Pattern patternS = Pattern.compile(subjectR);
        Matcher matcherS = patternS.matcher(subject);

        Pattern patternG = Pattern.compile(groupR);
        Matcher matcherG = patternG.matcher(group);

        return matcherS.find() || matcherG.find();
    }

    // Check if the subject or group is related to finance
    private static boolean fromFinance(String subject, String group) {
        String subjectR = "фін|фінанси";
        String groupR = "ф";

        Pattern patternS = Pattern.compile(subjectR);
        Matcher matcherS = patternS.matcher(subject);

        Pattern patternG = Pattern.compile(groupR);
        Matcher matcherG = patternG.matcher(group);

        return matcherS.find() || matcherG.find();
    }

    // Check if the subject or group is related to management
    private static boolean fromManagement(String subject, String group) {
        String subjectR = "мен|менеджмент";
        String groupR = "мен";

        Pattern patternS = Pattern.compile(subjectR);
        Matcher matcherS = patternS.matcher(subject);

        Pattern patternG = Pattern.compile(groupR);
        Matcher matcherG = patternG.matcher(group);

        return matcherS.find() || matcherG.find();
    }

    // Check if the subject or group is related to marketing
    private static boolean fromMarketing(String subject, String group) {
        subject = subject.replaceAll("\\n", "");
        String subjectR = "мар|маркетинг|марк";
        String groupR = "мар";

        Pattern patternS = Pattern.compile(subjectR);
        Matcher matcherS = patternS.matcher(subject);

        Pattern patternG = Pattern.compile(groupR);
        Matcher matcherG = patternG.matcher(group);

        return matcherS.find() || matcherG.find();
    }

    // Clean the subject name from FE-specific patterns
    private static String cleanSubjectFromFE(String subject) {
        String regex = "\\(екон\\.\\+мен\\.\\)|\\(фін\\.\\+мар\\.\\)|\\(менеджмент\\)|\\(ек\\)|\\(екон\\.\\)|\\(мен\\.\\)|\\(фін\\.\\)|\\(мар\\.\\)|\\(маркетинг\\)|\\(марк\\.\\)|\\(економ\\. теор\\.\\)|\\(марк,мен\\)|\\(марк, мен\\)|\\(фінанси\\)|\\(мен, мар\\)|\\(ек\\.\\)";
        String cleanedSubject = subject.replaceAll("\\n", "").replaceAll(regex, "").replaceAll("\\s+", " ");
        return cleanedSubject;
    }

    // Clean the group name from FE-specific patterns
    private static String cleanGroupFromFE(String group) {
        if (group.equals("Лекція") || group.equals("лекція"))
            return group;
        else {
            String regex = "ф\\+мар|ек\\+мен|мар\\+мен|е|ф";
            return group.replaceAll("\\n", "").replaceAll(regex, "").replaceAll("\\s+", "");
        }
    }

    // Write class schedules to a JSON file
    public static void writeToJson(Set<ClassSchedule> classSchedules, String jsonFileName) {
        ObjectMapper objectMapper = new ObjectMapper();

        try {
            objectMapper.writeValue(new File(jsonFileName), classSchedules);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
