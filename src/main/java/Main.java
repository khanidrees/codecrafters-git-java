import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;

public class Main {
  public static void main(String[] args){
    // You can use print statements as follows for debugging, they'll be visible when running tests.
    System.out.println("Logs from your program will appear here!");

    // Uncomment this block to pass the first stage

     final String command = args[0];

     switch (command) {
       case "init" -> {
         final File root = new File(".git");
         new File(root, "objects").mkdirs();
         new File(root, "refs").mkdirs();
         final File head = new File(root, "HEAD");

         try {
           head.createNewFile();
           Files.write(head.toPath(), "ref: refs/heads/main\n".getBytes());
           System.out.println("Initialized git directory");
         } catch (IOException e) {
           throw new RuntimeException(e);
         }
       }
       case "cat-file" -> {
         String path = args[2];
         File file = new File(".git/objects/"+path.substring(0,2)+"/"+path.substring(2,path.length()));
         try {
           FileReader reader = new FileReader(file);
           String content="";
           int c;
           while ((c = reader.read()) != -1) {
             char ch = (char) c;
             content+=ch;

           }
//           System.out.print(content);
           content = content.split("\0")[1];
           System.out.print(content);
//           System.out.println("Initialized git directory");
         } catch (IOException e) {
           throw new RuntimeException(e);
         }

       }
       default -> System.out.println("Unknown command: " + command);
     }
  }
}
