package cn.chaoqun.sfg;

import cn.chaoqun.sfg.config.SpriteOptions;
import cn.chaoqun.sfg.ffmpeg.FFmpegCmd;
import cn.chaoqun.sfg.graphic.Canvas;
import cn.chaoqun.sfg.utils.FFmpegCmds;
import com.google.gson.GsonBuilder;
import net.coobird.thumbnailator.Thumbnailator;
import net.coobird.thumbnailator.Thumbnails;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.BiConsumer;

/**
 * @author chaoqun
 * @date 2021/10/2 20:30
 */
public class SpriteFigureGenerator {

    public static void process(Path sourcePath) throws IOException {

        SpriteOptions spriteOptions = SpriteOptions.builder().primaryAxisMaxN(24).vGap(1).build();

        FFmpegCmds.extractKeyFrame(sourcePath, "%04d.bmp", spriteOptions).run();


        Path tmp = Paths.get(sourcePath.getParent().toString(), "tmp");
        long count = Files.walk(tmp).filter(path -> path.toFile().isFile()).count();

        Canvas canvas = new Canvas(spriteOptions, count);

        AtomicInteger row = new AtomicInteger();
        AtomicInteger col = new AtomicInteger();
        Files.walk(tmp).forEach(path -> {
            if (path.toFile().isDirectory()) {
                return;
            }


            if (spriteOptions.getLayoutDirection() == SpriteOptions.LayoutDirection.ROW_FIRST &&
                    col.get() == spriteOptions.getPrimaryAxisMaxN()) {
                row.incrementAndGet();
                col.set(0);
            }

            if (spriteOptions.getLayoutDirection() == SpriteOptions.LayoutDirection.COL_FIRST &&
                    row.get() == spriteOptions.getPrimaryAxisMaxN()) {
                col.incrementAndGet();
                row.set(0);
            }


            try {
                canvas.drawImage(ImageIO.read(path.toFile()),
                        col.get() * (spriteOptions.getPipWeight() + spriteOptions.getHGap()),
                        row.get() * (spriteOptions.getPipHeight() + spriteOptions.getVGap()));
            } catch (IOException e) {
                e.printStackTrace();
            }

            if (spriteOptions.getLayoutDirection() == SpriteOptions.LayoutDirection.ROW_FIRST ){
                col.incrementAndGet();
            }else {
                row.incrementAndGet();
            }

        });

        String exportName = sourcePath.toFile().getName().split("\\.")[0];
        Path originSF = canvas.export(Paths.get("."), exportName, "png");




    }

    public static void main(String[] args) throws ExecutionException, InterruptedException, IOException {

//        Path sourcePath = Paths.get("C:\\Users\\DELL\\Desktop\\ffmpeg\\forever_ever.mp4");
//        process(sourcePath);




//        Path originSF = Paths.get("E:\\Repository\\study\\sprite-figure-generator\\one_only.png");
//        System.out.println(20.0 * 1024 * 1024 / originSF.toFile().length());
//        Thumbnails.of(originSF.toFile())
//                .scale(1)
//                .outputQuality(20.0 * 1024 * 1024 / originSF.toFile().length())
//                .toFile(originSF.getParent().resolve("one_only_compress.png").toString());


//        CompletableFuture<Integer> completableFuture = CompletableFuture.supplyAsync(() -> {
//            System.out.println(Thread.currentThread());
//            return 1024;
//        }).handle((res, ex) -> {
//            System.out.println(Thread.currentThread());
//            return res;
//        });
//
//        completableFuture.whenComplete((res, ex) -> {
//            System.out.println(Thread.currentThread());
//            System.out.println("res = " + res);
//            System.out.println("ex = " + ex);
//        }).exceptionally((e)-> {
//            return 2;
//        }).get();
//
//
//        BiConsumer sout = (k, v) -> System.out.println(k + "=" + v);
//
//        System.getProperties().forEach(sout);
//
//        System.out.println("\n------------------------\n");
//
//        System.getenv().forEach(sout);
//
//        GsonBuilder gsonBuilder = new GsonBuilder().setPrettyPrinting();

//        FFmpegCmds.extractKeyFrame(Paths.get("C:\\Users\\DELL\\Desktop\\ffmpeg"), "1.mp4", "thumb/%04d.bmp").run();

//        Process process = Runtime.getRuntime().exec("ffmpeg -y -v quiet -re  -i C:\\Users\\DELL\\Desktop\\ffmpeg\\source.mp4 -vsync vfr -vf \"select='eq(pict_type, PICT_TYPE_I)'\" C:\\Users\\DELL\\Desktop\\ffmpeg\\thumb\\%04d.bmp");
//
//
//        String line;
//        BufferedReader reader = new BufferedReader(new InputStreamReader(process.getErrorStream(), StandardCharsets.UTF_8));
//        while ((line = reader.readLine()) != null) {
//            System.out.println(line);
//        }
//        reader.close();

//        System.out.println(Paths.get("/").toFile());


//        Canvas canvas = new Canvas(64, 64);
//        canvas.drawImage(ImageIO.read(Paths.get("C:\\Users\\DELL\\Desktop\\ffmpeg\\thumb\\0001.bmp").toFile()), 0, 0);
//        canvas.finish();
//        canvas.export(Paths.get("."), "abc", "png");


    }
}
