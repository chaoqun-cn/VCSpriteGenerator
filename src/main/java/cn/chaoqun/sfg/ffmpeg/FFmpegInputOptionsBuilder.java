package cn.chaoqun.sfg.ffmpeg;

import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * @author chaoqun
 * @date 2021/10/3 15:48
 */
public class FFmpegInputOptionsBuilder {

    private FFmpegCmdArgsBuilder cmdArgsBuilder;
    private String avSource;

    private final List<String> inputOps;

    public FFmpegInputOptionsBuilder(FFmpegCmdArgsBuilder cmdArgsBuilder, String avSource){
        this.cmdArgsBuilder = cmdArgsBuilder;
        this.avSource = avSource;
        this.inputOps = new ArrayList<>();
    }

    public FFmpegInputOptionsBuilder readAtNativeFrameRate(){
        return setExtraOption("-re", null);
    }

    public FFmpegInputOptionsBuilder startOffset(long offset, TimeUnit unit) {
        return setExtraOption("-ss", String.valueOf(unit.toSeconds(offset)));
    }

    public FFmpegInputOptionsBuilder setDuration(long duration, TimeUnit unit) {
        return setExtraOption("-t", String.valueOf(unit.toSeconds(duration)));
    }

    public FFmpegInputOptionsBuilder setExtraOption(String option, String setting) {
        inputOps.add(Objects.requireNonNull(option));
        if (setting != null) {
            inputOps.add(setting);
        }
        return this;
    }

    public FFmpegCmdArgsBuilder done() {
        return this.cmdArgsBuilder;
    }

    public List<String> build() {

        inputOps.add("-i");
        inputOps.add(avSource);

        return inputOps;
    }


}
