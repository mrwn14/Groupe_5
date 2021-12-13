package com.example.livrable3;
import static org.junit.Assert.*;

import org.junit.Test;

import com.example.livrable3.Admin.ServiceEditor;
import com.example.livrable3.Employee.SuccInfoActivity;
import com.example.livrable3.Client_.ClientServiceRequest;
import com.example.livrable3.Register_and_Login.RegisterActivity;

public class Tests {
    @Test
    public void testNameValidity(){
        String testerValue = "Jonathon____";
        boolean actual = RegisterActivity.isNameValid(testerValue);
        boolean expected = false;
        assertEquals(expected,actual);
    }
    @Test
    public void testUserNameValidity(){
        String testerValue = "ha";
        boolean actual = RegisterActivity.isUsernameValid(testerValue);
        boolean expected = false;
        assertEquals(expected,actual);
    }
    @Test
    public void testPasswordValitiy(){
        String testerValue = "hami";
        boolean actual = RegisterActivity.isPasswordValid(testerValue);
        boolean expected = false;
        assertEquals(expected,actual);
    }
    @Test
    public void testDocChampValidity(){
        String testerValue = "hammoudah";
        boolean actual = ServiceEditor.isDocValid(testerValue);
        boolean expected = true;
        assertEquals(expected,actual);
    }
    @Test
    public void testAddingServiceValidity(){
        int testerValue = 3;
        boolean actual = ServicesDisplay.isAddingServicePossible(testerValue);
        boolean expected = false;
        assertEquals(expected,actual);
    }
    @Test
    public void testNumberValidity(){
        String testerValue = "+19279307468";
        boolean actual = SuccInfoActivity.validateNumber(testerValue);
        boolean expected = true;
        assertEquals(expected,actual);
    }
    @Test
    public void testAddressValidity(){
        String testerValue = "oui";
        boolean actual = SuccInfoActivity.validateAdress(testerValue);
        boolean expected = false;
        assertEquals(expected,actual);
    }
    @Test
    public void testNomValidateur(){
        String testerValue = "Jonathon";
        boolean actual = ClientServiceRequest.nomValidator(testerValue);
        boolean expected = true;
        assertEquals(expected,actual);
    }
    @Test
    public void test2NomValidateur(){
        String testerValue = "Jonathon";
        boolean actual = ClientServiceRequest.nomValidator(testerValue);
        boolean expected = true;
        assertEquals(expected,actual);
    }
    @Test
    public void testDateDeNaissanceValidateur(){
        String testerValue = "1/2/2002";
        boolean actual = ClientServiceRequest.dateDeNaissanceValidator(testerValue);
        boolean expected = false;
        assertEquals(expected,actual);
    }
    @Test
    public void adresseValidateur(){
        String testerValue = "blabla ";
        boolean actual = ClientServiceRequest.adresseValidator(testerValue);
        boolean expected = false;
        assertEquals(expected,actual);
    }
    @Test
    public void prenomValidator(){
        String testerValue = "Jay";
        boolean actual = ClientServiceRequest.prenomValidator(testerValue);
        boolean expected = true;
        assertEquals(expected,actual);
    }
    @Test
    public void inputFieldValidator(){
        String testerValue = "Nom :";
        boolean actual = SuccInfoActivity.textInputValidator(testerValue);
        boolean expected = false;
        assertEquals(expected,actual);
    }

}