import java.util.*;
import java.io.*;

class OPtab {
        String opcode , mnclass, mnemonic ;
        OPtab (){opcode = ""; mnclass = ""; mnemonic = "";}
        OPtab (String a, String b, String c){opcode = a; mnclass = b; mnemonic = c;}
};
class SymTable {
        int no;
        String name, address;
        SymTable (){no = -1; name = ""; address = "";}
        SymTable (int a, String b, String c){no = a; name = b; address = c;}
};
class LitTable {
        int no;
        String name, address;
        LitTable (){no = -1; name = ""; address = "";}
        LitTable (int a, String b, String c){no = a; name = b; address = c;}
};
class PoolTable {
        int no, litNo;
        PoolTable (){no = -1; litNo = -1;}
        PoolTable (int a, int b){no = a; litNo = b;}
};

class main {
    static OPtab optab[] = {
		new OPtab("STOP",   "IS", "00"),
		new OPtab("ADD",    "IS", "01"),
		new OPtab("SUB",    "IS", "02"),
		new OPtab("MULT",   "IS", "03"),
		new OPtab("MOVER",  "IS", "04"),
		new OPtab("MOVEM",  "IS", "05"),
		new OPtab("COMP",   "IS", "06"),
		new OPtab("BC",     "IS", "07"),
		new OPtab("DIV",    "IS", "08"),
		new OPtab("READ",   "IS", "09"),
		new OPtab("PRINT",  "IS", "10"),
		new OPtab("START",  "AD", "01"),
		new OPtab("END",    "AD", "02"),
		new OPtab("ORIGIN", "AD", "03"),
		new OPtab("EQU",    "AD", "04"),
		new OPtab("LTORG",  "AD", "05"),
		new OPtab("DS",     "DL", "01"),
		new OPtab("DC",     "DL", "02")
    };
    static int MAX = 10;
    static SymTable ST[] = new SymTable[MAX];
    static LitTable LT[] = new LitTable[MAX];
    static PoolTable PT[] = new PoolTable[MAX];

    static int getOP(String s){
        for (int i = 0; i < 18; i++)
            if (optab[i].opcode.equals(s)) return i;
        return -1;
    }
    static int getRegID(String s){
        if(s.equals("AREG")) // || s.equals("A") )
            return 1;
        if(s.equals("BREG")) // || s.equals("B") )
            return 2;
        if(s.equals("CREG")) // || s.equals("C") )
            return 3;
		return (-1);
    }
    static int getConditionCode(String s){
        if(s.equals("LT"))
            return 1;
        if(s.equals("LE"))
            return 2;
        if(s.equals("EQ"))
            return 3;
        if(s.equals("GT"))
            return 4;
        if(s.equals("GE"))
            return 5;
        if(s.equals("ANY"))
            return 6;
		return (-1);
    }

    static boolean presentST(String s){
        for (int i = 0; i < MAX; ++i)
            if (ST[i].name.equals(s))return true;
        return false;
    }
    static int getSymID(String s){
        for (int i = 0; i < MAX; ++i)
            if (ST[i].name.equals(s))return i;
        return -1;
    }

    static boolean presentLT(String s){
        for (int i = 0; i < MAX; ++i)
            if (LT[i].name.equals(s))return true;
        return false;
    }
    static int getLitID(String s){
        for (int i = 0; i < MAX; ++i)
            if (LT[i].name.equals(s))return i;
        return -1;
    }


    public static void main(String[] args) {
        for (int i = 0; i < MAX; ++i){
            ST[i] = new SymTable();
            LT[i] = new LitTable();
            PT[i] = new PoolTable();
        }
        try {
            File Obj = new File("input.txt");
            Scanner Reader = new Scanner(Obj);

            FileWriter ic = new FileWriter("ic.txt");
            FileWriter st = new FileWriter("st.txt");
            FileWriter lt = new FileWriter("lt.txt");

            String label, opcode, operand1, operand2;
            int scnt = 0, lcnt = 0, nlcnt = 0, pcnt = 0, LC = 0;

            
            System.out.println("\nLABEL\tOPCODE\tOP1\tOP2\tLC\tINTERMEDIATE CODE\n");


            while (Reader.hasNext()) {
            	
                label = Reader.next();
                opcode = Reader.next();
                operand1 = Reader.next();
                operand2 = Reader.next();
                System.out.println("----------------------------------------------------------------");
                // System.out.println(label + " " + opcode +  " " + operand1 + " " + operand2);
                int id; String IC, lc = "---";

                id = getOP(opcode);
                IC = "(" + optab[id].mnclass + "," + optab[id].mnemonic + ") ";

                if (!label.equals("NAN")){
                    if (presentST(label)) ST[getSymID(label)].address = Integer.toString(LC);
                    else {
                        ST[scnt].no = scnt;
                        ST[scnt].name = label;
                        ST[scnt].address = Integer.toString(LC);
                        scnt++;
                    }
                }
                if (opcode.equals("START")) {
                    lc = "---";
                    if (!operand1.equals("NAN")) {
                        LC = Integer.parseInt(operand1);
                        IC += "(C," + operand1 + ") --";
                    }
                }
                if (opcode.equals("EQU")) {
                    lc = "---";

                    if (!presentST(label)) {
                        ST[scnt].no = scnt;
                        ST[scnt].name = label;
                        ST[scnt].address = Integer.toString(LC);
                        scnt++;
                    }
                    // ST[getSymID(label)].address = ST[getSymID(operand1)].address;
                    String token1 = "", token2 = ""; char op = '+'; boolean flag = true;
                    for (int i = 0; i < operand1.length(); ++i){
                        char tempChar = operand1.charAt(i);
                        if (Character.compare(tempChar, '+') == 0 || Character.compare(tempChar, '-') == 0) {
                            op = tempChar; flag = false;
                            continue;
                        }
                        if (flag) token1 += tempChar;
                        else token2 += tempChar;
                    }
                    if (!presentST(token1)) {
                        ST[scnt].no = scnt;
                        ST[scnt].name = token1;
                        ST[scnt].address = Integer.toString(LC);
                        scnt++;
                    }
                    if (token2.length() == 0)
                        ST[getSymID(label)].address = ST[getSymID(token1)].address;
                    else if (Character.compare(op, '+') == 0)
                        ST[getSymID(label)].address = Integer.toString(Integer.parseInt(ST[getSymID(token1)].address) + Integer.parseInt(token2));
                    else
                        ST[getSymID(label)].address = Integer.toString(Integer.parseInt(ST[getSymID(token1)].address) - Integer.parseInt(token2));

                    if (ST[getSymID(label)].address.length() != 0) IC += "(C," + ST[getSymID(label)].address + ") ";
                    IC += " --";
                }
                if (opcode.equals("ORIGIN")){
                    String token1 = "", token2 = ""; char op = '+'; boolean flag = true;
                    for (int i = 0; i < operand1.length(); ++i){
                        char tempChar = operand1.charAt(i);
                        if (Character.compare(tempChar, '+') == 0 || Character.compare(tempChar, '-') == 0) {
                            op = tempChar; flag = false;
                            continue;
                        }
                        if (flag) token1 += tempChar;
                        else token2 += tempChar;
                    }
                    lc = "---";
                    if (!presentST(token1)) {
                        ST[scnt].no = scnt;
                        ST[scnt].name = token1;
                        ST[scnt].address = Integer.toString(LC);
                        scnt++;
                    }
                    if (token2.length() == 0) {
                        LC = Integer.parseInt(ST[getSymID(token1)].address);
                        IC += "(S,0" + Integer.toString(ST[getSymID(token1)].no) + ") --";
                    }
                    else if (Character.compare(op, '+') == 0){
                        LC = Integer.parseInt(ST[getSymID(token1)].address) + Integer.parseInt(token2);
                        IC += "(S,0" + Integer.toString(ST[getSymID(token1)].no) + ")+" + token2 + " --";
                    }else {
                        LC = Integer.parseInt(ST[getSymID(token1)].address) - Integer.parseInt(token2);
        				IC += "(S,0" + Integer.toString(ST[getSymID(token1)].no) + ")-" + token2 + " --";
                    }
                }
                if (opcode.equals("LTORG")){
                	
                    System.out.print(label + "\t" + opcode + "\t" + operand1 + "\t" + operand2 + "\t");
                    for (int i = lcnt - nlcnt; i < lcnt; ++i){
                        lc = Integer.toString(LC);
                        IC = "(DL,02) (C,0";
                        for (int j = 2; j < LT[i].name.length()-1; ++j)
                            IC += LT[i].name.charAt(j);
                        IC += ")   --";
                        LT[i].address = Integer.toString(LC);
                        LC++;
                        if(i < lcnt - 1) System.out.print(lc + "\t" + IC + "\n\t\t\t\t");
                        else System.out.println(lc + "\t" + IC);
                        ic.write(lc + "\t" + IC + "\n");
                    }

                    PT[pcnt].litNo = LT[lcnt - nlcnt].no; PT[pcnt].no = ++pcnt;
                    nlcnt = 0;
                    continue;
                }
                if (opcode.equals("END")){
                    lc = "---";
                    IC += " --     --";
        			System.out.println(label + "\t" + opcode + "\t" + operand1 + "\t" + operand2 + "\t" + lc + "\t" + IC);
                    ic.write(lc + "\t" + IC + "\n");

                    if(nlcnt > 0){
                        for (int i = lcnt - nlcnt; i < lcnt; ++i){
                            lc = Integer.toString(LC);
                            IC = "(DL,02) (C,";
                            for (int j = 2; j < LT[i].name.length()-1; ++j)
                                IC += LT[i].name.charAt(j);
                            IC += ")    --";
                            LT[i].address = Integer.toString(LC);
                            LC++;
                            System.out.println("\t\t\t\t" + lc + "\t" + IC);
                            ic.write(lc + "\t" + IC + "\n");


                        }
                        PT[pcnt].litNo = LT[lcnt - nlcnt].no; PT[pcnt].no = ++pcnt;
                    }
                    break;
                }
                if (opcode.equals("DC") || opcode.equals("DS")){
                    lc = Integer.toString(LC);
                    if (opcode.equals("DS")) LC += Integer.parseInt(operand1);
                    else LC++;
                    IC += "(C," + operand1 + ")    --";
                }
                if (!opcode.equals("START") && !opcode.equals("END") && !opcode.equals("ORIGIN") && !opcode.equals("EQU") && !opcode.equals("LTORG") && !opcode.equals("DC") && !opcode.equals("DS")) {
                    if(operand2.equals("NAN")) {
                        if(operand1.equals("NAN")) {lc = Integer.toString(LC++);IC += " NAN     NAN";}
                        else {
                            if(presentST(operand1)) IC += "(S,0" + Integer.toString(ST[getSymID(operand1)].no) + ")";
                            else {
                                ST[scnt].no = scnt;
                                ST[scnt].name = operand1;
                                scnt++;
                                IC += "(S,0" + Integer.toString(ST[getSymID(operand1)].no) + ")";
                            }
                            lc = Integer.toString(LC++);
                        }
                    }
                    else {
                        if(opcode.equals("BC")) IC += "(CC,0" + Integer.toString(getConditionCode(operand1)) + ") ";
                        else IC += "(RG,0" + Integer.toString(getRegID(operand1)) + ") ";

                        if( Character.compare(operand2.charAt(0), '=') == 0) {
        					LT[lcnt].no = lcnt;
        					LT[lcnt].name = operand2;
        					lcnt++; nlcnt++;
        					IC += "(L,0" + Integer.toString(LT[lcnt-1].no) + ")";
                        }
                        else {
                            if(presentST(operand2)) IC += "(S,0" + Integer.toString(ST[getSymID(operand2)].no) + ")";
                            else {
                                ST[scnt].no = scnt;
                                ST[scnt].name = operand2;
                                scnt++;
                                IC += "(S,0" + Integer.toString(ST[getSymID(operand2)].no) + ")";
                            }
                        }
                        lc = Integer.toString(LC++);
                    }
                }
                System.out.println(label + "\t" + opcode + "\t" + operand1 + "\t" + operand2 + "\t" + lc + "\t" + IC);
                ic.write(lc + "\t" + IC + "\n");
            }
            System.out.println("\n");
            System.out.println("SYMBOL TABLE:");
            System.out.println(" NO.\tSYMBOL\tADDRESS");
            for (int i = 0; i < scnt; ++i) {
            	System.out.println("------------------------------");
                System.out.println("  " + ST[i].no + "\t " + ST[i].name + "\t  " + ST[i].address);
                st.write(ST[i].no + "\t " + ST[i].name + "\t  " + ST[i].address + "\n");
            }
            System.out.println("\n");
            System.out.println("LITERAL TABLE: ");
            System.out.println(" NO.\tLITERAL\tADDRESS");
            for( int i = 0; i < lcnt; ++i) {
            	System.out.println("------------------------------");
                System.out.println("  " + LT[i].no + "\t " + LT[i].name + "\t  " + LT[i].address);
                lt.write(LT[i].no + "\t " + LT[i].name + "\t  " + LT[i].address + "\n");
            }
        	System.out.println("\n");
        	System.out.println("POOL TABLE: ");
        	System.out.println(" NO.\tLITERAL_NO.");
        	for (int i = 0; i < pcnt; ++i)
        		
                System.out.println("  " + PT[i].no + "\t   " + PT[i].litNo);
        	    

            Reader.close();
            ic.close();
            st.close();
            lt.close();
        }
        catch (IOException e) {
            System.out.println("An error has occurred.");
            e.printStackTrace();
        }
    }
};
