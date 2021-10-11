package cn.chaoqun.sfg.utils;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

/**
 * @author chaoqun
 * @date 2021/10/3 08:50
 */
public class CmdHelper {

    public static Process run(List<String> cmd, Path workSpace) throws IOException {

        System.out.println(cmd.stream().reduce((acc, e) -> acc + " " + e));
        return new ProcessBuilder()
                .inheritIO()
                .command(cmd)
//                .redirectErrorStream(true)
                .directory(workSpace.toFile())
                .start();
    }


}
