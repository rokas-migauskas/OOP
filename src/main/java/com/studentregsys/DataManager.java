package com.studentregsys;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DataManager {
    private GroupManager groupManager;
    private File dataFile;
    private ObjectMapper objectMapper;
    private static DataManager instance;

    public static DataManager getInstance() {
        if (instance == null) {
            instance = new DataManager();
        }
        return instance;
    }
    public void setGroupManager(GroupManager groupManager) {
        this.groupManager = groupManager;
    }
    public DataManager() {
        groupManager = new GroupManager();
        dataFile = new File("data.json");
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule()); // Needed for LocalDate serialization
    }


    public String getAttendanceData(Student student, LocalDate startDate, LocalDate endDate) {
        int daysPresent = 0;
        int totalDays = 0;

        for (LocalDate date = startDate; !date.isAfter(endDate); date = date.plusDays(1)) {
            totalDays++;
            if (student.isPresent(date)) {
                daysPresent++;
            }
        }

        return daysPresent + "/" + totalDays;
    }
    public List<Student> getAllStudents() {
        List<Student> allStudents = new ArrayList<>();
        for (StudentGroup group : groupManager.getStudentGroups()) {
            allStudents.addAll(group.getStudents());
        }
        return allStudents;
    }
    public GroupManager getGroupManager() {
        return groupManager;
    }

    public void save() {
        try {
            objectMapper.writeValue(dataFile, groupManager);
            System.out.println("Data saved: " + groupManager);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void load() {
        if (dataFile.exists()) {
            try {
                GroupManager loadedGroupManager = objectMapper.readValue(dataFile, GroupManager.class);
                if (loadedGroupManager != null) {
                    this.groupManager = loadedGroupManager;
                    System.out.println("Data loaded: " + groupManager);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
