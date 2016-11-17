package hello;

import java.io.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class Ziper {

    public static void main(String[] args) throws Exception {

        File file1 = new File(args[1]);

        try (ZipOutputStream out = new ZipOutputStream(new FileOutputStream(stripExtension(file1.getCanonicalPath()) + ".zip"))) {

            File file2 = new File(args[0]);

            doZip(file2, out,"");
        } catch (FileNotFoundException e) {
            throw e;
        } catch (IOException e) {
            throw e;
        }
    }

    //Delete extension
    private static String stripExtension (String str) {

            if (str == null) return null;

            int pos = str.lastIndexOf(".");

            if (pos == -1) return str;

            return str.substring(0, pos);
        }

    private static void doZip(File dir, ZipOutputStream out, String start) throws IOException{
        String name = start + dir.getName();
        if (dir.listFiles().length > 0) {
            for (File f : dir.listFiles()) {
                if (f.isDirectory()) {
                    doZip(f, out, name + "/");
                } else {
                    out.putNextEntry(new ZipEntry(name + "/" + f.getName()));
                    write (new FileInputStream(f), out);
                }
            }
        } else {
            out.putNextEntry(new ZipEntry(name + "/"));
        }
    }

    private static void write(InputStream in, OutputStream out) throws IOException {
        byte[] buffer = new byte[1024];
        int len;
        while ((len = in.read(buffer)) >= 0)
            out.write(buffer, 0, len);
        in.close();
    }
}
