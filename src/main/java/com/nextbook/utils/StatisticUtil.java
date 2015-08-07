package com.nextbook.utils;

import io.keen.client.java.JavaKeenClientBuilder;
import io.keen.client.java.KeenClient;
import io.keen.client.java.KeenProject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by KutsykV on 07.08.2015.
 */
public class StatisticUtil {

    private String projectId;
    private String writeKey;
    private String readKey;
    private KeenProject project;
    private KeenClient client;

    public StatisticUtil(){
    }

    public StatisticUtil(String projectId, String writeKey, String readKey) {
        this.projectId = projectId;
        this.writeKey = writeKey;
        this.readKey = readKey;
        this.project = new KeenProject(projectId, writeKey, readKey);
        initClient();
    }

    public void addEvent(String eventName, Map<String, Object> event){
        if(!KeenClient.isInitialized())
            initClient();
        KeenClient.client().addEvent(eventName, event);
    }

    private void initClient(){
        client = new JavaKeenClientBuilder().build();
        KeenClient.initialize(client);
        KeenClient.client().setDefaultProject(this.getProject());
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

    public final KeenProject getProject() {
        if(project == null)
            this.project = new KeenProject(projectId, writeKey, readKey);
        return project;
    }

}
