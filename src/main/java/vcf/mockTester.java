package vcf;

import java.io.File;
import java.io.IOException;

public class mockTester {
    public static void main(String[] args){
        try {
            new VCFParser().addContact(new File("C:/Users/lovel/Downloads/Albin Skeppstedt NA17B.vcf").toPath() );
            new VCFParser().addContact(new File("C:/Users/lovel/Downloads/Namerson.vcf").toPath());
        }catch (IOException e){
            throw new RuntimeException();
        }
    }
}
