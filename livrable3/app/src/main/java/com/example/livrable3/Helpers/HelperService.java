package com.example.livrable3.Helpers;

import java.util.HashMap;
import java.util.Hashtable;

public class HelperService {
    String serviceName, employeeUsername;
    HashMap formulaire, document;
    public HelperService(){
    }
    public HelperService(String serviceName, String employeeUsername, HashMap formulaire, HashMap document) {
        this.serviceName = serviceName;
        this.employeeUsername = employeeUsername;
        this.formulaire = formulaire;
        this.document = document;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getEmployeeUsername() {
        return employeeUsername;
    }

    public void setEmployeeUsername(String employeeUsername) {
        this.employeeUsername = employeeUsername;
    }

    public HashMap getFormulaire() {
        return formulaire;
    }

    public void setFormulaire(HashMap formulaire) {
        this.formulaire = formulaire;
    }

    public HashMap getDocument() {
        return document;
    }

    public void setDocument(HashMap document) {
        this.document = document;
    }
}
