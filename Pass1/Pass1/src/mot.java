import java.util.*;
public class mot {
	HashMap<String,Integer> AD,IS,RG,DL,CC;
	
	public mot() {
		AD=new HashMap<String,Integer>();
		IS=new HashMap<String,Integer>();
		RG=new HashMap<String,Integer>();
		DL=new HashMap<String,Integer>();
		CC=new HashMap<String,Integer>();
		
		AD.put("START", 1);
		AD.put("END", 2);
		AD.put("ORIGIN", 3);
		AD.put("EQU", 4);
		AD.put("LTORG", 5);
		
		IS.put("STOP", 0);
		IS.put("ADD", 1);
		IS.put("SUB", 2);
		IS.put("MULT", 3);
		IS.put("MOVER", 4);
		IS.put("MOVEM", 5);
		IS.put("COMP", 6);
		IS.put("BC", 7);
		IS.put("DIV", 8);
		IS.put("READ", 9);
		
		DL.put("DS", 1);
		DL.put("DC", 2);
		
		
		RG.put("AREG", 1);
		RG.put("BREG", 2);
		RG.put("CREG", 3);
		
		CC.put("BQ", 1);
		
	}
	public String getType(String s) {
		s=s.toUpperCase();
		if(AD.containsKey(s))return "AD";
		else if(IS.containsKey(s))return "IS";
		else if(CC.containsKey(s))return "CC";
		else if(RG.containsKey(s))return "RG";
		else if(DL.containsKey(s))return "DL";
		return "";
		
	}
	public int getCode(String s) {
		String t=getType(s);
		if(t=="AD")return AD.get(s);
		else if(t=="IS")return IS.get(s);
		else if(t=="CC")return CC.get(s);
		else if(t=="RG")return RG.get(s);
		else if(t=="DL")return DL.get(s);
		return -1;
		
	}
}
