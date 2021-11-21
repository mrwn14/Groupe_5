package com.example.project_livrable;
import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import android.util.Patterns;

import com.example.project_livrable.RegisterActivity;
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

}
