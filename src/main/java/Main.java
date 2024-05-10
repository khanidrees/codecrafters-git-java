import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class Main {
  public static void main(String[] args){
    // You can use print statements as follows for debugging, they'll be visible when running tests.


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

           String content="";
           // Decompress the contents using Zlib
           Path blobContent = Files.createTempFile("blobContent","");
           ZlibUtil.decompressFile(file, blobContent.toFile());
           // Extract the actual "content" from the decompressed data
           String rawBlobData = Files.readString(blobContent);
           if (!rawBlobData.startsWith("blob")) {
             System.err.println("Not a valid blob: " + rawBlobData);
             System.exit(1);
           }
           int nullByte = rawBlobData.indexOf('\0');
           int blobContentLength = Integer.parseInt(rawBlobData.substring(5, nullByte));
           System.out.print(rawBlobData.substring(nullByte+1));
         } catch (IOException e) {
           throw new RuntimeException(e);
         }

       }
       default -> System.out.println("Unknown command: " + command);
     }
  }
}
