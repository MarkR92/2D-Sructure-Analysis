// Java program to find adjoint and inverse of a matrix 
import Jama.*;


public class CalculateInvMatrix{ 
      
	private int N=6; 
	private Matrix A;
	private Matrix B;
	

  public void setN(int N) {
	  this.N=N;
	  
  }
// Function to get cofactor of A[p][q] in temp[][]. n is current 
// dimension of A[][] 
static void getCofactor(double A[][], double temp[][], int p, int q, int n){
	
    int i = 0, j = 0; 
  
    for (int row = 0; row < n; row++) 
    { 
        for (int col = 0; col < n; col++) 
        { 
            // Copying into temporary matrix only those element 
            // which are not in given row and column 
            if (row != p && col != q) 
            { 
                temp[i][j++] = A[row][col]; 
  
                // Row is filled, so increase row index and 
                // reset col index 
                if (j == n - 1) 
                { 
                    j = 0; 
                    i++; 
                } 
            } 
        } 
    } 
} 
  
/* Recursive function for finding determinant of matrix. 
n is current dimension of A[][]. */
public double determinant(double A[][], int n) 
{ 
    double D = 0; // Initialize result 
  
    // Base case : if matrix contains single element 
    if (n == 1) 
        return A[0][0]; 
  
    double [][]temp = new double[N][N]; // To store cofactors 
  
    int sign = 1; // To store sign multiplier 
  
    // Iterate for each element of first row 
    for (int f = 0; f < n; f++) 
    { 
        // Getting Cofactor of A[0][f] 
        getCofactor(A, temp, 0, f, n); 
        D += sign * A[0][f] * determinant(temp, n - 1); 
  
        // terms are to be added with alternate sign 
        sign = -sign; 
    } 
  
    return D; 
} 
  
// Function to get adjoint of A[N][N] in adj[N][N]. 
public void adjoint(double A[][],double [][]adj) {
	//double [][]adj = new double[6][6];
    if (N == 1) 
    { 
        adj[0][0] = 1; 
        return; 
    } 
  
    // temp is used to store cofactors of A[][] 
    int sign = 1; 
    double [][]temp = new double[N][N]; 
  
    for (int i = 0; i < N; i++) 
    { 
        for (int j = 0; j < N; j++) 
        { 
            // Get cofactor of A[i][j] 
            getCofactor(A, temp, i, j, N); 
  
            // sign of adj[j][i] positive if sum of row 
            // and column indexes is even. 
            sign = ((i + j) % 2 == 0)? 1: -1; 
  
            // Interchanging rows and columns to get the 
            // transpose of the cofactor matrix 
            adj[j][i] = (sign)*(determinant(temp, N-1)); 
        } 
    } 
} 
  
// Function to calculate and store inverse, returns false if 
// matrix is singular 
public boolean inverse(double A[][], double [][]inverse){ 
    // Find determinant of A[][] 
    double det = determinant(A, N); 
    if (det == 0) 
    { 
        System.out.print("Singular matrix, can't find its inverse"); 
        return false; 
    } 
  
    // Find adjoint 
    double [][]adj = new double[N][N]; 
    adjoint(A, adj); 
  
    // Find Inverse using formula "inverse(A) = adj(A)/det(A)" 
    for (int i = 0; i < N; i++) 
        for (int j = 0; j < N; j++) 
            inverse[i][j] = adj[i][j]/det; 
  
    return true; 
} 
  
// Generic function to display the matrix. We use it to display 
// both adjoin and inverse. adjoin is integer matrix and inverse 
// is a float. 
  
public void display(double A[][]) 
{ 
    for (int i = 0; i < N; i++) 
    { 
        for (int j = 0; j < N; j++) 
            System.out.print(A[i][j]+ " "); 
        System.out.println(); 
    } 
} 
//public void display(double A[][]) 
//{ 
//    for (int i = 0; i < N; i++) 
//    { 
//        for (int j = 0; j < N; j++) 
//            System.out.printf("%.6f ",A[i][j]); 
//        System.out.println(); 
//    } 
//} 
  
// Driver program 
//public static void main(String[] args) 
//{ 
////    double A[][] = { {0, 1, 0, 0,0,0}, 
////                    {-1, 0, 0, 0,0,0}, 
////                    {0, 0, 1, 0,0,0}, 
////                    {0, 0, 0, 0,1,0},
////                    {0, 0, 0, -1,0,0},
////                    {0, 0, 0, 0,0,1}
////                    
////    }; 
//  
//   // int [][]adj = new int[N][N]; // To store adjoint of A[][] 
//  
//  //  float [][]inv = new float[N][N]; // To store inverse of A[][] 
//  
//   // System.out.print("Input matrix is :\n"); 
//   // display(A); 
//  
//   // System.out.print("\nThe Adjoint is :\n"); 
//   // adjoint(A, adj); 
//   // display(adj); 
//  
//  //  System.out.print("\nThe Inverse is :\n"); 
//   // if (inverse(A, inv)) 
//     //   display(inv); 
//  
//} 
} 
  