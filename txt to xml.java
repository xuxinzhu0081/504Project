package txttoxml;
 /*
  *txt转换xml 
  */
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
//import java.util.StringTokenizer;

public class TxtToXml {
 private String strTxtFileName;

 private String strXmlFileName;

 public TxtToXml() {
  strTxtFileName = new String();
  strXmlFileName = new String();
 }

 public void createXml(String strTxt, String strXml) {
  strTxtFileName = strTxt;
  strXmlFileName = strXml;
  String strTmp;
  try {
   BufferedReader inTxt = new BufferedReader(new FileReader(
     strTxtFileName));
   BufferedWriter outXml = new BufferedWriter(new FileWriter(
     strXmlFileName));
   outXml.write("<?xml version= \"1.0\" encoding=\"gb2312\"?>");
   outXml.newLine();
   outXml.write("<lists>");
   String strPID;
   String strHeat;
   String strSong;
   while ((strTmp = inTxt.readLine()) != null) {
       String[] strArr = strTmp.split("  ");
       outXml.newLine();
       outXml.write("<ID>");
       outXml.write(strArr[0]);
       outXml.newLine();
       outXml.write("</ID>");
       outXml.newLine();
       outXml.write("<Heat>");
       outXml.write(strArr[1]);
       outXml.newLine();
       outXml.write("</Heat>");
       outXml.newLine();
       outXml.write("<Song>");
       outXml.write(strArr[2]);
       outXml.newLine();
       outXml.write("</Song>");
        //for (int i = 0; i < 8; i++) {
            //System.out.println(strArr[i].trim());
        //}
    //StringTokenizer strToken = new StringTokenizer(strTmp, ",");
    //String arrTmp[];
    //arrTmp = new String[100];
    //for (int i = 0; i < 100; i++)
     //arrTmp[i] = new String("");
   // int index = 0;
            
    //outXml.newLine();
    //outXml.write("    <ID>");
    //while (strToken.hasMoreElements()) {
     //strTmp = (String) strToken.nextElement();
     //strTmp = strTmp.trim();
    // arrTmp[index++] = strTmp;
 
    //}
   
  
    //outXml.newLine();
   // outXml.write("        <name>" + arrTmp[0] + "</name>");
    //outXml.newLine();
    //outXml.write("        <sex>" + arrTmp[1] + "</sex>");
    //outXml.newLine();
    //outXml.write("        <age>" + arrTmp[2] + "</age>");
    //outXml.newLine();
    //outXml.write("    </students>");
   }
   outXml.newLine();
   outXml.write("</lists>");
   outXml.flush();
  } catch (Exception e) {
   e.printStackTrace();
  }
 }

 public static void main(String[] args) {
  String txtName = "top8.txt";
  String xmlName = "top8final.xml";
  TxtToXml thisClass = new TxtToXml();
  thisClass.createXml(txtName, xmlName);
 }
}
