package utils;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PushbackInputStream;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;


public class IOhandler {
    public static final String TEST_FILE = "testfile.txt";
    public static final String LEXER_FILE  = "lexer.txt";
    public static final String PARSER_FILE = "parser.txt";
    public static final String SYMBOL_FILE = "symbol.txt";
    public static final String IR_INIT_FILE = "llvm_ir_init.txt";
    public static final String IR_FILE     = "llvm_ir.txt";
    public static final String MIPS_FILE   = "mips.txt";
    public static final String ERROR_FILE  = "error.txt";

    private static PushbackInputStream source;
    private static HashMap<String, FileOutputStream> outputs = new HashMap<String, FileOutputStream>();

    private IOhandler() {}

    public static void init() throws IOException,FileNotFoundException {
        File testfile = new File(TEST_FILE);
       
        source = new PushbackInputStream(
                new BufferedInputStream(new FileInputStream(testfile), 65536), 16);
    }

    public static int read() throws IOException  { return source.read(); }
    public static void unread(int b) throws IOException  { if (b != -1) source.unread(b); }


    public static void write(String filename, String content) throws IOException {
        FileOutputStream out = outputs.get(filename);
        if (out == null) {
            out = new FileOutputStream(filename);
            outputs.put(filename, out);
        }
        out.write(content.getBytes(StandardCharsets.UTF_8));
    }
    public static void close() throws IOException {
        if (source != null) source.close();
        for (FileOutputStream out : outputs.values()) {
            out.close();
        }
    }
}