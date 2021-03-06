/*********************************************************************
 * Maxeler Technologies: Smith Waterman Demo                         *
 *                                                                   *
 * Version: 1.3                                                      *
 * Date:    30 July 2013                                             *
 *                                                                   *
 * GUI code source file                                              *
 *                                                                   *
 *********************************************************************/

package com.maxeler.publicdemos.smithwaterman.gui;

public class ScoringMatrix {
	public static enum Type{
		BLOSUM62,
		DNA
	}
	private int match;
	private int mismatch;
	
	private Type type = Type.BLOSUM62;
	
	public Type getType(){
		return type;
	}
	//Translates shifted characters (A=0, Z=25) into scoring matrix index entry 
	//BLOSUM62 : A is index0, R is index 1, N is index 2...
	//DNA : A is index 0, C is index 1, G is 2, T is 3, U is 4 and the rest are possible combinations
	private int translation[][]={
		//   A,  B, C,  D,  E,   F, G,  H,  I,   J,  K,  L,  M, N,  O,  P, Q, R,  S,  T,  U,  V,  W,  X,  Y,  Z, padding
			{0,	20,	4,	3,	6,	13,	7,	8,	9,	23,	11,	10,	12,	2, 23, 14, 5, 1, 15, 16, 23, 19, 17, 22, 18, 21, 23},
			{0, 14, 1, 11, 15,  15, 2, 12, 15,  15, 10, 15,  7,15, 15, 15,15, 5,  9,  3,  4, 13,  8, 15,  6, 15, 15}
	};
	private int tableWidth[] = {24,16};
	public int getTableWidth(){
		return tableWidth[type.ordinal()];
	}
	
	//Scoring matrix (BLOSUM62 and dna)
	private int[][] matrix = {
			{
			 4,-1,-2,-2, 0,-1,-1, 0,-2,-1,-1,-1,-1,-2,-1, 1, 0,-3,-2, 0,-2,-1, 0,-4,
			-1, 5, 0,-2,-3, 1, 0,-2, 0,-3,-2, 2,-1,-3,-2,-1,-1,-3,-2,-3,-1, 0,-1,-4,
			-2, 0, 6, 1,-3, 0, 0, 0, 1,-3,-3, 0,-2,-3,-2, 1, 0,-4,-2,-3, 3, 0,-1,-4,
			-2,-2, 1, 6,-3, 0, 2,-1,-1,-3,-4,-1,-3,-3,-1, 0,-1,-4,-3,-3, 4, 1,-1,-4,
			 0,-3,-3,-3, 9,-3,-4,-3,-3,-1,-1,-3,-1,-2,-3,-1,-1,-2,-2,-1,-3,-3,-2,-4,
			-1, 1, 0, 0,-3, 5, 2,-2, 0,-3,-2, 1, 0,-3,-1, 0,-1,-2,-1,-2, 0, 3,-1,-4,
			-1, 0, 0, 2,-4, 2, 5,-2, 0,-3,-3, 1,-2,-3,-1, 0,-1,-3,-2,-2, 1, 4,-1,-4,
			 0,-2, 0,-1,-3,-2,-2, 6,-2,-4,-4,-2,-3,-3,-2, 0,-2,-2,-3,-3,-1,-2,-1,-4,
			-2, 0, 1,-1,-3, 0, 0,-2, 8,-3,-3,-1,-2,-1,-2,-1,-2,-2, 2,-3, 0, 0,-1,-4,
			-1,-3,-3,-3,-1,-3,-3,-4,-3, 4, 2,-3, 1, 0,-3,-2,-1,-3,-1, 3,-3,-3,-1,-4,
			-1,-2,-3,-4,-1,-2,-3,-4,-3, 2, 4,-2, 2, 0,-3,-2,-1,-2,-1, 1,-4,-3,-1,-4,
			-1, 2, 0,-1,-3, 1, 1,-2,-1,-3,-2, 5,-1,-3,-1, 0,-1,-3,-2,-2, 0, 1,-1,-4,
			-1,-1,-2,-3,-1, 0,-2,-3,-2, 1, 2,-1, 5, 0,-2,-1,-1,-1,-1, 1,-3,-1,-1,-4,
			-2,-3,-3,-3,-2,-3,-3,-3,-1, 0, 0,-3, 0, 6,-4,-2,-2, 1, 3,-1,-3,-3,-1,-4,
			-1,-2,-2,-1,-3,-1,-1,-2,-2,-3,-3,-1,-2,-4, 7,-1,-1,-4,-3,-2,-2,-1,-2,-4,
			 1,-1, 1, 0,-1, 0, 0, 0,-1,-2,-2, 0,-1,-2,-1, 4, 1,-3,-2,-2, 0, 0, 0,-4,
			 0,-1, 0,-1,-1,-1,-1,-2,-2,-1,-1,-1,-1,-2,-1, 1, 5,-2,-2, 0,-1,-1, 0,-4,
			-3,-3,-4,-4,-2,-2,-3,-2,-2,-3,-2,-3,-1, 1,-4,-3,-2,11, 2,-3,-4,-3,-2,-4,
			-2,-2,-2,-3,-2,-1,-2,-3, 2,-1,-1,-2,-1, 3,-3,-2,-2, 2, 7,-1,-3,-2,-1,-4,
			 0,-3,-3,-3,-1,-2,-2,-3,-3, 3, 1,-2, 1,-1,-2,-2, 0,-3,-1, 4,-3,-2,-1,-4,
			-2,-1, 3, 4,-3, 0, 1,-1, 0,-3,-4, 0,-3,-3,-2, 0,-1,-4,-3,-3, 4, 1,-1,-4,
			-1, 0, 0, 1,-3, 3, 4,-2, 0,-3,-3, 1,-1,-3,-1, 0,-1,-3,-2,-2, 1, 4,-1,-4,
			 0,-1,-1,-1,-2,-1,-1,-1,-1,-1,-1,-1,-1,-1,-2, 0, 0,-2,-1,-1,-1,-1,-1,-4,
			-4,-4,-4,-4,-4,-4,-4,-4,-4,-4,-4,-4,-4,-4,-4,-4,-4,-4,-4,-4,-4,-4,-4, 1
			},
			{
		     5, -4, -4, -4, -4,  2, -1,  2,  2, -1, -1,  1,  1,  1, -2, -1, //X same as N to keep a 16x16 matrix (see dna.mat from fasta)
		    -4,  5, -4, -4, -4, -1,  2,  2, -1,  2, -1, -2,  1,  1,  1, -1,
		    -4, -4,  5, -4, -4,  2, -1, -1, -1,  2,  2,  1, -2,  1,  1, -1,
		    -4, -4, -4,  5,  5, -1,  2, -1,  2, -1,  2,  1,  1, -2,  1, -1,
		    -4, -4, -4,  5,  5, -1,  2, -1,  2, -1,  2,  1,  1, -2,  1, -1,
		     2, -1,  2, -1, -1,  2, -2, -1,  1,  1,  1,  1, -1,  1, -1, -1,
		    -1,  2, -1,  2,  2, -2,  2, -1,  1,  1,  1, -1,  1, -1,  1, -1,
		     2,  2, -1, -1, -1, -1, -1,  2,  1,  1, -1, -1,  1,  1, -1, -1,
		     2, -1, -1,  2,  2,  1,  1,  1,  2, -1,  1,  1,  1, -1, -1, -1,
		    -1,  2,  2, -1, -1,  1,  1,  1, -1,  2,  1, -1, -1,  1,  1, -1,
		    -1, -1,  2,  2,  2,  1,  1, -1,  1,  1,  2,  1, -1, -1,  1, -1,
		     1, -2,  1,  1,  1,  1, -1, -1,  1, -1,  1,  1, -1, -1, -1, -1,
		     1,  1, -2,  1,  1, -1,  1,  1,  1, -1, -1, -1,  1, -1, -1, -1,
		     1,  1,  1, -2, -2,  1, -1,  1, -1,  1, -1, -1, -1,  1, -1, -1,
		    -2,  1,  1,  1,  1, -1,  1, -1, -1,  1,  1, -1, -1, -1,  1, -1,
		    -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1
			}

	};
	public int[] getMatrix(){
		return matrix[type.ordinal()];
	}
	
	//Constructor
	public ScoringMatrix(Type typeMatrix, int match, int mismatch){
		this.type = typeMatrix;
		this.match = match;
		this.mismatch = mismatch;
	}
	
	//Set the type (DNA or BLOSUM62)
	public void setType(Type typeMatrix){
		this.type = typeMatrix;
	}
	
	//Get the substition score S(s,t)
	public int getScore(char s, char t){
		int res = 0;
		char S = Character.toUpperCase(s);
		char T = Character.toUpperCase(t);
		
		int asciiA = (int)'A';
		int asciiStar = (int)'Z'+1-(int)'A';
		int asciiT = T=='*' ? asciiStar : ((int)T) - asciiA; 
		int asciiS = S=='*' ? asciiStar : ((int)S) - asciiA;
		
		if(asciiT>=0 && asciiT <=26 && asciiS>=0 && asciiS <=26){
			res = matrix[type.ordinal()][ translation[type.ordinal()][asciiT]*tableWidth[type.ordinal()] + translation[type.ordinal()][asciiS] ]; 
		}
		return res;
	}
	
	
}
