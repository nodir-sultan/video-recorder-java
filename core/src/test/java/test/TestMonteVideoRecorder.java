package test;

import com.automation.remarks.video.exception.RecordingException;
import com.automation.remarks.video.recorder.MonteRecorder;
import org.apache.commons.io.FileUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

import static com.automation.remarks.video.recorder.MonteRecorder.conf;
import static org.hamcrest.CoreMatchers.startsWith;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;

/**
 * Created by sergey on 5/3/16.
 */
public class TestMonteVideoRecorder extends BaseTest{

    private static final String VIDEO_FILE_NAME = "video_test";
    private static final String VIDEO_FOLDER_NAME = "video";

    private File recordVideo() {
        MonteRecorder recorder = new MonteRecorder();
        recorder.start();
        return recorder.stopAndSave(VIDEO_FILE_NAME);
    }

    @Test
    public void shouldBeVideoInRecordingsFolder() throws IOException {
        conf().withVideoFolder(VIDEO_FOLDER_NAME);
        File video = recordVideo();
        String folderName = video.getParentFile().getName();
        assertEquals(folderName, VIDEO_FOLDER_NAME);
    }

    @Test
    public void shouldBeAbsoluteRecordingPath() throws Exception {
        File video = recordVideo();
        assertThat(video.getAbsolutePath(), startsWith(conf().getVideoFolder().getAbsolutePath() + File.separator + VIDEO_FILE_NAME));
    }

    @Test
    public void shouldBeExactVideoFileName() throws Exception {
        String fileName = recordVideo().getName();
        assertThat(fileName, startsWith(VIDEO_FILE_NAME));
    }

    @Test(expected = RecordingException.class)
    public void shouldBeRecordingExceptionIfRecordingWasNotStarted() throws Exception {
        MonteRecorder recorder = new MonteRecorder();
        recorder.stopAndSave(VIDEO_FILE_NAME);
    }
}