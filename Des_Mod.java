import java.util.*;
class DesUtil
{
    String hex_bin(String pt_hex)
    {
        HashMap<Character,String> conv=new HashMap<Character,String>();
        conv.put('0',"0000");
        conv.put('1',"0001");
        conv.put('2',"0010");
        conv.put('3',"0011");
        conv.put('4',"0100");
        conv.put('5',"0101");
        conv.put('6',"0110");
        conv.put('7',"0111");
        conv.put('8',"1000");
        conv.put('9',"1001");
        conv.put('A',"1010");
        conv.put('B',"1011");
        conv.put('C',"1100");
        conv.put('D',"1101");
        conv.put('E',"1110");
        conv.put('F',"1111");
        String bin="";
        int i;
        for(i=0;i<pt_hex.length();i++)
        {
            bin+=conv.get((pt_hex.charAt(i)));
        }
        return bin;
    }
    String bin_hex(String pt_bin)
    {
        HashMap<String,Character> conv=new HashMap<String,Character>();
        conv.put("0000",'0');
        conv.put("0001",'1');
        conv.put("0010",'2');
        conv.put("0011",'3');
        conv.put("0100",'4');
        conv.put("0101",'5');
        conv.put("0110",'6');
        conv.put("0111",'7');
        conv.put("1000",'8');
        conv.put("1001",'9');
        conv.put("1010",'A');
        conv.put("1011",'B');
        conv.put("1100",'C');
        conv.put("1101",'D');
        conv.put("1110",'E');
        conv.put("1111",'F');
        String pt_hex="",four_bits="";
        int i,j,k=0;
        for(i=0;i<pt_bin.length()/4;i++)
        {
            four_bits="";
            for(j=k;j<k+4;j++)
            {
                four_bits+=pt_bin.charAt(j);
            }
            pt_hex+=conv.get(four_bits);
            k+=4;
        }
        return pt_hex;
    }
    String initPermut(String inp_str)
    {
        int i;
        int initPos[]={
        57,49,41,33,25,17,9,1,
        59,51,43,35,27,19,11,3,
        61,53,45,37,29,21,13,5,
        63,55,47,39,31,23,15,7,
        56,48,40,32,24,16,8,0,
        58,50,42,34,26,18,10,2,
        60,52,44,36,28,20,12,4,
        62,54,46,38,30,22,14,6 
        };
        String init_per="";
        for(i=0;i<64;i++)
        {
            init_per+=inp_str.charAt(initPos[i]);
        }
        return init_per;
    }
    String exp_P_Box(String inp_str)
    {
        //                              EXPANDS 32 BITS TO 48 BITS
        int i;
        String e_str="";
        int pbox[]=
        {
            32,1,2,3,4,5,4,5,6,7,8,9,
            8,9,10,11,12,13,12,13,14,15,16,17,
            16,17,18,19,20,21,20,21,22,23,24,25,
            24,25,26,27,28,29,28,29,30,31,32,1
        };
        for(i=0;i<48;i++)
        {
            e_str+=inp_str.charAt(pbox[i]-1);
        }
        return e_str;
    }
    String comp_P_Box(String inp_str)
    {
        //          COMPRESS 56 BITS TO 48 BITS
        String c_str="";
        int i;
        int comp_p_b[]=
        {
            14,17,11,24, 1,5,
            3,28,15, 6,21,10,
            23,19,12, 4,26,8,
            16, 7,27,20,13,2,
            41,52,31,37,47,55,
            30,40,51,45,33,48,
            44,49,39,56,34,53,
            46,42,50,36,29,32
        };
        for(i=0;i<48;i++)
        {
            c_str+=inp_str.charAt(comp_p_b[i]-1);
        }
        return c_str;
    }
    String shiftLeft(String inp, int val)
    {
        String shifted_str="";
        shifted_str=inp.substring(val);
        shifted_str+=inp.substring(0,val);
        return shifted_str;        
    }
    HashMap<Integer,String> keyGenerator(String inp_key)
    {
        HashMap<Integer,String> keyVals= new HashMap<Integer, String>();
        int i,j;
        String inp_key_bin=hex_bin(inp_key);
        int parity_drop[]=
        {
            57,49,41,33,25,17,9,
            1,58,50,42,34,26,18,
            10,2,59,51,43,35,27,
            19,11,3,60,52,44,36,
            63,55,47,39,31,23,15,
            7,62,54,46,38,30,22,
            14,6,61,53,45,37,29,
            21,13,5,28,20,12,4
        };
        String per_str="",c="",d="";
        for(i=0;i<56;i++)
        {
            per_str+=inp_key_bin.charAt(parity_drop[i]-1);
            if(i<28)
            {
                c+=inp_key_bin.charAt(parity_drop[i]-1);
            }
            else
            {
                d+=inp_key_bin.charAt(parity_drop[i]-1);
            }
        }
        //System.out.println("C:"+c+"Length:"+c.length()+"D:"+d+"Length:"+d.length());
        int shifts[]=
        {
            1,1,2,2,2,2,2,2,1,2,2,2,2,2,2,1
        };
        String keys[]=new String[16];
        System.out.println("-------------------------------------------------------------");
        System.out.println("All 16 generated keys:");
        for(i=0;i<16;i++)
        {
            c=shiftLeft(c,shifts[i]);
            d=shiftLeft(d,shifts[i]);
            keys[i]=c+d;
            //System.out.println(i+":   "+keys[i]+"  Length: "+keys[i].length());
            keyVals.put(i+1,comp_P_Box(keys[i]));
            System.out.println((i+1)+":   "+keyVals.get(i+1));
        }
        return keyVals;
    }
    String s_Box(String inp_str)
    {
        //                              COMPRESS 48 BITS TO 32 BITS
        int i,j;
        int sbox[][][]=
		{
			{
				{14,4,13,1,2,15,11,8,3,10,6,12,5,9,0,7},
				{0,15,7,4,14,2,13,1,10,6,12,11,9,5,3,8},
				{4,1,14,8,13,6,2,11,15,12,9,7,3,10,5,0},
				{15,12,8,2,4,9,1,7,5,11,3,14,10,0,6,13}
			},
			{
				{15,1,8,14,6,11,3,4,9,7,2,13,12,0,5,10},
				{3,13,4,7,15,2,8,14,12,0,1,10,6,9,11,5},
				{0,14,7,11,10,4,13,1,5,8,12,6,9,3,2,15},
				{13,8,10,1,3,15,4,2,11,6,7,12,0,5,14,9}
			},
			{
				{10,0,9,14,6,3,15,5,1,13,12,7,11,4,2,8},
				{13,7,0,9,3,4,6,10,2,8,5,14,12,11,15,1},
				{13,6,4,9,8,15,3,0,11,1,2,12,5,10,14,7},
				{1,10,13,0,6,9,8,7,4,15,14,3,11,5,2,12}
			},
			{
				{7,13,14,3,0,6,9,10,1,2,8,5,11,12,4,15},
				{13,8,11,5,6,15,0,3,4,7,2,12,1,10,14,9},
				{10,6,9,0,12,11,7,13,15,1,3,14,5,2,8,4},
				{3,15,0,6,10,1,13,8,9,4,5,11,12,7,2,14}
			},
			{
				{2,12,4,1,7,10,11,6,8,5,3,15,13,0,14,9},
				{14,11,2,12,4,7,13,1,5,0,15,10,3,9,8,6},
				{4,2,1,11,10,13,7,8,15,9,12,5,6,3,0,14},
				{11,8,12,7,1,14,2,13,6,15,0,9,10,4,5,3}
			},
			{
				{12,1,10,15,9,2,6,8,0,13,3,4,14,7,5,11},
				{10,15,4,2,7,12,9,5,6,1,13,14,0,11,3,8},
				{9,14,15,5,2,8,12,3,7,0,4,10,1,13,11,6},
				{4,3,2,12,9,5,15,10,11,14,1,7,6,0,8,13}
			},
			{
				{4,11,2,14,15,0,8,13,3,12,9,7,5,10,6,1},
				{13,0,11,7,4,9,1,10,14,3,5,12,2,15,8,6},
				{1,4,11,13,12,3,7,14,10,15,6,8,0,5,9,2},
				{6,11,13,8,1,4,10,7,9,5,0,15,14,2,3,12}
			},
			{
				{13,2,8,4,6,15,11,1,10,9,3,14,5,0,12,7},
				{1,15,13,8,10,3,7,4,12,5,6,11,0,14,9,2},
				{7,11,4,1,9,12,14,2,0,6,10,13,15,3,5,8},
				{2,1,14,7,4,10,8,13,15,12,9,0,3,5,6,11}	
			}
		};	
		String blks[]=new String[8];
		for(i=0;i<8;i++)
		{
			blks[i]="";
		}
		int flag=0,k=0;
		for(i=0;i<48;i++)
		{
			blks[k]+=inp_str.charAt(i);
			flag++;		
			if(flag==6)
			{		
				flag=0;
				k++;
			}
		}
		String fir_las="",bet="",op_of_sbox="",fin_op="",compress_str="";
		int ind_fir_las,ind_mid,num,dif;
		for(j=0;j<8;j++)
		{
			fir_las="";
			bet="";
			op_of_sbox="";
			fin_op="";
			for(i=0;i<6;i++)
			{
				if(i==0 || i==5)
				{
					fir_las+=blks[j].charAt(i);
				}
				else
				{
					bet+=blks[j].charAt(i);	
				}
			}
			//System.out.println(blks[j]+" "+"FL:"+fir_las+"MID:"+bet);
			ind_fir_las=Integer.parseInt(fir_las,2);
			ind_mid=Integer.parseInt(bet,2);
			//System.out.println("FL:"+ind_fir_las+"MID:"+ind_mid);
			num=sbox[j][ind_fir_las][ind_mid];
			//System.out.print(num+":");
			op_of_sbox=Integer.toBinaryString(num);
			if(op_of_sbox.length()<4)
			{
				dif=4-op_of_sbox.length();
				for(i=0;i<dif;i++)
				{
					fin_op+="0";
				}
				fin_op+=op_of_sbox;
				compress_str+=fin_op;
			//	System.out.println(fin_op);
			}
			else
			{
				compress_str+=op_of_sbox;	
				//System.out.println(op_of_sbox);
			}
		}
        return compress_str;
    }
    String straight_P_Box(String inp_str)
    {
        int i;
        String p_str="";
        int straight_pb[]=
        {
            16,7,20,21,
            29,12,28,17,
            1,15,23,26,
            5,18,31,10,
            2,8,24,14,
            32,27,3,9,
            19,13,30,6,
            22,11,4,25
        };
        for(i=0;i<32;i++)
        {
            p_str+=inp_str.charAt(straight_pb[i]-1);
        }
        return p_str;
    }
    String final_Permut(String inp_str)
    {
        int i;
        String fin_per="";
        int fin_p[]=
        {
            40,8,48,16,56,24,64,32,
            39,7,47,15,55,23,63,31,
            38,6,46,14,54,22,62,30,
            37,5,45,13,53,21,61,29,
            36,4,44,12,52,20,60,28,
            35,3,43,11,51,19,59,27,
            34,2,42,10,50,18,58,26,
            33,1,41,9,49,17,57,25  
        };
        for(i=0;i<64;i++)
        {
            fin_per+=inp_str.charAt(fin_p[i]-1);
        }
        return fin_per;
    }
}
//-----------------------------------------------------------------------------------
class Des_Mod
{
	public static void main(String args[])
	{
        Scanner sc=new Scanner(System.in);
        DesUtil du=new DesUtil();
        int i,j,z;
        HashMap <Integer,String> keys=new HashMap <Integer,String>();
        String plain_text,p_box_op,s_box_op,init_per_op,left_half="",right_half="",key,x_or_op="",fin_rn="",fin="";
        String plain_text_bin;
        System.out.println("Enter plain text");
        plain_text=sc.nextLine();
        plain_text_bin=du.hex_bin(plain_text);
        System.out.println("Enter key in hexadecimal");
        key=sc.nextLine();
        keys=du.keyGenerator(key);
        init_per_op=du.initPermut(plain_text_bin);
        System.out.println("---------------------------------------------------------------");
        System.out.println("INITIAL-PERMUTATION-OUTPUT:"+init_per_op);
        left_half=init_per_op.substring(0,32);
        right_half=init_per_op.substring(32,64);
        //System.out.println("LH:"+left_half+" RH:"+right_half);

        for(z=0;z<16;z++)
        {
            x_or_op="";
            fin="";

            p_box_op=du.exp_P_Box(right_half);
            //System.out.println("Expansion-P-Box:"+p_box_op);
            for(i=0;i<p_box_op.length();i++)
            {
                x_or_op+=p_box_op.charAt(i)^keys.get(z+1).charAt(i);
            }
            //System.out.println(keys.get(z+1)+" + "+p_box_op+"="+x_or_op);
            s_box_op=du.s_Box(x_or_op);
            //System.out.println("After going from s-box:"+s_box_op);
            fin_rn=du.straight_P_Box(s_box_op);
            //System.out.println("After permutaion:"+fin_rn+" Length:"+fin_rn.length());
            for(i=0;i<fin_rn.length();i++)
            {
                fin+=left_half.charAt(i)^fin_rn.charAt(i);
            }
            //System.out.println("R1:"+left_half+"^"+fin_rn+"="+fin);
            left_half=right_half;
            right_half=fin;
        } 
        System.out.println("\n---------------------------------------------------------------");
        System.out.println("Left Half:"+left_half+"     Right Half:"+right_half);   
        System.out.println("\n---------------------------------------------------------------");
        String rev_str=right_half+left_half;
        String cipher=du.final_Permut(rev_str);
        System.out.println("Cipher Text in BINARY: "+cipher);
        System.out.println("Cipher Text in HEX: "+du.bin_hex(cipher));
        System.out.println("---------------------------------------------------------------");

        String pt_init;
        pt_init=du.initPermut(cipher);
        String pt_lh,pt_rh;
        pt_lh=pt_init.substring(0,32);
        pt_rh=pt_init.substring(32,64);
        p_box_op="";
        s_box_op="";
        for(z=15;z>=0;z--)
        {
            x_or_op="";
            fin="";
            p_box_op=du.exp_P_Box(pt_rh);
            //System.out.println("Expansion-P-Box:"+p_box_op);
            for(i=0;i<p_box_op.length();i++)
            {
                x_or_op+=p_box_op.charAt(i)^keys.get(z+1).charAt(i);
            }
            //System.out.println(keys.get(z+1)+" + "+p_box_op+"="+x_or_op);
            s_box_op=du.s_Box(x_or_op);
            //System.out.println("After going from s-box:"+s_box_op);
            fin_rn=du.straight_P_Box(s_box_op);
            //System.out.println("After permutaion:"+fin_rn+" Length:"+fin_rn.length());
            for(i=0;i<fin_rn.length();i++)
            {
                fin+=pt_lh.charAt(i)^fin_rn.charAt(i);
            }
            //System.out.println("R1:"+left_half+"^"+fin_rn+"="+fin);
            pt_lh=pt_rh;
            pt_rh=fin;
        } 
        String dec_pt_rev;
        dec_pt_rev=pt_rh+pt_lh;
        String dec_pt;
        dec_pt=du.final_Permut(dec_pt_rev);
        System.out.println("Decrypted text in binary:"+dec_pt);
        System.out.println("Decrypted text in hexadecimal:"+du.bin_hex(dec_pt));
        System.out.println("-------------------------------------------------------------");
	}	
}