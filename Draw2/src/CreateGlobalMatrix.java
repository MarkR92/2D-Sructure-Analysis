import java.util.ArrayList;
import Jama.Matrix;




public class CreateGlobalMatrix {
	
	private int dof;  					//degree of freedom determines global matrix size
	private int reduceddof;				//dof after boundary conditions are applied

	private int indexI;
	private int indexJ;
	private int iadjust;
	private int jadjust;
	private Matrix globalStiffness ; //global stiffness  matrix
	private Matrix localStiffness; //local stiffness matrix "blown up" with zeros

	
	private ArrayList<double[][]> localKlist = new ArrayList<double[][]>() ;
	
	//double[][] reducedGlobalStiffness ;
	Matrix reducedGlobalStiffness;
	Matrix reducedGlobalStiffnessInverse;
	
	public CreateGlobalMatrix(int dof) {
		
	    this.dof = dof;
	    globalStiffness= new Matrix(new double[dof][dof]);
	   
	}

	
	public void blowupLocalStiffness( Matrix localkPrime, int[] nodeNumber) {

		localStiffness= new Matrix(new double[dof][dof]); 								//create zero matrix based on dof
		//System.out.println("Local Prime");
		for(int i=0;i<6;i++) {
			
			 indexI = nodeNumber[i]-1;
			
			for(int j=0;j<6;j++) {
				
				indexJ = nodeNumber[j]-1;
			
				localStiffness.getArray()[indexI][indexJ]+= localkPrime.getArray()[i][j];
			}
			
		}


		 globalStiffness.plusEquals(localStiffness);
	
	}
	
	public void reduceglobalStiffness(ArrayList<String> fixtureList) {

		 double[][] K=new double[dof][dof];
		 for(int i=0;i<dof;i++) {
				
				for(int j=0;j<dof;j++) {
					
					K[i][j] += globalStiffness.getArray()[i][j];		
					
					
					}
				
		 }
		
		
		reduceddof=dof;
		
		for(int z=0; z<fixtureList.size();z++) {
			
		if(fixtureList.get(z).matches("Fixed")) {
			
			
			reduceddof = reduceddof-3;
			
		for(int i=0;i<dof;i++) {
			
			for(int j=z*3;j<3*z+3;j++) {
				
				K[i][j]=Double.NaN;
				
			}
			
			}
		
		for(int i=z*3;i<3*z+3;i++) {
			
			for(int j=0;j<dof;j++) {
				
				K[i][j]=Double.NaN;
				//System.out.print(K[i][j] + "  ");
			}
			//System.out.println();
			}
		
		}
		
		
		if(fixtureList.get(z).matches("Pinned")) {	
			
			reduceddof = reduceddof-2;
			
		for(int i=0;i<dof;i++) {
			
			for(int j=z*3;j<3*z+2;j++) {
				
				K[i][j]=Double.NaN;
				
			}
			
			}
		
		for(int i=z*3;i<3*z+2;i++) {
			
			for(int j=0;j<dof;j++) {
				
				K[i][j]=Double.NaN;
				//System.out.print(K[i][j] + "  ");
			}
			//
			
			//System.out.println();
			}
		
		
		}	
		if(fixtureList.get(z).matches("Sliding") ) {
			reduceddof=reduceddof-1;
			//System.out.println("hh");
			
			for(int i=0;i<dof;i++) {
				
				for(int j=1+z*3;j<3*z+2;j++) {
					
					K[i][j]=Double.NaN;
					
				}
				
				}
			
			for(int i=1+z*3;i<3*z+2;i++) {
				
				for(int j=0;j<dof;j++) {
					
					K[i][j]=Double.NaN;
					//System.out.print(K[i][j] + "  ");
				}
				//System.out.println();
				}
		}
		
		}

		
		reducedGlobalStiffness = new Matrix(new double[reduceddof][reduceddof]);
		
		
		for(int i=0;i<dof;i++) {
			for(int j=0;j<dof;j++) {
				//System.out.println(countj);
					if (Double.isNaN(K[i][j])!=true) {
						//System.out.println(count);
					
						reducedGlobalStiffness.getArray()[iadjust][jadjust] += K[i][j];
//					
					jadjust++;
					if(jadjust==reduceddof) {
						jadjust=0;
						iadjust++;	
					}
						}
					
					}
				
			}
		System.out.println("*********Reduced Global Stiffness*********");
		reducedGlobalStiffness.print(reduceddof,1);
//		//counti=0;
//		for(int i=0;i<reduceddof;i++) {
//			for(int j=0;j<reduceddof;j++) {
//				System.out.print(reducedGlobalStiffness.getArray()[i][j] + "  ");
//				
//			}
//		System.out.println();
//		
//			}
	
		System.out.println("********************");
			}
	
	public double[][] getGlobalStiffness(){
		
		

		for(int i=0;i<dof;i++) {
			
			for(int j=0;j<dof;j++) {
								
				
				System.out.print(globalStiffness.getArray()[i][j] + "g  ");
	}
				System.out.println();
		}
		
		return globalStiffness.getArray();
	}
	public double[][] getreducedGlobalStiffness(){
		
		return reducedGlobalStiffness.getArray();
	}
public Matrix getreducedGlobalStiffnessInverse(){
	reducedGlobalStiffnessInverse=reducedGlobalStiffness.inverse();
		return reducedGlobalStiffnessInverse;
	}
	
	public int getReducedDOF() {
		
		return reduceddof;
		
	}
public ArrayList<double[][]> getlocalKlist(){
		
		return localKlist;
		
	}
	
	}

