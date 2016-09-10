package org.pucko.core;


import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import org.pucko.commands.Command;
import org.junit.Before;
import org.junit.Rule;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;


public class CommandFactoryTest {
    
    private CommandFactory cf;
    private WorkingDirectory wd;
    private OutputHandler oh;
    private String command;
    ArrayList<Command> returnedCommands;
    
    @Before
    public void setUp() throws Exception {
        
        
        cf = new CommandFactory();
        wd = new WorkingDirectory(Paths.get("/tmp"));
        oh = mock(OutputHandler.class);
        command = "cd ..";
        
    }
    
    @Test
    public void testCFReturnsArrayList() {
        returnedCommands = cf.createCommands(command, wd, oh);
        
        assertTrue(returnedCommands instanceof ArrayList<?>);
        
    }
    
    @Test
    public void testCFReturnsNonEmptyArrayList() {
         
        returnedCommands = cf.createCommands(command, wd, oh);
        
        
        assertFalse(returnedCommands.isEmpty());
        
    }
}
