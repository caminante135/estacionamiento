package com.example.gerardo.miestacionamiento.controller.util;

import java.io.File;
import java.util.List;
import java.util.Objects;

/**
 * Created by fsanchirico on 22-02-15.
 */
public class RunnableArgs implements Runnable {

    private int response;
    private File file;

    private List<Objects> arguments;

    public void setResponse(int mResponse) {
        this.response = mResponse;
    }

    public int getResponse() {
        return this.response;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public File getFile() {
        return file;
    }

    public void run() {
        // do whatever you want with data
    }
}
