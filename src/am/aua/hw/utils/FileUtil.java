package am.aua.hw.utils;

import java.io.*;
import java.util.Arrays;

public class FileUtil
{
    public static void saveStringsToFile(String[] content, String path) throws FileNotFoundException
    {
        PrintWriter writer = new PrintWriter(new FileOutputStream(path), true);
        for(String s : content) {
            writer.println(s);
        }
        writer.close();
    }

    public static String[] loadStringsFromFile(String path) throws FileNotFoundException, IOException
    {
        BufferedReader reader = new BufferedReader(new FileReader(path));
        String[] answer = {};
        String line = null;
        while((line = reader.readLine()) != null) {
            answer = Arrays.copyOf(answer, answer.length + 1);
            answer[answer.length - 1] = line;
        }
        reader.close();
        return answer;
    }
}
