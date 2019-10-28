package com.itcast.util;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.security.MessageDigest;
import java.util.UUID;

/**
 * 上传文件工具类
 *
 * @author yihang
 */
public final class UploadUtils {

    private UploadUtils() {
    }

    private static final int BUFFER_LEN = 1024 * 16;

    private static final char[] HEX_DIGITS = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd',
            'e', 'f'};

    private static final String ALGORITHM = "md5";

    /**
     * 求某个字符串的 md5 签名结果 <br>
     * demo:
     * <pre>
     *     toMd5("123")
     * </pre>
     * 返回:
     * <pre>
     *     202cb962ac59075b964b07152d234b70
     * </pre>
     *
     * @param str 原始字符串
     * @return md5 结果
     */
    public static String toMd5(String str, Charset charset) {
        return toMd5(new ByteArrayInputStream(str.getBytes(charset)));
    }

    /**
     * 生成某一个输入流的 md5 签名结果
     * demo:
     * <pre>
     *     toMd5(new ByteArrayInputStream("123".getBytes()))
     * </pre>
     * 返回:
     * <pre>
     *     202cb962ac59075b964b07152d234b70
     * </pre>
     *
     * @param is 输入流 方法内会关闭流
     * @return md5 结果
     */
    public static String toMd5(InputStream is) {
        try {
            // 获得签名算法对象
            MessageDigest md5 = MessageDigest.getInstance(ALGORITHM);
            // 从输出流获取循环读取字节
            int bufferLen = BUFFER_LEN;
            final byte[] buffer = new byte[bufferLen];
            int read = is.read(buffer, 0, bufferLen);
            while (read > -1) {
                // 签名算法对象更新结果
                md5.update(buffer, 0, read);
                read = is.read(buffer, 0, bufferLen);
            }
            return new String(toHex(md5.digest()));
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException ignored) {
                }
            }
        }
    }

    /**
     * 求文件的扩展名, 包含 . 字符, 如果扩展名不存在, 返回 "" <br>
     * demo:
     * <pre>
     *     ext("1.txt");
     * </pre>
     * 返回:
     * <pre>
     *     .txt
     * </pre>
     *
     * @param filename 原始文件名
     * @return 文件后缀
     */
    public static String ext(String filename) {
        int idx = filename.lastIndexOf(".");
        if (idx == -1) {
            return "";
        }
        return filename.substring(idx);
    }

    /**
     * demo:
     * <pre>
     *     InputStream stream = new ByteArrayInputStream("123".getBytes());
     *     toMd5File(stream, "e:\\", ".txt");
     * </pre>
     * 结果:
     * <pre>
     *     e:\202cb962ac59075b964b07152d234b70.txt
     * </pre>
     *
     * @param is  输入流 方法内会关闭流
     * @param dir 输出目录, 注意目录要以 / 结尾
     * @param ext 输出文件后缀, 注意后缀要以 . 开头
     * @see UploadUtils#toMd5File(InputStream, String, String, int)
     */
    public static String toMd5File(InputStream is, String dir, String ext) {
        return toMd5File(is, dir, ext, 0);
    }

    /**
     * demo:
     * <pre>
     *     InputStream stream = new ByteArrayInputStream("123".getBytes());
     *     toMd5File(stream, "e:\\", ".txt", 6);
     * </pre>
     * 结果:
     * <pre>
     *     e:\2\0\202cb962ac59075b964b07152d234b70.txt
     * </pre>
     *
     * @param is    输入流 方法内会关闭流
     * @param dir   输出目录, 注意目录要以 / 结尾
     * @param ext   输出文件后缀, 注意后缀要以 . 开头
     * @param level 代表子文件夹的层数
     */
    public static String toMd5File(InputStream is, String dir, String ext, int level) {
        File dirfile = new File(dir);
        if (!dirfile.exists()) {
            dirfile.mkdirs();
        }
        String src = dir + UUID.randomUUID().toString();
        try (FileOutputStream os = new FileOutputStream(src)) {
            // 获得签名算法对象
            MessageDigest md5 = MessageDigest.getInstance(ALGORITHM);

            // 从输出流获取循环读取字节
            int bufferLen = BUFFER_LEN;
            final byte[] buffer = new byte[bufferLen];
            int read = is.read(buffer, 0, bufferLen);
            while (read > -1) {
                // 签名算法对象更新结果
                md5.update(buffer, 0, read);
                // 同时写出到输出流
                os.write(buffer, 0, read);
                read = is.read(buffer, 0, bufferLen);
            }
            String filename = new String(toHex(md5.digest()));
            StringBuilder subdir = new StringBuilder();
            for (int i = 0; i < level; i++) {
                subdir.append(filename.charAt(i)).append(File.separator);
            }
            File subdirfile = new File(dir + subdir);
            if (!subdirfile.exists()) {
                // 创建多级文件夹
                subdirfile.mkdirs();
            }
            String dist = dir + subdir + filename + ext;
            String imageurl = subdir + filename + ext;
            try {
                Files.move(new File(src).toPath(), new File(dist).toPath(), StandardCopyOption.REPLACE_EXISTING);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            return imageurl;
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException ignored) {
                }
            }
        }
    }

    private static char[] toHex(final byte[] data) {
        final int l = data.length;
        final char[] out = new char[l << 1];
        // two characters form the hex value.
        char[] hexDigits = HEX_DIGITS;
        for (int i = 0, j = 0; i < l; i++) {
            out[j++] = hexDigits[(0xF0 & data[i]) >>> 4];
            out[j++] = hexDigits[0x0F & data[i]];
        }
        return out;
    }

}
