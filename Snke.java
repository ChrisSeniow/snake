package game;

import org.lwjgl.input.Mouse;
import org.newdawn.slick.*;
import org.newdawn.slick.state.*;

public class Snke extends BasicGameState {

	private int[][] board = new int[30][30];
	private int appleY;
	private int appleX;
	private int range;
	private int max;
	private int move;
	private static boolean over;
	private boolean play;
	private boolean exit;
	private String mouse;

	public Snke(int state) {

	}

	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
		board[16][15] = 1;
		range = (29) + 1;
		appleX = (int) (Math.random() * range);
		appleY = (int) (Math.random() * range);
		max = 1;
		move = 0;
		over = false;
		// mouse = "";
		play = false;
		exit = false;

	}

	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
		// g.drawString(mouse, 10, 40);
		for (int i = 0; i < board.length; i++) {
			for (int j = 0; j < board[i].length; j++) {
				if (i == appleX && j == appleY) {
					// g.drawString("x", j*30, i*30);
					g.setColor(Color.green);
					g.fillOval(j * 20, i * 20, 20, 20);
					// g.drawRect(j*30, i*30, 10, 10);
					g.setColor(Color.white);
				} else if (board[i][j] != 0) {
					// g.drawString("" + board[i][j], j*30, i*30);
					if (board[i][j] == max) {
						//g.setColor(Color.blue);
					}

					g.fillRect(j * 20, i * 20, 20, 20);
					g.setColor(Color.white);
				}

			}
			// System.out.println("");

		}

		if (over == false) {
			g.drawString("Score: " + (max - 1), 500, 50);
		}

		g.setColor(Color.black);
		for (int i = 0; i < 600; i += 20) {
			g.drawLine(i, 600, i, 0);
		}
		for (int i = 0; i < 600; i += 20) {
			g.drawLine(600, i, 0, i);
		}
		g.setColor(Color.white);

		if (over) {
			g.drawString("Game Over", 250, 250);
			g.drawString("Score: " + (max - 1), 252, 270);

			if (play) {
				g.fillRect(50, 450, 150, 60);

				g.drawString("Play Agian", 80, 470);

			} else {
				g.drawRect(50, 450, 150, 60);

				g.drawString("Play Agian", 80, 470);

			}

			if (exit) {
				g.fillRect(390, 450, 150, 60);
				g.drawString("Exit", 450, 470);
			} else {
				g.drawRect(390, 450, 150, 60);
				g.drawString("Exit", 450, 470);
			}
		}
	}

	public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException {
		Input input = gc.getInput();
		int xpos = Mouse.getX();
		int ypos = Mouse.getY();
		// mouse = "Mouse position - x: " + xpos + " y: " + ypos;

		if (over == false) {
			if ((input.isKeyDown(Input.KEY_UP) || input.isKeyDown(Input.KEY_W)) && move != 3) {
				move = 1;
			} else if ((input.isKeyDown(Input.KEY_LEFT) || input.isKeyDown(Input.KEY_A)) && move != 4) {
				move = 2;
			} else if ((input.isKeyDown(Input.KEY_DOWN) || input.isKeyDown(Input.KEY_S)) && move != 1 && move != 0) {
				move = 3;
			} else if ((input.isKeyDown(Input.KEY_RIGHT) || input.isKeyDown(Input.KEY_D)) && move != 2) {
				move = 4;
			}

			if (move == 1) {
				moveUp(board, max);
			} else if (move == 2) {
				moveLeft(board, max);
			} else if (move == 3) {
				moveDown(board, max);
			} else if (move == 4) {
				moveRight(board, max);
			}

			if (board[appleX][appleY] == max) {
				max += 1;
				board[appleX][appleY] = max;

				while (board[appleX][appleY] > 0) {
					appleX = (int) (Math.random() * range);
					appleY = (int) (Math.random() * range);
				}

			}
		}

		if (over) {
			if ((xpos >= 50 && xpos <= 200) && (ypos >= 90 && ypos <= 150)) {
				play = true;
				if (input.isMousePressed(0)) {

					for (int i = 0; i < board.length; i++) {
						for (int j = 0; j < board[i].length; j++) {
							board[i][j] = 0;
						}

					}
					board[16][15] = 1;
					range = (29) + 1;
					appleX = (int) (Math.random() * range);
					appleY = (int) (Math.random() * range);
					max = 1;
					move = 0;
					over = false;
					// mouse = "";
				}
			} else if (input.isKeyDown(Input.KEY_ENTER)) {
				for (int i = 0; i < board.length; i++) {
					for (int j = 0; j < board[i].length; j++) {
						board[i][j] = 0;
					}

				}
				board[16][15] = 1;
				range = (29) + 1;
				appleX = (int) (Math.random() * range);
				appleY = (int) (Math.random() * range);
				max = 1;
				move = 0;
				over = false;
				// mouse = "";
			} else {
				play = false;
			}
			if ((xpos >= 390 && xpos <= 540) && (ypos >= 90 && ypos <= 150)) {
				exit = true;
				if (input.isMousePressed(0)) {
					System.exit(0);
				}
				// }else if(input.isKeyDown(Input.KEY_UP)){

			} else {
				exit = false;
			}

		}

	}

	public int getID() {
		return 0;
	}

	public static void moveUp(int[][] board, int max) {
		for (int i = 0; i < board.length; i++) {
			for (int j = 0; j < board[i].length; j++) {
				if (board[i][j] == 1 || noOne(board)) {
					for (int k = 0; k < board.length; k++) {
						for (int l = 0; l < board[i].length; l++) {

							if (board[k][l] == max) {
								if (k == 0) {
									end();
									return;
								}
								if (board[k - 1][l] > 0) {
									end();
									return;
								}
								board[k - 1][l] = max + 1;

								for (int m = 0; m < board.length; m++) {
									for (int n = 0; n < board[i].length; n++) {

										if (board[m][n] != 0) {
											board[m][n] -= 1;
										}

									}

								}
								return;
							}
						}

					}

				}
			}

		}
	}

	public static void moveDown(int[][] board, int max) {
		for (int i = 0; i < board.length; i++) {
			for (int j = 0; j < board[i].length; j++) {
				if (board[i][j] == 1 || noOne(board)) {

					for (int k = 0; k < board.length; k++) {
						for (int l = 0; l < board[i].length; l++) {

							if (board[k][l] == max) {
								if (k == board.length - 1) {
									end();
									return;
								}
								if (board[k + 1][l] > 0) {
									end();
									return;
								}
								board[k + 1][l] = max + 1;

								for (int m = 0; m < board.length; m++) {
									for (int n = 0; n < board[i].length; n++) {

										if (board[m][n] != 0) {
											board[m][n] -= 1;
										}

									}

								}
								return;
							}
						}

					}

				}
			}

		}
	}

	public static void moveLeft(int[][] board, int max) {
		for (int i = 0; i < board.length; i++) {
			for (int j = 0; j < board[i].length; j++) {
				if (board[i][j] == 1 || noOne(board)) {

					for (int k = 0; k < board.length; k++) {
						for (int l = 0; l < board[i].length; l++) {

							if (board[k][l] == max) {
								if (l == 0) {
									end();
									return;
								}

								if (board[k][l - 1] > 0) {
									end();
									return;
								}
								board[k][l - 1] = max + 1;

								for (int m = 0; m < board.length; m++) {
									for (int n = 0; n < board[i].length; n++) {

										if (board[m][n] != 0) {
											board[m][n] -= 1;
										}

									}

								}
								return;
							}
						}

					}

				}
			}

		}
	}

	public static void moveRight(int[][] board, int max) {
		for (int i = 0; i < board.length; i++) {
			for (int j = 0; j < board[i].length; j++) {
				if (board[i][j] == 1 || noOne(board)) {

					for (int k = 0; k < board.length; k++) {
						for (int l = 0; l < board[i].length; l++) {

							if (board[k][l] == max) {
								if (l == board[i].length - 1) {
									end();
									return;
								}
								if (board[k][l + 1] > 0) {
									end();
									return;
								}
								board[k][l + 1] = max + 1;

								for (int m = 0; m < board.length; m++) {
									for (int n = 0; n < board[i].length; n++) {

										if (board[m][n] != 0) {
											board[m][n] -= 1;
										}

									}

								}
								return;
							}
						}

					}

				}
			}

		}
	}

	public static boolean noOne(int[][] board) {
		for (int i = 0; i < board.length; i++) {
			for (int j = 0; j < board[i].length; j++) {
				if (board[i][j] == 1) {
					return false;
				}
			}

		}
		return true;
	}

	public static void end() {

		over = true;

		// System.exit(1);
	}

}