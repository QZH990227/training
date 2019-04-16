import org.apache.commons.io.FileUtils;

import java.io.*;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class Main {

    public static void main(String[] args) {
        try {
            test_apache();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            test_filechannel();
            test_bufferedtoutputstream();
            test_outputstream();
            test_bufferedwriter();
            test_filewriter();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private static void test_filewriter()throws IOException {
        long startTime;
        long endTime;
        File apacheout=new File("apacheout.txt");
        File apachein=new File("apachein.txt");
        FileWriter fileWriter=new FileWriter(apacheout);
        FileReader fileReader=new FileReader(apachein);
        char []ch=new char[1024];
        int length;
        startTime=System.currentTimeMillis();
        length=fileReader.read(ch);
        while(length!=-1){
            fileWriter.write(ch,0,length);
            length=fileReader.read(ch);
        }
        endTime=System.currentTimeMillis();
        System.out.println(endTime-startTime);
        System.out.println("........filewriter");
    }

    private static void test_bufferedwriter() throws IOException{
        long startTime;
        long endTime;
        File apachein=new File("apachein.txt");
        File apacheout=new File("apacheout.txt");
        BufferedWriter bufferedWriter=new BufferedWriter(new FileWriter(apacheout));
        BufferedReader bufferedReader= new BufferedReader(new FileReader(apachein));
        startTime=System.currentTimeMillis();
        String string=bufferedReader.readLine();
        while (string!=null){
            bufferedWriter.write(string,0,string.length());
            string=bufferedReader.readLine();
        }
        endTime=System.currentTimeMillis();
        System.out.println(endTime-startTime);
        System.out.println("............bufferedwriter");
    }

    private static void test_outputstream() throws IOException{
        long startTime;
        long endTime;
        File apachein=new File("apachein.txt");
        File apacheout=new File("apacheout.txt");
        int length;
        FileInputStream fileInputStream1=new FileInputStream(apachein);
        FileOutputStream fileOutputStream1= new FileOutputStream(apacheout);
        length=0;
        startTime=System.currentTimeMillis();
        byte[] read=new byte[1024];
        while((length=fileInputStream1.read(read))!=-1){
            fileOutputStream1.write(read,0,length);
        }
        endTime=System.currentTimeMillis();
        System.out.println(endTime-startTime);
        System.out.println("...........outputstream");
    }

    private static void test_bufferedtoutputstream() throws IOException {
        long startTime;
        long endTime;
        File apachein=new File("apachein.txt");
        File apacheout=new File("apacheout.txt");
        BufferedInputStream bufferedInputStream=new BufferedInputStream(new FileInputStream(apachein));
        BufferedOutputStream bufferedOutputStream=new BufferedOutputStream(new FileOutputStream(apacheout));
        byte[] read=new byte[1024];
        int length=0;
        startTime=System.currentTimeMillis();
        while ((length=bufferedInputStream.read(read))!=-1){
            bufferedOutputStream.write(read,0,length);
        }
        endTime=System.currentTimeMillis();
        System.out.println(endTime-startTime);
        System.out.println("..........bufferedoutputstream");
        //使用bufferedoutputstream
    }

    private static void test_filechannel() throws IOException{
        long startTime;
        long endTime;
        File apachein=new File("apachein.txt");
        File apacheout=new File("apacheout.txt");
        FileInputStream fileInputStream=new FileInputStream(apachein);
        FileOutputStream fileOutputStream=new FileOutputStream(apacheout);
        FileChannel inchannel=fileInputStream.getChannel();
        FileChannel outchannel= fileOutputStream.getChannel();
        startTime=System.currentTimeMillis();
//        FileInputStream fileInputStream=new FileInputStream(apachein);
//        FileOutputStream fileOutputStream=new FileOutputStream(apacheout);
//        FileChannel outchannel= fileOutputStream.getChannel();
//        startTime=System.currentTimeMillis();
        inchannel.transferTo(0,inchannel.size(),outchannel);
        endTime=System.currentTimeMillis();
        System.out.println(endTime-startTime);
        System.out.println(".......filechannel");
        //使用file channel复制文件
    }

    private static void test_apache()throws  IOException{
        File apachein=new File("apachein.txt");
        File apacheout=new File("apacheout.txt");
        if (!apachein.exists()){
            try {
                System.out.println(apachein.createNewFile());
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                System.out.println(apacheout.createNewFile());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        System.out.println(apachein.getAbsolutePath());
        long startTime = System.currentTimeMillis();
        String str=FileUtils.readFileToString(apachein,"UTF-8");
        //FileUtils.writeStringToFile(file2,"你好 ","UTF-8");
        long endTime = System.currentTimeMillis();
        System.out.println(endTime-startTime);
        startTime=System.currentTimeMillis();
        FileUtils.writeStringToFile(apacheout,str,"UTF-8");
        endTime=System.currentTimeMillis();
        System.out.println(endTime-startTime);
        System.out.println("......FileUtils");
        //使用apacheio复制文件
//        FileWriter writer=new FileWriter(apacheout);
//        FileReader reader=new FileReader(apacheout);
//        startTime=System.currentTimeMillis();
//        writer.write(str);
//        endTime=System.currentTimeMillis();
//        System.out.println(endTime-startTime);
//        char []ch=new char[1024];
//        startTime=System.currentTimeMillis();
//        length=fileReader.read(ch);
//
//        endTime=System.currentTimeMillis();
//        System.out.println(endTime-startTime);
//        System.out.println("........filewriter");


    }
}
