package additional_packages.Notepad;

import java.io.*;

import static ui.layout.BorderFunc.drawTripleBorder;
import static ui.layout.ColorFunc.displayContent;
import static ui.layout.ThemesFunc.displayErrorAscii;

public class Notepad {
    private String title;
    private String content;

    public Notepad(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public String getTitle(){
        return title;
    }

    public String getContent(){
        return content;
    }

    public void setTitle(String title){
        this.title = title;
    }

    public void setContent(String content){
        this.content = content;
    }

    public void saveToFile(){
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(title + ".txt"))) {
            writer.write(content);
        }
        catch(Exception e){
            System.out.println("\n");
            drawTripleBorder();
            System.out.println("\n");
            displayErrorAscii();
            displayContent("Error, try again", "red", 0);
        }
    }

    public static Notepad readFromFile(String title) {
        StringBuilder content = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(title + ".txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                content.append(line).append("\n");
            }
        } catch (IOException e) {
            displayErrorAscii();
            displayContent("Error, try again", "red", 0);
            return null;
        }
        return new Notepad(title, content.toString());
    }

    public static boolean deleteNoteFile(String title) {
        File file = new File(title + ".txt");
        return file.delete();
    }
}
