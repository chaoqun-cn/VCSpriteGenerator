package cn.chaoqun.sfg.ffmpeg;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * A simple builder for ffmpeg command line
 *
 * To make things short, the FFmpeg command line program expects the following argument format to perform its actions
 * ffmpeg {1} {2} -i {3} {4} {5}, where:
 *
 * 1 - global options
 * 2 - input file options
 * 3 - input url
 * 4 - output file options
 * 5 - output url
 * The parts 2, 3, 4 and 5 can be as many as you need. It's easier to understand this argument format in action:
 *
 * <pre><code>
 * # WARNING: this file is around 300MB
 * $ wget -O bunny_1080p_60fps.mp4 http://distribution.bbb3d.renderfarming.net/video/mp4/bbb_sunflower_1080p_60fps_normal.mp4
 *
 * $ ffmpeg \
 * -y \ # global options
 * -c:a libfdk_aac -c:v libx264 \ # input options
 * -i bunny_1080p_60fps.mp4 \ # input url
 * -c:v libvpx-vp9 -c:a libvorbis \ # output options
 * bunny_1080p_60fps_vp9.webm # output url
 *
 * </code></pre>
 *
 * @author chaoqun
 * @date 2021/10/3 11:01
 *
 */
public class FFmpegCmdArgsBuilder {

    private static final Logger
            LOG = LogManager.getLogger(FFmpegCmdArgsBuilder.class);

    private final String FFMPEG_CMD = "D:\\ffmpeg\\bin\\ffmpeg.exe";

    /**
     * Log level options: https://ffmpeg.org/ffmpeg.html#Generic-options
     */
    public enum LogLevel {
        /**
         * Specific LogLevel
         */
        QUIET, PANIC, FATAL, ERROR, WARNING, INFO, VERBOSE, DEBUG, TRACE;

        @Override
        public String toString() {
            return name().toLowerCase();
        }
    }

    // Global Settings

    private boolean override = true;
    private LogLevel logLevel = LogLevel.ERROR;


    // Input Options

    private List<FFmpegInputOptionsBuilder> inputs = new ArrayList<>();


    // Output Options

    final List<FFmpegOutputOptionsBuilder> outputs = new ArrayList<>();



    // ----------------------------------------------
    // --------- begin build ffmpeg cmd line --------
    // ----------------------------------------------

    public FFmpegCmdArgsBuilder overrideOutputFiles(boolean override) {
        this.override = override;
        return this;
    }

    public FFmpegCmdArgsBuilder setLogLevel(LogLevel logLevel) {
        this.logLevel = logLevel;
        return this;
    }

    public FFmpegInputOptionsBuilder addInputOption(String avSource) {
        Objects.requireNonNull(avSource);
        FFmpegInputOptionsBuilder inputOptionsBuilder = new FFmpegInputOptionsBuilder(this, avSource);
        inputs.add(inputOptionsBuilder);
        return inputOptionsBuilder;
    }

    public FFmpegOutputOptionsBuilder addOutputOption(String dst) {
        Objects.requireNonNull(dst);
        FFmpegOutputOptionsBuilder outputOptionsBuilder = new FFmpegOutputOptionsBuilder(this, dst);
        outputs.add(outputOptionsBuilder);
        return outputOptionsBuilder;
    }

    public FFmpegCmdArgs build() {

        FFmpegCmdArgs cmdArgs = new FFmpegCmdArgs(FFMPEG_CMD);

        cmdArgs.addArgs(this.override ? "-y" : "-n");
//        cmdArgs.addArgs("-v", this.logLevel.toString());

        inputs.forEach(inOp -> cmdArgs.addAll(inOp.build()));
        outputs.forEach(outOp -> cmdArgs.addAll(outOp.build()));

        LOG.info("Final cmd is:\n{}", cmdArgs);

        return cmdArgs;
    }



    public final class FFmpegCmdArgs extends ArrayList<String> {

        private final String SPACE = " ";

        FFmpegCmdArgs(String cmd) {
            Objects.requireNonNull(cmd);
            add(cmd);
        }

        boolean addArgs(String... args) {
            String reduce = Arrays.stream(args).reduce(SPACE, (acc, arg) -> acc + SPACE + arg);
            return add(reduce.trim());
        }
    }

}
