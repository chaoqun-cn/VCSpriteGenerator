package cn.chaoqun.sfg.utils;

import cn.chaoqun.sfg.config.SpriteOptions;
import cn.chaoqun.sfg.ffmpeg.FFmpegCmd;
import cn.chaoqun.sfg.ffmpeg.FFmpegCmdArgsBuilder;
import cn.chaoqun.sfg.ffmpeg.SimpleFFmpegCmdImpl;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.TimeUnit;


/**
 * @author chaoqun
 * @date 2021/10/3 17:08
 */
public class FFmpegCmds {

    /**
     * ffmpeg -i source.mp4 -vf "select='eq(pict_type, PICT_TYPE_I)'" -vsync vfr ./thumb/%04d.bmp
     *
     * @return
     */
    public static FFmpegCmd extractKeyFrame(Path avSource, String outputPattern, SpriteOptions spriteOptions) {

        Path outPath = Paths.get(avSource.getParent().toString(), "tmp", outputPattern);
        try {
            Files.createDirectories(outPath.getParent());
        } catch (IOException e) {
            e.printStackTrace();
        }

        FFmpegCmdArgsBuilder.FFmpegCmdArgs cmdArgs = new FFmpegCmdArgsBuilder()
                .overrideOutputFiles(true)
                .setLogLevel(FFmpegCmdArgsBuilder.LogLevel.INFO)
                .addInputOption(avSource.toString())
                .startOffset(2, TimeUnit.MINUTES)
//                .readAtNativeFrameRate()
                .done()
                .addOutputOption(outPath.toString())
                .setSimpleVideoFilterGraph("\"select='eq(pict_type, PICT_TYPE_I)'\"")
                .setExtraOption("-vsync", "vfr")
                .setExtraOption("-s", spriteOptions.getPipWeight() + "*" + spriteOptions.getPipHeight())
                .done()
                .build();

        return new SimpleFFmpegCmdImpl(cmdArgs, avSource.getParent());
    }
}
