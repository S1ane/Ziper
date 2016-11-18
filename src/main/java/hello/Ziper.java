package hello;

import java.io.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class Ziper {

    public static void main(String[] args) throws Exception {

        String sourceFolderName =  args[0];
        String outputFileName = args[1];

        try (ZipOutputStream out = new ZipOutputStream(new FileOutputStream(stripExtension(outputFileName)+".zip"))) {

            doZip(sourceFolderName, out,sourceFolderName);

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    //Delete extension
    private static String stripExtension (String str) {

            if (str == null) return null;

            int pos = str.lastIndexOf(".");

            if (pos == -1) return str;

            return str.substring(0, pos);
        }

    private static void doZip(String folderName, ZipOutputStream out, String baseFolderName) throws Exception{
        File f = new File(folderName);
        if (f.exists()) {

            if (f.isDirectory()) {

                if (!folderName.equalsIgnoreCase(baseFolderName)) {
                    String entryName = folderName.substring(baseFolderName.length() + 1, folderName.length()) + File.separatorChar;
                    ZipEntry ze = new ZipEntry(entryName);
                    out.putNextEntry(ze);
                }
                File f2[] = f.listFiles();
                for (int i = 0; i < f2.length; i++) {
                    doZip(f2[i].getAbsolutePath(), out, baseFolderName);
                }
            } else {
                String entryName = folderName.substring(baseFolderName.length() + 1, folderName.length());
                out.putNextEntry(new ZipEntry(entryName));
                write(new FileInputStream(folderName), out);
            }
        }
        else {
        System.out.println("File or directory not found " + folderName);
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
