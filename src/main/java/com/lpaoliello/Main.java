package com.lpaoliello;

public class Main {

	private static final int[] dir_i = {-1, 0, 1};
	private static final int[] dir_j = {1, 1, 1};
	
	private static final int CANT_FILAS = 8;
	private static final int CANT_COLUMNAS = 8;

	public static void main(String[] args) {
		boolean[][] tablero = new boolean[CANT_COLUMNAS][CANT_FILAS];
		boolean[][] casillerosBloqueados = new boolean[8][8];

		encontrarPosicionReinas(tablero, casillerosBloqueados, 0, 0);
	}

	private static boolean encontrarPosicionReinas(boolean[][] tablero, boolean[][] casillerosBloqueados, int filaElegida, int columnaElegida) {
		boolean flag = false;
		
		for(int i = columnaElegida; i < CANT_COLUMNAS; i++) {			
			if(esCasilleroLibre(casillerosBloqueados, filaElegida, i)) {
				tablero[filaElegida][i] = true;
				
				/*Si encuentro una solucion, limpio para seguir buscando mas soluciones.*/
				if(filaElegida == CANT_FILAS - 1) {
					printSolucion(tablero);
					limpiarSolucionPretendida(tablero, casillerosBloqueados, filaElegida, i);
				} else {
					if(!encontrarPosicionReinas(tablero, casillerosBloqueados, filaElegida + 1, columnaElegida)) {
						limpiarSolucionPretendida(tablero, casillerosBloqueados, filaElegida, i);
					} else {
						flag = true;
					};
				}		
			}
		}
		
		return flag;
	}

	private static void limpiarSolucionPretendida(boolean[][] tablero, boolean[][] casillerosBloqueados,
			int filaElegida, int i) {
		tablero[filaElegida][i] = false;
		regenerarCasillerosBloqueados(tablero, casillerosBloqueados);
	}

	private static void printSolucion(boolean[][] tablero) {
		for(boolean fila[] : tablero) {
			for(boolean columna : fila) {
				System.out.print(columna + " " );
			}
			System.out.println("");
		}
		
	}

	private static void regenerarCasillerosBloqueados(boolean[][] tablero, boolean[][] casillerosBloqueados) {
		limpiarCasillerosBloqueados(casillerosBloqueados);
		
		for(int i = 0; i < CANT_FILAS; i++) {
			for(int j = 0; j < CANT_COLUMNAS; j++) {
				if(tablero[i][j] == true) {
					bloquearProximosCasilleros(casillerosBloqueados, i, j);
				}
			}
		}
	}

	private static void limpiarCasillerosBloqueados(boolean[][] casillerosBloqueados) {
		for(int i = 0; i < CANT_FILAS; i++) {
			for(int j = 0; j < CANT_COLUMNAS; j++) {
				casillerosBloqueados[i][j] = false;
			}
		}
	}

	private static boolean esCasilleroLibre(boolean[][] casillerosBloqueados, int filaElegida,
			int columnaElegida) {

		boolean existen = false;
		
		if(posValida(columnaElegida, filaElegida) && casillerosBloqueados[filaElegida][columnaElegida] == false) {
			bloquearProximosCasilleros(casillerosBloqueados, filaElegida, columnaElegida);
			existen = true;
		}

		return existen;
	}

	private static void bloquearProximosCasilleros(boolean[][] casillerosBloqueados, int filaElegida, int columnaElegida) {
		
		for(int pos = 0; pos < dir_i.length; pos++) {
			int pos_i = dir_i[pos];
			int pos_j = dir_j[pos];
						
			bloquearProxCasillerosDireccionados(pos_i, pos_j, filaElegida, columnaElegida, casillerosBloqueados);			
		}
		
	}

	private static void bloquearProxCasillerosDireccionados(int pos_i, int pos_j, int filaElegida, int columnaElegida,
			boolean[][] casillerosBloqueados) {
		
		int bloquear_pos_i = pos_i + columnaElegida;
		int bloquear_pos_j = pos_j + filaElegida;
		
		while(posValida(bloquear_pos_i, bloquear_pos_j)) {
			casillerosBloqueados[bloquear_pos_j][bloquear_pos_i] = true;

			bloquear_pos_i = pos_i + bloquear_pos_i;
			bloquear_pos_j = pos_j + bloquear_pos_j;
		}
	}

	private static boolean posValida(int bloquear_pos_i, int bloquear_pos_j) {
		boolean posValida = false;
		if(bloquear_pos_i >= 0 && bloquear_pos_j >= 0
				&& bloquear_pos_i < CANT_COLUMNAS && bloquear_pos_j < CANT_FILAS)
			posValida = true;
		return posValida;
	}
}
