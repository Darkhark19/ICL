
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
import java.io.File;

public class CodeBlock {
    String[] code;
    int pc, framesize, refsize, labels;
    String[] frame ;
    String[] ref;

    public CodeBlock(){
        pc = 0;
        framesize = 0;
        code = new String[1000];
        frame = new String[1000];
        ref = new String[1000];
        refsize = 0;
    }

    void emit(String opcode) {
        code[pc++] = opcode;
    }

    int newLabel() {
        return labels++;
    }

    void addFrame(int frameId, int ancestroFrameId){
        StringBuilder code = new StringBuilder();
        code.append(".class public frame_" + frameId +"\n");
        code.append(".super java/lang/Object\n");
        if(ancestroFrameId >= 0){
            code.append(".field public sl Lframe_" + ancestroFrameId +";\n");
        }else{
            code.append(".field public sl Ljava/lang/Object;\n");
        }

        frame[framesize++] = code.toString();
    }

    void addBinding(int frameId, int counter,IType type){
        String frameCode = frame[frameId];
        String code = ".field public v" + counter + " " + type.toJasmin() +"\n";
        frameCode += code;
        frame[frameId] = frameCode;
    }

    void addRef(IType type){
        String code = ".class public ref_of_"+type.toStr()+"\n";
        code += ".super java/lang/Object\n";
        code += ".field public v " + type.toJasmin() +"\n";
        code += ".method public	<init>()V\n";
		code += "aload_0\n";
		code += "invokenonvirtual java/lang/Object/<init>()V\n";
		code += "return\n";
		code += ".end method\n";
        ref[refsize++] = code;
    }


    void dump() {
        try{
            for(int i = 0; i < framesize; i++){
                String code = frame[i];
                code+= ".method public <init>()V\n";
                code+= "aload_0\n";
                code+= "invokenonvirtual java/lang/Object/<init>()V\n";
                code+= "return\n";
                code+= ".end method\n";
                File newFile = new File("frame_" + i + ".j");
                Files.write(newFile.toPath(), code.getBytes());
            }
        }catch(Exception e){
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        try{
            for(int i = 0; i < refsize; i++){
                String code = ref[i];
                File newFile = new File("ref_of_" + i + ".j");
                Files.write(newFile.toPath(), code.getBytes());
            }
        }catch(Exception e){
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        try{
            List<String> list = new LinkedList<>();
            FileInputStream o = new FileInputStream("Header.j");
            Scanner sc = new Scanner(o);
            String line = null; 
            while(sc.hasNextLine()){
                line = sc.nextLine();
                list.add(line);
                if(line.contains("; START")){
                    list.add("\n");
                    break;
                }
            }
            for(int i = 0; i < pc; i++){
                list.add(code[i]);
            }

            boolean found = false;
            while(sc.hasNextLine()){
                line = sc.nextLine();
                if(line.contains("; END") || found){
                    if(!found)  list.add("\n");
                    found = true;
                    list.add(line);
                }
            }
            Path out = Paths.get("Header.j");
            Files.write(out, list, Charset.defaultCharset());
            sc.close();
            System.out.println("ICLCOmpiler: Compilation successful");
            try{
                String command = "java -jar jasmin-2.4/jasmin.jar Header.j";
                for(int i = 0; i < framesize; i++){
                    command += " frame_" + i + ".j";
                }
                for(int i = 0; i < refsize; i++){
                    command += " ref_of_" + i + ".j";
                }
                Process p = Runtime.getRuntime().exec(command);
                if(p.waitFor() == 0){
                    System.out.println("Jasmin: Compilation successful");
                    // p = Runtime.getRuntime().exec("java Header");
                    // if(p.waitFor() == 0){
                    //     System.out.println("Header: Execution successful");
                    // }
                    // else{
                    //     System.out.println("Header: Execution failed");
                    // }
                }
                else{
                    System.out.println("Jasmin: Compilation failed");
                }
            }catch(Exception e){
                System.out.println(e.getMessage());
            }
            pc = 0;
            framesize = 0;
            code = new String[100];
            frame = new String[100];
        }catch(IOException e){
            System.out.println(e.getMessage()); 
            
        }
    }
}
