package tictactoe;

import java.util.Scanner;

public class TicTacToe {

	public static void main(String[] args) {
		UI_Menu();
	}
	

	private static void UI_Menu() {

		
		int sel = 666;
        Scanner userSel = new Scanner(System.in);
		
		while(true)
        {
            System.out.println(" |**********************| ");
            System.out.println(" |****     MENU     ****| ");
            System.out.println(" |**********************| ");
            System.out.println(" 1. player vs computer"); 
            System.out.println(" 2. player1 vs player2  "); 
            System.out.println(" 3. Rules  "); 

            System.out.println(" 100. Exit ");
            System.out.println("....................");
            System.out.println("Make your choice!");

            try {
                sel = userSel.nextInt();
            } catch (Exception e) {
                System.out.println("bad input");
                userSel.next();
            }

            //exit
            if(sel == 100) {
            	System.out.println("-Program ended----------");
            	userSel.close();
            	break;
            }

            switch (sel) {
                case 1 -> {
                	System.out.println("Player vs computer started!");
                	System.out.println("Select \"99\" for main menu and ending the game ");
                	gameController(1);
                }

                
                case 2 -> {
                	System.out.println("Player1 vs Player2 started!");
                	System.out.println("Select \"99\" for main menu and ending the game ");
                	gameController(2);
                }
                
                case 3 -> {
                	System.out.println("**********RULES***********");
                	System.out.println("First player to reach 3 in a row wins");
                	System.out.println("Player1 moves are marked with \" O\"");
                	System.out.println("Player2 moves are marked with \" X\"");
                	System.out.println("Computer moves are marked with \" X\"");
                	System.out.println("Select \"99\" for main menu and ending the game " + '\n' + '\n');
                }
                

                default -> System.out.println("No valid selection");
            }
           }
		
		
	}

	private static void gameController(int mode) {

        Scanner userSel = new Scanner(System.in);
        int Xpos = 99, Ypos = 99;
        int _mode = mode;
        String playerName = null;
        String gameEngineResult = "";
        
        String[][] board = { { "____", "____" , "____"}, { "____", "____" , "____"}, { "____", "____" , "____"} };
		
		while(true)
        {
			if (_mode == 2) {
				if (!gameEngineResult.equals("samePlayer")){
					if (playerName==null) playerName = "Player1";
					else if (playerName.equals("Player1")) playerName = "Player2";
					else if (playerName.equals("Player2")) playerName = "Player1";
				 }
				
				System.out.println(playerName + ", make your move!");
		        UI_drawGameBoard(board);
		        
				
				System.out.println("COL: ");
				try {
					Ypos = userSel.nextInt();
				} catch (Exception e) {
					System.out.println("bad input");
				}
				if (Ypos==99) UI_Menu();

				
				System.out.println("ROW: ");
				try {
					Xpos = userSel.nextInt();
				} catch (Exception e) {
					System.out.println("bad input");
				}			
				if (Xpos==99) UI_Menu();
				
				
				gameEngineResult = gameEngine(_mode,board, Xpos, Ypos, playerName);
				if (!gameEngineResult.equals("") && !gameEngineResult.equals("samePlayer")) {
					System.out.println("Winner is " + gameEngineResult);
					UI_drawGameBoard(board);
			        
			        try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						
						e.printStackTrace();
					}
			          
			        UI_Menu();
				}
			}else if (_mode == 1) {
				if (!gameEngineResult.equals("samePlayer")){
					if (playerName==null) playerName = "Player1";
					else if (playerName.equals("Player1")) playerName = "CPU";
					else if (playerName.equals("CPU")) playerName = "Player1";
				}
				
				System.out.println(playerName + ", make your move!");
		        UI_drawGameBoard(board);
		        
				if (playerName.equals("Player1")) {
					System.out.println("COL: ");
					try {
						Ypos = userSel.nextInt();
					} catch (Exception e) {
						System.out.println("bad input");
					}
					if (Ypos==99) UI_Menu();

					
					System.out.println("ROW: ");
					try {
						Xpos = userSel.nextInt();
					} catch (Exception e) {
						System.out.println("bad input");
					}			
					if (Xpos==99) UI_Menu();					
				} else if (playerName.equals("CPU")) {
					int[] posArr = CPU_selectPos(board);
					Xpos = posArr[0];
					Ypos = posArr[1];
				}
				
				
				gameEngineResult = gameEngine(_mode,board, Xpos, Ypos, playerName);
				if (!gameEngineResult.equals("") && !gameEngineResult.equals("samePlayer")) {
					System.out.println("Winner is " + gameEngineResult);
					UI_drawGameBoard(board);
			        
			        try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						
						e.printStackTrace();
					}
			          
			        UI_Menu();
				}
			}
			
			
				
           }
	}
	
	private static void UI_drawGameBoard(String[][] board) {
		System.out.println("________________");
        System.out.println("|"+board[0][0]+"|"+board[0][1]+"|"+board[0][2]+"|");
        System.out.println("|"+board[1][0]+"|"+board[1][1]+"|"+board[1][2]+"|");
        System.out.println("|"+board[2][0]+"|"+board[2][1]+"|"+board[2][2]+"|");
	}

	public static String gameEngine(int mode,String[][] board, int Xpos, int Ypos, String playerName) {		
		String playerSign = "";
		
		if (playerName.equals("Player1")) playerSign = "_O__";
		else playerSign = "_X__";
		
		if (collissionDetection(board, Xpos, Ypos)) {
			board[Xpos][Ypos] = playerSign;
			if (!checkWinner(mode, board, playerSign).equals(""))
				return checkWinner(mode, board, playerSign);
		} else
			return "samePlayer";
		
		return "";
	}

	public static int[] CPU_selectPos(String[][] board) {
		
		for (int i = 0; i < board.length; i++) {
			for (int j = 0; j < board.length; j++) {
				if (board[i][j].equals("____")) {
					return new int[] {i,j};
				}
			}
		}
		
		return new int[] {99,99};
	}
	
	public static boolean collissionDetection(String[][] board, int Xpos, int Ypos) {
		
		boolean returnStatus = false;
		
		if (Xpos >= board.length || Ypos >= board.length) {
			System.out.println("Outside borders not possible");
			
		}
			
		else if ((board[Xpos][Ypos] != "_O__") && (board[Xpos][Ypos] != "_X__")) {
			//System.out.println(" No collision");
			returnStatus = true;
		}
			
		else {
			System.out.println(" Collision");
			returnStatus = false;
		}
			
		
		return returnStatus;
	}
	
	public static String checkWinner(int mode, String[][] board, String playerSign) {
		
		
		if (!checkVertical(board, playerSign, mode).equals(""))
			return checkVertical(board, playerSign, mode);
		else if (!checkHorizontal(board, playerSign, mode).equals(""))
			return checkHorizontal(board, playerSign, mode);
		else if	(!checkDiagonal(board, playerSign, mode).equals(""))
			return checkDiagonal(board, playerSign, mode);
		else if (!checkNoWinner(board).equals(""))
			return checkNoWinner(board);
		else 
			return "";
	}

	public static String checkVertical(String[][] board, String sign, int mode) {
		
		//possible wins
		//[0][0]+[0][1]+[0][2]
		//[1][0]+[1][1]+[1][2]
		//[2][0]+[2][1]+[2][2]
		
		if (
				(board[0][0].equals(sign) && board[0][1].equals(sign) && board[0][2].equals(sign)) ||
				(board[1][0].equals(sign) && board[1][1].equals(sign) && board[1][2].equals(sign)) ||
				(board[2][0].equals(sign) && board[2][1].equals(sign) && board[2][2].equals(sign))
			) 
		{
			return signCheck(sign, mode);
			/*
			 * System.out.println("Winner is " + signCheck(sign, mode)); try {
			 * Thread.sleep(1000); } catch (InterruptedException e) { // TODO Auto-generated
			 * catch block e.printStackTrace(); } UI_Menu();
			 */
		}
		return "";
			
				
	}
	public static String checkHorizontal(String[][] board, String sign, int mode) {
		//possible wins
		//[0][0]+[1][0]+[2][0]
		//[0][1]+[1][1]+[2][1]
		//[0][2]+[1][2]+[2][2]
		
		if (
				(board[0][0].equals(sign) && board[1][0].equals(sign) && board[2][0].equals(sign)) ||
				(board[0][1].equals(sign) && board[1][1].equals(sign) && board[2][1].equals(sign)) ||
				(board[0][2].equals(sign) && board[1][2].equals(sign) && board[2][2].equals(sign))
			)
		{
			return signCheck(sign, mode);
			/*
			 * System.out.println("Winner is " + signCheck(sign, mode)); try {
			 * Thread.sleep(1000); } catch (InterruptedException e) { // TODO Auto-generated
			 * catch block e.printStackTrace(); } UI_Menu();
			 */
		}
		return "";
	}
	public static String checkDiagonal(String[][] board, String sign, int mode) {
		//possible wins
		//[0][0]+[1][1]+[2][2]
		//[2][0]+[1][1]+[0][2]
		
		if (
				(board[0][0].equals(sign) && board[1][1].equals(sign) && board[2][2].equals(sign)) ||
				(board[2][0].equals(sign) && board[1][1].equals(sign) && board[0][2].equals(sign))
			)
		{
			return signCheck(sign, mode);
			/*
			 * System.out.println("Winner is " + signCheck(sign, mode)); try {
			 * Thread.sleep(1000); } catch (InterruptedException e) { // TODO Auto-generated
			 * catch block e.printStackTrace(); } UI_Menu();
			 */
		}
		return "";
	
	}
	public static String checkNoWinner(String[][] board) {
		for (int i = 0; i < board.length; i++) {
			for (int j = 0; j < board.length; j++) {
				if (board[i][j].equals("____")) {
					return "";
				}	
			}
		}
		//System.out.println("No winner");
		return "Draw";
	}
	public static String signCheck(String playerSign, int mode) {
		
		if (playerSign.equals("_O__")) return "Player1";
		else if (playerSign.equals("_X__") && mode == 2) return "Player2";
		else if (playerSign.equals("_X__") && mode == 1) return "CPU";
		else return "NoName";
		
	}

}
