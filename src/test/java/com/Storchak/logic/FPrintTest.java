package com.Storchak.logic;

import com.Storchak.Exceptions.UserNotFoundException;
import com.Storchak.dao.FPDao;
import com.Storchak.dao.UsersDao;
import com.Storchak.dto.User;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import static org.junit.Assert.assertEquals;

public class FPrintTest {
    private FPRecognizeService test;
    private FPDao fpDao;
    private UsersDao usersDao;

    @Before
    public void init() {
        fpDao = new FPDao();
        usersDao = new UsersDao();
        test = new FPRecognizeService(fpDao, usersDao);
    }

    @After
    public void close() {
        fpDao.closeEntityManagerFactory();
        usersDao.closeEntityManagerFactory();
    }


    @Test
    public void identifyFpDenTest() throws IOException, UserNotFoundException {
        User user = test.identifyFp(readFile("src/main/resources/testFiles/denTest.bin"));
        assertEquals("Den", user.getName());
    }

    @Test
    public void identifyFpChewbaccaTest() throws IOException, UserNotFoundException {
        User user = test.identifyFp(readFile("src/main/resources/testFiles/chewbaccaTest.bin"));
        assertEquals("Chewbacca", user.getName());
    }

    @Test
    public void identifyFpMordatyTest() throws IOException, UserNotFoundException {
        User user = test.identifyFp(readFile("src/main/resources/testFiles/mordatyTest.bin"));
        assertEquals("Mordaty", user.getName());
    }

    @Test
    public void identifyFpJewTest() throws IOException, UserNotFoundException {
        User user = test.identifyFp(readFile("src/main/resources/testFiles/jewTest.bin"));
        assertEquals("Jew", user.getName());
    }

    public int[] readFile(String path) throws IOException {
        int[] imageData1 = new int[30400];
        FileInputStream fin = new FileInputStream(new File(path));
        int a = 0;
        int k = 0;
        fin.skip(2);
        do {
            a = fin.read();
            if (a != -1) imageData1[k] = a;
            k++;
        } while (a != -1);
        fin.close();
        return imageData1;
    }
}
