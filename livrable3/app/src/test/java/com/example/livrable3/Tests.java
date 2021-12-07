package com.example.livrable3;
import static org.junit.Assert.*;

import org.junit.Test;

import com.example.livrable3.Admin.ServiceEditor;
import com.example.livrable3.Employee.SuccInfoActivity;

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


}