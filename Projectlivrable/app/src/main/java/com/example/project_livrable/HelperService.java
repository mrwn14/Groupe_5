package com.example.project_livrable;

public class HelperService {
    String serviceName, employeeUsername, document;
    HelperClass formulaire;
    public HelperService(){
    }
    public HelperService(String serviceName, String employeeUsername, HelperClass formulaire, String document) {
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

    public HelperClass getFormulaire() {
        return formulaire;
    }

    public void setFormulaire(HelperClass formulaire) {
        this.formulaire = formulaire;
    }

    public String getDocument() {
        return document;
    }

    public void setDocument(String document) {
        this.document = document;
    }
}
