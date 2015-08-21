package com.nextbook.utils;

import com.google.gson.Gson;
import com.nextbook.domain.pojo.User;
import io.keen.client.java.JavaKeenClientBuilder;
import io.keen.client.java.KeenClient;
import io.keen.client.java.KeenProject;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by KutsykV on 07.08.2015.
 */
@Component
public class StatisticUtil {

    private static String projectId;
    private static String writeKey;
    private static String readKey;
    private static KeenProject project;
    private static KeenClient client;
    private static Gson gson;

    public StatisticUtil(){
        projectId = "55bf358090e4bd5654f0b01b";
        writeKey = "85a99e97d6af61801104f77b6eea11f4523805028d91eb2c0104bf0c62f3665cca5567aa928e9fabcaa59ddb642f08329ede014d477384f9ed48d65a20062b1da7820aea774c1097a74f7b87ac0f0710033be8d79003d28140bf5cd2d42de538e9b053c637eb360b3e061069dfa5fc8f";
        readKey = "ef84bfd5531894d70d78324546544a3e30b6dc757e01326e9e27e33070f388a475d3709fd01e39793366f0ed4d5f4b7897c0faa6ef380783affc0bcc1c6cff13c67b53a7ca8d1f172e3504b8c817b12fb87cb3ea8ec08cc4fbfab5bafc9dc333ea4d061ce3df4c94c4d42dd00a51cef2";
        gson = new Gson();
        project = new KeenProject(projectId, writeKey, readKey);
        initClient();
    }

    private void initClient(){
        client = new JavaKeenClientBuilder().build();
        KeenClient.initialize(client);
        KeenClient.client().setDefaultProject(getProject());
    }

    public void deleteEvent(User who, Object object){
        Map<String, Object> deleteEvent = new HashMap<String, Object>();
        deleteEvent.put("user_id", who.getId());
        deleteEvent.put("user_role", who.getRole());
        deleteEvent.put("object", gson.toJson(object));
        addEvent("Delete", deleteEvent);
    }

    public void registrationEvent(User newUser){
        Map<String, Object> addEvent = new HashMap<String, Object>();
        addEvent.put("new_user", gson.toJson(newUser));
        addEvent("Registration", addEvent);
    }

    public void addEvent(User who, Object object){
        Map<String, Object> addEvent = new HashMap<String, Object>();
        addEvent.put("user_id", who.getId());
        addEvent.put("user_role", who.getRole());
        addEvent.put("object", gson.toJson(object));
        addEvent("Add", addEvent);
    }

    public void uploadEvent(User who, MultipartFile file){
        Map<String, Object> addEvent = new HashMap<String, Object>();
        addEvent.put("user_id", who.getId());
        addEvent.put("user_role", who.getRole());
        String fileName = file.getOriginalFilename();
        String fileType = fileName.substring(fileName.lastIndexOf(".")+1);
        addEvent.put("file_name", fileName);
        fileType = fileType.equals("png") || fileType.equals("jpg") ? "image" : "book";
        addEvent.put("file_type", fileType);
        addEvent("Upload", addEvent);
    }

    public void updateEvent(User who, Object oldObject, Object newObject){
        Map<String, Object> addEvent = new HashMap<String, Object>();
        addEvent.put("user_id", who.getId());
        addEvent.put("user_role", who.getRole());
        addEvent.put("before", gson.toJson(oldObject));
        addEvent.put("after", gson.toJson(newObject));
        addEvent("Update", addEvent);
    }

    private void addEvent(String eventName, Map<String, Object> event){
        if(!KeenClient.isInitialized())
            initClient();
        KeenClient.client().addEvent(eventName, event);
    }

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public String getWriteKey() {
        return writeKey;
    }

    public void setWriteKey(String writeKey) {
        this.writeKey = writeKey;
    }

    public String getReadKey() {
        return readKey;
    }

    public void setReadKey(String readKey) {
        this.readKey = readKey;
    }

    public KeenProject getProject() {
        if(project == null)
            project = new KeenProject(projectId, writeKey, readKey);
        return project;
    }


}
