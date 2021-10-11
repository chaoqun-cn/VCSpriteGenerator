package cn.chaoqun.sfg.ffmpeg;

import java.util.*;

/**
 * @author chaoqun
 * @date 2021/10/3 15:48
 */
public class FFmpegOutputOptionsBuilder {

    private FFmpegCmdArgsBuilder cmdArgsBuilder;
    private String dst;

    private final List<String> outputOps;

    public FFmpegOutputOptionsBuilder(FFmpegCmdArgsBuilder cmdArgsBuilder, String dst){
        this.cmdArgsBuilder = cmdArgsBuilder;
        this.dst = dst;
        this.outputOps = new ArrayList<>();
    }

    public FFmpegOutputOptionsBuilder disableAudio() {
        return setExtraOption("-an", null);
    }

    public FFmpegOutputOptionsBuilder disableSubtitle() {
        return setExtraOption("-vn", null);
    }

    public FFmpegOutputOptionsBuilder disableVideo() {
        return setExtraOption("-sn", null);
    }

    public FFmpegOutputOptionsBuilder setSimpleVideoFilterGraph(String simpleVideoFilterGraph) {
        return setExtraOption("-vf", simpleVideoFilterGraph);
    }


    public FFmpegOutputOptionsBuilder setExtraOption(String option, String setting) {
        outputOps.add(Objects.requireNonNull(option));
        if (setting != null) {
            outputOps.add(setting);
        }
        return this;
    }

    public FFmpegCmdArgsBuilder done() {
        return this.cmdArgsBuilder;
    }

    public List<String> build() {

        outputOps.add(dst);

        return outputOps;
    }
}
