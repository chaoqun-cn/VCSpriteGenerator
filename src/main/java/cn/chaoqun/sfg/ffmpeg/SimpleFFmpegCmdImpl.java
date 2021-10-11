package cn.chaoqun.sfg.ffmpeg;

import cn.chaoqun.sfg.utils.CmdHelper;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;

/**
 * @author chaoqun
 * @date 2021/10/3 17:22
 */
public class SimpleFFmpegCmdImpl implements FFmpegCmd {

    private FFmpegCmdArgsBuilder.FFmpegCmdArgs cmdArgs;
    private Path workSpace;

    public SimpleFFmpegCmdImpl(FFmpegCmdArgsBuilder.FFmpegCmdArgs cmdArgs, Path workSpace) {
        this.cmdArgs = cmdArgs;
        this.workSpace = workSpace;
    }


    @Override
    public void run() {
        try {
            Process process = CmdHelper.run(this.cmdArgs, this.workSpace);
            process.waitFor();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
