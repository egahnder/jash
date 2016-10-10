
package org.pucko.commands;

/**
 * Created by Tobias on 15/09/16.
 */

import static org.junit.Assert.*;

import com.google.common.collect.ImmutableList;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;

import org.mockito.Mock;
import org.pucko.testutilities.ArgsPopulator;
import org.junit.Before;
import org.junit.Rule;

import static org.pucko.testutilities.TestUtils.setArgs;


public class TouchTest {

    @Mock
    private CommandUtils commandUtils;
    private ArgsPopulator argsPopulator;
    private Path folderPath;
    private static final String VALID_TOUCH_COMMAND = "touch";
    private static final String VALID_FILENAME = "filename";

    @Rule
    public TemporaryFolder testFolder = new TemporaryFolder();

    @Before
    public void setUp() {
        initMocks(this);
        argsPopulator = new ArgsPopulator();
        folderPath = testFolder.getRoot().toPath();
    }

    @Test
    public void testCreatesSingleFile() throws IOException {

        when(commandUtils.getWorkingDirectory()).thenReturn(folderPath);

        setArgs(commandUtils, VALID_TOUCH_COMMAND, VALID_FILENAME);
        Touch t = new Touch(commandUtils);

        ArrayList<Path> filePathArray = createPathArray(commandUtils.getArgs());
        t.runCommand();
        assertFiles(filePathArray);
    }

    @Test
    public void testCreatesTwoFiles() throws IOException {

        when(commandUtils.getWorkingDirectory()).thenReturn(folderPath);
        setArgs(commandUtils, VALID_TOUCH_COMMAND, "filnamn", "filnamn1", "filnamn2");
        Touch t = new Touch(commandUtils);

        ArrayList<Path> filePathArray = createPathArray(commandUtils.getArgs());
        t.runCommand();
        assertFiles(filePathArray);
    }

    @Test
    public void testCreatesMultipleFiles() throws IOException {

        when(commandUtils.getWorkingDirectory()).thenReturn(folderPath);
        setArgs(commandUtils, "filnamn", "filnamn1", "filnamn2", "filnamn3", "filnamn4", "filnamn5", "filnamn6");
        Touch t = new Touch(commandUtils);

        ArrayList<Path> filePathArray = createPathArray(commandUtils.getArgs());
        t.runCommand();
        assertFiles(filePathArray);
    }


    @Test
    public void testReturnsTrueWithOneFile() {

        when(commandUtils.getWorkingDirectory()).thenReturn(folderPath);
        setArgs(commandUtils, VALID_TOUCH_COMMAND, VALID_FILENAME);
        Touch t = new Touch(commandUtils);

        assertTrue(t.runCommand());

    }

    @Test
    public void testReturnsFalseWhenFileAlreadyExists() throws IOException {
        when(commandUtils.getWorkingDirectory()).thenReturn(folderPath);
        setArgs(commandUtils, VALID_TOUCH_COMMAND, VALID_FILENAME);
        Touch t = new Touch(commandUtils);

        testFolder.newFile(VALID_FILENAME);
        assertFalse(t.runCommand());
    }


    @Test
    public void testReturnsFalseWhenNoFileName() {
        when(commandUtils.getWorkingDirectory()).thenReturn(folderPath);
        setArgs(commandUtils, VALID_TOUCH_COMMAND);
        Touch t = new Touch(commandUtils);
        assertFalse(t.runCommand());
    }


    @Test
    public void testErrorMsgWhenNoFileName() {
        when(commandUtils.getWorkingDirectory()).thenReturn(folderPath);
        setArgs(commandUtils, VALID_TOUCH_COMMAND);
        Touch t = new Touch(commandUtils);
        t.runCommand();
        verify(commandUtils, times(1)).error("Filename is missing");
    }

    @Test
    public void testErrorMsgWhenFileAlreadyExists() throws IOException {
        when(commandUtils.getWorkingDirectory()).thenReturn(folderPath);

        setArgs(commandUtils, VALID_TOUCH_COMMAND, VALID_FILENAME);
        Touch t = new Touch(commandUtils);

        testFolder.newFile(VALID_FILENAME);
        t.runCommand();

        verify(commandUtils, times(1)).error("File already exists");

    }

    @Test
    public void testErrorMsgOnDirectoryNotReadable() {
        testFolder.getRoot().setReadable(false);
        when(commandUtils.getWorkingDirectory()).thenReturn(folderPath);
        setArgs(commandUtils, VALID_TOUCH_COMMAND, VALID_FILENAME);
        Touch t = new Touch(commandUtils);

        t.runCommand();

        verify(commandUtils, times(1)).error("touch: can not make 'touch' on <<"+VALID_FILENAME+">>: permission denied");
    }

    @Test
    public void testReturnsFalseOnDirectoryNotWritable() {
        testFolder.getRoot().setWritable(false);
        when(commandUtils.getWorkingDirectory()).thenReturn(folderPath);
        setArgs(commandUtils, VALID_TOUCH_COMMAND, VALID_FILENAME);
        Touch t = new Touch(commandUtils);

        assertFalse(t.runCommand());
    }

    @Test
    public void testErrorMsgOnDirectoryNotWritable() {
        testFolder.getRoot().setWritable(false);
        when(commandUtils.getWorkingDirectory()).thenReturn(folderPath);
        setArgs(commandUtils, VALID_TOUCH_COMMAND, VALID_FILENAME);
        Touch t = new Touch(commandUtils);

        t.runCommand();

        verify(commandUtils, times(1)).error("touch: can not make 'touch' on <<"+VALID_FILENAME+">>: permission denied");
    }

    @Test
    public void testReturnsFalseOnDirectoryNotReadable() {
        testFolder.getRoot().setReadable(false);
        when(commandUtils.getWorkingDirectory()).thenReturn(folderPath);
        setArgs(commandUtils, VALID_TOUCH_COMMAND, VALID_FILENAME);
        Touch t = new Touch(commandUtils);

        assertFalse(t.runCommand());
    }

    private ArrayList<Path> createPathArray(ImmutableList<String> input) {

        Path filePath;
        ArrayList<Path> filePathArray = new ArrayList<>();

        for (String s : input) {
            filePath = folderPath.resolve(s);
            filePathArray.add(filePath);
        }
        return filePathArray;
    }

    private void assertFiles(ArrayList<Path> filePathArray) {
        for (Path p : filePathArray) {
            assertTrue("File: " + p.toString(), new File(p.toString()).isFile());
        }

    }

}
