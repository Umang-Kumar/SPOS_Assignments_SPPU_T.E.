import java.io.*;
import java.util.*;
public class main {
	LinkedHashMap<String,Tables> MNT;
	ArrayList<Tables> MDT;
	LinkedHashMap<String, LinkedHashMap<String,String>> ALA;
	LinkedHashMap<String,String> ref;
	LinkedHashMap<String,String> actual_val;
	ArrayList<String> CODE;
	int indx;
	int mdtp_indx;
	int mdtp_ctr;
	public main()
	{
		MNT =new LinkedHashMap<>();
		MDT=new ArrayList<>();
		ALA= new LinkedHashMap<>();
		CODE=new ArrayList<>();
		indx=0;
		mdtp_indx=0;
		mdtp_ctr=0;
	}
	public static void main(String []args )  {
		main mainn=new main();
		try {
			mainn.parseFile();
		}  catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void parseFile() throws Exception{
		BufferedReader input=new BufferedReader(new FileReader("input.txt"));
		BufferedWriter mnt=new BufferedWriter(new FileWriter("MNT.txt"));
		BufferedWriter mdt=new BufferedWriter(new FileWriter("MDT.txt"));
		BufferedWriter ala=new BufferedWriter(new FileWriter("ALA.txt"));
		String line;
		int fl=0;
		while((line=input.readLine())!=null) {
			String parts[]=line.split("\\s+");
			String ress="";
			for(int i=0;i<parts.length;i++) {
				ress=ress+parts[i]+" ";	
			}
			
			if(parts[0].equals("MACRO")) {
				fl=1;
			}
			else if(fl==1) {
				String s=parts[0];
				String indx_str=Integer.toString(indx);
				String mdtp_indx_str=Integer.toString(mdtp_indx);
				Tables table=new Tables(indx,s,mdtp_indx);
				LinkedHashMap<String,String> ala_val=new LinkedHashMap<>();
				String part2=parts[1];
				String[] commParts=part2.split(",");
				for(int i=0;i<commParts.length;i++) {
					ala_val.put(commParts[i], "#"+Integer.toString(i+1));
				}
				ref=ala_val;
				ALA.put(s,ala_val);
				MNT.put(s,table);
				mnt.write(indx_str+" ");
				mnt.write(s+" ");
				mnt.write(mdtp_indx_str+"\n");
				String resss=parts[0]+" ";
				String fe=parts[1];
				String splitt[]=fe.split(",");
				for(int i=0;i<splitt.length;i++) {
					
						resss+=splitt[i]+",";
						
				}
				Tables table2=new Tables(mdtp_indx,resss);
				MDT.add(table2);
				mdt.write(Integer.toString(mdtp_indx)+" ");
				mdt.write(ress+"\n");
				indx++;
				mdtp_indx++;
				fl=0;
			}else if(fl==0 && !parts[0].equals("MEND")) {
				String resss=parts[0]+" ";
				String fe=parts[1];
				String splitt[]=fe.split(",");
				for(int i=0;i<splitt.length;i++) {
					if(ref.containsKey(splitt[i])) {
						String act_val=ref.get(splitt[i]);
						resss+=act_val+",";
					}else {
						resss+=splitt[i]+",";
					}
				}
				Tables table2=new Tables(mdtp_indx,resss);
				MDT.add(table2);
				mdt.write(Integer.toString(mdtp_indx)+" ");
				mdt.write(resss+"\n");
				mdtp_indx++;
			}
			else if(parts[0].equals("MEND")) {
				Tables table2=new Tables(mdtp_indx,"MEND");
				MDT.add(table2);
				mdt.write(Integer.toString(mdtp_indx)+" ");
				mdt.write(ress+"\n");
				mdtp_indx++;
				fl=-1;
			}else if(fl==-1) {
				if(MNT.containsKey(parts[0])) {
					Tables table=MNT.get(parts[0]);
					String fe=parts[1];
					String splitt[]=fe.split(",");
					int ctr=0;
					 actual_val=new LinkedHashMap<>();
					LinkedHashMap<String,String> printt=ALA.get(parts[0]);
						
					for( Map.Entry<String, String> entry1 : printt.entrySet() ){
						  actual_val.put(entry1.getValue(), splitt[ctr]);
						  ctr++;
					}
					int mdtp_start=table.mdtpIndx;
					for(int i=mdtp_start+1;i<MDT.size();i++) {
						String resss="";
						String mdtp_str=MDT.get(i).body;
						String part[]=mdtp_str.split("\\s+");
						if(part[0].equals("MEND")) {
							break;
						}
						resss+=part[0]+" ";
						String []parr=part[1].split(",");
						for(int j=0;j<parr.length;j++) {
							if(actual_val.containsKey(parr[j])) {
								resss+=actual_val.get(parr[j])+",";
							}else {
								resss+=parr[j]+",";
							}
						}
						CODE.add(resss);
					}
				}else {
					CODE.add(ress);
				}
			}
		}
		mnt.close();
		mdt.close();
		
		//Print MNT TABLE
		System.out.println("MNT TABLE");
		System.out.println("No"+" "+"Name"+" MDTP");
		for( Map.Entry<String, Tables> entry1 : MNT.entrySet() ){
			Tables table=entry1.getValue();
			System.out.print(Integer.toString(table.indx)+"  ");
			String arr=table.body;	
			System.out.print(arr+"  ");
			System.out.print(Integer.toString(table.mdtpIndx)+"\n");
		}
		System.out.println();
		//print MDTP TABLE
		System.out.println("MDTP"+" "+"Defination");
		for(int i=0;i<MDT.size();i++) {
			Tables table=MDT.get(i);
			System.out.print(Integer.toString(table.indx)+"    ");
			String arr=table.body;
			
				System.out.print(arr);
			
			System.out.println();
		}
		System.out.println();
		//Print ALA Table
		System.out.println("ALA Table");
		System.out.println("-------");
		for( Map.Entry<String, LinkedHashMap<String,String>> entry : ALA.entrySet() ){
			System.out.println(entry.getKey());
			LinkedHashMap<String,String> printt=entry.getValue();
			
			for( Map.Entry<String, String> entry1 : printt.entrySet() ){
			    System.out.println( actual_val.get(entry1.getValue()) );   
			}
			System.out.println("-------");
		}
		
		//Expanded Code
		System.out.println("---Expanded Code---");
		for(int i=0;i<CODE.size();i++) {
			System.out.println(CODE.get(i));
		}	
	}
}
